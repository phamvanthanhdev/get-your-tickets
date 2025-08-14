package com.getyourtickets.implement;

import com.getyourtickets.dto.jwt.VerifyRequest;
import com.getyourtickets.dto.logout.LogoutRequest;
import com.getyourtickets.dto.refresh.RefreshRequest;
import com.getyourtickets.dto.refresh.RefreshResponse;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.getyourtickets.exception.ErrorEnum;
import com.getyourtickets.exception.GytException;
import com.getyourtickets.mapper.LogoutMapper;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.Permission;
import com.getyourtickets.model.Role;
import com.getyourtickets.model.User;
import com.getyourtickets.service.AuthenticationService;
import com.getyourtickets.service.PermissionService;
import com.getyourtickets.service.RoleService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final LogoutMapper logoutMapper;

    @Value("${jwt.signer-key}")
    protected String signerKey;
    @Value("${jwt.expiration}")
    protected long expiration;


    @Override
    public boolean verifyToken(String token) throws JOSEException, ParseException {
        try {
            if (this.isValidToken(token)) {
                SignedJWT signedJWT = SignedJWT.parse(token);
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                String username = claimsSet.getSubject();
                User user = userMapper.getUserByUsername(username);
                if (user != null) {
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    @Override
    public UserLoginResponse generateToken(UserLoginRequest request) {
        User user = userMapper.getUserByUsername(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = generateToken(user);
            return UserLoginResponse.builder().token(token).build();
        }

        return null;
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.getyourtickets")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(expiration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", getRoleNamesByUser(user.getId()))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        MACSigner signer = null;
        try {
            signer = new MACSigner(signerKey.getBytes());
            jwsObject.sign(signer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    private String getRoleNamesByUser(int userId) {
        List<Role> roles = roleService.getRolesByUserId(userId);
        StringJoiner stringJoiner = new StringJoiner(" ");

        for (Role role : roles) {
            stringJoiner.add("ROLE_" + role.getName());
            List<Permission> permissions = permissionService.getPermissionsByRoleId(Long.valueOf(role.getId()));
            for (Permission permission : permissions) {
                stringJoiner.add(permission.getName());
            }
        }
        return stringJoiner.toString();
    }


    @Override
    public void logout(LogoutRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        if (isValidToken(token)) {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            logoutMapper.insertLogout(buildMapInsertLogout(claimsSet, token));
        }
    }

    private boolean isValidToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if (signedJWT.verify(verifier)
                && claimsSet.getExpirationTime().after(new Date())) {
            if (logoutMapper.getLogoutByJwtId(claimsSet.getJWTID()) != null) {
                return false;
            }
            return true;
        } else {
            throw new GytException(ErrorEnum.AUTHENTICATION_FAILED);
        }
    }


    @Override
    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        if (this.isValidToken(token)) {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            logoutMapper.insertLogout(buildMapInsertLogout(claimsSet, token));

            String username = claimsSet.getSubject();
            User user = userMapper.getUserByUsername(username);

            String newToken = this.generateToken(user);

            return RefreshResponse.builder().token(newToken).build();
        } else {
            throw new GytException(ErrorEnum.AUTHENTICATION_FAILED);
        }
    }

    private Map<String, Object> buildMapInsertLogout(JWTClaimsSet claimsSet, String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("jwtId", claimsSet.getJWTID());
        map.put("token", token);
        Date expirationDate = new Date(claimsSet.getExpirationTime().getTime());
        map.put("expiredTime", expirationDate);
        return map;
    }
}

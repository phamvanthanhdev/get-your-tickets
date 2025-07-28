package com.getyourtickets.implement;

import com.getyourtickets.dto.jwt.VerifyRequest;
import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.dto.userlogin.UserLoginResponse;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.User;
import com.getyourtickets.service.AuthenticationService;
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
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserMapper userMapper;

    @Value("${jwt.signer-key}")
    protected String signerKey;
    @Value("${jwt.expiration}")
    protected long expiration;


    @Override
    public boolean verifyToken(VerifyRequest request) throws JOSEException, ParseException {
        JWSVerifier verifier =new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        if (signedJWT.verify(verifier) && claimsSet.getExpirationTime().after(new Date())) {
            String username = claimsSet.getSubject();
            User user = userMapper.getUserByUsername(username);
            if (user != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public UserLoginResponse generateToken(UserLoginRequest request) {
        User user = userMapper.getUserByUsername(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("com.getyourtickets")
                    .issueTime(new Date())
                    .expirationTime(new Date(Instant.now().plus(expiration, ChronoUnit.SECONDS).toEpochMilli()))
                    .claim("roles", "ROLE_USER")
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
            String token = jwsObject.serialize();
            return UserLoginResponse.builder().token(token).build();
        }

        return null;
    }


}

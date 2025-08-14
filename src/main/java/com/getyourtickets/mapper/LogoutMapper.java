package com.getyourtickets.mapper;

import com.getyourtickets.model.Logout;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LogoutMapper {
    Logout getLogoutByJwtId(String jwtId);
    void insertLogout(Map<String, Object> map);
}

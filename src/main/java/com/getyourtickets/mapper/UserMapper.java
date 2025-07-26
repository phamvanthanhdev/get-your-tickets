package com.getyourtickets.mapper;

import com.getyourtickets.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    void insertUser(Map<String, Object> map);
}

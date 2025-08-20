package com.getyourtickets.mapper;

import com.getyourtickets.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User getUserByUsername(String username);
    int insertUser(Map<String, Object> map);
    User getUserById(int id);
    List<User> getAllUsers();
}

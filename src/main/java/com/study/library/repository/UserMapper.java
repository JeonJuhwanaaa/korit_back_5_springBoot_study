package com.study.library.repository;


import com.study.library.entity.RoleRegister;
import com.study.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


// 호출하면 .xml 로 매소드 명이랑 같은 것이 실행
@Mapper
public interface UserMapper {
    public User findUserByUsername(String username);
    public int saveUser(User user);
    public RoleRegister findRoleRegisterByUserIdAndRoleId(@Param("userId") int userId, @Param("roleId") int roleId);
    // .xml 에 쓰이는 키값
    public int saveRole(@Param("userId") int userId, @Param("roleId") int roleId);
}

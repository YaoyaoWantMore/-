package com.hao.mapper;

import com.hao.pojo.StudentUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface userMapper {

    public StudentUser getUserByOpenId(String openid);

    public int addUser(StudentUser user);

    public int deleteUserById(int id);

    public int updateUser(StudentUser user);

}

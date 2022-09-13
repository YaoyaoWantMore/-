package com.hao.mapper;

import com.hao.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface adminMapper {
    public int changeName(@Param("name") String name);
    public int changePwd(@Param("password") String password);
    public Admin queryByName(@Param("name") String name);
}

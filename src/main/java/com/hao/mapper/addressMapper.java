package com.hao.mapper;

import com.hao.pojo.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface addressMapper {

    public List<Address> getAddressByUsername(String username);

    public int addAddress(Address address);

    public int deleteAddressById(int id);

    public int updateAddress(Address address);



}

package com.hao.service;

import com.hao.pojo.Address;
import org.springframework.stereotype.Service;

import java.util.List;

public interface addressService {
    public List<Address> getAllAddress(String username);


    public int addAddress(Address address);

    public int deleteAddressById(int id);

    public int updateAddress(Address address);

    public void SetStatus(String username);


}

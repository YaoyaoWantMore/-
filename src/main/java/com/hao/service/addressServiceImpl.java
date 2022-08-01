package com.hao.service;

import com.hao.mapper.addressMapper;
import com.hao.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class addressServiceImpl implements addressService{

    @Autowired
    private addressMapper mapper;


    @Override
    public List<Address> getAllAddress(String username) {
        return mapper.getAddressByUsername(username);
    }


    @Override
    public int addAddress(Address address) {
        return mapper.addAddress(address);
    }

    @Override
    public int deleteAddressById(int id) {
        return mapper.deleteAddressById(id);
    }

    @Override
    public int updateAddress(Address address) {
        return mapper.updateAddress(address);
    }

    @Override
    public void SetStatus(String username) {
        List<Address> addresses = mapper.getAddressByUsername(username);
        for (Address address : addresses) {
            if(address.getStatus()==1){
                address.setStatus(0);
                mapper.updateAddress(address);
            }
        }


    }
}

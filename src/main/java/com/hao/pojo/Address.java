package com.hao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String address_detail;
    private String username;
    private int status;

}

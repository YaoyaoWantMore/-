package com.hao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUser {
    private String name;
    private String phone;
    private String useraddress;
    private long datetimesingle;
}

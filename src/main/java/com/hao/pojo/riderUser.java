package com.hao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class riderUser {
    private int id;
    private String name;
    private String phone;
    private String studentId;
    private String college;
    private double moneyPackage;
    private int orderNum;
    private String openid;
    private int status;

}

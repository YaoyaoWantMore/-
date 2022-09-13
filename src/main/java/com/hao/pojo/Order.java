package com.hao.pojo;

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private String order_id;
    private String user_name;
    private String user_phone;
    private String user_address;
    private String order_price;
    private String order_time;
    private String order_address;
    private String order_detail;
    private String openid;
    private String rider;
    private int status;
}

package com.hao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack {
    private int id;
    private int question_type;
    private String description;
    private String openid;
    private String contact;
    private String contact_type;
    private int status;
    private String order_id;

}

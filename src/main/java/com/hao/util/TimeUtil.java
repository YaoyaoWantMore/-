package com.hao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String Todate(String str){
        if(!str.equals("undefined")){

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH : mm : ss ");
            String dates = format1.format(new Date(Long.parseLong(str)));
            return dates;

        }else{
            return "no date";
        }
    }
}

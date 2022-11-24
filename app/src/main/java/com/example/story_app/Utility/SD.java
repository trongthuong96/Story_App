package com.example.story_app.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SD {
    // role
    public static final String Role_User_Indi = "Individual";
    public static final String Role_Maneger_User = "Maneger";
    public static final String Role_Admin = "Admin";

    //change format date
    public static Date ChangeFomatDate(String date){
        SimpleDateFormat dateFormatParse = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String targetDate = date;
        Date dateString = null;
        try {
            dateString = dateFormatParse.parse(targetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        };
        return dateString;
    }
}

package com.example.mnote.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.mnote.Activitiys.HomePage;
import com.example.mnote.Activitiys.MainActivity;

import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetTime;
import java.util.*;

public class Util
{
    public static String getDateAndTime(){
        SimpleDateFormat date = new SimpleDateFormat("dd-MMMM-yyyy");
        String apnaTime = date.format(Calendar.getInstance().getTime());
        OffsetTime time = OffsetTime.now();
        return apnaTime+"-"+time.getHour()+"-"+time.getMinute()+"-"+time.getSecond();
    }

    public static String getDate(){
        SimpleDateFormat date =new SimpleDateFormat("dd-MMMM-yyyy");
        return date.format(Calendar.getInstance().getTime()).toString();
    }

    public static String getTime(){
        OffsetTime time = OffsetTime.now();
        String x = time.getHour()+":"+time.getMinute()+":"+time.getSecond()+"";
        return x;
    }
    public static void toast(Activity activity , String mesg){
        Toast.makeText(activity, " "+ mesg, Toast.LENGTH_SHORT).show();
    }

    public static Intent goToHomePageActivity(Context context , Class<HomePage> homePageClass)
    {
        Intent in =  new Intent(context,HomePage.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return in;

    }
    public static Intent goToMaineActivity(Context context , Class<MainActivity> homePageClass)
    {
        Intent in =  new Intent(context,MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return in;

    }
}

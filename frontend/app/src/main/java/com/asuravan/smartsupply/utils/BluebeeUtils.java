package com.asuravan.smartsupply.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.asuravan.smartsupply.pojo.AppPushMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Map;

import okhttp3.ResponseBody;

public class BluebeeUtils {

    public static final String USER_NAME="";
    public static final String PASSWORD="";
    public static final String BASE_URL="https://smartsupply-delightful-ratel-vd.eu-gb.mybluemix.net/";
    static Gson gson;
    static BluebeeUtils bluebeeUtils;
    public static SimpleDateFormat sf=new SimpleDateFormat("MM/dd/yyyy");

   public static BluebeeUtils GetInstance(){
        if(bluebeeUtils==null){
            bluebeeUtils=new BluebeeUtils();
            init();
        }
        return bluebeeUtils;
    }

    private static void init() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Date .class, new DateDeserializer());
         gson = gsonBuilder.create();
    }

    public  Object convertToObject(String str, Type cls){
        return gson.fromJson(str,cls );
    }
    public  Object convertToPushMessage(String str){
        return gson.fromJson(str, AppPushMessage.class);
    }


    public  Map<String,Object> convertToMap(String str){
        return gson.fromJson(str, Map.class);
    }

    public  String convertToString(Object str){
        return gson.toJson(str);
    }

    public boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
               }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String b64toString(String str){
       return new String(Base64.getDecoder().decode(str));
    }
}

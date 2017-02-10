package com.segaudi.util;

import com.google.gson.FieldAttributes;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by xuchenyou on 1/11/17.
 */
public class Utils {
    public static Gson gson = new Gson();
    public static String toJson(Object object){
        try{
            return gson.toJson(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void toJson(String jsonName, Object object){
        try{
            String jsonContent = toJson(object);
            PrintWriter output = new PrintWriter(jsonName + ".json");
            output.println(jsonContent);
            output.flush();
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static <T> T fromJsonString(String json, Class<T> myclass){
        try{
            return gson.fromJson(json, myclass);
        } catch (Exception e){
            return null;
        }
    }
}




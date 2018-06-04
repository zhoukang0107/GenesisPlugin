package com.example.hotfix.hotfix;

import android.util.Log;

import java.lang.reflect.Field;

public class ReflectUtil {
    static final String TAG = "ReflectUtil";
    public static Object getDeclaredFieldObject(Object object, Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
        return null;
    }

    public static void setDeclaredFieldObject(Object object,Class<?> clazz, Object fieldObject, String fieldName){
        try {
            Field filed = clazz.getDeclaredField(fieldName);
            filed.setAccessible(true);
            filed.set(object,fieldObject);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

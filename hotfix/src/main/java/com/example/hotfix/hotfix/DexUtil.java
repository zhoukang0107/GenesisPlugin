package com.example.hotfix.hotfix;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

public class DexUtil {
    public static final String TAG = "DexUtil";

    public static void init(Context context){
        File dexFileDir = context.getDir(Constants.FIX_DEX_DIR, Context.MODE_PRIVATE);
        String optDir = context.getDir(Constants.FIX_OPT_DIR, Context.MODE_PRIVATE).getAbsolutePath();
        String dexPaths = getDexPaths(dexFileDir);
        if (TextUtils.isEmpty(dexPaths)){
            Log.e(TAG,"dex path is null, return");
            return;
        }
        BaseDexClassLoader classLoader = (BaseDexClassLoader) DexUtil.class.getClassLoader();
        Object bugElements = getDexElements(classLoader);
        Object patchElements = makeDexElements(dexPaths,optDir,null);
        Object newElements = mergeDexElements(patchElements,bugElements);
        setDexElements(classLoader, newElements);
    }

    public static String getDexPaths(File dexFileDir){
        if (!dexFileDir.exists()){
            return "";
        }
        File[] listFiles = dexFileDir.listFiles();
        StringBuilder dexPaths = new StringBuilder();
        for(File file:listFiles){
            if(file.getName().startsWith("classes")&&file.getName().endsWith(".dex")){
                dexPaths.append(file.getAbsolutePath()).append(":");
            }
        }
        if (dexPaths.length()>0){
            dexPaths.deleteCharAt(dexPaths.length()-1);
        }
        Log.d(TAG,"dex path is "+dexPaths.toString());
        return dexPaths.toString();
    }

    public static Object getDexPathList(BaseDexClassLoader classLoader){
        return ReflectUtil.getDeclaredFieldObject(classLoader,BaseDexClassLoader.class,"pathList");
    }

    public static Object getDexElements(BaseDexClassLoader classLoader){
        Object dexPathList = getDexPathList(classLoader);
        if (dexPathList==null){
            Log.e(TAG,"pathList is null");
            return null;
        }
        return ReflectUtil.getDeclaredFieldObject(dexPathList,dexPathList.getClass(),"dexElements");
    }

    public static void setDexElements(BaseDexClassLoader classLoader, Object fieldObject){
        if (fieldObject==null){
            Log.e(TAG,"setDexElements is failed,because the value is null!");
            return;
        }
        Object pathList = getDexPathList(classLoader);
        if (pathList==null){
            Log.e(TAG,"pathList is null! return");
            return;
        }
        ReflectUtil.setDeclaredFieldObject(pathList,pathList.getClass(),fieldObject,"dexElements");
    }


    /**
     *
     * @param dexPath  dexPath：dex相关文件路径集合，多个路径用文件分隔符分隔，默认文件分隔符为‘：’
     * @param optimizedDirectory  optimizedDirectory：解压的dex文件存储路径，这个路径必须是一个内部存储路径，一般情况下使用当前应用程序的私有路径：/data/data/<Package Name>/...。
     * @param librarySearchPath   librarySearchPath：包含 C/C++ 库的路径集合，多个路径用文件分隔符分隔分割，可以为null。
     * @return
     */
    public static Object makeDexElements(String dexPath, String optimizedDirectory, String librarySearchPath) {
        BaseDexClassLoader classLoader = new DexClassLoader(dexPath,optimizedDirectory,librarySearchPath,DexUtil.class.getClassLoader());
        Object pathList = ReflectUtil.getDeclaredFieldObject(classLoader,BaseDexClassLoader.class,"pathList");
        if (pathList==null){
            Log.e(TAG,"make dexElements fialed!");
            return null;
        }
        try {
            Class<?> clazz = Class.forName(pathList.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ReflectUtil.getDeclaredFieldObject(pathList,pathList.getClass(),"dexElements");
    }

    public static Object mergeDexElements(Object dexElements1, Object dexElements2){
        if (dexElements1==null||dexElements2==null||
                !dexElements1.getClass().isArray()||!dexElements2.getClass().isArray()){
            Log.e(TAG,"dexElements1 or dexElements2 is null!");
            return null;
        }
        int dexElements1Length = Array.getLength(dexElements1);
        int dexElements2Length = Array.getLength(dexElements2);
        Object result = Array.newInstance(dexElements1.getClass().getComponentType(),dexElements1Length + dexElements2Length);
        for (int i=0;i<dexElements1Length+dexElements2Length;i++){
            if (i<dexElements1Length){
                Array.set(result,i,Array.get(dexElements1,i));
            }else{
                Array.set(result,i,Array.get(dexElements2,i-dexElements1Length));
            }
        }
        Log.e(TAG,dexElements1Length+" array "+dexElements2Length);
        printArray(result);
        return result;
    }

    private static void printArray(Object result) {
        for (int i=0;i<Array.getLength(result);i++){
            Object object = Array.get(result,i);
            Log.e(TAG,i+" array "+object.toString());
        }
    }
}

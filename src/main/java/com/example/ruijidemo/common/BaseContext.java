package com.example.ruijidemo.common;

/**
 * 基于ThreadLocal封装工具类,用来保存和获取用户id
 * */
public class BaseContext {

    /**
     * 设置id
     */
    private  static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取id
     * @return
     */
    public static  Long getCurrentId(){
        return threadLocal.get();
    }
}

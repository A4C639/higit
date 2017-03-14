package com.example.xonvi.washing2.util;

/**
 * Created by xonvi on 2017/1/3.
 */

//用于页面层次的用户信息校验
public class RegisterCheckUtil {
    //两次输入密码是否一致
    public static boolean passCheck(String pass,String passagain){

        //先判断是否为空
        if(pass.equals("")||pass==null){
            ToastUtil.toast("输入密码为空!");
            return false;
        }
        if(passagain.equals("")||passagain==null){
            ToastUtil.toast("重复密码为空!");
            return false;
        }

        if(pass.equals(passagain)){
            return true;
        }
        ToastUtil.toast("两次输入密码不一致!");
        return false;
    }

    //用户名或者密码的长度限制
    public static boolean lengthCheck(String pass,String username){
        //根据数据库的设定 密码长度不能超过12字符 ,用户名的长度不能超过10个字符 并且都不能为空

        if(pass.length()>0&&pass.length()<=12){
            if(username.length()>0&&username.length()<=10){
                return true;
            }
        }

        ToastUtil.toast("用户名最多不超过10个字符,密码最多不超过12个字符");
        return false;

    }
}

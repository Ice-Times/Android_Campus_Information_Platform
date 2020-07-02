package com.example.gs;

import android.app.Application;
import android.net.Uri;

import java.util.ArrayList;

public class globalValue extends Application {

    //声明一个变量
    public String nameString;
    public Uri url;
    public int Itemindex;
    public String UserName;
    public ArrayList<String> globalType;
    public ArrayList<String> globalName;
    public ArrayList<String> globalMain;
    public ArrayList<String> globalPhone;


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        setname("wumz");
        seturl(null);
        setItemindex(0);
        this.globalType=new ArrayList<String>();
        this.globalName=new ArrayList<String>();
        this.globalMain=new ArrayList<String>();
        this.globalPhone=new ArrayList<String>();
    }

    //实现setname()方法，设置变量的值
    public void setname(String name) {
        this.nameString = name;
    }

    //实现getname()方法，获取变量的值
    public String getname() {
        return nameString;
    }
//url
    public void seturl(Uri url) {
        this.url = url;
    }

    public Uri geturl() {
        return url;
    }
    //Itemindex
    public void setItemindex(int Itemindex) {
    this.Itemindex = Itemindex;
    }

    public int getItemindex() {
        return Itemindex;
    }

    public void setGlobalAll(ArrayList<String> globalType,
                             ArrayList<String> globalName,
                             ArrayList<String> globalMain,
                             ArrayList<String> globalPhone){

        this.globalType=(ArrayList<String>) globalType.clone();
        this.globalName=(ArrayList<String>) globalName.clone();
        this.globalMain=(ArrayList<String>) globalMain.clone();
        this.globalPhone=(ArrayList<String>) globalPhone.clone();

    }
    public ArrayList<String> getGlobalType() {
        return globalType;
    }

    public ArrayList<String> getGlobalName() {
        return globalName;
    }
    public ArrayList<String> getGlobalMain() {
        return globalMain;
    }

    public ArrayList<String> getGlobalPhone() {
        return globalPhone;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName(){
        return UserName;
    }
}

package com.example.gs;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class login_in extends AppCompatActivity {

    private static final String HOST = "192.168.1.2";
    private static final int PORT = 10000;

    private AlertDialog.Builder builder;


    Button loginBt;
    Button signinBt;

    EditText inputuserName;
    EditText inputuserPassword;
    boolean gea=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        if(getSupportActionBar()!=null){
            //getSupportActionBar().setTitle("校园信息平台");
            ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
            ActionBar Ab=getSupportActionBar();
            Ab.setCustomView(mActionBarView, lp);
            Ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            Ab.setDisplayShowCustomEnabled(true);
            Ab.setDisplayShowHomeEnabled(false);
            Ab.setDisplayShowTitleEnabled(false);

//            getSupportActionBar().hide();
//
//            getWindow().setFlags(
//
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//
//            );

        }



        loginBt=(Button)findViewById(R.id.loginBt);
        signinBt=(Button)findViewById(R.id.signinBt);
        inputuserName=(EditText)findViewById(R.id.et1_1);
        inputuserPassword=(EditText)findViewById(R.id.et1_2);

        signinBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Log.d("11", "onClick: ");
                Intent i = new Intent(login_in.this , sign_in.class);
                startActivity(i);

            }
        });

        loginBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String state="D";
                            String sendmsg="";
                            sendmsg="\""+state+"\"";
                            System.out.println("登录语：" + sendmsg);
                            Socket sc = new Socket();
                            SocketAddress socAddress = new InetSocketAddress(HOST, PORT);
                            sc.connect(socAddress, 2000);

                            DataOutputStream outputStream=new DataOutputStream(sc.getOutputStream());

                            try{
                                DataOutputStream  outt;
                                outt=new DataOutputStream(sc.getOutputStream());
                                outt.writeUTF(sendmsg);

                                Log.d("输出到服务器完成", "66 ");
                            }catch(Exception e){
                                Log.d("输出到服务器失败", "00 ");
                            }

                            sc.close();

//读入用户数据
                            sc = new Socket(HOST, PORT);
                            String serviceData = "";
                            DataInputStream inputStream=new DataInputStream(sc.getInputStream());


                            try{
                                Log.d("接收服务器的数据", "接收服务器的数据");
                                serviceData=inputStream.readUTF();


                            }catch(Exception e){
                                System.out.println("接收服务器数据异常");
                                e.printStackTrace();
                            }

                            Log.d("服务器发送的数据为 ", serviceData);
                            outputStream.close();
                            inputStream.close();
                            sc.close();
                            //--处理输入数据

                            List<List<String>> user=new ArrayList<List<String>>();


                            int num=0;
                            int index=0;
                            String tname ="";
                            String tpass ="";

                            for(int i=0;i<serviceData.length();i++)
                            {
                                if(serviceData.charAt(i)=='\"')
                                    num++;
                                if(num==7)
                                {

                                    List temp=new ArrayList<String>();
                                    temp.add(tname);
                                    temp.add(tpass);
                                    user.add(temp);

//                                    userName.add(tname);
//                                    userPassword.add(tpass);

                                    num=1;
                                    index++;
                                    tname="";
                                    tpass="";

                                }
                                if(num==3&&serviceData.charAt(i)!='\"')
                                    tname=tname+String.valueOf(serviceData.charAt(i));
                                else if(num==5&&serviceData.charAt(i)!='\"')
                                    tpass=tpass+String.valueOf(serviceData.charAt(i));


                                //Log.d("timestring", timeString);
                                if(i==serviceData.length()-1)
                                {
                                    List temp=new ArrayList<String>();
                                    temp.add(tname);
                                    temp.add(tpass);
                                    user.add(temp);
                                }

                            }


                            //验证用户是否存在及密码正确
                            gea=false;
                            String s1=inputuserName.getText().toString();

                            String s2=inputuserPassword.getText().toString();

                            for(int i=0;i<user.size();i++)
                            {
                                if(user.get(i).get(0).equals(s1)&&user.get(i).get(1).endsWith(s2))
                                {
                                    globalValue gv=(globalValue) getApplication();
                                    gv.setUserName(s1);
                                    Log.d("mm", "密码正确");
                                    gea=true;
                                }
                            }


                            if(gea==true)
                            {
                                Looper.prepare();//增加部分
                                Handler mainHandler = new Handler(Looper.getMainLooper());
                                mainHandler.post(new Runnable() {

                                    @Override

                                    public void run() {

                                        //已在主线程中，可以更新UI
                                        Intent i = new Intent(login_in.this , Main2Activity.class);
                                        startActivity(i);

                                    }
                                });
                                Looper.loop();//增加

                            }
                            else
                            {

                                Looper.prepare();//增加部分
                                showOne();
                                Looper.loop();//增加
                            }








                        }catch(Exception e){
                            System.out.println("wrong");
                            e.printStackTrace();

                            Looper.prepare();//增加部分
                            showWarning();
                            Looper.loop();//增加

                        }
                    }
                }).start();










            }
        });





    }


    private void showOne() {

        builder = new AlertDialog.Builder(this).setIcon(R.drawable.apppic).setTitle("")
                .setMessage("密码错误或者用户不存在").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Toast.makeText(page2.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }


    private void showWarning() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("连接服务器失败")
                .setMessage("请检查手机与服务器的连接").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
//                        Intent ii = new Intent(sign_in.this , login_in.class);
//                        startActivity(ii);
                        //Toast.makeText(page2.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
}

package com.example.gs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.net.Socket;

public class sign_in extends AppCompatActivity {
    private static final String HOST = "192.168.1.2";
    private static final int PORT = 10000;

    private AlertDialog.Builder builder;

    EditText userName;
    EditText userPassword;
    Button signinBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userName=(EditText)findViewById(R.id.et1_1);
        userPassword=(EditText)findViewById(R.id.et1_2);
        signinBt=(Button)findViewById(R.id.signinBt2);



        getSupportActionBar().setTitle("注册用户");


        signinBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            String state="I";
                            String sendmsg="";
                            sendmsg="\""+state+"\"    \""+userName.getText()+"\"    \""+userPassword.getText()+"\"";
                            System.out.println("注册语：" + sendmsg);
                            Socket sc = new Socket(HOST, PORT);

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



                        }catch(Exception e){
                            System.out.println("wrong");
                            e.printStackTrace();
                        }
                    }
                }).start();
                showOne();




                //Log.d("11", "onClick: ");
                Intent i = new Intent(sign_in.this , login_in.class);
                startActivity(i);

            }
        });



    }

    private void showOne() {

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("")
                .setMessage("注册成功").setNegativeButton("确定", new DialogInterface.OnClickListener() {
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

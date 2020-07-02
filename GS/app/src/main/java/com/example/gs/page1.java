package com.example.gs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gs.globalValue;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class page1 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView=null;
    private static final String HOST = "192.168.1.2";
    private static final int PORT = 9998;
    private static ArrayList<String> globalType=new ArrayList<String>();
    private static ArrayList<String> globalName=new ArrayList<String>();
    private static ArrayList<String> globalMain=new ArrayList<String>();
    private static ArrayList<String> globalPhone=new ArrayList<String>();
    private static ArrayList<String> globalTimeString=new ArrayList<String>();
    private SwipeRefreshLayout sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        if(getSupportActionBar()!=null){

            getSupportActionBar().hide();

            getWindow().setFlags(

                    WindowManager.LayoutParams.FLAG_FULLSCREEN,

                    WindowManager.LayoutParams.FLAG_FULLSCREEN

            );

        }



        //final SwipeRefreshLayout sw =  findViewById(R.id.swipeRefreshLayout);
        sw =  findViewById(R.id.swipeRefreshLayout);
        sw.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);

        listView=(ListView)findViewById(R.id.listview1);
        List<List<String>> list=getData();
        listView.setAdapter(new listAdapt(this, list));

//        sw.setRefreshing(true);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Log.d("", "run: 233");
//                sw.setRefreshing(false);
//            }
//        }, 5000);
        //sw.setRefreshing(false);

        listView.setOnItemClickListener(this);


        //----
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟网络请求需要3000毫秒，请求完成，设置setRefreshing 为false

//                List<List<String>> data2=new ArrayList<List<String>>();
//
//                List temp=new ArrayList<String>();
//                temp.add("失物招领");
//                temp.add("裤子");
//
//                data2.add(temp);
//
//                List temp2=new ArrayList<String>();
//                temp2.add("失物招领");
//                temp2.add("篮球");
//
//                data2.add(temp2);


                //---
                //listView=(ListView)findViewById(R.id.listview1);
                //listView.setAdapter(new listAdapt(this, data2));


//                ListView listView = (ListView) findViewById(R.id.listview1);
////                List<List<String>> list=getData();
////                listView.setAdapter(new listAdapt(sw.getContext(), data2));


                //---

                //--获取文件
                final String dataToServices="\"G\"";
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Socket sc = new Socket(HOST, PORT);

                            DataOutputStream outputStream=new DataOutputStream(sc.getOutputStream());
                            String sendmsg=dataToServices;
                            Log.d("捕捉到输入", sendmsg);
                            String serMes;
                            try{
                                DataOutputStream  outt;
                                outt=new DataOutputStream(sc.getOutputStream());
                                outt.writeUTF(sendmsg);

                                Log.d("输出到服务器完成", "success");
                            }catch(Exception e){

                            }

                            sc.close();
                            sc = new Socket(HOST, PORT);
                            String serviceData = "";
                            //InputStreamReader fileReader = new InputStreamReader(sc.getInputStream(),"GBK");
                            //BufferedReader fp = new BufferedReader(fileReader);

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

                            ArrayList<String> type=new ArrayList<String>();
                            ArrayList<String> name=new ArrayList<String>();
                            ArrayList<String> maintext=new ArrayList<String>();
                            ArrayList<String> phone=new ArrayList<String>();
                            ArrayList<String> time=new ArrayList<String>();

                            int num=0;
                            int index=0;
                            String ttype ="";
                            String tname ="";
                            String tmaintext ="";
                            String tphone ="";
                            String timeString ="";

                            for(int i=0;i<serviceData.length();i++)
                            {
                                if(serviceData.charAt(i)=='\"')
                                    num++;
                                if(num==13)
                                {
                                    type.add(ttype);
                                    name.add(tname);
                                    maintext.add(tmaintext);
                                    phone.add(tphone);
                                    time.add(timeString);
                                    num=1;
                                    index++;
                                    ttype ="";
                                    tname ="";
                                    tmaintext ="";
                                    tphone ="";
                                    timeString ="";

                                }
                                if(num==3&&serviceData.charAt(i)!='\"')
                                    ttype=ttype+String.valueOf(serviceData.charAt(i));
                                else if(num==5&&serviceData.charAt(i)!='\"')
                                    tname=tname+String.valueOf(serviceData.charAt(i));
                                else if(num==7&&serviceData.charAt(i)!='\"')
                                    tmaintext=tmaintext+String.valueOf(serviceData.charAt(i));
                                else if(num==9&&serviceData.charAt(i)!='\"')
                                    tphone=tphone+String.valueOf(serviceData.charAt(i));
                                else if(num==11&&serviceData.charAt(i)!='\"')
                                    timeString=timeString+String.valueOf(serviceData.charAt(i));

                                //Log.d("timestring", timeString);
                                if(i==serviceData.length()-1)
                                {
                                    type.add(ttype);
                                    name.add(tname);
                                    maintext.add(tmaintext);
                                    phone.add(tphone);
                                    time.add(timeString);
                                }

                            }
//                            for(int k=0;k<type.size();k++)
//                            {
//                                Log.d("类型", type.get(k));
//                                Log.d("名称", name.get(k));
//                                Log.d("描述", maintext.get(k));
//                                Log.d("电话", phone.get(k));
//                            }
                            globalType= (ArrayList<String>) type.clone();
                            globalName= (ArrayList<String>) name.clone();
                            globalMain= (ArrayList<String>) maintext.clone();
                            globalPhone= (ArrayList<String>) phone.clone();
                            globalTimeString=(ArrayList<String>) time.clone();


                            final List<List<String>> data=new ArrayList<List<String>>();
                            for(int k=type.size()-1;k>=0;k--)
                            {
                                List temp=new ArrayList<String>();
                                temp.add(type.get(k));
                                temp.add(name.get(k));
                                temp.add(time.get(k));

                                data.add(temp);
                            }
                            //改变UI
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            mainHandler.post(new Runnable() {

                                @Override

                                public void run() {

                                    //已在主线程中，可以更新UI
                                    ListView listView = (ListView) findViewById(R.id.listview1);
                                    //List<List<String>> list=getData();
                                    listView.setAdapter(new listAdapt(sw.getContext(), data));

                                }
                            });



                            //---
                        }catch(Exception e){
                            System.out.println("wrong");
                            e.printStackTrace();
                        }
                    }


                }).start();


                //---
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sw.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        Button bt1=(Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("11", "onClick: ");
                Intent i = new Intent(page1.this , page2.class);

                startActivity(i);

            }
        });


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        String text= listView.getItemAtPosition(position)+"";

        //传入全局变量
        globalValue gv=(globalValue) getApplication();
        gv.setItemindex(globalMain.size()-position-1);

        gv.setGlobalAll(globalType,globalName,globalMain,globalPhone);



        Log.d("msgg", globalMain.get(globalMain.size()-position-1));
        Intent i = new Intent(page1.this , page4.class);
        startActivity(i);



        Toast.makeText(this, "position="+position+"text="+text,
                Toast.LENGTH_SHORT).show();
    }



    public List<List<String>> getData(){
        final String dataToServices="\"G\"";
        final List<List<String>> data=new ArrayList<List<String>>();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket sc = new Socket(HOST, PORT);

                    DataOutputStream outputStream=new DataOutputStream(sc.getOutputStream());
                    String sendmsg=dataToServices;
                    Log.d("捕捉到输入", sendmsg);
                    String serMes;
                    try{
                        DataOutputStream  outt;
                        outt=new DataOutputStream(sc.getOutputStream());
                        outt.writeUTF(sendmsg);

                        Log.d("输出到服务器完成", "success");
                    }catch(Exception e){

                    }

                    sc.close();
                    sc = new Socket(HOST, PORT);
                    String serviceData = "";
                    //InputStreamReader fileReader = new InputStreamReader(sc.getInputStream(),"GBK");
                    //BufferedReader fp = new BufferedReader(fileReader);

                    DataInputStream inputStream=new DataInputStream(sc.getInputStream());


                    try{
                        Log.d("接收服务器的数据", "接收服务器的数据");
                        serviceData=inputStream.readUTF();


                    }catch(Exception e){
                        System.out.println("接收服务器数据异常");
                        e.printStackTrace();
                    }

                    Log.d("服务器发送数据", serviceData);
                    outputStream.close();
                    inputStream.close();
                    sc.close();
                    //--处理输入数据

                    ArrayList<String> type=new ArrayList<String>();
                    ArrayList<String> name=new ArrayList<String>();
                    ArrayList<String> maintext=new ArrayList<String>();
                    ArrayList<String> phone=new ArrayList<String>();
                    ArrayList<String> time=new ArrayList<String>();


                    int num=0;
                    int index=0;
                    String ttype ="";
                    String tname ="";
                    String tmaintext ="";
                    String tphone ="";
                    String timeString ="";

                    for(int i=0;i<serviceData.length();i++)
                    {
                        if(serviceData.charAt(i)=='\"')
                            num++;
                        if(num==13)
                        {
                            type.add(ttype);
                            name.add(tname);
                            maintext.add(tmaintext);
                            phone.add(tphone);
                            time.add(timeString);
                            num=1;
                            index++;
                            ttype ="";
                            tname ="";
                            tmaintext ="";
                            tphone ="";
                            timeString="";

                        }
                        if(num==3&&serviceData.charAt(i)!='\"')
                            ttype=ttype+String.valueOf(serviceData.charAt(i));
                        else if(num==5&&serviceData.charAt(i)!='\"')
                            tname=tname+String.valueOf(serviceData.charAt(i));
                        else if(num==7&&serviceData.charAt(i)!='\"')
                            tmaintext=tmaintext+String.valueOf(serviceData.charAt(i));
                        else if(num==9&&serviceData.charAt(i)!='\"')
                            tphone=tphone+String.valueOf(serviceData.charAt(i));
                        else if(num==11&&serviceData.charAt(i)!='\"')
                            timeString=timeString+String.valueOf(serviceData.charAt(i));



                        if(i==serviceData.length()-1)
                        {
                            type.add(ttype);
                            name.add(tname);
                            maintext.add(tmaintext);
                            phone.add(tphone);
                            time.add(timeString);
                            Log.d(timeString, timeString);
                        }

                    }
                    globalType= (ArrayList<String>) type.clone();
                    globalName= (ArrayList<String>) name.clone();
                    globalMain= (ArrayList<String>) maintext.clone();
                    globalPhone= (ArrayList<String>) phone.clone();
                    globalTimeString=(ArrayList<String>) time.clone();

                    for(int k=type.size()-1;k>=0;k--)
                    {
                        List temp=new ArrayList<String>();
                        temp.add(type.get(k));
                        temp.add(name.get(k));
                        temp.add(time.get(k));

                        data.add(temp);
                    }
                    //改变UI
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {

                        @Override

                        public void run() {

                            //已在主线程中，可以更新UI
                            ListView listView = (ListView) findViewById(R.id.listview1);
                            //List<List<String>> list=data;
                            listView.setAdapter(new listAdapt(sw.getContext(), data));

                        }
                    });



                    //---
                }catch(Exception e){
                    System.out.println("wrong");
                    e.printStackTrace();
                }
            }


        }).start();



        return data;
    }



}

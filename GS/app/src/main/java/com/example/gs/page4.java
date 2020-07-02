package com.example.gs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class page4 extends AppCompatActivity {
    private static final String HOST = "192.168.1.2";
    private static final int PORT = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("详情");
        }



        TextView typeText = findViewById(R.id.textview4_1);
        TextView nameText = findViewById(R.id.textView4_2);
        TextView mainText = findViewById(R.id.textView4_3);
        TextView phoneText = findViewById(R.id.textView4_4);
        final ImageView imgview = findViewById(R.id.imageView4_1);

        final globalValue gv = (globalValue) getApplication();





        Typeface tf4= Typeface.createFromAsset(getAssets(),"fonts/xk.ttf"); // 通过自定义字体生成字体对象
        phoneText.setTypeface(tf4); // 将TextView设置自定义字体。

        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/kt.ttf"); // 通过自定义字体生成字体对象
        mainText.setTypeface(tf3); // 将TextView设置自定义字体。

        typeText.setText(gv.globalType.get(gv.getItemindex()));
        nameText.setText("名称："+gv.globalName.get(gv.getItemindex()));
        mainText.setText("描述：\n"+gv.globalMain.get(gv.getItemindex()));
        phoneText.setText("联系电话："+gv.globalPhone.get(gv.getItemindex()));
        final String lookforpicNum=String.valueOf(gv.getItemindex());
        Log.d("looklook", lookforpicNum);

        new Thread(new Runnable() {
            public void run() {
                try {
                    Socket sc = new Socket(HOST, PORT);


                    DataOutputStream outputStream=new DataOutputStream(sc.getOutputStream());
                    String sendmsg=lookforpicNum;
                    Log.d("捕捉到输入", sendmsg);
                    String serMes;
                    try{
                        DataOutputStream  outt;
                        outt=new DataOutputStream(sc.getOutputStream());
                        outt.writeUTF(sendmsg);

                        Log.d("输出到服务器完成", "66 ");
                    }catch(Exception e){
                        Log.d("输出到服务器失败", "00 ");
                    }

                    sc.close();


//接收图片
                    sc = new Socket(HOST, PORT);
                    DataInputStream dataInput = new DataInputStream(sc.getInputStream());
                    int size = dataInput.readInt();
                    byte[] data = new byte[size];
                    int len = 0;
                    while (len < size) {
                        len += dataInput.read(data, len, size - len);
                    }
                    sc.close();

                    ByteArrayOutputStream outPut = new ByteArrayOutputStream();


                    Log.d("接收图片", "完成");
                    final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    while(data==null || bmp==null) {
                        Log.d("www", "wait");
                    }
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outPut);

                    Log.d("bianma图片", "完成");

                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {

                        @Override
                        public void run() {

                        //已在主线程中，可以更新UI
                            imgview.setImageBitmap(bmp);
                            Log.d("显示图片", "run: xsxs");
                            //initViewImg();
                        }

                    });

                    //imgview.setImageBitmap(bmp);


                } catch (Exception e) {
                    System.out.println("服务器连接出错");
                    e.printStackTrace();
                }

            }
        }).start();
    }

//    private void initViewImg() {
//        ImageView mImageView = (ImageView) findViewById(R.id.imageView4_1);
//        //mImageView.setImageResource(R.drawable.fengjing_1);
//        ImageViewUtil.matchAll(this, mImageView);
//    }


}

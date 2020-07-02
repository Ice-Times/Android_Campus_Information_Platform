package com.example.gs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.Socket;


public class page2 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView imageView;

    private AlertDialog.Builder builder;
    private CheckBox[] checkBoxes=new CheckBox[3];
    private EditText nametext;
    private EditText maintext;
    private EditText phonetext;
    private Uri ImgUri;
    private  Bitmap ImgBitmap;
    private static final String HOST = "192.168.1.2";
    private static final int PORT = 9998;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);


        Log.d("123", "进入page2");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("发布信息");
        }
        

        checkBoxes[0] = (CheckBox) findViewById(R.id.cb1);//二手交易
        checkBoxes[1] = (CheckBox) findViewById(R.id.cb2);//失物招领
        checkBoxes[2] = (CheckBox) findViewById(R.id.cb3);//寻物启事

        nametext=(EditText) findViewById(R.id.nametext);
        maintext=(EditText) findViewById(R.id.maintext);
        phonetext=(EditText) findViewById(R.id.phonetext);

        Button submitbt= (Button)findViewById(R.id.submitbt);

        checkBoxes[0].setOnCheckedChangeListener(this);
        checkBoxes[1].setOnCheckedChangeListener(this);
        checkBoxes[2].setOnCheckedChangeListener(this);



        submitbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String checkBoxName="";
                String name=nametext.getText().toString();
                String mainpage=maintext.getText().toString();
                String phone=phonetext.getText().toString();

                for(int i=0;i<checkBoxes.length;i++)
                {
                    if(checkBoxes[i].isChecked())
                    {
                        if(i==0)
                            checkBoxName="二手交易";
                        else if(i==1)
                            checkBoxName="失物招领";
                        else
                            checkBoxName="寻物启事";

                    }

                }

                Log.d("cheackstate", checkBoxName);
                Log.d("name", name);
                Log.d("main", mainpage);
                Log.d("phone", phone);

//将图片变成字符串发送
                String ImgToSting="";
//                if(ImgBitmap!=null)
//                    ImgToSting=convertIconToString(ImgBitmap);
//                else
//                    Log.d("fff", "图片为空");
//                Log.d("string 图片", ImgToSting);
//                String te=ImgToSting.toString();


//----

                String sendState="S";
                final String dataToServices="\""+sendState+"\"    \""+checkBoxName+"\"    \""+name+"\"    \""+mainpage+"\"    \""+phone+"\"";

                if(ImgBitmap==null)
                {
                    showOne();
                    return;
                }

                //----提交数据到服务器
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

                                Log.d("输出到服务器完成", "66 ");
                            }catch(Exception e){
                                Log.d("输出到服务器失败", "00 ");
                            }

                            sc.close();



                            sc = new Socket(HOST, PORT);

                            //InputStreamReader fileReader = new InputStreamReader(sc.getInputStream(),"GBK");
                            //BufferedReader fp = new BufferedReader(fileReader);

                            DataInputStream inputStream=new DataInputStream(sc.getInputStream());
                            String connectState ="";
                            String ttt="";
                            try{
                                Log.d("接收服务器的数据", "接收服务器的数据");
                                connectState=inputStream.readUTF();
//                                while(true) {
//                                    connectState = fp.readLine();
//                                    ttt=ttt+connectState;
//                                    System.out.println("服务器：" + connectState);
//                                    if(connectState==null)
//                                        break;
//
//                                }

                            }catch(Exception e){
                                System.out.println("接收服务器数据异常");
                                e.printStackTrace();
                            }

                            Log.d("服务器发送数据", connectState);

                            outputStream.close();
                            inputStream.close();
                            sc.close();


                            //---发送图
                            sc = new Socket(HOST, 9999);
                            OutputStream os = sc.getOutputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();





                            ImgBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] data = baos.toByteArray();
                            os.write(data);
                            os.flush();//刷新缓冲区
                            os.close();
                            sc.close();
                            ImgBitmap=null;
                            //---发送图


                        }catch(Exception e){
                            System.out.println("wrong");
                            e.printStackTrace();
                        }
                    }


                }).start();


                //-------

                showTwo();
            }
        });

//添加图片
        Button btImg = (Button) findViewById(R.id.chooseImg);
        imageView = (ImageView) findViewById(R.id.imageView2);
        btImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageView.getImageMatrix()!=null)
                {

                    BlurBuilder.snapShotWithoutStatusBar(page2.this);

                    Intent i = new Intent(page2.this , page3.class);
                    startActivity(i);
                }



            }
        });





    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                //imageView.setImageURI(uri);
                globalValue gv=(globalValue) getApplication();
                gv.seturl(uri);
                ImgUri=uri;

                Log.e("uri", uri.toString());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 32;
                Bitmap bitmap = BitmapFactory.decodeFile(getRealFilePath(this,uri),options);
                Log.d("", "水涨船高");
                imageView.setImageBitmap(bitmap);

                ContentResolver cr = this.getContentResolver();

                try {
                    //获取图片
                    ImgBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(),e);
                }


            }
        }
    }
//url
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
         Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);

        if (null != cursor) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (index > -1) {
                data = cursor.getString(index);
            }
            }
        cursor.close();
        }
        }
    return data;
    }




    //--



    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        checkBoxes[0] = (CheckBox) findViewById(R.id.cb1);
//        checkBoxes[1] = (CheckBox) findViewById(R.id.cb2);
//        checkBoxes[2] = (CheckBox) findViewById(R.id.cb3);
//        checkBoxes[1].setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
//        checkBoxes[2].setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
//        checkBoxes[3].setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        Log.d("111", "onCheckedChanged: ");
        if(b) {
            for (int i = 0; i < checkBoxes.length; i++) {
                //不等于当前选中的就变成false
                if (checkBoxes[i].getText().toString().equals(compoundButton.getText().toString())) {
                    checkBoxes[i].setChecked(true);
                } else {
                    checkBoxes[i].setChecked(false);
                }
            }
        }
    }
    private void showTwo() {

        builder = new AlertDialog.Builder(this).setIcon(R.drawable.apppic).setTitle("")
                .setMessage("发布成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //ToDo: 你想做的事情
                        //Toast.makeText(page2.this, "确定按钮", Toast.LENGTH_LONG).show();

                        finish();
                        Intent newpage = new Intent(page2.this , Main2Activity.class);
                        startActivity(newpage);
                    }
                }).setNegativeButton("再发布一个", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Toast.makeText(page2.this, "关闭按钮", Toast.LENGTH_LONG).show();
//                        Intent newpage = new Intent(page2.this , page2.class);
//                        startActivity(newpage);
                        finish();
                        startActivity(getIntent());
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showOne() {

        builder = new AlertDialog.Builder(this).setIcon(R.drawable.apppic).setTitle("")
                .setMessage("警告!!!请添加图片").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        //Toast.makeText(page2.this, "关闭按钮", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }





    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }
    public static Bitmap convertStringToIcon(String str) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bytes = Base64.decode(str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


    public void refresh() {

        onCreate(null);

    }



}

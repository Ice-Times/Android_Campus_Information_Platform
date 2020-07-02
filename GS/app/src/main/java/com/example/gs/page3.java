package com.example.gs;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.FileNotFoundException;

public class page3 extends AppCompatActivity {
    globalValue gv;
    ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        cl=findViewById(R.id.clt);

        Bitmap img = BlurBuilder.blur(cl);




        if (BlurBuilder.isBlurFlag()) {
        cl.setBackground( new BitmapDrawable(getResources(),img));
        }



//       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(getSupportActionBar()!=null){


            getSupportActionBar().setTitle("显示图片");
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


        gv = (globalValue) getApplication();
        Uri dizhi = gv.geturl();
        Log.d("gv uri地址", dizhi.toString());
        //Log.d("gv uri地址", gv.getname());

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 256;
//        Bitmap bitmap = BitmapFactory.decodeFile(getRealFilePath(this,dizhi),options);
//
        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
//
//        imageView.setImageBitmap(bitmap);

        ContentResolver cr = this.getContentResolver();

        try {
            //获取图片
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(dizhi));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(), e);
        }


    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
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

    @Override
     public void finish() {
        Log.d("page3", "page3 finish: ");
       // BlurBuilder.recycle();
        // overridePendingTransition(android.view.animation.Animation.INFINITE, android.view.animation.Animation.INFINITE);
    }

}
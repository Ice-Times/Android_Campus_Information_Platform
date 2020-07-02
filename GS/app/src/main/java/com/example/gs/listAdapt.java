package com.example.gs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class listAdapt extends BaseAdapter {
    private List<List<String>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public listAdapt(Context context, List<List<String>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Zujian {
        public TextView text1;
        public TextView text2;
        public TextView text3;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        listAdapt.Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件

            convertView = layoutInflater.inflate(R.layout.mylist, null);

            zujian.text1 = (TextView) convertView.findViewById(R.id.title1);
            zujian.text2 = (TextView) convertView.findViewById(R.id.title2);
            zujian.text3=  (TextView) convertView.findViewById(R.id.title3);
            convertView.setTag(zujian);
        } else {
            zujian = (listAdapt.Zujian) convertView.getTag();
        }

        zujian.text1.setText((String)data.get(position).get(0));
        zujian.text2.setText((String)data.get(position).get(1));
        zujian.text3.setText((String)data.get(position).get(2));
//        zujian.text1.setText("11");
//        zujian.text2.setText("22");

        return convertView;
    }
}

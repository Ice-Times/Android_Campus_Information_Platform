package com.example.gs.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gs.R;
import com.example.gs.globalValue;
import com.example.gs.login_in;
import com.example.gs.sign_in;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView t1=(TextView)root.findViewById(R.id.textView2);
        globalValue gv=(globalValue) getActivity().getApplication();

        t1.setText(gv.getUserName());


        Button bt=(Button) root.findViewById(R.id.exitBt);

        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Log.d("11", "onClick: ");
                Intent i = new Intent(getActivity() , login_in.class);
                startActivity(i);

            }
        });


        return root;
    }
}

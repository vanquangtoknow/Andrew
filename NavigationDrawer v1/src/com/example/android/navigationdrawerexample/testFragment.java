package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.Locale;

import DTO.Employee;
import WS.WCFNail;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//tao 1 class fragment tuong ung voi 1 man hinh 
public  class testFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public testFragment() {
        // Empty constructor required for fragment subclasses
    }
    private Button login;
    private TextView id, code;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//lay layout cho fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("LOGIN");
        //su dung tham so
        id = (TextView) rootView.findViewById(R.id.idlogin);
        code = (TextView) rootView.findViewById(R.id.codelogin);
        login = (Button) rootView.findViewById(R.id.Login);
        //login.setOnClickListener(l)
        login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				Log.e("get", "1");
				Thread networkThread1 = new Thread() {
					@Override
					public void run() {
						try {
							WCFNail ws = new WCFNail();
							Log.e("get", "1");
							String tmp = ws
									.getNameSaleItem(new ArrayList<String>() {
										{
											add("2");
										}
									});
							Log.e("get", tmp);

						} catch (Exception e) {
						}
					}
				};
				
				networkThread1.start();
			}
		});
        return rootView;
    }
}

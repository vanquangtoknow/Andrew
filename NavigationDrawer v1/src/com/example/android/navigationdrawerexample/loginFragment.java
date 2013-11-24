package com.example.android.navigationdrawerexample;

import java.util.Locale;

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
public  class loginFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public loginFragment() {
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
				Log.e("login", "1");
				((MainActivity)getActivity()).setLogin(true);
				Log.e("login", "2");
				((MainActivity)getActivity()).setCode(code.getText().toString());
				Log.e("login", "3");
				((MainActivity)getActivity()).setId(Integer.parseInt(id.getText().toString()));
				((MainActivity)getActivity()).changeFragment(new ReportFragment());
				((MainActivity)getActivity()).loginRow.setBackgroundColor(Color.parseColor("#91BEF7"));
				((MainActivity)getActivity()).loginId.setText("Login as " + id.getText().toString());
				
			}
		});
        return rootView;
    }
}

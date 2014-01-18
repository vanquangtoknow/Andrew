package com.example.android.navigationdrawerexample;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import WS.WCFNail;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;
//tao 1 class fragment tuong ung voi 1 man hinh 
public  class loginFragment extends Fragment {
    public static final String ARG_PLANET_NUMBER = "planet_number";

    public loginFragment() {
        // Empty constructor required for fragment subclasses
    }
    private String usernameSave = "";
    private String passwordSave = "";
    private Button login;
    private TextView id, code;
    private WCFNail nailservice =  new WCFNail();
    private EditText edtusername;
    private EditText edtpassword;
    private CheckBox chbSaveUserPass;
    private static final String SPF_NAME = "vidslogin"; //  <--- Add this
    private static final String USERNAME = "username";  //  <--- To save username
    private static final String PASSWORD = "password"; 
    ProgressDialog ringProgressDialog = null;
	private boolean flag_ringprogress = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//lay layout cho fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle("LOGIN");
        //su dung tham so
       /* id = (TextView) rootView.findViewById(R.id.idlogin);
        code = (TextView) rootView.findViewById(R.id.codelogin);*/
        login = (Button) rootView.findViewById(R.id.Login);
        edtusername = (EditText) rootView.findViewById(R.id.edtUsername);
        edtpassword = (EditText) rootView.findViewById(R.id.edtPassword);
        chbSaveUserPass = (CheckBox) rootView.findViewById(R.id.chb_SaveUserPass);
        //----------doc file xml xem da save username and password chua
        SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_NAME,
                Context.MODE_PRIVATE);
        edtusername.setText(loginPreferences.getString(USERNAME, ""));
        edtpassword.setText(loginPreferences.getString(PASSWORD, ""));
        if(loginPreferences.getString(USERNAME, "").equals("")==false)
        {
        	chbSaveUserPass.setChecked(true);
        }
        login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username =edtusername.getText().toString().trim() ;
				String pass = edtpassword.getText().toString().trim();

				checkLogin(username,pass,chbSaveUserPass.isChecked());
			}
		});
        return rootView;
    }
    public void checkLogin(final String username,final String pass, final boolean check)
    {
    	Thread threadLogin = new Thread()
    	{
    		@Override
    		public void run() {
    			Log.i("Login checklogin","start: username"+ username +"--" +pass);
    			boolean checkLogin = nailservice.checkLoginOwner(new ArrayList<String>(){{
    				add(username);
    				add(pass);
    			}});
    			checkLogin = true;
    			if( checkLogin==true)
    			{
    				Log.i("Login susscess", "true");
    				if(check==true)
    				{
    		    		SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
    		            loginPreferences.edit().putString(USERNAME, username).putString(PASSWORD, pass).commit();
    				}else
    				{
    					SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
    	                loginPreferences.edit().clear().commit();
    				}
    				Log.e("login", "1");
    				((MainActivity)getActivity()).setLogin(true);
    				Log.e("login", "2");
    				((MainActivity)getActivity()).setCode("abc");
    				Log.e("login", "3");
    				((MainActivity)getActivity()).setId("123");
    				
    				getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((MainActivity)getActivity()).changeFragment(new ReportFragment());
		    				((MainActivity)getActivity()).loginRow.setBackgroundColor(Color.parseColor("#91BEF7"));
		    				((MainActivity)getActivity()).loginId.setText("Login as " + "vanquag");
						}
					});
    				this.interrupt();
    				return;
    			}
    			else
    			{
    				Log.i("Login fail", "false");
    				getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "Please check username and password", Toast.LENGTH_LONG).show();
						}
					});
    				
    			}
    		}
    	};
    	threadLogin.start();
    	
    }
    
}
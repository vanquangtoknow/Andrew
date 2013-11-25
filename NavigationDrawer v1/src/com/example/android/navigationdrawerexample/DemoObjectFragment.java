package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import DTO.Employee;
import DTO.Ticket;
import WS.WCFNail;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DemoObjectFragment extends Fragment {

	public DemoObjectFragment() {
        // Empty constructor required for fragment subclasses
    }
    public static final String ARG_OBJECT = "object";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        final View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);
        Bundle args = getArguments();
        
        
        Thread networkThread = new Thread() {
			@Override
			public void run() {
				try {
					WCFNail ws = new WCFNail();
					ArrayList<String> para = new ArrayList<String>() {
						{
							add("1");
							add("11/25/2012 4:37 AM");
							add("11/25/2013 4:37 AM");
						}
					};
			        final ArrayList<Ticket >  tmp =ws.getListTicketBetween(para);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							
					        ((TextView) rootView.findViewById(android.R.id.text1)).setText(tmp.get(0).view());
						}
					});
					
				} catch (Exception e) {
				}
			}
		};
		
		networkThread.start();
        
		Thread networkThread1 = new Thread() {
			@Override
			public void run() {
				try {
					WCFNail ws = new WCFNail();
					final ArrayList<Employee> arrayP = new ArrayList<Employee>();
					arrayP.addAll(ws.getAllEmployee());
/*					arrayP = ws.getAllEmployee();*/
					
				} catch (Exception e) {
				}
			}
		};
		
		networkThread1.start();
		
        return rootView;
    }
}
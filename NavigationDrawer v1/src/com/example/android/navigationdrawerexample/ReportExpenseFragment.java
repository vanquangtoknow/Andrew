package com.example.android.navigationdrawerexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Adapter.ExpenseAdapter;
import Adapter.ListAdapter;
import DTO.Item;
import DTO.OrderExpense;
import WS.WCFNail;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ReportExpenseFragment extends Fragment{
	public ReportExpenseFragment() {
        // Empty constructor required for fragment subclasses
    }
	
	private ExpenseAdapter adapter;
	private ArrayList<OrderExpense> arrayItem;
	
	private RelativeLayout parent;
	public String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
	public ProgressBar progress, progress1;
	public Calendar myCalendar = Calendar.getInstance();
	public EditText start,end, single;
	public Button okButton, day, month, year;
	public TableRow row1, row2;
	public Switch togg;
	public TableRow buttonContainer;
	public TextView title;

	DatePickerDialog.OnDateSetListener startTime = new DatePickerDialog.OnDateSetListener() {
		@Override 
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			 myCalendar.set(Calendar.YEAR, arg1);
		        myCalendar.set(Calendar.MONTH, arg2);
		        myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
		        updateLabel1();
		}

	};
	DatePickerDialog.OnDateSetListener endTime = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			 myCalendar.set(Calendar.YEAR, arg1);
	        myCalendar.set(Calendar.MONTH, arg2);
	        myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
	        updateLabel2();
		}

	};
	DatePickerDialog.OnDateSetListener singleTime = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			 myCalendar.set(Calendar.YEAR, arg1);
	        myCalendar.set(Calendar.MONTH, arg2);
	        myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
	        updateLabel3();
		}

	};
	private void updateLabel1() {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);

	    start.setText(sdf.format(myCalendar.getTime()));
	    start.setTag(xmlSdf.format(myCalendar.getTime()));
	    }
	private void updateLabel2() {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
	    
	    end.setText(sdf.format(myCalendar.getTime()));
	    end.setTag(xmlSdf.format(myCalendar.getTime()));
	    }
	private void updateLabel3() {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
	    
	    single.setText(sdf.format(myCalendar.getTime()));
	    single.setTag(xmlSdf.format(myCalendar.getTime()));
	    }
	public String rs="";

	public void addrow(final OrderExpense item) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				arrayItem.add(item);
				adapter.notifyDataSetChanged();
			}
		});

	}

	public void getData(final String S, final String E, final View button, final View progressBar)
	{
		Thread networkThread = new Thread() {
			@Override
			public void run() {
				try {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getActivity(), "Get Data... Pls wait a moment", Toast.LENGTH_LONG).show();
							adapter.Clear();
							adapter.notifyDataSetChanged();
							togg.setEnabled(false);
							progressBar.setVisibility(View.VISIBLE);
							button.setVisibility(View.GONE);
						}
					});

					float total = 0;
					
					
					// tao class xu ly WS
					WCFNail WS = new WCFNail();
					// lay du leiu tu WS
					ArrayList<OrderExpense> tmp = new ArrayList<OrderExpense>();
					try {
						tmp = WS.getAllExpense(new ArrayList<String>() {
							{
								add(S);
								add(E);
							}
						});
						arrayItem.addAll(tmp);
						for( int i = 0; i < arrayItem.size() ; i ++)
						{
							total =  total + arrayItem.get(i).getMoney();
						}
						OrderExpense exp =  new OrderExpense();
						exp.setId(-1);
						exp.setDate("-1");
						exp.setName("Total");
						exp.setMoney(total);
						arrayItem.add(exp);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						Log.e("WSE", e1.getMessage());
					}
					
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							togg.setEnabled(true);
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
						}
					});

				} catch (Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							togg.setEnabled(true);
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
							Toast.makeText(getActivity(), "Internet Connection Problem !! Timeout..", Toast.LENGTH_LONG).show();
						}
					});
					Log.e("WSE", e.getMessage());
					
				}
			}
		};
		networkThread.start();

	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//lay layout cho fragment
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        getActivity().setTitle("EXPENSE REPORT");
        
       //set layout cho tung view
        parent = (RelativeLayout)rootView.findViewById(R.id.FullReportLayout);
        Log.e("frag","1");
		ListView listview = (ListView) rootView.findViewById(R.id.listView1);
		start = (EditText) rootView.findViewById(R.id.editText1);
		end = (EditText) rootView.findViewById(R.id.editText2);
		single = (EditText) rootView.findViewById(R.id.Single1);
		okButton = (Button) rootView.findViewById(R.id.OK);
		buttonContainer = (TableRow) rootView.findViewById(R.id.TableRow03);
		progress = (ProgressBar) rootView.findViewById(R.id.progressBar1);
		progress1 = (ProgressBar) rootView.findViewById(R.id.ProgressBar01);
		row1 = (TableRow) rootView.findViewById(R.id.tableRow1);
		row2 = (TableRow) rootView.findViewById(R.id.TableRow02);
		row1.setVisibility(View.GONE);
		progress.setVisibility(View.GONE);
		progress1.setVisibility(View.GONE);
		title = (TextView) rootView.findViewById(R.id.title);
		title.setText("EXPENSE REPORT");
		//lay ngay thang
		updateLabel1();
		updateLabel2();
		updateLabel3();
		//set event onclick cho tung button
		Log.e("frag", "2");
		day = (Button) rootView.findViewById(R.id.DayChoose1);
		month = (Button) rootView.findViewById(R.id.MonthChoose1);
		year = (Button) rootView.findViewById(R.id.YearChoose1);
		day.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("dayClick","1");
				
				getData(single.getTag().toString(), single.getTag().toString(), buttonContainer, progress1);
			}
		});
		month.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("monClick","1");
				Date dt1 = new Date();
				Date dt2 = new Date();
				try {
					dt1 = xmlSdf.parse(single.getTag().toString());
					dt2 = xmlSdf.parse(single.getTag().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("monClick","2");
				dt1.setDate(1);
				Log.e("monClick","3");
				dt2.setDate(myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Log.e("monClick","4");
				getData(xmlSdf.format(dt1), xmlSdf.format(dt2), buttonContainer, progress1);
			}
		});
		year.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("yearClick","1");
				Date dt1 = new Date();
				Date dt2 = new Date();
				try {
					dt1 = xmlSdf.parse(single.getTag().toString());
					dt2 = xmlSdf.parse(single.getTag().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("yearClick","2");
				dt1.setMonth(0);
				dt1.setDate(1);
				Log.e("yearClick","3");
				dt2.setMonth(11);
				dt2.setDate(31);
				Log.e("yearClick","4");
				getData(xmlSdf.format(dt1), xmlSdf.format(dt2), buttonContainer, progress1);
			}
		});
		togg = (Switch) rootView.findViewById(R.id.toggleButton1);
		togg.setTextOff("");
		togg.setTextOn("");
		
	
		togg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean on = ((Switch) v).isChecked();
			    
			    if (on) {
			    	row1.setVisibility(View.VISIBLE);
			    	row2.setVisibility(View.GONE);
			    } else {
			        // Disable vibrate
			    	row1.setVisibility(View.GONE);
			    	row2.setVisibility(View.VISIBLE);
			    }
			}
		});
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String S = start.getTag().toString();
				String E = end.getTag().toString();
				getData(S, E, okButton, progress);

			}
		});
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("startClick", "CLICK!");
				new DatePickerDialog(getActivity(), startTime, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		end.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("endClick", "CLICK!");
				new DatePickerDialog(getActivity(), endTime, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		single.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("singleClick", "CLICK!");
				new DatePickerDialog(getActivity(), singleTime, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		//kiem tra login
				if(((MainActivity)getActivity()).getLogin() == false )
				{
					Log.e("NOTLOGIN", "1");
					disable(parent);
				}
		//tao adapter cho listview
		Log.e("frag", "3");
		arrayItem = new ArrayList<OrderExpense>();
		adapter = new ExpenseAdapter(getActivity(), arrayItem);
		listview.setAdapter(adapter);
		Log.e("frag", "4");
		try {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					adapter.notifyDataSetChanged();
					Log.e("frag", "00");
				}
			});
		} catch (Exception e) {
			Log.e("err", e.getMessage());
			// TODO: handle exception
		}

		Log.e("frag", "5");
		return rootView;
    }
    public void disable ( View v)
	{
		if(v instanceof ViewGroup)
		{
			for(int i = 0 ; i < ((ViewGroup)v).getChildCount() ; i++ )
			{
				disable (((ViewGroup)v).getChildAt(i));
			}
		}
		else
		{
			v.setEnabled(false);
		}
	}
}

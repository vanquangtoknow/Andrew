package com.example.android.navigationdrawerexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.AutoCompleteEmployee;
import Adapter.ListCheckInfoTableTempAdapter;
import Adapter.ListTrackingTempAdapter;
import DAO.CheckInfoTableDAO;
import DAO.CheckTableDao;
import DAO.EmployeeDAO;
import DAO.TicketDAO;
import DTO.CheckInfoTableTemp;
import DTO.Employee;
import DTO.Ticket;
import DTO.TrackingTemp;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.PendingIntent.OnFinished;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class fgm_tracking extends Fragment{
	
	
	//Khai bao bien de dung 
	private boolean IsStop = false;
	
	
	private Calendar myCalendar = Calendar.getInstance();
	private EditText edtDateSelect;
	private ListView lvTracking;
	private TextView tvDatePresent;
	private AutoCompleteTextView autoEmployeeSearch;
	
	private ArrayList<Employee> listEmployee = new ArrayList<Employee>();
	private ArrayList<TrackingTemp> listTracking = new ArrayList<TrackingTemp>();
	private ArrayList<TrackingTemp> listTrackingSaving = new ArrayList<TrackingTemp>();
	//-------Khai bao Cac Adapter
	private ListTrackingTempAdapter adapterListTracking;
	private AutoCompleteEmployee adapterAutoEmployeeSearch;
	//-------Khai bao DAO
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private TicketDAO ticketDAO  = new TicketDAO();
	
	private boolean flag = false;
	
	
	private ArrayList<CheckInfoTableTemp> listCheckInfoTableTemp = new ArrayList<CheckInfoTableTemp>();
	ProgressDialog ringProgressDialog = null;
	boolean flag_ringprogress = false;
	private int idEmployee = -1;
	private ImageButton btnRefresh;
	
	String myFormatDate = "dd/MM/yyyy"; // In which you need put here
	String XMLFormatDate = "yyyy-MM-dd'T'HH:mm:ss";
	SimpleDateFormat sdfDate = new SimpleDateFormat(myFormatDate, Locale.US);
	SimpleDateFormat xmlSdfDate = new SimpleDateFormat(XMLFormatDate, Locale.US);
	
	private Timer timer = new Timer();
	private TimerTask timerTask;
	
	public fgm_tracking() {
		
	}
	private int timesCalled = 1;
	DatePickerDialog.OnDateSetListener TimeSelect = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			myCalendar.set(Calendar.YEAR, arg1);
			myCalendar.set(Calendar.MONTH, arg2);
			myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
			updateLabel1();
			if (arg0.isShown()) {
				autoEmployeeSearch.setTag(-1);
				autoEmployeeSearch.setText("");
				ringProgressDialog = new ProgressDialog(getActivity());
				ringProgressDialog.setTitle("Please wait");
				ringProgressDialog.setMessage("Loading...");
				ringProgressDialog.setCancelable(false);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				
				ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	flag = true;
				        dialog.dismiss();
				    }
				});
				
				ringProgressDialog.show();
				flag_ringprogress = true;
				Log.i("Datepicker click", "date: " + edtDateSelect.getTag().toString() + "----" + arg3);
				getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
		    }
			
			
			/*timesCalled += 1;
			if(timesCalled%2==0)
			{
				autoEmployeeSearch.setTag(-1);
				autoEmployeeSearch.setText("");
				ringProgressDialog = new ProgressDialog(getActivity());
				ringProgressDialog.setTitle("Please wait");
				ringProgressDialog.setMessage("Loading...");
				ringProgressDialog.setCancelable(false);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				
				ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	flag = true;
				        dialog.dismiss();
				    }
				});
				
				ringProgressDialog.show();
				flag_ringprogress = true;
				Log.i("Datepicker click", "date: " + edtDateSelect.getTag().toString() + "----" + arg3);
				getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
			}*/
		}
	};
	private void updateLabel1() {
		String myFormat = "dd/MM/yyyy"; // In which you need put here
		String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
		edtDateSelect.setText(sdf.format(myCalendar.getTime()));
		edtDateSelect.setTag(xmlSdf.format(myCalendar.getTime()));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tracking, container,
				false);
		getActivity().setTitle("TRACKING");
		lvTracking = (ListView) rootView.findViewById(R.id.lvCheckTable);
		edtDateSelect = (EditText) rootView.findViewById(R.id.edtDateSelect);
		autoEmployeeSearch = (AutoCompleteTextView) rootView.findViewById(R.id.autoEmployeeSearch);
		btnRefresh = (ImageButton) rootView.findViewById(R.id.imgbtnRefresh);
		tvDatePresent = (TextView) rootView.findViewById(R.id.tvDatePresent);
		
		timerTask = new TimerTask() {
			 @Override
			 public void run() {
				 if(IsStop==true)
				 {
					 this.cancel();
					 return;
				 }
				 getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tvDatePresent.setText("Date Present: " + getCurrentTime());
					}
				});
			 }
			};
			timer.schedule(timerTask, 0, 100000);
		
			
	
		Toast.makeText(getActivity(), Boolean.toString(isOnline()), Toast.LENGTH_LONG).show();
		if(isOnline()==true)
		{
			getAllEmployee();
		}else
		{
			showDialogMessage("Don't have internet");
		}
		adapterAutoEmployeeSearch = new AutoCompleteEmployee(getActivity(), R.layout.autocomplete_item,listEmployee);
		autoEmployeeSearch.setThreshold(1);
		autoEmployeeSearch.setAdapter(adapterAutoEmployeeSearch);
		edtDateSelect.setTag(xmlSdfDate.format(myCalendar.getTime()));
		edtDateSelect.setText(sdfDate.format(myCalendar.getTime()));
		tvDatePresent.setText("Date present: " + sdfDate.format(myCalendar.getTime()));

		autoEmployeeSearch.setTag(-1);
		ringProgressDialog = new ProgressDialog(getActivity());
		ringProgressDialog.setTitle("Please wait");
		ringProgressDialog.setMessage("Loading...");
		ringProgressDialog.setCancelable(false);
		ringProgressDialog.setCanceledOnTouchOutside(false);
		ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	flag = true;
		        dialog.dismiss();
		    }
		});
		ringProgressDialog.show();
		flag_ringprogress = true;
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ringProgressDialog = new ProgressDialog(getActivity());
				ringProgressDialog.setTitle("Please wait");
				ringProgressDialog.setMessage("Loading...");
				ringProgressDialog.setCancelable(false);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	flag = true;
				        dialog.dismiss();
				    }
				});
				ringProgressDialog.show();
				flag_ringprogress = true;
				autoEmployeeSearch.setTag(-1);
				autoEmployeeSearch.setText("");
				getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
			}
		});
		autoEmployeeSearch.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Employee a = (Employee) arg0.getItemAtPosition(arg2);
				autoEmployeeSearch.setText(a.getstrName());
				autoEmployeeSearch.setTag(a.getID_Employee());
				
				Log.i("DatePickerDialog", "get tag: "+ edtDateSelect.getTag().toString());
				String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
				String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
				Log.i("Date select", newS + "---" + newE);
				Log.i("autoEmployeeSearch click", a.getID_Employee() + "time: " +newS + "---"+ newE);
				if(a.getID_Employee()!=-1)
				{
					getListTrackingByIdEmployee(a.getID_Employee());
				}else
				{
					getListTrackingByIdAll();
				}
			}
		});
		
		
		autoEmployeeSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(s.length()==0)
				{
					getListTrackingByIdAll();
				}
				
			}
		});
		/*edtDateSelect.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				DatePickerDialog a = new DatePickerDialog(getActivity(), TimeSelect, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH));
				a.show();
				return false;
			}
		});*/
		edtDateSelect.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				DatePickerDialog a = new DatePickerDialog(getActivity(), TimeSelect, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH));
				/*a.setButton3("Load", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Toast.makeText(getActivity(), "Loading...",Toast.LENGTH_SHORT).show();
					}
				});*/
				a.show();
				return true;
			}
		});
		adapterListTracking = new ListTrackingTempAdapter(getActivity(), listTracking);
		lvTracking.setAdapter(adapterListTracking);
		return rootView;
	}
	private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String formattedTime = sdfDate.format(c.getTime());
        return formattedTime;
        
    }
	public void getAllEmployee()
	{
		Thread threadEmployee = new Thread()
		{
			@Override
			public void run() {
				Log.i("getAllEmployee",""+listEmployee.size());
				listEmployee.addAll(employeeDAO.getAllEmployeeDAO());
				getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
			}
		};
		threadEmployee.start();
	}
	
	
	public void setViewForListTracking()
	{
		for(int i=1;i<listTracking.size();i++)
		{
			listTracking.get(i).setIsView(true);
			int temp = i+1;
			if(temp<listTracking.size())
			{
				if(listTracking.get(i).getTotal()!=listTracking.get(temp).getTotal())
				{
					break;
				}
			}
		}
	}
	public void getListTrackingByIdAll()
	{
		listTracking.clear();
		Log.i("getListTrackingByIdEmployee", "size listtracking: clear"+ listTracking.size());
		listTracking.addAll(listTrackingSaving);
		adapterListTracking.notifyDataSetChanged();
	}
	public void showDialogMessage(String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   dialog.dismiss();
                   }
               });
        builder.create().show();
	}
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	public void getListTrackingByIdEmployee(int id)
	{
		Log.i("getListTrackingByIdEmployee","start : id: "+id+" size: "+ listTracking.size());
		ArrayList<TrackingTemp> listHadAdd = new ArrayList<TrackingTemp>();
		for(int i = 0;i<listTrackingSaving.size();i++)
		{
			if(listTrackingSaving.get(i).getIdEmployee()==id)
			{
				listHadAdd.add(listTrackingSaving.get(0));
				listHadAdd.add(listTrackingSaving.get(i));
				break;
			}
		}
		listTracking.clear();
		Log.i("getListTrackingByIdEmployee", "size listtracking: clear"+ listTracking.size());
		listTracking.addAll(listHadAdd);
		Log.i("getListTrackingByIdEmployee", "size listtracking: add"+ listTracking.size());
		
		adapterListTracking.notifyDataSetChanged();
	}
	@Override
    public void onPause()
    {
    	IsStop = true;
    	super.onPause();
    }
	@Override
	public void onDestroyView() {
		IsStop = true;
		super.onDestroyView();
	}
	@Override
	public void onDestroy() {
		IsStop = true;
		super.onDestroy();
	}
	public void getListTracking(final String startday,final String endday)
	{
		listTracking.clear();
		listTracking.add(new TrackingTemp("",-1,"",-Float.MAX_VALUE,false));
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapterListTracking.notifyDataSetChanged();
			}
		});
		final String newS = startday.substring(0, 10) + "T00:00:00";
		final String newE = endday.substring(0, 10) + "T23:59:59";
			Thread getTrackingFromIdEmployee  = new Thread()
			{
				@Override
				public void run() {
					
					if(listEmployee.size()>0)
					{
						for(int i=0;i<listEmployee.size();i++)
						{
							TrackingTemp trackingtemp = new TrackingTemp();
							trackingtemp.setIdEmployee(listEmployee.get(i).getID_Employee());
							trackingtemp.setNameEmployee(listEmployee.get(i).getstrName());
							trackingtemp.setTime(startday.substring(0,10));
							if(flag==true)
							{
								flag = false;
								listTrackingSaving = (ArrayList<TrackingTemp>) listTracking.clone();
								if(IsStop==true)
								{
									this.interrupt();
									return;
								}
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										adapterListTracking.notifyDataSetChanged();
										if(flag_ringprogress==true)
										{
											ringProgressDialog.dismiss();
											flag_ringprogress=false;
										}
									}
								});
								this.interrupt();
								return;
							}
							Log.i("getTracking", "Id Employee: " + listEmployee.get(i).getID_Employee()+ " date: " + startday + "--" + endday);
							float totalOfEmployee = ticketDAO.getTotalMomneyOfEmployeeId(listEmployee.get(i).getID_Employee(), newS, newE);
							trackingtemp.setTotal(totalOfEmployee);
							listTracking.add(trackingtemp);
							final int k = i;
							if(IsStop==true)
							{
								this.interrupt();
								return;
							}
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapterListTracking.notifyDataSetChanged();
									if(flag_ringprogress==true)
									{
										ringProgressDialog.setMessage("Loading employee " + (k+1) +" in "+ listEmployee.size()+" employees.");
									}
								}
							});
							Log.i("getTracking", "Id Employee: " + listEmployee.get(i).getID_Employee() + " total:  " + totalOfEmployee);
							Collections.sort((List)listTracking, new Comparator<TrackingTemp>() {
							    public int compare(TrackingTemp a, TrackingTemp b) {
							        return (int)(a.getTotal() - b.getTotal());
							    }
							});
						}
						setViewForListTracking();
						listTrackingSaving = (ArrayList<TrackingTemp>) listTracking.clone();
						if(IsStop==true)
						{
							//this.interrupt();
							return;
						}
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								adapterListTracking.notifyDataSetChanged();
								if(flag_ringprogress==true)
								{
									ringProgressDialog.dismiss();
									flag_ringprogress=false;
								}
								
							}
						});
					}
				}
			};
			getTrackingFromIdEmployee.start();
			
	}
}

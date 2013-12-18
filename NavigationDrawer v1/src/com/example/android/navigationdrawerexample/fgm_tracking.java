package com.example.android.navigationdrawerexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class fgm_tracking extends Fragment{
	private Calendar myCalendar = Calendar.getInstance();
	private EditText edtDateSelect;
	private ListView lvTracking;
	private TextView tvDatePresent;
	private AutoCompleteTextView autoEmployeeSearch;
	
	private ArrayList<Employee> listEmployee = new ArrayList<Employee>();
	private ArrayList<TrackingTemp> listTracking = new ArrayList<TrackingTemp>();
	
	//-------Khai bao Cac Adapter
	private ListTrackingTempAdapter adapterListTracking;
	private AutoCompleteEmployee adapterAutoEmployeeSearch;
	//-------Khai bao DAO
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private TicketDAO ticketDAO  = new TicketDAO();
	
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
	DatePickerDialog.OnDateSetListener TimeSelect = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			myCalendar.set(Calendar.YEAR, arg1);
			myCalendar.set(Calendar.MONTH, arg2);
			myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
			updateLabel1();
			ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
			ringProgressDialog.setCancelable(true);
			flag_ringprogress = true;
			getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
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
		View rootView = inflater.inflate(R.layout.fragment_checkin_checout, container,
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
				 getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tvDatePresent.setText("Date Present: " + getCurrentTime());
					}
				});
			 }
			};
			timer.schedule(timerTask, 0, 100000);
		
		getAllEmployee();
		adapterAutoEmployeeSearch = new AutoCompleteEmployee(getActivity(), R.layout.autocomplete_item,listEmployee);
		autoEmployeeSearch.setThreshold(1);
		autoEmployeeSearch.setAdapter(adapterAutoEmployeeSearch);
		edtDateSelect.setTag(xmlSdfDate.format(myCalendar.getTime()));
		edtDateSelect.setText(sdfDate.format(myCalendar.getTime()));
		tvDatePresent.setText("Date present: " + sdfDate.format(myCalendar.getTime()));

		autoEmployeeSearch.setTag(-1);
		ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading data...", true);
		ringProgressDialog.setCancelable(true);
		flag_ringprogress = true;
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading data...", true);
				ringProgressDialog.setCancelable(true);
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
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				flag_ringprogress = true;
				Log.i("autoEmployeeSearch click", a.getID_Employee() + "time: " +newS + "---"+ newE);
				if(a.getID_Employee()!=-1)
				{
					getListTrackingByIdEmployee(a.getID_Employee());
				}else
				{
					getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
					
				}
			}
		});
		edtDateSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(getActivity(), TimeSelect, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
	public void getListTrackingByIdEmployee(int id)
	{
		int stt = -1;
		Log.i("getListTrackingByIdEmployee","start : "+ listTracking.size());
		for(int i = 0;i<listTracking.size();i++)
		{
			if(listTracking.get(i).getIdEmployee()==id)
			{
				stt = i;
				break;
			}
		}
		Log.i("getListTrackingByIdEmployee","bandau: "+ listTracking.size());
		if(listTracking.size()>0)
		{
			TrackingTemp temp = null;
			if(stt!=-1)
			{
				temp = listTracking.get(stt);
			}
			listTracking.clear();
			listTracking.add(new TrackingTemp("",-1,"",-Float.MAX_VALUE,false));
			if(temp!=null)
			{
				listTracking.add(temp);
			}
			Log.i("getListTrackingByIdEmployee","sau do: "+ listTracking.size());
			setViewForListTracking();
		}
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				adapterListTracking.setSelectedIndex(0);
				adapterListTracking.notifyDataSetChanged();
				if(flag_ringprogress==true)
				{
					ringProgressDialog.dismiss();
					flag_ringprogress=false;
				}
				
			}
		});
	}
	public void getListTracking(final String startday,final String endday)
	{
		listTracking.clear();
		listTracking.add(new TrackingTemp("",-1,"",-Float.MAX_VALUE,false));
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
							trackingtemp.setTime(startday);
							Log.i("getTracking", "Id Employee: " + listEmployee.get(i).getID_Employee()+ " date: " + startday + "--" + endday);
							float totalOfEmployee = ticketDAO.getTotalMomneyOfEmployeeId(listEmployee.get(i).getID_Employee(), newS, newE);
							trackingtemp.setTotal(totalOfEmployee);
							listTracking.add(trackingtemp);
							Log.i("getTracking", "Id Employee: " + listEmployee.get(i).getID_Employee() + " total:  " + totalOfEmployee);
							Collections.sort((List)listTracking, new Comparator<TrackingTemp>() {
							    public int compare(TrackingTemp a, TrackingTemp b) {
							        return (int)(a.getTotal() - b.getTotal());
							    }
							});
						}
						setViewForListTracking();
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								adapterListTracking.setSelectedIndex(0);
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

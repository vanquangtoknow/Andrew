package com.example.android.navigationdrawerexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.AutoCompleteCheckInfoTable;
import Adapter.AutoCompleteEmployee;
import Adapter.ListCheckInfoTableTempAdapter;
import DAO.CheckInfoTableDAO;
import DAO.CheckTableDao;
import DAO.EmployeeDAO;
import DTO.CheckInfoTable;
import DTO.CheckInfoTableTemp;
import DTO.CheckTableTemp;
import DTO.Employee;
import WS.WCFNail;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class fgm_manager_checkincheckout extends Fragment {
	
	private Calendar myCalendar = Calendar.getInstance();
	private EditText edtDateSelect;
	private ListView lvCheckTable;
	private TextView tvDatePresent;
	private ListCheckInfoTableTempAdapter adapterCheckInfoTableTemp;
	private AutoCompleteTextView autoEmployeeSearch;
	private AutoCompleteEmployee adapterAutoEmployeeSearch;
	private ArrayList<Employee> listEmployee = new ArrayList<Employee>();
	private CheckTableDao checkTableDao  = new CheckTableDao();
	private CheckInfoTableDAO checkInfoTableDAO = new CheckInfoTableDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private ArrayList<CheckInfoTableTemp> listCheckInfoTableTemp = new ArrayList<CheckInfoTableTemp>();
	ProgressDialog ringProgressDialog = null;
	boolean flag_ringprogress = false;
	private int idEmployee = -1;
	private ImageButton btnRefresh;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	
	String myFormatDate = "dd/MM/yyyy"; // In which you need put here
	String XMLFormatDate = "yyyy-MM-dd'T'HH:mm:ss";
	SimpleDateFormat sdfDate = new SimpleDateFormat(myFormatDate, Locale.US);
	SimpleDateFormat xmlSdfDate = new SimpleDateFormat(XMLFormatDate, Locale.US);
	public fgm_manager_checkincheckout() {
	}
	DatePickerDialog.OnDateSetListener TimeSelect = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			myCalendar.set(Calendar.YEAR, arg1);
			myCalendar.set(Calendar.MONTH, arg2);
			myCalendar.set(Calendar.DAY_OF_MONTH, arg3);
			updateLabel1();
			Log.i("DatePickerDialog", "get tag: "+ autoEmployeeSearch.getTag().toString());

			String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
			String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
			Log.i("Date select", newS + "---" + newE);
			ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
			ringProgressDialog.setCancelable(true);
			flag_ringprogress = true;
			getListCheckTableInfoWithTime(edtDateSelect.getTag().toString());
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
		getActivity().setTitle("CHECKIN-CHECKOUT");
		getAllEmployee();
		lvCheckTable = (ListView) rootView.findViewById(R.id.lvCheckTable);
		edtDateSelect = (EditText) rootView.findViewById(R.id.edtDateSelect);
		autoEmployeeSearch = (AutoCompleteTextView) rootView.findViewById(R.id.autoEmployeeSearch);
		btnRefresh = (ImageButton) rootView.findViewById(R.id.imgbtnRefresh);
		tvDatePresent = (TextView) rootView.findViewById(R.id.tvDatePresent);
		adapterCheckInfoTableTemp = new ListCheckInfoTableTempAdapter(getActivity(), listCheckInfoTableTemp);
		lvCheckTable.setAdapter(adapterCheckInfoTableTemp);
		ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
		ringProgressDialog.setCancelable(true);
		flag_ringprogress = true;
		edtDateSelect.setTag(xmlSdfDate.format(myCalendar.getTime()));
		edtDateSelect.setText(sdfDate.format(myCalendar.getTime()));
		tvDatePresent.setText("Date present: " + sdfDate.format(myCalendar.getTime()));
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
		
		autoEmployeeSearch.setTag(-1);
		getListCheckTableInfoWithTime(edtDateSelect.getTag().toString());
		adapterAutoEmployeeSearch = new AutoCompleteEmployee(getActivity(), R.layout.autocomplete_item,listEmployee);
		autoEmployeeSearch.setThreshold(1);
		autoEmployeeSearch.setAdapter(adapterAutoEmployeeSearch);
		
		edtDateSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(getActivity(), TimeSelect, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				/*idEmployee = -1;
				String s = autoEmployeeSearch.getText().toString();
				if(autoEmployeeSearch.getText()!=null)
				{
					if(autoEmployeeSearch.getText().toString().compareTo("")!=0)
					{
						idEmployee = Integer.parseInt(autoEmployeeSearch.getText().toString());
					}
				}*/
			}
		});
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
				String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
				Log.i("Date select", newS + "---" + newE);
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				flag_ringprogress = true;
				getListCheckTableInfoWithTime(edtDateSelect.getTag().toString());
			}
		});
		autoEmployeeSearch.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Employee a = (Employee) arg0.getItemAtPosition(arg2);
				autoEmployeeSearch.setText(a.getstrName());
				autoEmployeeSearch.setTag(a.getID_Employee());
				
				String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
				String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
				Log.i("Date select", newS + "---" + newE);
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				flag_ringprogress = true;
				Log.i("autoEmployeeSearch click", a.getID_Employee() + "time: " +newS + "---"+ newE);
				
				getListCheckTableByIdEmployee(a.getID_Employee());
			}
		});
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
			}
		};
		threadEmployee.start();
	}
	public void getListCheckTableByIdEmployee(int IdEmployee)
	{
		ArrayList<CheckInfoTableTemp> dsTemp = new ArrayList<CheckInfoTableTemp>();
		for(int i=0;i<listCheckInfoTableTemp.size();i++)
		{
			if(listCheckInfoTableTemp.get(i).getId_Employee()==IdEmployee)
			{
				dsTemp.add(listCheckInfoTableTemp.get(i));
			}
		}
		listCheckInfoTableTemp.clear();
		listCheckInfoTableTemp.addAll(dsTemp);
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				adapterCheckInfoTableTemp.setSelectedIndex(0);
				adapterCheckInfoTableTemp.notifyDataSetChanged();
				if(flag_ringprogress==true)
				{
					ringProgressDialog.dismiss();
					flag_ringprogress=false;
				}
			}
		});
	}
	public void getListCheckTableInfoWithTimeAndEmployee(final int idEmployee, final String datestart, final String dateend)
	{
		listCheckInfoTableTemp.clear();
		Thread getListCheckTableInfoWithTimeAndEmployee = new Thread()
		{
			@Override
			public void run() {
				ArrayList<CheckInfoTable> listCheckInfoTable = checkInfoTableDAO.getAllChekInfoWithEmployee(idEmployee, datestart, dateend);
				if(listCheckInfoTable!=null)
				{
					Log.i("getListCheckTableInfoWithTimeAndEmployee","size of listChecktableInfo: "+ listCheckInfoTable.size());
				}
				listCheckInfoTableTemp.addAll(convertToCheckTableInfoTemp(listCheckInfoTable, checkTableDao.getNailservice()));
				Log.i("getListCheckTableInfoWithTimeAndEmployee","size of listChecktableInfoTemp: "+ listCheckInfoTableTemp.size());
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterCheckInfoTableTemp.setSelectedIndex(0);
						adapterCheckInfoTableTemp.notifyDataSetChanged();
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
					}
				});
			}
		};
		getListCheckTableInfoWithTimeAndEmployee.start();
	}
	public void getListCheckTableInfoWithTime(final String time)
	{
		listCheckInfoTableTemp.clear();
		Thread threadGetCheckTable = new Thread()
		{
			@Override
			public void run() {
				Log.i("getListCheckTableInfoWithTime", "time: " + time);
				ArrayList<CheckInfoTable> listCheckInfoTable = checkInfoTableDAO.getAllChekcTableInfoWithTime(time);
				Log.i("getListCheckTableInfoWithTime","size of listChecktableInfo: "+ listCheckInfoTable.size());
				listCheckInfoTableTemp.addAll(convertToCheckTableInfoTemp(listCheckInfoTable, checkTableDao.getNailservice()));
				Log.i("getListCheckTableInfoWithTime","size of listChecktableInfoTemp: "+ listCheckInfoTableTemp.size());
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterCheckInfoTableTemp.setSelectedIndex(0);
						adapterCheckInfoTableTemp.notifyDataSetChanged();
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
					}
				});
			}
		};
		threadGetCheckTable.start();
	}
	public ArrayList<CheckInfoTableTemp> convertToCheckTableInfoTemp(ArrayList<CheckInfoTable> listCheckInfoTable, WCFNail nailservice)
	{
		Log.i("convertToCheckTableInfoTemp","size list checkinfo: " + listCheckInfoTable.size());
		ArrayList<CheckInfoTableTemp> listCheckInfoTableTemp  =  new ArrayList<CheckInfoTableTemp>();
		if(listCheckInfoTable!=null)
		{
			for(int i=0;i<listCheckInfoTable.size();i++)
			{
				Log.i("convertToCheckTableInfoTemp","idemployee: "+listCheckInfoTable.get(i).getId_Employee());
				CheckInfoTableTemp temp = listCheckInfoTable.get(i).convertToCheckTableInfoTemp(nailservice);
				Log.i("convertToCheckTableInfoTemp","list checkinfo "+i+"idemployee " + temp.getEmployeeName());
				listCheckInfoTableTemp.add(temp);
			}
		}
		return listCheckInfoTableTemp;
	}

	
}

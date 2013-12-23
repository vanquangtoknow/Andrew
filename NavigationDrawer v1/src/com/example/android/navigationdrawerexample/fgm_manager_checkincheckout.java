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
import DTO.TrackingTemp;
import WS.WCFNail;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
	// Khai bao bien de thoat khoi thread
	private boolean IsStop = false;
	
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
	private ArrayList<CheckInfoTableTemp> listCheckInfoTableTempSaving = new ArrayList<CheckInfoTableTemp>();
	ProgressDialog ringProgressDialog = null;
	boolean flag_ringprogress = false;
	private int idEmployee = -1;
	private ImageButton btnRefresh;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	
	private boolean flag = false;
	private boolean flagCancel = false;
	
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
			/*Log.i("DatePickerDialog", "get tag: "+ autoEmployeeSearch.getTag().toString());

			String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
			String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
			Log.i("Date select", newS + "---" + newE);
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
			    	if(flagCancel==true)
			    	{
			    		flag = true;
			    		flagCancel = false;
			    		dialog.dismiss();
			    	}
			    }
			});
			ringProgressDialog.show();
			flag_ringprogress = true;
			getListCheckTableInfoWithTime(edtDateSelect.getTag().toString());*/
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

		ringProgressDialog = new ProgressDialog(getActivity());
		ringProgressDialog.setTitle("Please wait");
		ringProgressDialog.setMessage("Loading...");
		ringProgressDialog.setCancelable(false);
		ringProgressDialog.setCanceledOnTouchOutside(false);
		ringProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	if(flagCancel==true)
		    	{
		    		flag = true;
		    		flagCancel = false;
		    		dialog.dismiss();
		    	}
		    }
		});
		ringProgressDialog.show();
		flag_ringprogress = true;
		edtDateSelect.setTag(xmlSdfDate.format(myCalendar.getTime()));
		edtDateSelect.setText(sdfDate.format(myCalendar.getTime()));
		tvDatePresent.setText("Date present: " + sdfDate.format(myCalendar.getTime()));
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
			}
		});
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String newS = edtDateSelect.getTag().toString().substring(0, 10) + "T00:00:00";
				String newE = edtDateSelect.getTag().toString().substring(0, 10) + "T23:59:59";
				Log.i("Date select", newS + "---" + newE);
				
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
				    	if(flagCancel==true)
				    	{
				    		flag = true;
				    		flagCancel = false;
				    		dialog.dismiss();
				    	}
				    }
				});
				ringProgressDialog.show();
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
					getListCheckTableByIdAllEmployee();
				}
				
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
	public void getListCheckTableByIdAllEmployee()
	{
		listCheckInfoTableTemp.clear();
		listCheckInfoTableTemp.addAll(listCheckInfoTableTempSaving);
		adapterCheckInfoTableTemp.notifyDataSetChanged();
	}
	public void getListCheckTableByIdEmployee(int IdEmployee)
	{
		ArrayList<CheckInfoTableTemp> listAdd = new ArrayList<CheckInfoTableTemp>();
		Log.i("getListCheckTableByIdEmployee", "size: "+listCheckInfoTableTempSaving.size() + "idemployee: "+ IdEmployee);
		for(int i=0;i<listCheckInfoTableTempSaving.size();i++)
		{
			if(listCheckInfoTableTempSaving.get(i).getId_Employee()==IdEmployee)
			{
				listAdd.add(listCheckInfoTableTempSaving.get(i));
			}
		}
		listCheckInfoTableTemp.clear();
		listCheckInfoTableTemp.add(new CheckInfoTableTemp("",-Integer.MAX_VALUE, -Integer.MAX_VALUE, "",""));
		listCheckInfoTableTemp.addAll(listAdd);
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
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterCheckInfoTableTemp.notifyDataSetChanged();
					}
				});
				Log.i("getListCheckTableInfoWithTime", "time: " + time);
				// Lay danh sach checktableinfo
				ArrayList<CheckInfoTable> listCheckInfoTable = checkInfoTableDAO.getAllChekcTableInfoWithTime(time);
				flagCancel = true;
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ringProgressDialog.setMessage("Loading info checkin - checkout...");
					}
				});
				if(listCheckInfoTable!=null)
				{
					Log.i("getListCheckTableInfoWithTime","size of listChecktableInfo: "+ listCheckInfoTable.size());
				}
				listCheckInfoTableTemp.add(new CheckInfoTableTemp("",-Integer.MAX_VALUE, -Integer.MAX_VALUE, "",""));
				if(listCheckInfoTable!=null)
				{
					for(int i=0;i<listCheckInfoTable.size();i++)
					{
						if(flag==true)
						{
							listCheckInfoTableTempSaving = (ArrayList<CheckInfoTableTemp>) listCheckInfoTableTemp.clone();
							flag = false;
							this.interrupt();
							if(IsStop==true)
							{
								this.interrupt();
								return;
							}
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapterCheckInfoTableTemp.notifyDataSetChanged();
									if(flag_ringprogress==true)
									{
										ringProgressDialog.dismiss();
										flag_ringprogress=false;
									}
								}
							});
							return;
							
						}
						listCheckInfoTableTemp.add(convertFromCheckTableInfo(listCheckInfoTable.get(i), checkTableDao.getNailservice()));
					}
					listCheckInfoTableTempSaving = (ArrayList<CheckInfoTableTemp>) listCheckInfoTableTemp.clone();
				}else
				{
					listCheckInfoTableTempSaving = new ArrayList<CheckInfoTableTemp>();
				}
				if(IsStop==true)
				{
					//this.interrupt();
					return;
				}
				if(IsStop==true)
				{
					this.interrupt();
					return;
				}
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
						flagCancel = false;	// trang thai mac dinh
					}
				});
				
			}
		};
		
		threadGetCheckTable.start();
	}
	public CheckInfoTableTemp convertFromCheckTableInfo(CheckInfoTable checkInfoTable, WCFNail nailservice)
	{
		Log.i("convertToCheckTableInfoTemp","idemployee: "+checkInfoTable.getId_Employee());
		CheckInfoTableTemp temp = checkInfoTable.convertToCheckTableInfoTemp(nailservice);
		temp.setCheckIn(temp.getCheckIn().substring(11, 19));
		temp.setCheckOut(temp.getCheckOut().substring(11, 19));
		return temp;
	}
	public ArrayList<CheckInfoTableTemp> convertToCheckTableInfoTemp(ArrayList<CheckInfoTable> listCheckInfoTable, WCFNail nailservice)
	{
		if(listCheckInfoTable!=null)
		{
			Log.i("convertToCheckTableInfoTemp","size list checkinfo: " + listCheckInfoTable.size());
		}
		ArrayList<CheckInfoTableTemp> listCheckInfoTableTemp  =  new ArrayList<CheckInfoTableTemp>();
		if(listCheckInfoTable!=null)
		{
			for(int i=0;i<listCheckInfoTable.size();i++)
			{
				Log.i("convertToCheckTableInfoTemp","idemployee: "+listCheckInfoTable.get(i).getId_Employee());
				CheckInfoTableTemp temp = listCheckInfoTable.get(i).convertToCheckTableInfoTemp(nailservice);
				temp.setCheckIn(temp.getCheckIn().substring(11, 19));
				temp.setCheckOut(temp.getCheckOut().substring(11, 19));
				Log.i("convertToCheckTableInfoTemp","list checkinfo "+i+"idemployee " + temp.getEmployeeName());
				listCheckInfoTableTemp.add(temp);
			}
		}
		
		
		return listCheckInfoTableTemp;
	}
	@Override
	public void onPause() {
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
}

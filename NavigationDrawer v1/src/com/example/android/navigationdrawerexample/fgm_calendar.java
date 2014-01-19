package com.example.android.navigationdrawerexample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Adapter.AutoCompleteEmployee;
import Adapter.ListBaseAdapter;
import Adapter.ListCalendarAdapter;
import Adapter.TicketEditAdapter;
import DAO.CalendarDAO;
import DAO.EmployeeDAO;
import DTO.Employee;
import DTO.ItemInfo;
import DTO.ItemTicketAdapter;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
public class fgm_calendar extends Fragment {
		private boolean IsStop = false;
		private Calendar myCalendar = Calendar.getInstance();
		private EditText edtDateSelect;
		private ListView lvCalendar;
		private AutoCompleteTextView autoEmployeeSearch;
		
		private ArrayList<Employee> listEmployee = new ArrayList<Employee>();
		private ArrayList<ItemInfo> listCalendar = new ArrayList<ItemInfo>();
		//private ArrayList<TrackingTemp> listTrackingSaving = new ArrayList<TrackingTemp>();
		//-------Khai bao Cac Adapter
		private ListCalendarAdapter adapterListCalendar;
		private AutoCompleteEmployee adapterAutoEmployeeSearch;
		//-------Khai bao DAO
		private EmployeeDAO employeeDAO = new EmployeeDAO();
		private CalendarDAO calendarDAO = new CalendarDAO();
		//private TicketDAO ticketDAO  = new TicketDAO();
		//------Khai bao mang chua cac action them xoa sua
		private String[] commandAction=new String[] {"Add","Edit","Delete"};
		private int actionSelected = 0;
		private boolean flag = false;
		//-----thoi gian duoc chon
		private int controlSelected = -1;
		private String messageSelected = "";
		private String timestartSelected = "";
		private String timeendSelected = "";
		
		
		ProgressDialog ringProgressDialog = null;
		boolean flag_ringprogress = false;
		
		String myFormatDate = "dd/MM/yyyy"; // In which you need put here
		String XMLFormatDate = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdfDate = new SimpleDateFormat(myFormatDate, Locale.US);
		SimpleDateFormat xmlSdfDate = new SimpleDateFormat(XMLFormatDate, Locale.US);
		
		public fgm_calendar() {
			
		}
		private int timesCalled = 1;
		TimePickerDialog.OnTimeSetListener TimeSelected = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String strHour = "";
				String strMinute = "";
				if(hourOfDay<10)
				{
					strHour = "0" + Integer.toString(hourOfDay);
				}else
				{
					strHour = hourOfDay + "";
				}
				if(minute<10)
				{
					strMinute = "0" + Integer.toString(minute);
				}else
				{
					strMinute = strMinute + "";
				}
				if(controlSelected!=-1)
				{
					switch (controlSelected) {
					case 1:
						dlg_edtTimestart_Add.setText(strHour + ":" + strMinute);
						break;
					case 2:
						dlg_edtTimeend_Add.setText(strHour + ":" + strMinute);
					default:
						break;
					}
				}
			}
		};
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
					
					ringProgressDialog.show();
					flag_ringprogress = true;
					Log.i("Datepicker click", "date: " + edtDateSelect.getTag().toString() + "----" + arg3);
					//getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
					getAllListItemInfoOfCalendarByDay(edtDateSelect.getTag().toString());
			    }
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
			View rootView = inflater.inflate(R.layout.fragment_calendar, container,
					false);
			getActivity().setTitle("TRACKING");
			lvCalendar = (ListView) rootView.findViewById(R.id.lvCalendar);
			edtDateSelect = (EditText) rootView.findViewById(R.id.edtDateSelect);
			autoEmployeeSearch = (AutoCompleteTextView) rootView.findViewById(R.id.autoEmployeeSearch);
			adapterAutoEmployeeSearch = new AutoCompleteEmployee(getActivity(), R.layout.autocomplete_item,listEmployee);
			autoEmployeeSearch.setThreshold(1);
			autoEmployeeSearch.setAdapter(adapterAutoEmployeeSearch);
			getAllEmployee();
			//-----hien thi ngay hien tai va luu trong tag
			edtDateSelect.setTag(xmlSdfDate.format(myCalendar.getTime()));
			edtDateSelect.setText(sdfDate.format(myCalendar.getTime()));

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
			listCalendar.add(new ItemInfo("acdac", "adaca", "vanquang", 0, 0, 0, 0));
			adapterListCalendar = new ListCalendarAdapter(getActivity(), listCalendar);
			lvCalendar.setAdapter(adapterListCalendar);
			getAllListItemInfoOfCalendarByDay(xmlSdfDate.format(myCalendar.getTime()));
			
			
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
						//getListTrackingByIdEmployee(a.getID_Employee());
					}else
					{
						//getListTrackingByIdAll();
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
						//getListTrackingByIdAll();
					}
					
				}
			});
			lvCalendar.setOnItemLongClickListener(new OnItemLongClickListener() 
			{
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) 
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				    builder.setTitle("Choose action");
				    actionSelected = 0;
				    builder.setSingleChoiceItems(commandAction, 0, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	Log.i("choose int", whichButton +"");
				        	actionSelected = whichButton;
				        }
				    });
				    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() 
				    {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if(actionSelected>=0&&actionSelected<=2)
				        	{
				        		//add iteminfo
				        		switch (actionSelected) {
								case 0:
									showFormAddNewItemInfo("", "08:08", "10:10", 0);
									break;
								case 1:
									String time1 = listCalendar.get(arg2).getStartday().substring(11, 16);
									String time2 = listCalendar.get(arg2).getEndday().substring(11, 16);
									messageSelected = listCalendar.get(arg2).getText();
									timestartSelected = listCalendar.get(arg2).getStartday();
									timeendSelected = listCalendar.get(arg2).getEndday();
									Log.i("update iteminfo save",messageSelected + "---"+ timestartSelected + "--" + timeendSelected);
									showFormAddNewItemInfo(listCalendar.get(arg2).getText(), time1, time2, 1);
									break;
								case 2:
									String t1 = listCalendar.get(arg2).getStartday();
									String t2 = listCalendar.get(arg2).getEndday();
									showFromDeleteItemInfo(listCalendar.get(arg2).getText(), t1, t2);
								default:
									break;
								}
				        		
				        	}
				        }
				    });
				    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	dialog.dismiss();
				        }
				    });
				    builder.create().show();
					return true;
				}
			});
			edtDateSelect.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					DatePickerDialog a = new DatePickerDialog(getActivity(), TimeSelect, myCalendar
							.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
							myCalendar.get(Calendar.DAY_OF_MONTH));
					a.show();
					return true;
				}
			});
			
			return rootView;
		}
		private EditText dlg_edtMessage_Add;
		private EditText dlg_edtTimestart_Add;
		private EditText dlg_edtTimeend_Add;
		private void showFromDeleteItemInfo(final String message,final String timestart, final String timeend) {
			Log.i("delete iteminfo", timestart + "--" + timeend);
			Thread threadAdd = new Thread()
			{
				@Override
				public void run() {
					ItemInfo iteminfo = new ItemInfo(timestart, timeend, message, 0, 0, 0, 0);
					calendarDAO.removeItemInfoFromCalendar(iteminfo);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ringProgressDialog = new ProgressDialog(getActivity());
							ringProgressDialog.setTitle("Please wait");
							ringProgressDialog.setMessage("Deleting...");
							ringProgressDialog.setCancelable(false);
							ringProgressDialog.setCanceledOnTouchOutside(false);
							ringProgressDialog.show();
							flag_ringprogress = true;
						}
					});
					getAllListItemInfoOfCalendarByDay(edtDateSelect.getTag().toString());
				}
			};
			threadAdd.start();
		}
		private void showFormAddNewItemInfo(String message, String timestart, String timeend,final int action)
		{
			ImageButton dlgbtnok_iteminfo_add;
		  	ImageButton dlgbtncancel_iteminfo_add;
		  	final Dialog dialogadd = new Dialog(getActivity());
		  	dialogadd.setContentView(R.layout.dialog_calendar_add);
		  	dialogadd.setTitle("Please input these field");
		  	dlgbtnok_iteminfo_add = (ImageButton) dialogadd.findViewById(R.id.dlgbtnok_calendaradd);
		  	dlgbtncancel_iteminfo_add = (ImageButton) dialogadd.findViewById(R.id.dlgbtncancel_calendaradd);
			dlg_edtMessage_Add = (EditText) dialogadd.findViewById(R.id.dlg_edtMessage);
			dlg_edtMessage_Add.setText(message);
			dlg_edtTimestart_Add = (EditText) dialogadd.findViewById(R.id.dlg_edttimestart);
			dlg_edtTimestart_Add.setText(timestart);
			dlg_edtTimeend_Add = (EditText) dialogadd.findViewById(R.id.dlg_edttimeend);
			dlg_edtTimeend_Add.setText(timeend);
			dlg_edtTimestart_Add.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					controlSelected = 1;
					TimePickerDialog time = new TimePickerDialog(getActivity(), TimeSelected, 10, 10, true);
					time.show();
					return false;
				}
			});
			dlg_edtTimeend_Add.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) 
				{
					controlSelected = 2;
					TimePickerDialog time = new TimePickerDialog(getActivity(), TimeSelected, 10, 10, true);
					time.show();
					return false;
				}
			});
			
			dlgbtnok_iteminfo_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String message = dlg_edtMessage_Add.getText().toString();
					if(message!=null)
					{
						if(!message.equals(""))
						{
							String datepresent = edtDateSelect.getTag().toString();
							Log.i("add iteminfo", datepresent);
							String subdatepresent = datepresent.substring(0, 11);
							if(action==0)
							{
								String des_start = subdatepresent + dlg_edtTimestart_Add.getText().toString();
								String des_end = subdatepresent + dlg_edtTimeend_Add.getText().toString();
								addNewItemInfo(message, des_start, des_end);
								dialogadd.dismiss();
							}
							else
							{
								if(action==1)
								{
									
									String des_start = subdatepresent + dlg_edtTimestart_Add.getText().toString();
									String des_end = subdatepresent + dlg_edtTimeend_Add.getText().toString();
									Log.i("update start", messageSelected + "--" + timestartSelected + "--" +timeendSelected);
									Log.i("update start", message + "--" + des_start + "--" +des_end);
									updateItemInfo(messageSelected, timestartSelected, timeendSelected,message, des_start, des_end);
									dialogadd.dismiss();
								}
							}
						}
					}
				}
			});
			dlgbtncancel_iteminfo_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogadd.dismiss();
				}
			});
			dialogadd.show();
			
		}
		private String getCurrentTime() {
	        Calendar c = Calendar.getInstance();
	        String formattedTime = sdfDate.format(c.getTime());
	        return formattedTime;
	        
	    }
		public void addNewItemInfo(final String message,final String timestart,final String timeend)
		{
			Log.i("add iteminfo", timestart + "--" + timeend);
			Thread threadAdd = new Thread()
			{
				@Override
				public void run() {
					ItemInfo iteminfo = new ItemInfo(timestart, timeend, message, 0, 0, 0, 0);
					calendarDAO.addItemInfoToCalendar(iteminfo);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ringProgressDialog = new ProgressDialog(getActivity());
							ringProgressDialog.setTitle("Please wait");
							ringProgressDialog.setMessage("Saving...");
							ringProgressDialog.setCancelable(false);
							ringProgressDialog.setCanceledOnTouchOutside(false);
							ringProgressDialog.show();
							flag_ringprogress = true;
						}
					});
					
					getAllListItemInfoOfCalendarByDay(edtDateSelect.getTag().toString());
				}
			};
			threadAdd.start();
			
		}
		public void updateItemInfo(final String message1, final String t1, final String t2, final String message,final String timestart,final String timeend)
		{
			Log.i("update iteminfo1",message1 + "---"+ t1 + "--" + t2);
			Log.i("update iteminfo2",message + "---"+ timestart + "--" + timeend);
			Thread threadUpdate = new Thread()
			{
				@Override
				public void run() {
					ItemInfo iteminfo1 = new ItemInfo(t1, t2,message1, 0, 0, 0, 0);
					ItemInfo iteminfo2 = new ItemInfo(timestart, timeend,message, 0, 0, 0, 0);
					
					calendarDAO.updateItemInfoOfCalendar(iteminfo1, iteminfo2);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ringProgressDialog = new ProgressDialog(getActivity());
							ringProgressDialog.setTitle("Please wait");
							ringProgressDialog.setMessage("Saving...");
							ringProgressDialog.setCancelable(false);
							ringProgressDialog.setCanceledOnTouchOutside(false);
							ringProgressDialog.show();
							flag_ringprogress = true;
						}
					});
					getAllListItemInfoOfCalendarByDay(edtDateSelect.getTag().toString());
				}
			};
			threadUpdate.start();
		}
		
		public void getAllEmployee()
		{
			Thread threadEmployee = new Thread()
			{
				@Override
				public void run() {
					Log.i("getAllEmployee",""+listEmployee.size());
					listEmployee.addAll(employeeDAO.getAllEmployeeDAO());
					//getListTracking(edtDateSelect.getTag().toString(), edtDateSelect.getTag().toString());
				}
			};
			threadEmployee.start();
		}
		public void getAllListItemInfoOfCalendarByDay(final String date)
		{
			listCalendar.clear();
			listCalendar.add(new ItemInfo("number", "bac", "vanquang", 0, 0, 0, 0));
			Thread threadGet = new Thread()
			{
				@Override
				public void run()
				{
					Log.i("getItemInfo", date);
					ArrayList<ItemInfo> ds = calendarDAO.getListItemInfoByDay(date);
					Log.i("getItemInfo", ds.size()+"");
					if(ds!=null)
					{
						if(ds.size()>0)
						{
							listCalendar.addAll(ds);
						}
					}
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterListCalendar.notifyDataSetChanged();
							if(flag_ringprogress==true)
							{
								ringProgressDialog.dismiss();
								flag_ringprogress=false;
							}
						}
					});
				}
			};
			threadGet.start();
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

	}

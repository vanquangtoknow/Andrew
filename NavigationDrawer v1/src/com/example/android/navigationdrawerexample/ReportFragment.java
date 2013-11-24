package com.example.android.navigationdrawerexample;

import java.util.concurrent.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Adapter.ListAdapter;
import DTO.Item;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ReportFragment extends Fragment {
	public ReportFragment() {
		// Empty constructor required for fragment subclasses
	}
	private String fulltext;
	private ListAdapter adapter;
	private ArrayList<Item> arrayItem;
	public String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
	public ProgressBar progress, progress1;
	public Calendar myCalendar = Calendar.getInstance();
	public EditText start, end, single;
	public Button okButton, day, month, year;
	public TableRow row1, row2;
	public Switch togg;
	public TableRow buttonContainer;
	private RelativeLayout parent;
	private ThreadPoolExecutor manageThread;
	private BlockingQueue<Runnable> mWorkQueue;

	/**
	 * set lai cac gia tri date, month, year cho calendar
	 */
	DatePickerDialog.OnDateSetListener startTime = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
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

	/**
	 * set tag dang xmlformat(yyyy-MM-dd'T'HH:mm:ss), set text(dd/MM/yyyy) cho edittext
	 */
	private void updateLabel1() {

		String myFormat = "dd/MM/yyyy"; // In which you need put here
		String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);

		start.setText(sdf.format(myCalendar.getTime()));
		start.setTag(xmlSdf.format(myCalendar.getTime()));
	}
	private void updateLabel2() {

		String myFormat = "dd/MM/yyyy"; // In which you need put here
		String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);

		end.setText(sdf.format(myCalendar.getTime()));
		end.setTag(xmlSdf.format(myCalendar.getTime()));
	}
	private void updateLabel3() {

		String myFormat = "dd/MM/yyyy"; // In which you need put here
		String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);

		single.setText(sdf.format(myCalendar.getTime()));
		fulltext = single.getText().toString();
		single.setTag(xmlSdf.format(myCalendar.getTime()));
	}

	public String rs = "";

	public void addrow(final String value, final String name) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Item item = new Item();
				item.name = name;
				item.value = value;
				arrayItem.add(item);
				adapter.notifyDataSetChanged();
			}
		});

	}

	public void getData(final String S, final String E, final View button,final View progressBar) {
		manageThread.execute(new Runnable() {
			@Override
			public void run() {
				try {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getActivity(),
									"Get Data... Pls wait a moment",
									Toast.LENGTH_LONG).show();
							adapter.Clear();
							Log.e("GetData", "1");
							togg.setEnabled(false);
							progressBar.setVisibility(View.VISIBLE);
							button.setVisibility(View.GONE);
							Log.e("GetData", "2");
						}
					});
					int total1 = 0;
					int total2 = 0;
					ArrayList<String> arrayName = new ArrayList<String>() {
						{
							add("Cash");
							add("Gift Card");
							add("Credit Card");
							add("Cheque");
							add("No Charge");
							add("----------------------------------");
							add("Total");
							add("----------------------------------");
							add("Product");
							add("Service");
							add("Extra");
							add("Tips");
							add("Coupon");
							add("Discount");
							add("Discount by Point");
							add("----------------------------------");
							add("Total");
						}
					};
					ArrayList<String> arrayValue = new ArrayList<String>() {
						{
							add("0");
							add("0");
							add("0");
							add("0");
							add("0");
							add("--------");
							add("0");
							add("--------");
							add("0");
							add("0");
							add("0");
							add("0");
							add("0");
							add("0");
							add("0");
							add("--------");
							add("0");
						}
					};
					// tao class xu ly WS
					WCFNail WS = new WCFNail();
					// lay du leiu tu WS
					for (int i = 0; i < 5; i++) {
						final int pos = i;
						Log.e("get", "1" + String.valueOf(i));
						// set Value-- thuc hien ket noi internet
						String data = "-";
						try {
							data = WS.getData("GetMoneyWithBetweenDay",new ArrayList<String>() {
												{
													add(S);
													add(E);
													add(String.valueOf(pos + 1));
												}
											}, true).get(0).toString();
							
							Log.e("DataReport", data);
						} catch (Exception e) {
							Log.e("timeout", "timeout");

						}
						Log.e("null", "null");

						arrayValue.set(i, data);
						// tinh toan lai Total
						total1 += Integer.parseInt(arrayValue.get(i).toString());
						// them vao listView
						addrow(arrayValue.get(i), arrayName.get(i));
						Log.e("get", "2" + String.valueOf(i));
					}
					Log.e("a", "1");
					arrayValue.set(6, String.valueOf(total1));
					Log.e("a", "2");
					addrow(arrayValue.get(5), arrayName.get(5));
					Log.e("a", "3");
					addrow(arrayValue.get(6), arrayName.get(6));
					addrow(arrayValue.get(7), arrayName.get(7));

					for (int i = 0; i < 7; i++) {
						final int pos = i;
						Log.e("get", "1" + String.valueOf(i));
						// set Value
						String data = "-";
						try {
							data = WS.getData("GetMoneyTypeWithBetweenDay",
											new ArrayList<String>() {
												{
													add(S);
													add(E);
													add(String.valueOf(pos + 1));
												}
											}, true).get(0).toString();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Log.e("timeout", "timeout");
						}

						arrayValue.set(i + 8, data);
						// tinh toan lai Total
						total2 += Integer.parseInt(arrayValue.get(i + 8)
								.toString());
						// them vao listView
						addrow(arrayValue.get(i + 8), arrayName.get(i + 8));
						Log.e("get", "2" + String.valueOf(i));
					}
					Log.e("a", "1");
					arrayValue.set(16, String.valueOf(total2));
					Log.e("a", "2");
					addrow(arrayValue.get(15), arrayName.get(15));
					Log.e("a", "3");
					addrow(arrayValue.get(16), arrayName.get(16));

					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
							togg.setEnabled(true);
						}
					});

				} catch (Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							togg.setEnabled(true);
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
							Toast.makeText(getActivity(),
									"Internet Connection Problem !! Timeout..",
									Toast.LENGTH_LONG).show();
						}
					});
					Log.e("WSE", e.getMessage());

				}
			}

		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// lay layout cho fragment
		View rootView = inflater.inflate(R.layout.fragment_report, container,false);
		getActivity().setTitle("FULL REPORT");
		// set threadpool
		// RejectedExecutionHandler implementation
		mWorkQueue = new LinkedBlockingQueue<Runnable>();
		RejectedExecutionHandler rejectionHandler = new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r,ThreadPoolExecutor executor) {
			}
		};
		// Get the ThreadFactory implementation to use
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		// creating the ThreadPoolExecutor
		manageThread = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,mWorkQueue, threadFactory, rejectionHandler);
		// set layout cho tung view
		Log.e("frag", "1");
		ListView listview = (ListView) rootView.findViewById(R.id.listView1);
		day = (Button) rootView.findViewById(R.id.DayChoose1);
		month = (Button) rootView.findViewById(R.id.MonthChoose1);
		year = (Button) rootView.findViewById(R.id.YearChoose1);
		day.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("dayClick", "1");
				single.setText("D " + fulltext);
				Log.e("Xem Ngay", single.getTag().toString());
				getData(single.getTag().toString(), single.getTag().toString(),
						buttonContainer, progress1);
			}
		});
		month.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Xem ngay", fulltext.substring(3));
				// gia tri la thang/nam
				single.setText("M " + fulltext.substring(3));
				Log.e("monClick", "1");
				Date dt1 = new Date();
				Date dt2 = new Date();
				try {
					dt1 = xmlSdf.parse(single.getTag().toString());
					dt2 = xmlSdf.parse(single.getTag().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("monClick", "2");
				dt1.setDate(1);
				Log.e("monClick", "3");
				dt2.setDate(myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Log.e("monClick", "4");
				Log.e("Xem thang","date1  " + single.getTag().toString());
				Log.e("Xem thang","date2  " + single.getTag().toString());
				// hai date nay giong nhau nhung chi lay thang thoi
				getData(xmlSdf.format(dt1), xmlSdf.format(dt2),buttonContainer, progress1);
			}
		});
		year.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				single.setText("Y " + fulltext.substring(6));
				Log.e("yearClick", "1");
				Date dt1 = new Date();
				Date dt2 = new Date();
				try {
					dt1 = xmlSdf.parse(single.getTag().toString());
					dt2 = xmlSdf.parse(single.getTag().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("yearClick", "2");
				dt1.setMonth(0);
				dt1.setDate(1);
				Log.e("yearClick", "3");
				dt2.setMonth(11);
				dt2.setDate(31);
				Log.e("yearClick", "4");
				getData(xmlSdf.format(dt1), xmlSdf.format(dt2),
						buttonContainer, progress1);
			}
		});

		togg = (Switch) rootView.findViewById(R.id.toggleButton1);
		togg.setTextOff("O-T");
		togg.setTextOn("T-T");

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

		parent = (RelativeLayout) rootView.findViewById(R.id.FullReportLayout);
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
		// lay ngay thang
		updateLabel1();
		updateLabel2();
		updateLabel3();
		// set event onclick cho tung button
		Log.e("frag", "2");
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getData(start.getTag().toString(), end.getTag().toString(),
						okButton, progress);
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
		// tao adapter cho listview
		Log.e("frag", "3");
		arrayItem = new ArrayList<Item>();
		adapter = new ListAdapter(getActivity(), arrayItem);
		listview.setAdapter(adapter);
		
		// kiem tra login
		/*if (((MainActivity) getActivity()).getLogin() == false) {
			Log.e("NOTLOGIN", "1");
			disable(parent);
		}*/
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

	public void disable(View v) {
		if (v instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
				disable(((ViewGroup) v).getChildAt(i));
			}
		} else {
			v.setEnabled(false);
		}
	}

	@Override
	public void onPause() {

		Log.e("shutdown", "start");

		Runnable[] runnableArray = new Runnable[mWorkQueue.size()];
		// Populates the array with the Runnables in the queue
		mWorkQueue.toArray(runnableArray);
		// Stores the array length in order to iterate over the array
		int len = runnableArray.length;
		/*
		 * Iterates over the array of Runnables and interrupts each one's
		 * Thread.
		 */
		synchronized (this) {
			// Iterates over the array of tasks
			for (int runnableIndex = 0; runnableIndex < len; runnableIndex++) {
				// Gets the current thread
				Thread thread = (Thread) runnableArray[runnableIndex];
				// if the Thread exists, post an interrupt to it
				if (null != thread) {
					thread.interrupt();
				}
			}
		}
		manageThread.shutdownNow();
		Log.e("shutdown", "end");
		super.onPause();

	}

}

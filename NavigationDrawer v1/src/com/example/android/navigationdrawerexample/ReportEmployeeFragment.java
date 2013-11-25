package com.example.android.navigationdrawerexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Adapter.ListAdapter;
import Adapter.personListAdapter;
import DTO.Employee;
import DTO.Item;
import WS.WCFNail;
import android.R.color;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class ReportEmployeeFragment extends Fragment {
	public ReportEmployeeFragment() {
		// Empty constructor required for fragment subclasses
	}

	private ListView plistview;
	private boolean IsStop = false;
	private String fulltext;
	private personListAdapter pAdapter;
	private ListAdapter adapter;
	private ArrayList<Item> arrayItem;
	private ArrayList<Employee> arrayP = new ArrayList<Employee>();
	private RelativeLayout parent;
	public String XMLFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public SimpleDateFormat xmlSdf = new SimpleDateFormat(XMLFormat, Locale.US);
	public ProgressBar progress, progress1;
	public Calendar myCalendar = Calendar.getInstance();
	public EditText start, end, single;
	public Button refresh, okButton, day, month, year;
	public TableRow row1, row2;
	public Switch togg;
	public TableRow buttonContainer;
	private boolean isDay, isMon, isYea, isBetween = true;

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

	public void getData(final String ID, final String S, final String E,
			final View button, final View progressBar) {
		Thread networkThread = new Thread() {
			@Override
			public void run() {
				final String newS = S.substring(0, 10) + "T00:00:00";
				final String newE = E.substring(0, 10) + "T23:59:59";
				
				try {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getActivity(),
									"Get Data... Pls wait a moment",
									Toast.LENGTH_LONG).show();
							adapter.Clear();
							Log.e("GetData", "1");
							progressBar.setVisibility(View.VISIBLE);
							button.setVisibility(View.GONE);
							refresh.setEnabled(false);
							togg.setEnabled(false);
							plistview.setEnabled(false);
							Log.e("GetData", "2");
						}
					});

					float total1 = 0;
					float total2 = 0;
					ArrayList<String> arrayName = new ArrayList<String>() {
						{
							add("Cash");
							add("Gift Card");
							add("Credit Card");
							add("Cheque");
							add("No Charge");
							add("Deduced by ticket");
							add("Deduced by day");
							add("Deduced by week");
							add("Deduced by month");
							add("Reward Card");
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
					ArrayList<Float> result = new ArrayList<Float>();
					ArrayList<String> para = new ArrayList<String>();
					para.add(newS);
					para.add(newE);
					para.add(ID);
					Log.e("test", "1");
					result = WS.getEmployeeReport(para);
					if (IsStop)
						return;
					Log.e("test", String.valueOf( result.size()));
					
					// lay du leiu tu WS
					for (int i = 0; i < 10; i++) {
						
						Log.e("get", "1" + String.valueOf(i));
						// set Value-- thuc hien ket noi internet
						Log.e("test", "2");
						if (IsStop)
							return;
						arrayValue.set(i, result.get(i).toString());
						Log.e("test", "3");
						// tinh toan lai Total
						total1 += Float.parseFloat(arrayValue.get(i).toString());
						Log.e("test", "4");
						// them vao listView
						addrow(arrayValue.get(i), arrayName.get(i));
						Log.e("get", "2" + String.valueOf(i));
					}
					Log.e("a", "1");
					arrayValue.set(11, String.valueOf(total1));
					Log.e("a", "2");
					addrow(arrayValue.get(10), arrayName.get(10));
					Log.e("a", "3");
					addrow(arrayValue.get(11), arrayName.get(11));
					addrow(arrayValue.get(12), arrayName.get(12));
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
					for (int i = 0; i < 7; i++) {
						Log.e("get", "1" + String.valueOf(i));
						// set Value
						
						if (IsStop)
							return;
						arrayValue.set(i + 13, result.get(i + 10).toString());
						// tinh toan lai Total
						total2 += Float.parseFloat(arrayValue.get(i + 13)
								.toString());
						// them vao listView
						addrow(arrayValue.get(i + 13), arrayName.get(i + 13));
						Log.e("get", "2" + String.valueOf(i));
					}
					Log.e("a", "1");
					arrayValue.set(21, String.valueOf(total2));
					Log.e("a", "2");
					addrow(arrayValue.get(20), arrayName.get(20));
					Log.e("a", "3");
					addrow(arrayValue.get(21), arrayName.get(21));
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
						}
					});
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
							togg.setEnabled(true);
							refresh.setEnabled(true);
							plistview.setEnabled(true);
						}
					});

				} catch (Exception e) {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();
							refresh.setEnabled(true);
							togg.setEnabled(true);
							progressBar.setVisibility(View.GONE);
							button.setVisibility(View.VISIBLE);
							plistview.setEnabled(true);
							Toast.makeText(getActivity(),
									"Internet Connection Problem !! Timeout..",
									Toast.LENGTH_LONG).show();
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
		// lay layout cho fragment
		View rootView = inflater.inflate(R.layout.fragment_reportemployee,
				container, false);
		getActivity().setTitle("EMPLOYEE REPORT");

		// set layout cho tung view
		Log.e("frag", "1");
		plistview = (ListView) rootView.findViewById(R.id.plistView1);
		ListView listview = (ListView) rootView.findViewById(R.id.plistView2);
		parent = (RelativeLayout) rootView
				.findViewById(R.id.ReportEmployeeLayout);
		day = (Button) rootView.findViewById(R.id.DayChoose1);
		month = (Button) rootView.findViewById(R.id.MonthChoose1);
		year = (Button) rootView.findViewById(R.id.YearChoose1);
		day.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				single.setText("D " + fulltext);
				Log.e("dayClick", "1");

				isDay = true;
				isMon = false;
				isYea = false;
				day.setTextColor(Color.rgb(22, 96, 120));
				month.setTextColor(Color.WHITE);
				year.setTextColor(Color.WHITE);
			}
		});

		month.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				single.setText("M " + fulltext.substring(3));
				Log.e("monClick", "1");
				isDay = false;
				isMon = true;
				isYea = false;
				day.setTextColor(Color.WHITE);
				month.setTextColor(Color.rgb(22, 96, 120));
				year.setTextColor(Color.WHITE);

			}
		});

		year.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				single.setText("Y " + fulltext.substring(6));
				Log.e("yearClick", "1");
				isDay = false;
				isMon = false;
				isYea = true;
				day.setTextColor(Color.WHITE);
				month.setTextColor(Color.WHITE);
				year.setTextColor(Color.rgb(22, 96, 120));
			}
		});

		refresh = (Button) rootView.findViewById(R.id.Refresh);
		Log.e("frag", "a");

		start = (EditText) rootView.findViewById(R.id.peditText1);
		end = (EditText) rootView.findViewById(R.id.peditText2);
		single = (EditText) rootView.findViewById(R.id.Single1);
		buttonContainer = (TableRow) rootView.findViewById(R.id.TableRow03);
		progress = (ProgressBar) rootView.findViewById(R.id.pprogressBar1);
		progress1 = (ProgressBar) rootView.findViewById(R.id.ProgressBar01);
		row1 = (TableRow) rootView.findViewById(R.id.tableRow1);
		row2 = (TableRow) rootView.findViewById(R.id.TableRow02);
		row2.setVisibility(View.GONE);
		progress.setVisibility(View.GONE);
		progress1.setVisibility(View.GONE);
		togg = (Switch) rootView.findViewById(R.id.toggleButton1);
		togg.setTextOff("");
		togg.setTextOn("");
		togg.setChecked(true);

		togg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean on = ((Switch) v).isChecked();

				if (on) {
					row1.setVisibility(View.VISIBLE);
					row2.setVisibility(View.GONE);
					isBetween = true;
					isDay = false;
					isMon = false;
					isYea = false;
				} else {
					// Disable vibrate
					row1.setVisibility(View.GONE);
					row2.setVisibility(View.VISIBLE);
					isBetween = false;
					isDay = true;
					isMon = false;
					isYea = false;
					day.setTextColor(Color.rgb(22, 96, 120));
					month.setTextColor(Color.WHITE);
					year.setTextColor(Color.WHITE);
				}
			}
		});
		Log.e("frag", "b");
		// lay ngay thang
		updateLabel1();
		updateLabel2();
		updateLabel3();
		// set event onclick cho tung button
		Log.e("frag", "2");
		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Thread networkThread = new Thread() {
					@Override
					public void run() {
						try {
							Looper.prepare();
							arrayP.clear();
							getActivity().runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									pAdapter.notifyDataSetChanged();
									Toast.makeText(getActivity(),
											"Refresh list employees...",
											Toast.LENGTH_SHORT).show();
								}
							});

							WCFNail ws = new WCFNail();
							ArrayList<Employee> result = ws.getAllEmployee();
							if (IsStop)
								return;
							arrayP.addAll(result);
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Log.e("eeeeeeeeeeee",
											String.valueOf(arrayP.size()));
									if (arrayP.size() == 0) {
										Toast.makeText(
												getActivity(),
												"Internet Connection Problem !! Timeout..",
												Toast.LENGTH_LONG).show();
									}
									pAdapter.notifyDataSetChanged();
								}
							});

						} catch (Exception e) {

							Log.e("WSE", e.getMessage());
						}
					}
				};

				networkThread.start();
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

		plistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				final int ID = pAdapter.getItem(position).getID_Employee();
				pAdapter.setSelectedIndex(position);

				if (isBetween == true) {
					
					String S = start.getTag().toString();
					String E = end.getTag().toString();
					getData(String.valueOf(ID), S, E, refresh, progress);
				} else if (isDay == true) {
					String S = single.getTag().toString();
					String E = single.getTag().toString();
					getData(String.valueOf(ID), S, E, refresh, progress1);
				} else if (isMon == true) {
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
					dt2.setDate(myCalendar
							.getActualMaximum(Calendar.DAY_OF_MONTH));
					Log.e("monClick", "4");
					String S = xmlSdf.format(dt1);
					String E = xmlSdf.format(dt2);
					getData(String.valueOf(ID), S, E, refresh, progress1);
				} else if (isYea == true) {
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
					String S = xmlSdf.format(dt1);
					String E = xmlSdf.format(dt2);
					getData(String.valueOf(ID), S, E, refresh, progress1);

				}

			}
		});
		// kiem tra login
		if (((MainActivity) getActivity()).getLogin() == false) {
			Log.e("NOTLOGIN", "1");
			disable(parent);
		} else {
			// adapter
			pAdapter = new personListAdapter(getActivity(), arrayP);

			adapter = new ListAdapter(getActivity(), arrayItem);
			listview.setAdapter(adapter);
			plistview.setAdapter(pAdapter);
			Thread networkThread = new Thread() {
				@Override
				public void run() {
					try {
						WCFNail ws = new WCFNail();
						ArrayList<Employee> result = ws.getAllEmployee();
						if (IsStop)
							return;
						arrayP.addAll(result);
						/* arrayP = ws.getAllEmployee(); */
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Log.e("eeeeeeeeeeee",
										String.valueOf(arrayP.size()));
								if (arrayP.size() == 0) {
									Toast.makeText(
											getActivity(),
											"Internet Connection Problem !! Timeout..",
											Toast.LENGTH_LONG).show();
								}
								pAdapter.notifyDataSetChanged();
							}
						});

					} catch (Exception e) {
					}
				}
			};

			networkThread.start();

			Log.e("frag", "4");
			try {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
			} catch (Exception e) {
				Log.e("err", e.getMessage());
				// TODO: handle exception
			}
		}
		Log.e("frag", "5");
		return rootView;
	}
	
	public void disable(View v) {
		if (v instanceof ViewGroup) {
			v.setEnabled(false);
			for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
				disable(((ViewGroup) v).getChildAt(i));
			}
		} else {
			v.setEnabled(false);
		}
	}

	@Override
	public void onPause() {
		IsStop = true;
		super.onPause();
	}
}

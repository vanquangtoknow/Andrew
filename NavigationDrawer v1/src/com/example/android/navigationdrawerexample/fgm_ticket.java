package com.example.android.navigationdrawerexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



import Adapter.ListAdapter;
import Adapter.ListBaseAdapter;
import Adapter.TicketEditAdapter;
import DTO.Employee;
import DTO.Item;
import DTO.ItemTicket;
import DTO.ItemTicketEdit;
import DTO.Ticket;
import WS.WCFNail;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class fgm_ticket extends Fragment {
	public fgm_ticket() {
	}
	
	// region Khai bao cac control
	
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
	TabHost tabhost;
	// Luu id hien tai cua nguoi dung
	private final String idEmployeePresent ="";
	// Luu id ticket hien tai
	private String idTicketPresent="";
	private ListView lvEmployees;
	private ListView lvTickets;
	private ListView lvEdits;
	private ListBaseAdapter adapterEmployees;
	public ArrayList<Employee> listEmployee = new ArrayList<Employee>();
	private ListBaseAdapter adapterTickets;
	public ArrayList<Ticket> listTickets = new ArrayList<Ticket>();
	private TicketEditAdapter adapterEdits;
	private final ArrayList<ItemTicket> listEdits= new ArrayList<ItemTicket>();
	public String rs = "";
	
	private ListView lvCategories;
	private ListView lvServices;
	private ListView lvTicketAdd;
	private final ArrayList<ItemTicket> listTicketAdd = new ArrayList<ItemTicket>();
	private final ArrayList<Item> listCategories = new ArrayList<Item>();
	private final ArrayList<Item> listServices = new ArrayList<Item>();
	private ListAdapter adapterCategories;
	private  ListAdapter adapterServices;
	private  TicketEditAdapter adapterTicketAdd;
	public Button btnGetService;
	private int action_Ticketfunction = 0;
	private Button btnTicketEdit_Add;
	// endregion

	// region khoi tao cac date time va update cac label
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
	// endregion
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// region dang ki su dung cac control cua ticket form
		// lay layout cho fragment
		View rootView = inflater.inflate(R.layout.fragment_ticket, container,false);
		getActivity().setTitle("TICKET");
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
		day = (Button) rootView.findViewById(R.id.DayChoose1);
		month = (Button) rootView.findViewById(R.id.MonthChoose1);
		year = (Button) rootView.findViewById(R.id.YearChoose1);
		togg = (Switch) rootView.findViewById(R.id.toggleButton1);
		togg.setTextOff("O-T");
		togg.setTextOn("T-T");
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
		//-------------su dung tabhost
		tabhost = (TabHost) rootView.findViewById(R.id.tabhost);
		tabhost.setup();
		TabHost.TabSpec tabspec;
		tabspec = tabhost.newTabSpec("Tab Report");
		tabspec.setContent(R.id.tab_ticketemploy);
		tabspec.setIndicator("Emloyee and ticket");
		tabhost.addTab(tabspec);
		tabspec = tabhost.newTabSpec("Tab Report1");
		tabspec.setContent(R.id.tab_tickereport);
		tabspec.setIndicator("View report");
		tabhost.addTab(tabspec);
		tabspec = tabhost.newTabSpec("Tab Report2");
		tabspec.setContent(R.id.tab_ticketedit);
		tabspec.setIndicator("Edit ");
		tabhost.addTab(tabspec);
		tabhost.setCurrentTab(0);
		//--su dung listview employee, va listview ticket
		lvEmployees = (ListView)rootView.findViewById(R.id.listviewemployee);
		lvTickets = (ListView)rootView.findViewById(R.id.listviewticket);
		lvEdits = (ListView) rootView.findViewById(R.id.listviewedit);
		
		btnTicketEdit_Add = (Button) rootView.findViewById(R.id.btn_ticketedit_add);
		btnGetService = (Button) rootView.findViewById(R.id.btnGetService);
		// endregion
		// region Su kien click vao cac button day, mon, year, togg, ok, start, end single
		// 
		day.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("dayClick", "1");
				single.setText("D " + fulltext);
				Log.e("Xem Ngay", single.getTag().toString());
				//getData(single.getTag().toString(), single.getTag().toString(),buttonContainer, progress1);
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
				//getData(xmlSdf.format(dt1), xmlSdf.format(dt2),buttonContainer, progress1);
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
				//getData(xmlSdf.format(dt1), xmlSdf.format(dt2),buttonContainer, progress1);
			}
		});
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
				//getData(start.getTag().toString(), end.getTag().toString(),okButton, progress);
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
		// lay ngay thang
		updateLabel1();
		updateLabel2();
		updateLabel3();
		adapterEmployees = new ListBaseAdapter(getActivity(),listEmployee);
		adapterEmployees.initListBaseAdapter(1, 1);
		lvEmployees.setAdapter(adapterEmployees);
		adapterTickets = new ListBaseAdapter(getActivity(), listTickets);
		adapterTickets.initListBaseAdapter(2,1);
		lvTickets.setAdapter(adapterTickets);
		/**
		 * Lay toan bo danh sach nhan vien, neu ds>0 thi lay danh sach ticket cua nhan vien dau tien
		 * Va lay ra danh sach cho Tab report edit
		 * Luu lai idTicketPresent cua ticket dau tien nay nho trim()
		 */
		Thread threadGetEmployee =  new Thread(){
			@Override
			public void run() {
				try {
					WCFNail nailservice = new WCFNail();
					listEmployee.clear();
					listEmployee.addAll(nailservice.getAllEmployee());
					if(listEmployee.size()>0)
					{
						listTickets.clear();
						listTickets.addAll(nailservice.getListTicketByIDEmployee(new ArrayList<String>() {
							{
								add(Integer.toString(listEmployee.get(0).getID_Employee()));
							}
						}));
						if(listTickets.size()>0)
						{
							idTicketPresent = Integer.toString(listTickets.get(0).getID());
							listEdits.clear();
							listEdits.addAll(nailservice.getListItemTicketByIDTicket(
									new ArrayList<String>(){{
										add(idTicketPresent);
									}}));
						}
					}
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterEdits.notifyDataSetChanged();
							adapterEmployees.notifyDataSetChanged();
							Log.e("error listedit", "Loi adapter");
							adapterTickets.notifyDataSetChanged();
						}
					});
				} catch (Exception e) {
				}
			}
		};
		threadGetEmployee.start();
		/**
		 * Khi click vao 1 item trong danh sach nhan vien thi hien thi danh sach ticket
		 * Luu lai idticketpresent mac dinh lan dau tien cua danh sach ticket
		 */
		lvEmployees.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,long arg3)
			{
				adapterEmployees.setSelectedItem(position);
				adapterEmployees.notifyDataSetChanged();
				Thread threadtickets = new Thread()
				{
					@Override
					public void run() {
						try {
							WCFNail nailservice = new WCFNail();
							listTickets.clear();
							adapterTickets.setSelectedItem(0);
							listTickets.addAll(nailservice.getListTicketByIDEmployee(new ArrayList<String>() {
								{
									add(Integer.toString(listEmployee.get(position).getID_Employee()));
								}
							}));
							if(listTickets.size()>0)
							{
								idTicketPresent = Integer.toString(listTickets.get(0).getID());
							}
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapterTickets.notifyDataSetChanged();
								}
							});
							
						} catch (Exception e) {
						}
					}
				};
				threadtickets.start();
			}
		});
		/**
		 * Khi click vao 1 item trong danh sach ticket. Update du lieu cho tab report
		 * Lay id ticletpresent va thuc hien lay cac item ticket
		 */
		lvTickets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				idTicketPresent = Integer.toString(listTickets.get(arg2).getID());
				adapterTickets.setSelectedItem(arg2);
				adapterTickets.notifyDataSetChanged();
				Thread threadupdateReport = new Thread()
				{
					
					@Override
					public void run() {
						WCFNail nailservice = new WCFNail();
						listEdits.clear();
						// Dong dau tien cua Item ticket de view Type, Quality, Description, Price
						listEdits.add(new ItemTicket(-1, -1, -1, -1, -1));
						listEdits.add(new ItemTicket());
						listEdits.addAll(nailservice.getListItemTicketByIDTicket(
								new ArrayList<String>(){{
									add(idTicketPresent);
								}}));
						if(listEdits.size()+1<13)
						{
							int count = 13-listEdits.size();
							for(int i=0;i<count;i++)
							{
								listEdits.add(new ItemTicket(-2, -2, -2, -2, -2));
							}
						}
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								adapterEdits.notifyDataSetChanged();
								tabhost.setCurrentTab(2);
							}
						});
					}
				};
				threadupdateReport.start();
			}
		});

		/*Thread threadTicketEditAll = new Thread()
		{
			@Override
			public void run() {
				WCFNail nailservice = new WCFNail();
				if(idTicketPresent.compareTo("")!=0)
				{
					listEdits.clear();
					String s = idTicketPresent;
					//listEdits.add(new ItemTicket("Type", "Quality", "Description", "Price"));
					listEdits.addAll(nailservice.getListItemTicketByIDTicket(
							new ArrayList<String>(){{
								add(idTicketPresent);
							}}));
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterEmployees.notifyDataSetChanged();
							adapterTickets.notifyDataSetChanged();
						}
					});
				}
			}
		};
		threadTicketEditAll.start();*/
		adapterEdits = new TicketEditAdapter(getActivity(), listEdits);
		lvEdits.setAdapter(adapterEdits);
		btnGetService.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				tabhost.setCurrentTab(2);
			}
		});
		// endregion
		// region them du lieu maus
			//-------them du lieu cho categories, va services
		
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listCategories.add(new Item("vava", "1"));
			listServices.add(new Item("service1", "1"));
			listServices.add(new Item("servce 2", "1"));
			listServices.add(new Item("servie 2", "1"));
			listServices.add(new Item("vava", "1"));
			listServices.add(new Item("vava", "1"));
			
				
				// endregion
		
			btnTicketEdit_Add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Button dlgbtnok_ticketadd;
				  	Button dlgbtncancel_ticketadd;
				  	final Dialog dialogadd = new Dialog(getActivity());
				  	dialogadd.setContentView(R.layout.dialog_ticketadd);
				  	dialogadd.setTitle("Nhap du lieu");
				    lvServices = (ListView) dialogadd.findViewById(R.id.dlglv_services);
				    lvCategories = (ListView) dialogadd.findViewById(R.id.dlglv_categories);
				    lvTicketAdd = (ListView) dialogadd.findViewById(R.id.dlglv_ticketadd);
				    String s = listCategories.get(2).name;
				    Log.e("View list", listCategories.get(2).name);
				    adapterCategories = new ListAdapter(getActivity(), listCategories);
					lvCategories.setAdapter(adapterCategories);
					adapterServices = new ListAdapter(getActivity(), listServices);
					lvServices.setAdapter(adapterServices);
					adapterTicketAdd = new TicketEditAdapter(getActivity(), listTicketAdd);
					lvTicketAdd.setAdapter(adapterTicketAdd);
					dlgbtnok_ticketadd = (Button) dialogadd.findViewById(R.id.dlgbtnok_ticketadd);
					dlgbtncancel_ticketadd = (Button) dialogadd.findViewById(R.id.dlgbtncancel_ticketadd);
					dlgbtncancel_ticketadd.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialogadd.dismiss();
						}
					});
					dlgbtnok_ticketadd.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialogadd.dismiss();
						}
					});
					lvServices.setOnItemLongClickListener(new OnItemLongClickListener() {
						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
							adapterServices.setSelectedItem(arg2);
							adapterServices.notifyDataSetChanged();
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					        builder.setMessage("Add sale this sale item")
					               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                	   listTicketAdd.clear();
					                	   listTicketAdd.add(new ItemTicket(2, 2, 1, 1, 1));
					                	   listTicketAdd.add(new ItemTicket(2, 2, 1, 1, 1));
					                	   listTicketAdd.add(new ItemTicket(2, 2, 1, 1, 1));
					                	   adapterTicketAdd.notifyDataSetChanged();
					                   }
					               })
					               .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                       
					                   }
					               });
					        // Create the AlertDialog object and return it
					        builder.create().show();
							return false;
						}
					});
					lvCategories.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
							adapterCategories.setSelectedItem(arg2);
							listServices.clear();
							listServices.add(new Item("van quang", "1"));
							listServices.add(new Item("xuan cuong", "1"));
							listServices.add(new Item("van ngoc", "1"));
							listServices.add(new Item("vava", "1"));
							adapterServices.notifyDataSetChanged();
							adapterCategories.notifyDataSetChanged();
						}
					});
					dialogadd.show();
					
				}
			});
			lvEdits.setOnItemLongClickListener (new OnItemLongClickListener() {
				  public boolean onItemLongClick(AdapterView parent, View view, int position, long id)
				  {
					  ItemTicket itemticket = listEdits.get(position);
					  if(itemticket.getID()==-1&&itemticket.getID_SaleItem()==-1&&itemticket.getID_Ticket()==-1&&itemticket.getPrice()==-1&&itemticket.getQuality()==-1)
					  {
						  return false;
					  }
					  else
					  {
						  if(itemticket.getID()==-2&&itemticket.getID_SaleItem()==-2&&itemticket.getID_Ticket()==-2&&itemticket.getPrice()==-2&&itemticket.getQuality()==-2)
						  {
							  return false;
						  }
						  else
						  {
							  String[] dsaction = {"Edit", "Delete"};
							  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
							    builder.setTitle("Please choose action: ")
							           .setSingleChoiceItems(dsaction, 0, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											action_Ticketfunction = which;
										}
									}).setPositiveButton("OK", new DialogInterface.OnClickListener() {
							               @Override
							               public void onClick(DialogInterface dialog, int id) {
							                	   	switch (action_Ticketfunction)
							                	   	{
							                	   	case 0:
							                	   		// region chon chuc nang edit sau khi cho 1 sale item
							                	   		final Dialog dialogedit = new Dialog(getActivity());
							                	   		dialogedit.setContentView(R.layout.dialog_ticketedit);
							                	   		dialogedit.setTitle("Ban hay nhap diem cho hoc sinh" + action_Ticketfunction);
							        					// set the custom dialog components - text, image and button
							        					//TextView text = (TextView) dialog.findViewById(R.id.textView1);
							        					//text.setText("Nhap diem cho ban quang: ");
							        					//edittext = (EditText) dialog.findViewById(R.id.editText1);
							        					//ImageView image = (ImageView) dialog.findViewById(R.id.image);
							        					//image.setImageResource(R.drawable.ic_launcher);
							        					
							        					Button dialogButtonok = (Button) dialogedit.findViewById(R.id.dlgbtn_cancel__ticketedit);
							        					dialogButtonok.setOnClickListener(new OnClickListener() {
							        						@Override
							        						public void onClick(View v) {
							        							dialogedit.dismiss();
							        							action_Ticketfunction = 0;
							        						}
							        					});
							        					dialogedit.show();
							        					// endregion
							                	   		break;
													case 1:
														//region  chon chuc nang delete
														AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
														builder.setMessage("Are you sure when delete this sale item" + action_Ticketfunction)
														       .setTitle("Alert !!!")
														       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which) {
																	action_Ticketfunction = 0;
																}
															}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which) {
																	action_Ticketfunction = 0;
																}
															});
														builder.create().show();
														break;
														// endregion
			
													}
							                   }
							           })
							           .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
							               @Override
							               public void onClick(DialogInterface dialog, int id) {
							                   
							               }
							           });
							    builder.create().show();
						  }
					  }
					  return true;
				  }
				});

			return rootView;
	}
	public void getListCategories()
	{
		/*Thread threadCategories = new Thread(){
			@Override
			public void run() {
				WCFNail nailservice = new WCFNail();
				listCategories.clear();
				adapterCategories.setSelectedItem(0);
				listTickets.addAll(nailservice.getListTicketByIDEmployee(new ArrayList<String>() {
					{
						add(Integer.toString(listEmployee.get(position).getID_Employee()));
					}
				}));
				if(listTickets.size()>0)
				{
					idTicketPresent = Integer.toString(listTickets.get(0).getID());
				}
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterTickets.notifyDataSetChanged();
					}
				});
			}
		};
		threadCategories.start();*/
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

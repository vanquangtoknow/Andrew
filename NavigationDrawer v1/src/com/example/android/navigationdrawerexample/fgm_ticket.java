package com.example.android.navigationdrawerexample;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import DTO.Category;
import DTO.Employee;
import DTO.Item;
import DTO.ItemTicket;
import DTO.ItemTicketAdapter;
import DTO.ReportDTO;
import DTO.SaleItem;
import DTO.Ticket;
import WS.WCFNail;
import DAO.*;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class fgm_ticket extends Fragment {
	public fgm_ticket() {
	}
	//start
	private TicketDAO ticketDAO = new TicketDAO();
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private ItemTicketDAO itemTicketDAO = new ItemTicketDAO();
	private ReportDAO reportDAO = new ReportDAO();
	private SaleItemDAO saleItemDAO = new SaleItemDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();
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
	private Employee EmployeePresent;
	// Luu id ticket hien tai
	private Ticket TicketPresent;
	private ItemTicket ItemTicketPresent;
	private String valueEmployeePresent;
	private String valueTicketPresent;
	private ItemTicketAdapter ItemTicketAdapterPresent = null;
	private ListView lvEmployees;
	private ListView lvTickets;
	private ListView lvEdits;
	private ListBaseAdapter adapterEmployees;
	public ArrayList<Employee> listEmployee = new ArrayList<Employee>();
	private ListBaseAdapter adapterTickets;
	public ArrayList<Ticket> listTickets = new ArrayList<Ticket>();
	private TicketEditAdapter adapterEdits;
	private final ArrayList<ItemTicketAdapter> listEdits= new ArrayList<ItemTicketAdapter>();
	private ProgressBar progessbarTicketEdit;
	public String rs = "";
	
	private ListView lvCategories;
	private ListView lvServices;
	private ListView lvTicketAdd;
	private final ArrayList<ItemTicketAdapter> listItemTicketAdd = new ArrayList<ItemTicketAdapter>();
	private ArrayList<Category> listCategories = new ArrayList<Category>();
	private ArrayList<SaleItem> listServices = new ArrayList<SaleItem>();
	private ListBaseAdapter adapterCategories;
	private  ListBaseAdapter adapterServices;
	private  TicketEditAdapter adapterTicketAdd;
	private int action_Ticketfunction = 0;
	private ImageButton btnTicketEdit_Add;
	private ImageButton btnTicketEdit_DeleteTicket;
	
	//------Su dung cho tinh total, va deducted
	private float Deducted;
	private float Sum;
	private float ForTech;
	private float ForOwner;
	private TextView tvTotal;
	private TextView tvDeducted;
	private TextView tvForTech;
	private TextView tvForOwner;
	String total="";
	String deducted="";
	String fortech ="";
	String forowner ="";
	//-------Su dung thoi gian & cac format
	String myFormatDate = "dd/MM/yyyy"; // In which you need put here
	String XMLFormatDate = "yyyy-MM-dd'T'HH:mm:ss";
	SimpleDateFormat sdfDate = new SimpleDateFormat(myFormatDate, Locale.US);
	SimpleDateFormat xmlSdfDate = new SimpleDateFormat(XMLFormatDate, Locale.US);
	
	private Date datestart = new Date();
	private Date dateend = new Date();
	//------Su dung de cho saving du lieu
	ProgressDialog ringProgressDialog = null;
	private boolean flag_ringprogress = false;
	//----- Khai bao va su dung cho tab information
	private TextView tvInfoEmpoyee;
	private TextView tvInfoTicket;
	private TextView tvInfoCustomner;
	private TextView tvInfoMoneyTime;
	//end
	

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
		progessbarTicketEdit = (ProgressBar) rootView.findViewById(R.id.progressBar2);

		
		progessbarTicketEdit.setVisibility(View.GONE);
		//------------- khai bao va su dung de tinh total, dedudeted
		tvTotal = (TextView) rootView.findViewById(R.id.report_txt_total);
		tvDeducted = (TextView) rootView.findViewById(R.id.report_txt_deduted);
		tvForTech = (TextView) rootView.findViewById(R.id.report_txt_fortech);
		tvForOwner = (TextView) rootView.findViewById(R.id.report_txt_forowner);
		
		//---------------Khai bao va su dung tab infomation
		tvInfoEmpoyee = (TextView) rootView.findViewById(R.id.tabinfo_tvemployee);
		tvInfoCustomner = (TextView) rootView.findViewById(R.id.tabinfo_tvcustommer);
		tvInfoTicket = (TextView) rootView.findViewById(R.id.tabinfo_tvticket);
		tvInfoMoneyTime = (TextView) rootView.findViewById(R.id.tabinfo_tvmoneytime);
		
		//-------------su dung tabhost
		tabhost = (TabHost) rootView.findViewById(R.id.tabhost);
		tabhost.setup();
		
		TabHost.TabSpec tabspec;
		tabspec = tabhost.newTabSpec("Tab Report");
		tabspec.setContent(R.id.tab_ticketemploy);
		tabspec.setIndicator("Emloyee and ticket");
		tabhost.addTab(tabspec);
		
		tabspec = tabhost.newTabSpec("Tab Report1");
		tabspec.setContent(R.id.tab_ticketedit);
		tabspec.setIndicator("View and Edit");
		tabhost.addTab(tabspec);
		
		tabspec = tabhost.newTabSpec("Tab Report2");
		tabspec.setContent(R.id.tab_tickereport);
		tabspec.setIndicator("View Info");
		tabhost.addTab(tabspec);
		
		tabhost.setCurrentTab(0);
		//--su dung listview employee, va listview ticket
		lvEmployees = (ListView)rootView.findViewById(R.id.listviewemployee);
		lvTickets = (ListView)rootView.findViewById(R.id.listviewticket);
		lvEdits = (ListView) rootView.findViewById(R.id.listviewedit);
		
		
		btnTicketEdit_Add = (ImageButton) rootView.findViewById(R.id.btn_ticketedit_add);
		btnTicketEdit_DeleteTicket = (ImageButton) rootView.findViewById(R.id.btn_ticketedit_deleteticket);
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
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				//ringProgressDialog.setCancelable(true);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				flag_ringprogress = true;
				String newS = single.getTag().toString().substring(0, 10) + "T00:00:00";
				String newE = single.getTag().toString().substring(0, 10) + "T23:59:59";
				Log.i("Get ticket by day", newS + "--" + newE);
				LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), newS, newE);
			}
		});
		
		month.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				single.setText("M " + fulltext.substring(3));
				
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				flag_ringprogress = true;
				getDayStartAndDayEndBySingleText(2);
				Log.i("Get ticket by month","IdEmployee" +EmployeePresent.getID_Employee()+" -- "+ xmlSdfDate.format(datestart) + "--" + xmlSdfDate.format(dateend));
				LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), xmlSdfDate.format(datestart), xmlSdfDate.format(dateend));
			}
		});
		year.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				single.setText("Y " + fulltext.substring(6));
				
				getDayStartAndDayEndBySingleText(3);
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				flag_ringprogress = true;
				Log.i("Get ticket by year","IdEmployee" +EmployeePresent.getID_Employee()+" -- "+ xmlSdfDate.format(datestart) + "--" + xmlSdfDate.format(dateend));
				LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), xmlSdfDate.format(datestart), xmlSdfDate.format(dateend));
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
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading ticket...", true);
				ringProgressDialog.setCancelable(true);
				flag_ringprogress = true;
				LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), start.getTag().toString(), end.getTag().toString());
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
		
		fulltext = sdfDate.format(myCalendar.getTime());
		single.setTag(xmlSdfDate.format(myCalendar.getTime()));
		single.setText("D " + fulltext);
		
		
		adapterEmployees = new ListBaseAdapter(getActivity(),listEmployee);
		adapterEmployees.initListBaseAdapter(1, 1);
		lvEmployees.setAdapter(adapterEmployees);
		adapterTickets = new ListBaseAdapter(getActivity(), listTickets);
		adapterTickets.initListBaseAdapter(2,2);
		lvTickets.setAdapter(adapterTickets);
		/**
		 * Lay toan bo danh sach nhan vien, neu ds>0 thi lay danh sach ticket cua nhan vien dau tien
		 * Va lay ra danh sach cho Tab report edit
		 * Luu lai idTicketPresent cua ticket dau tien nay nho trim()
		 */
		//btnTicketEdit_Add.setEnabled(false);
		ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
		ringProgressDialog.setCancelable(true);
		ringProgressDialog.setCanceledOnTouchOutside(false);
		flag_ringprogress = true;
		Thread threadGetEmployee =  new Thread(){
			@Override
			public void run() {
				try {
					listEmployee.clear();
					listEmployee.addAll(employeeDAO.getAllEmployeeDAO());
					if(listEmployee.size()>0)
					{
						EmployeePresent = listEmployee.get(0);
						tvInfoEmpoyee.setText(EmployeePresent.getstrName());
						listTickets.clear();
						
						
						//Hien thi thong tin nhan vien sau khi da load xong nhan vien
						if(flag_ringprogress==true)
						{
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapterTickets.notifyDataSetChanged();
									adapterEmployees.notifyDataSetChanged();
									//ringProgressDialog.setMessage("Loading ticket...");
								}
							});
						}
						getDayStartAndDayEndBySingleText(1);
						listTickets.addAll(ticketDAO.getListTicketBetween(listEmployee.get(0).getID_Employee(),xmlSdf.format(datestart), xmlSdf.format(dateend)));
						if(listTickets.size()>0)
						{
							TicketPresent = listTickets.get(0);
							adapterTickets.setSelectedItem(0);
							Log.i("thread getemployee","id ticket: "+listTickets.get(0).getID() );
							convertListItemTicketToItemTiketAdapter(itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID()));
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									adapterTickets.notifyDataSetChanged();		//Load xong ticket
									adapterEdits.notifyDataSetChanged();
									total = "Total: " +Sum;
									deducted = "Deducted: " + Deducted;
									forowner = "For Owner: "+ForOwner;
									fortech = "For Tech: " +ForTech;
									tvTotal.setText(total);
									tvDeducted.setText(deducted);
									tvForTech.setText(fortech);
									tvForOwner.setText(forowner);
									/*if(flag_ringprogress==true)
									{
										ringProgressDialog.dismiss();
										flag_ringprogress=false;
									}*/
									
								}
							});
						}
						else
						{
							listEdits.clear();
							addvalueEmptyToListEdit();
							total="Total: ";
							deducted="Deducted: ";
							forowner="For Owner: ";
							fortech ="For Tech: ";
							valueTicketPresent = "";
							TicketPresent = null;
							
						}
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								adapterEmployees.notifyDataSetChanged();
								//progessbarTicketEdit.setVisibility(View.GONE);
								if(flag_ringprogress==true)
								{
									ringProgressDialog.dismiss();
									flag_ringprogress=false;
								}
								if(TicketPresent==null)
								{
									btnTicketEdit_Add.setEnabled(false);
									btnTicketEdit_DeleteTicket.setEnabled(false);
								}else
								{
									btnTicketEdit_Add.setEnabled(true);
									btnTicketEdit_DeleteTicket.setEnabled(true);
								}
							}
						});
					}
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
				EmployeePresent = listEmployee.get(position);
				tvInfoEmpoyee.setText(EmployeePresent.getstrName());
				
				single.setText("D " + fulltext.substring(6));
				
				
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading ticket...", true);
				ringProgressDialog.setCancelable(true);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				flag_ringprogress = true;
				//row1.setVisibility(View.VISIBLE);
				if(row1.getVisibility() == View.VISIBLE)
				{
					getDayStartAndDayEndByTwoText();
				}else
				{
					getDayStartAndDayEndBySingleText(1);
					
				}
				Log.i("employee click","IdEmployee" +EmployeePresent.getID_Employee()+" -- "+ xmlSdfDate.format(datestart) + "--" + xmlSdfDate.format(dateend));
				LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), xmlSdfDate.format(datestart), xmlSdfDate.format(dateend));
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
				
				TicketPresent = listTickets.get(arg2);
				adapterTickets.setSelectedItem(arg2);
				adapterTickets.notifyDataSetChanged();
				tvInfoTicket.setText(TicketPresent.getCode());
				
				ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Loading...", true);
				ringProgressDialog.setCancelable(true);
				ringProgressDialog.setCanceledOnTouchOutside(false);
				flag_ringprogress = true;
				LoadItemTicketByIdTicket(TicketPresent.getID());
				//tabhost.setCurrentTab(1);
			}
		});
		adapterEdits = new TicketEditAdapter(getActivity(), listEdits);
		lvEdits.setAdapter(adapterEdits);
	
		// endregion
		// region them du lieu maus
		//-------them du lieu cho categories, va services
		getListCategories();
				// endregion
			btnTicketEdit_DeleteTicket.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			        builder.setMessage("Delete this ticket")
			        		.setTitle("Arlert!!!")
			               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                	   	ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Please wait to delete this ticket...", true);
				       				ringProgressDialog.setCancelable(true);
				       				flag_ringprogress = true;
				       				deleteTicket();
				       				
				       				//getDayStartAndDayEndBySingleText(3);
				       				//LoadTicketByIdEmployee(EmployeePresent.getID_Employee(), datestart.toString(), dateend.toString());
			                   }
			               })
			               .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
			                   public void onClick(DialogInterface dialog, int id) {
			                       
			                   }
			               });
			        // Create the AlertDialog object and return it
			        builder.create().show();
				}
			});
			btnTicketEdit_Add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					ImageButton dlgbtnok_ticketadd;
				  	ImageButton dlgbtncancel_ticketadd;
				  	final Dialog dialogadd = new Dialog(getActivity());
				  	dialogadd.setContentView(R.layout.dialog_ticketadd);
				  	dialogadd.setTitle("Nhap du lieu");
				    lvServices = (ListView) dialogadd.findViewById(R.id.dlglv_services);
				    lvCategories = (ListView) dialogadd.findViewById(R.id.dlglv_categories);
				    lvTicketAdd = (ListView) dialogadd.findViewById(R.id.dlglv_ticketadd);
				    //String s = listCategories.get(2).name;
				    //Log.e("View list", listCategories.get(2).name);
				    adapterCategories = new ListBaseAdapter(getActivity(), listCategories);
				    adapterCategories.setSelectedItem(0);
				    adapterCategories.initListBaseAdapter(3, 1);
					lvCategories.setAdapter(adapterCategories);
					
					
					adapterServices = new ListBaseAdapter(getActivity(), listServices);
					adapterServices.setSelectedItem(0);
					adapterServices.initListBaseAdapter(4, 1);
					lvServices.setAdapter(adapterServices);
					
					
					adapterTicketAdd = new TicketEditAdapter(getActivity(), listItemTicketAdd);
					lvTicketAdd.setAdapter(adapterTicketAdd);
					getListItemTicketAdd();
					dlgbtnok_ticketadd = (ImageButton) dialogadd.findViewById(R.id.dlgbtnok_ticketadd);
					dlgbtncancel_ticketadd = (ImageButton) dialogadd.findViewById(R.id.dlgbtncancel_ticketadd);
					dlgbtncancel_ticketadd.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							listItemTicketAdd.clear();
	   						listItemTicketAdd.add(new ItemTicketAdapter(-1,"-1","-1",-1, -1, false));
							dialogadd.dismiss();
						}
					});
					dlgbtnok_ticketadd.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							saveListItemTicketAdapterIsAdd();
							dialogadd.dismiss();
						}
					});
					lvCategories.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
							adapterCategories.setSelectedItem(arg2);
							adapterCategories.notifyDataSetChanged();
							Log.i("ticket functions", "Clicked list categories position: " + listCategories.get(arg2).getID());
							getListServices(listCategories.get(arg2).getID());
							//adapterCategories.notifyDataSetChanged();
						}
					});
					lvServices.setOnItemLongClickListener(new OnItemLongClickListener() {
						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,View arg1, final int arg2, long arg3) {
							adapterServices.setSelectedItem(arg2);
							adapterServices.notifyDataSetChanged();
							AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					        builder.setMessage("Add sale this sale item")
					               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
					                   public void onClick(DialogInterface dialog, int id) {
					                	   //listItemTicketAdd.clear();
					                	   //listTicketAdd.add(new ItemTicketAdapter(1,"Product","sale item 1",2, 3, false));
					                	   ItemTicketAdapter itemadd = new ItemTicketAdapter();
					                	   itemadd.setID_ItemTicket(TicketPresent.getID());
					                	   itemadd.setDescriptioon(listServices.get(arg2).getName());
					                	   itemadd.setID_SaleItem(listServices.get(arg2).getID());
					                	   itemadd.setPrice(listServices.get(arg2).getPrice());
					                	   itemadd.setQuality(1);
					                	   itemadd.setType(getSalteItemType(listServices.get(arg2).getId_Type()));
					                	   checkingAddItemTicketAdapter(itemadd);
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
					dialogadd.show();
					
				}
			});
			lvEdits.setOnItemLongClickListener (new OnItemLongClickListener() {
				  public boolean onItemLongClick(AdapterView parent, View view, int position, long id)
				  {
					  ItemTicketAdapter itemticket = listEdits.get(position);
					  if(itemticket.getID_ItemTicket()==-1&&itemticket.getQuality()==-1&&itemticket.getPrice()==-1&&itemticket.getDescriptioon().equals("-1"))
					  {
						  return false;
					  }
					  else
					  {
						  if(itemticket.getID_ItemTicket()==-2&&itemticket.getQuality()==-2&&itemticket.getPrice()==-2&&itemticket.getDescriptioon().equals("-2"))
						  {
							  return false;
						  }
						  else
						  {
							  ItemTicketAdapterPresent = listEdits.get(position);
							  Log.i("ticket function", "Set itemticket adapter present " + ItemTicketAdapterPresent.getID_ItemTicket());
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
							                	   		dialogedit.setTitle("Ban hay nhap diem cho hoc sinh");
							        					TextView tvtype = (TextView) dialogedit.findViewById(R.id.dlgtv_valuetype);
							        					tvtype.setText(ItemTicketAdapterPresent.getType());
							        					TextView tvdescription = (TextView) dialogedit.findViewById(R.id.dlgtv_valuedescription);
							        					tvdescription.setText(ItemTicketAdapterPresent.getDescriptioon());
							        					TextView tvprice = (TextView) dialogedit.findViewById(R.id.dlgtv_valueprice);
							        					tvprice.setText(Float.toString(ItemTicketAdapterPresent.getPrice()));
							        					final EditText edittext = (EditText) dialogedit.findViewById(R.id.dlged__ticketedit);
							        					edittext.setText(Integer.toString(ItemTicketAdapterPresent.getQuality()));
							        					ImageButton dialogButtonok = (ImageButton) dialogedit.findViewById(R.id.dlgbtn_ok_ticketedit);
							        					ImageButton dialogButtoncancell = (ImageButton) dialogedit.findViewById(R.id.dlgbtn_cancel__ticketedit);
							        					dialogButtonok.setOnClickListener(new OnClickListener() {
							        						@Override
							        						public void onClick(View v) {
							        							String s = edittext.getText().toString();
							        							if(isNumeric(s)==true)
							        							{
							        								saveItemTicketAdapterIsEdited(Integer.parseInt(s));
							        								action_Ticketfunction = 0;
							        								//ItemTicketAdapterPresent = null;
							        								dialogedit.dismiss();
							        							}
							        						}
							        					});
							        					dialogButtoncancell.setOnClickListener(new OnClickListener() {
							        						@Override
							        						public void onClick(View v) {
							        							ItemTicketAdapterPresent = null;
							        							dialogedit.dismiss();
							        							
							        						}
							        					});
							        					dialogedit.show();
							        					// endregion
							                	   		break;
													case 1:
														//region  chon chuc nang delete
														AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
														builder.setMessage("Are you sure when delete this sale item")
														       .setTitle("Alert !!!")
														       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which) {
																	action_Ticketfunction = 0;
																	deleteItemTicketAdapterIsEdited();
																	ItemTicketPresent =null;
																}
															}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
																@Override
																public void onClick(DialogInterface dialog, int which) {
																	action_Ticketfunction = 0;
																	ItemTicketAdapterPresent = null;
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
	// region cac ham xu ly
	public boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}
	public String getSalteItemType(int id)
    {
        if(id==1)
        {
        	return "Product";
        }
        if(id==2)
        {
        	return "Service";
        }
        if(id==3)
        {
        	return "Extra";
        }
        if(id==4)
        {
        	return "Tips";
        }
        if(id==5)
        {
        	return "Coupon";
        }
        if(id==6)
        {
        	return "Discount";
        }
        if(id==7)
        {
        	return "Discount by Point";
        }
        if(id==8)
        {
        	return "Deducted";
        }
        return null;

    }
	public void addvalueEmptyToListEdit()
	{
		listEdits.add(new ItemTicketAdapter(-1,"-1","-1",-1, -1, false));
		for(int h=0;h<13;h++)
		{
			listEdits.add(new ItemTicketAdapter(-2,"-2","-2",-2, -2, false));
		}
		
	}
	public int getTypeMoney(String text)
    {
        if(text.equals("Product"))
        {
        	return 1;
        }
        if(text.equals("Service"))
        {
        	return 2;
        }
        if(text.equals("Extra"))
        {
        	return 3;
        }
        if(text.equals("Tips"))
        {
        	return 4;
        }
        if(text.equals("Coupon"))
        {
        	return 5;
        }
        if(text.equals("Discount"))
        {
        	return 6;
        }
        if(text.equals("Discount by Point"))
        {
        	return 7;
        }
        if(text.equals("Deducted"))
        {
        	return 8;
        }
        return -1;

    }
	// endregion
	/**
	 * Neu idtype = 1 get by day
	 * Neu idtype = 2 get by month
	 * Neu idtype = 3 get by year
	 * @param idtype
	 */
	public void getDayStartAndDayEndBySingleText(int idtype)
	{
		try {
			datestart = xmlSdf.parse(single.getTag().toString());
			dateend = xmlSdf.parse(single.getTag().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		switch (idtype) {
		case 1:
			break;
		case 2:
			datestart.setDate(1);
			dateend.setDate(myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			break;
		case 3:
			datestart.setMonth(0);
			datestart.setDate(1);
			dateend.setMonth(11);
			dateend.setDate(31);
			break;
		default:
			break;
		}
	}
	public void getDayStartAndDayEndByTwoText()
	{
		try {
			datestart = xmlSdf.parse(start.getTag().toString());
			dateend = xmlSdf.parse(end.getTag().toString());
			Log.i("Get day", start.getTag().toString() + "--" + start.getTag().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private void LoadItemTicketByIdTicket(final int idTicket)
	{
		Thread threadloadItemTicket = new Thread()
		{
			@Override
			public void run() {
				convertListItemTicketToItemTiketAdapter(itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID()));
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//enable(tabhost);
						adapterEdits.notifyDataSetChanged();
						tabhost.setCurrentTab(1);
						total = "Total: " +Sum;
						deducted = "Deducted: " + Deducted;
						forowner = "For Owner: "+ForOwner;
						fortech = "For Tech: " +ForTech;
						tvTotal.setText(total);
						tvDeducted.setText(deducted);
						tvForTech.setText(fortech);
						tvForOwner.setText(forowner);
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
						
					}
				});
			}
		};
		threadloadItemTicket.start();
	}
	private void LoadTicketByIdEmployee(final int id, final String datebegin,final String dateend)
	{
		Thread threadLoadTickets = new Thread(){
			@Override
			public void run() {
				LoadingTicket(id, datebegin, dateend);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Log.i("Load employee", "end progressbar");
						tvInfoTicket.setText(valueTicketPresent);
						total="Total: " + Sum;
						deducted="Deducted: " + Deducted;
						forowner="For Owner: " +  ForOwner;
						fortech ="For Tech: "  +ForTech;
						tvTotal.setText(total);
						tvDeducted.setText(deducted);
						tvForTech.setText(fortech);
						tvForOwner.setText(forowner);
						adapterTickets.notifyDataSetChanged();
						if(TicketPresent==null)
						{
							btnTicketEdit_Add.setEnabled(false);
							btnTicketEdit_DeleteTicket.setEnabled(false);
						}else
						{
							btnTicketEdit_Add.setEnabled(true);
							btnTicketEdit_DeleteTicket.setEnabled(true);
						}
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
						tabhost.setCurrentTab(0);
					}
				});
			}
		};
		threadLoadTickets.start();
	}
	public void LoadingTicket(final int id, final String datebegin,final String dateend)
	{
		listTickets.clear();
		Log.i("loading ticket", "idemployee: "+id + "time: "+datebegin +"--"+dateend);
		listTickets.addAll(ticketDAO.getListTicketBetween(id, datebegin, dateend));
		Log.i("loading ticket", "idEmployee "+ id + listTickets.size());
		if(listTickets.size()>0)
		{
			adapterTickets.setSelectedItem(0);
			TicketPresent = listTickets.get(0);
			valueTicketPresent = TicketPresent.getCode();
			Log.i("loading ticket","first ticket id: " + TicketPresent.getID());
			convertListItemTicketToItemTiketAdapter(itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID()));
		}
		else
		{
			Log.i("loading ticket","no ticket for idemployee");
			listEdits.clear();
			addvalueEmptyToListEdit();
			Sum = 0;
			ForOwner = 0;
			ForTech = 0;
			Deducted = 0;
			valueTicketPresent = "";
			TicketPresent = null;
		}
		Log.i("loading ticket","finish loading ticket");
	}
	int h =0;
	public void saveItemTicketAdapterIsEdited(final int soluongmoi)
	{
		ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Saving...", true);
		ringProgressDialog.setCancelable(true);
		flag_ringprogress = true;
		Thread saveitemticketAdapter = new Thread()
		{
			@Override
			public void run() {
				Log.i("saveItemTicketAdapterIsEdited","start");
				updateItemTicketAdapter(ItemTicketAdapterPresent.getSoluongcu(),soluongmoi,  ItemTicketAdapterPresent);
				//ArrayList<ItemTicket> dsupdateticket = itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID());
				Log.i("saveItemTicketAdapterIsEdited", "end");
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterEdits.notifyDataSetChanged();
						progessbarTicketEdit.setVisibility(View.GONE);
						tabhost.setCurrentTab(1);
						total = "Total: " +Sum;
						deducted = "Deducted: " + Deducted;
						forowner = "For Owner: "+ForOwner;
						fortech = "For Tech: " +ForTech;
						tvTotal.setText(total);
						tvDeducted.setText(deducted);
						tvForTech.setText(fortech);
						tvForOwner.setText(forowner);
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
						
					}
				});
			};
		};
		
		saveitemticketAdapter.start();
	}
	/**
	 * update cho itemticketadapter
	 * @param soluongcu
	 * @param soluongmoi
	 * @param itemticketadapter
	 */
	public void updateItemTicketAdapter(final int soluongcu,final int soluongmoi,final ItemTicketAdapter itemticketadapter)
	{
				Log.i("updateItemTicketAdapter", "start");
				Log.i("updateitemticketadapter", "soluongcu: "+ soluongcu + " soluongmoi: " + soluongmoi);
				ReportDTO reportDTO = new ReportDTO();
				ItemTicket itemticket = itemTicketDAO.getItemTicketById(itemticketadapter.getID_ItemTicket());
				reportDTO.setDate(TicketPresent.getDate());
				reportDTO.setId_Employee(TicketPresent.getID_Employee());
				reportDTO.setId_saleitem(itemticket.getID_SaleItem());
				reportDTO.setMoney(itemticket.getPrice());
				reportDTO.setId_type_money(getTypeMoney(itemticketadapter.getType()));
				reportDTO.setId_type_method(1);
				 if(soluongmoi>0&&soluongmoi!=soluongcu)
				{
					if (soluongmoi > soluongcu)
			        {
			            for (int i = soluongcu; i < soluongmoi; i++)
			            {
			                Log.i("updateItemTicket","TH soluongmoi>soluonghientai: them" +" dang them " + i+"idsaleitem" + reportDTO.getId_saleitem());
		                	Log.i("updateItemTicket","TH soluongmoi>soluonghientai: value Report idEmployee" + reportDTO.getId_Employee() + "idSaleItem: " + reportDTO.getId_saleitem());
			                if(reportDAO.InsertReport(reportDTO)==true)
			                {
			                	Log.i("updateItemTicket","TH soluongmoi>soluonghientai: them succesfull" + i + reportDTO.getId_saleitem());
			                }
			                else
			                {
			                	Log.i("updateItemTicket","TH soluongmoi>soluonghientai: them failed" + i + reportDTO.getId_saleitem());
			                }
			            }
			        }
			        else
			        {
			        	Log.i("updateItemTicket","soluong moi: " +soluongmoi + "< so luong hien tai: " +ItemTicketAdapterPresent.getQuality());
			            final ArrayList<ReportDTO> listReport = reportDAO.GetIListtemReportWithEmployee(TicketPresent.getID_Employee());
			            int k = 0;
			            h=0;
			            for (h = 0; h < listReport.size(); h++)
			            {
			                if (k == itemticket.getQuality() -soluongmoi)
			                {
			                    break;
			                }
			                else
			                {
			                	if (listReport.get(h).getId_saleitem() == reportDTO.getId_saleitem())
			                    {
			                		Log.i("ticket functions", "xoa item report");
			                     	reportDAO.deleteItemReport(listReport.get(h).getId());
			                        k++;
			                    }
			                }
			            }
			        }
					final ItemTicket item = itemTicketDAO.getItemTicketById(itemticketadapter.getID_ItemTicket());
					item.setQuality(soluongmoi);
					Log.i("saveItemTicket ", "update ItemTicket value idItemticket "+item.getID()+" idSale: "+ item.getID_SaleItem()+" idIdTicket: " + item.getID_Ticket());
					if(itemTicketDAO.updateItemTicket(item)==true)
					{
						Log.i("saveItemTicket ", "update succesfully itemticket");
					}
					else
					{
						Log.i("saveItemTicket ", "update: failed itemticket");
					}
					convertListItemTicketToItemTiketAdapter(itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID()));
					//itemticketadapter = null;
				}
				 Log.i("updateItemTicketAdapter", "start");
	}
	public void deleteItemTicketAdapterIsEdited()
	{
		if(ItemTicketAdapterPresent!=null)
		{
			ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Saving...", true);
			ringProgressDialog.setCancelable(true);
			flag_ringprogress = true;
			Thread threaddeleteItemTicket = new Thread()
			{
				int p;
				@Override
				public void run() {
					final ArrayList<ReportDTO> dsreport = reportDAO.GetIListtemReportWithEmployee(EmployeePresent.getID_Employee());
					String dateticket = TicketPresent.getDate();
                    for (p = 0; p < dsreport.size(); p++)
                    {
                        if (dsreport.get(p).getDate().compareTo(dateticket)==0&&dsreport.get(p).getId_saleitem()==ItemTicketAdapterPresent.getID_SaleItem())
                        {
                        	Log.i("ticket functions", "Delete success report id employee"+ dsreport.get(p).getId_Employee() + "id saleitem" +dsreport.get(p).getId_saleitem());
                            if(reportDAO.deleteItemReport(dsreport.get(p).getId())==true)
                            {
                            	Log.i("ticket function", "xoa successfull");
                            }
                            else
                            {
                            	Log.i("ticket function", "xoa report fail");
                            }
                            break;
                        }
                    }
					if(itemTicketDAO.deleteItemTicket(ItemTicketAdapterPresent.getID_ItemTicket())==true)
					{
						Log.i("ticket functions", "Delete successfull item ticket id: " + ItemTicketAdapterPresent.getID_ItemTicket());
					}
					else
					{
						Log.i("ticket functions", "Delete fail item ticket id: " + ItemTicketAdapterPresent.getID_ItemTicket());
					}
					ArrayList<ItemTicket> dsupdateticket = itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID());
					convertListItemTicketToItemTiketAdapter(dsupdateticket);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterEdits.notifyDataSetChanged();
							progessbarTicketEdit.setVisibility(View.GONE);
							tabhost.setCurrentTab(1);
							tvTotal.setText("Total: " + Sum);
							tvDeducted.setText("Deducted: " +  Deducted);
							tvForTech.setText("For Tech: "+ ForTech);
							tvForOwner.setText("For Owner: "+ ForOwner);
							if(flag_ringprogress==true)
							{
								ringProgressDialog.dismiss();
								flag_ringprogress=false;
							}
							
						}
					});
					ItemTicketAdapterPresent = null;
				};
			};
			threaddeleteItemTicket.start();
		}
	}
	public void convertListItemTicketToItemTiketAdapter(final ArrayList<ItemTicket> array)
	{
		Sum = 0;
		ForTech = 0;
		ForOwner= 0;
		listEdits.clear();
		listEdits.add(new ItemTicketAdapter(-1,"-1","-1",-1, -1, false));
		Log.i("Loadeditticket", "size array: " + array.size());
			int i = 0;
				//WCFNail wcf = new WCFNail();
				Sum = 0;
				for(i=0;i<array.size();i++)
				{
					int id = array.get(i).getID_SaleItem();
					int quality = array.get(i).getQuality();
					if(array.get(i).getID_SaleItem()==-1)
					{
						Deducted = array.get(i).getPrice();
						Log.i("Loadeditticket", "row " + i +" deducted is" + array.get(i).getPrice());
					}
					else
					{
						ItemTicketAdapter temp = new ItemTicketAdapter();
						String description = "-2";
						String type = "-2";
						if(array.get(i).getID_SaleItem()==0)
						{
							description = "Tips";
							type = "Tips";
							temp.setType("Tips");
							temp.setDescriptioon("Tips");
							Log.i("Loadeditticket", "Load ListEdit Ticket: row " + i +" is Tips" + array.get(i).getPrice());
						}
						else
						{
							description = saleItemDAO.getNameSaleItem(array.get(i).getID_SaleItem());
							type = saleItemDAO.getTypeSaleItem(array.get(i).getID_SaleItem());
						}
						// Chu y truong hop Bang SaleItem da bi xoa ma khong xoa khoa ngoai toi no nen Banh ItemTicket khong the tim ra ID_SaleItem
						if((description.equals("-2")==false||description.equals("Tips")==true)&&(type.equals("-2")==false||type.equals("Tips")==true))
						{
							temp.setDescriptioon(description);
							temp.setType(type);
						}
						String price = Float.toString(array.get(i).getPrice());
						temp.setPrice(Float.parseFloat(price)*quality);
						temp.setQuality(array.get(i).getQuality());
						Log.i("Loadeditticket", "info: quality"+array.get(i).getQuality());
						int aa = array.get(i).getID();
						temp.setID_ItemTicket(array.get(i).getID());
						temp.setIsAdd(false);
						temp.setSoluongcu(array.get(i).getQuality());
						temp.setID_SaleItem(array.get(i).getID_SaleItem());
						listEdits.add(temp);
						Sum += Float.parseFloat(price)*quality;
						// de tinh toan cho tech anh ownner
						float tempForTwoPeople = Sum -Deducted;
						float percent = EmployeePresent.getPercent();
						DecimalFormat df = new DecimalFormat("##.##");
						df.setRoundingMode(RoundingMode.DOWN);
						String forowner = df.format((double)(percent*tempForTwoPeople)/100);
						String fortech = df.format((double)((100-percent)*tempForTwoPeople)/100);
						ForOwner = Float.parseFloat(forowner);
						ForTech = Float.parseFloat(fortech);
					}
				}
				if(listEdits.size()+1<13)
				{
					int count = 13-listEdits.size();
					for(int h=0;h<count;h++)
					{
						listEdits.add(new ItemTicketAdapter(-2,"-2","-2",-2, -2, false));
					}
				}
	}
	
	public void getListCategories()
	{
		Thread threadCategories = new Thread(){
			@Override
			public void run() {
				listCategories = categoryDAO.getListCategory();
				if(listCategories.size()>0)
				{
					listServices = saleItemDAO.getListSaleItemByIDCategory(listCategories.get(0).getID());
				}
			}
		};
		threadCategories.start();
	}
	/**
	 * Lay danh sach cac saleitem trong csdl tu mot categories
	 * @param idCategory
	 */
	public void getListServices(final int idCategory)
	{
		listServices.clear();
		Thread threadService = new Thread(){
			@Override
			public void run() {
				WCFNail nailservice = new WCFNail();
				listServices.addAll(saleItemDAO.getListSaleItemByIDCategory(idCategory));
				if(listServices.size()>0)
				{
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterServices.setSelectedItem(0);
							adapterServices.notifyDataSetChanged();
						}
					});
				}
			}
		};
		threadService.start();
		
	}
	/**
	 * Kiem tra danh sach nguoi dung chon them vao do co chứa item mà người dung chon k
	 * neu co tang so luong len 1
	 * @param itemticketAdd
	 */
	public void checkingAddItemTicketAdapter(ItemTicketAdapter itemticketAdd)
	{
		// chua co trong danh sach
		boolean flag = false;
		for(int i=0;i<listItemTicketAdd.size();i++)
		{
			if(itemticketAdd.getID_SaleItem()==listItemTicketAdd.get(i).getID_SaleItem())
			{
				int soluong = listItemTicketAdd.get(i).getQuality();
				float price = listItemTicketAdd.get(i).getPrice();
				float pricegoc = price/soluong;
				listItemTicketAdd.get(i).setQuality(soluong+1);
				listItemTicketAdd.get(i).setPrice((soluong+1)*pricegoc);
				//listItemTicketAdd.get(i).setIsAdd(false);
				flag =true;
				break;
			}
		}
		// neu chua co trong danh sach thi them vao 
		if(flag==false)
		{
			itemticketAdd.setIsAdd(true);
			listItemTicketAdd.add(itemticketAdd);
		}
	}
	public void saveListItemTicketAdapterIsAdd()
	{
		if(listItemTicketAdd.size()>1)
		{
			ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...","Saving...", true);
			ringProgressDialog.setCancelable(true);
			flag_ringprogress = true;
			Thread saveitemticketisadd = new Thread()
			{
				@Override
				public void run() {
					for(int i=1;i<listItemTicketAdd.size();i++)
					{
						ItemTicketAdapter itemticketadapter = listItemTicketAdd.get(i);
						// bang false thi cap nhat la so luong
						if(itemticketadapter.isIsAdd()==false)
						{
							updateItemTicketAdapter(itemticketadapter.getSoluongcu(), itemticketadapter.getQuality(), itemticketadapter);
						}
						else
						{
							ItemTicket item = new ItemTicket();
							item.setID_SaleItem(listItemTicketAdd.get(i).getID_SaleItem());
							item.setID_Ticket(TicketPresent.getID());
							int pricegoc = (int) (listItemTicketAdd.get(i).getPrice()/listItemTicketAdd.get(i).getQuality());
							item.setPrice(pricegoc);
							item.setQuality(listItemTicketAdd.get(i).getQuality());
							itemTicketDAO.insertItemTicket(item);
							for(int h=0;h<listItemTicketAdd.get(i).getQuality();h++)
							{
								ReportDTO report = new ReportDTO();
								report.setDate(TicketPresent.getDate());
								report.setId_Employee(EmployeePresent.getID_Employee());
								report.setId_saleitem(listItemTicketAdd.get(i).getID_SaleItem());
								report.setId_type_money(getTypeMoney(itemticketadapter.getType()));
								report.setId_type_method(1);
								reportDAO.InsertReport(report);
							}
						}
					}
					ArrayList<ItemTicket> dsupdateticket = itemTicketDAO.getListItemTicketByIDTicket(TicketPresent.getID());
					convertListItemTicketToItemTiketAdapter(dsupdateticket);
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapterEdits.notifyDataSetChanged();
							progessbarTicketEdit.setVisibility(View.GONE);
							tabhost.setCurrentTab(1);
							total = "Total: " +Sum;
							deducted = "Deducted: " + Deducted;
							forowner = "For Owner: "+ForOwner;
							fortech = "For Tech: " +ForTech;
							tvTotal.setText(total);
							tvDeducted.setText(deducted);
							tvForTech.setText(fortech);
							tvForOwner.setText(forowner);
							if(flag_ringprogress==true)
							{
								ringProgressDialog.dismiss();
								flag_ringprogress=false;
							}
							
						}
					});
				}
			};
			saveitemticketisadd.start();
			
		}
	}
	public void deleteTicket()
	{
		Thread threaddeleteticket = new Thread()
		{
			@Override
			public void run() {
				Log.i("delete ticket","id "+ TicketPresent.getID() );
				Log.i("delete ticket", "delete items ticket");
				if(itemTicketDAO.deleteItemTicketByIDTicket(TicketPresent.getID())==true)
				{
					Log.i("delete ticket", "delete items ticket susscessfully");
				}else
				{
					Log.i("delete ticket", "delete items ticket fail");
				}
				Log.i("delete ticket", "delete ticket");
				if(ticketDAO.deleteTicket(TicketPresent.getID())==true)
				{
					Log.i("delete ticket", "delete ticket susscessfully");
				}else
				{
					Log.i("delete ticket", "delete ticket fail");
				}
				Log.i("delete ticket", "delete list dto");
				ArrayList<ReportDTO> dsreport = reportDAO.GetIListtemReportWithEmployee(EmployeePresent.getID_Employee());
				for(int i=0;i<dsreport.size();i++)
				{
					Log.i("delete ticket", "ticket date: "+ TicketPresent.getDate()+" -- "+dsreport.get(i).getId_saleitem());
					if(dsreport.get(i).getDate().compareTo(TicketPresent.getDate())==0 && dsreport.get(i).getId_saleitem()!=-1)
					{
						Log.i("delete ticket", "delete list dto i" + dsreport.get(i).getId_saleitem() + "---" + dsreport.get(i).getId_saleitem());
						reportDAO.deleteItemReport(dsreport.get(i).getId());
					}
				}
				Log.i("delete ticket", "delete finish");
				TicketPresent = null;
				ItemTicketAdapterPresent = null;
				getDayStartAndDayEndBySingleText(3);
				
				LoadingTicket(EmployeePresent.getID_Employee(), xmlSdfDate.format(datestart), xmlSdfDate.format(dateend));
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Log.i("Load employee", "end progressbar");
						tvInfoTicket.setText(valueTicketPresent);
						total="Total: " + Sum;
						deducted="Deducted: " + Deducted;
						forowner="For Owner: " +  ForOwner;
						fortech ="For Tech: "  +ForTech;
						tvTotal.setText(total);
						tvDeducted.setText(deducted);
						tvForTech.setText(fortech);
						tvForOwner.setText(forowner);
						adapterTickets.notifyDataSetChanged();
						if(TicketPresent==null)
						{
							btnTicketEdit_Add.setEnabled(false);
							btnTicketEdit_DeleteTicket.setEnabled(false);
						}else
						{
							btnTicketEdit_Add.setEnabled(true);
							btnTicketEdit_DeleteTicket.setEnabled(true);
						}
						if(flag_ringprogress==true)
						{
							ringProgressDialog.dismiss();
							flag_ringprogress=false;
						}
						tabhost.setCurrentTab(0);
					}
				});
			};
		};
		threaddeleteticket.start();
	}
	public void getListItemTicketAdd()
	{
		Thread getListItemticketAdd = new Thread()
		{
			@Override
			public void run() {
				listItemTicketAdd.clear();
				for(int i=0;i<listEdits.size();i++)
				{
					ItemTicketAdapter itemticket = new ItemTicketAdapter();
					if(listEdits.get(i).getID_ItemTicket()==-2&&listEdits.get(i).getQuality()==-2&&listEdits.get(i).getPrice()==-2&&listEdits.get(i).getDescriptioon().equals("-2"))
					{
						break;
					}
					itemticket.setDescriptioon(listEdits.get(i).getDescriptioon());
					itemticket.setID_ItemTicket(listEdits.get(i).getID_ItemTicket());
					itemticket.setID_SaleItem(listEdits.get(i).getID_SaleItem());
					itemticket.setPrice(listEdits.get(i).getPrice());
					itemticket.setQuality(listEdits.get(i).getQuality());
					itemticket.setSoluongcu(listEdits.get(i).getSoluongcu());
					itemticket.setType(listEdits.get(i).getType());
					itemticket.setIsAdd(false);
					listItemTicketAdd.add(itemticket);
				}
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapterTicketAdd.notifyDataSetChanged();
					}
				});
			};
		};
		getListItemticketAdd.start();
		
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
	public void enable(View v) {
		if (v instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
				enable(((ViewGroup) v).getChildAt(i));
			}
		} else {
			v.setEnabled(true);
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

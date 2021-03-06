package WS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import DTO.Category;
import DTO.CheckInfoTable;
import DTO.CheckTable;
import DTO.Employee;
import DTO.ItemInfo;
import DTO.ItemTicket;
import DTO.OrderExpense;
import DTO.ReportDTO;
import DTO.SaleItem;
import DTO.Ticket;
import android.util.Log;

public class WCFNail {
	//private static String NAMESPACE = "http://tempuri.org/";
	private static String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "http://posifyrt.cloudapp.net/NailService.svc";
	//private static String NAMESPACE = "http://localhost:1623/";
	//private static final String URL = "http://localhost:1623/NailService.svc";

	private String ISERVICE = "INailService/";

	public WCFNail() {
	}

	/**
	 * 
	 * @param para
	 * @param func
	 * @return
	 */
	private SoapPrimitive getSoapPrimitive(ArrayList<String> para, String func) {
		SoapPrimitive root = null;
		try {
			root = (SoapPrimitive) getData(func, para, true).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return null;
		}
		return root;
	}

	private SoapObject getSoap(ArrayList<String> para, String func) {
		SoapObject root = null;
		try {
			root = (SoapObject) getData(func, para, false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return null;
		}
		return root;
	}

	private SoapPrimitive getSoapPrimitiveNew(ArrayList<Object> para,
			String func) {
		SoapPrimitive root = null;
		try {
			root = (SoapPrimitive) getDataNew(func, para, true).get(0);
		} catch (Exception e) {
			return null;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return null;
		}
		return root;
	}

	private SoapObject getSoapNew(ArrayList<Object> para, String func) {
		SoapObject root = null;
		try {
			root = (SoapObject) getDataNew(func, para, false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return null;
		}
		return root;
	}

	public ArrayList<Object> getDataNew(String METHOD_NAME,
			ArrayList<Object> para, boolean isPrimitive) {
		ArrayList<Object> result = new ArrayList<Object>();
		SoapObject resultSoap = null;

		String SOAP_ACTION = NAMESPACE + ISERVICE + METHOD_NAME;
		try {
			// tao soapObject de thuc thi ket noi
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			if (para != null) {
				for (int i = 0; i < para.size(); i++) {
					SoapObject tmp = (SoapObject) para.get(i);
					request.addProperty("arg" + String.valueOf(i), tmp);
				}
			}
			Log.e("WS", "1");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			Log.e("WS", "1");
			envelope.setOutputSoapObject(request);
			Log.e("WS", "2");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,
					10000);
			// tao ket noi
			Log.e("WS", "3");
			Log.e(SOAP_ACTION, URL);
			Log.e("errorWS", SOAP_ACTION);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// lay du lieu tra ve
			Log.e("WS", "4");
			if (isPrimitive == false) {
				resultSoap = (SoapObject) envelope.bodyIn;// envelope.getResponse();
				result.add((SoapObject) (resultSoap.getProperty(0)));
				return result;
			} else {
				try {

					Log.e("WS", "6");
					Log.e("WS", envelope.bodyIn.toString());
					resultSoap = (SoapObject) envelope.bodyIn;
					Log.e("WS", "6");
					result.add((SoapPrimitive) resultSoap.getProperty(0));
					Log.e("WS", "7");
					return result;
				} catch (Exception e) {
					Log.e("e", e.getMessage());
				}
			}
			Log.e("WS", "5");

		} catch (Exception e) {
			Log.e("WSerror", e.getMessage());
			return null;
		}
		return null;
		// xu ly du lieu vua nhan duoc
	}

        /**
         * return SoapPrimitive or SoapObject
         * @param METHOD_NAME
         * @param para
         * @param isPrimitive
         * @return return SoapPrimitive or SoapObject
         */
        public ArrayList<Object> getData(String METHOD_NAME,
                        ArrayList<String> para, boolean isPrimitive) {
                ArrayList<Object> result = new ArrayList<Object>();
                SoapObject resultSoap = null;

		String SOAP_ACTION = NAMESPACE + ISERVICE + METHOD_NAME;
		try {
			// tao soapObject de thuc thi ket noi
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			if (para != null) {
				for (int i = 0; i < para.size(); i++) {
					request.addProperty("arg" + String.valueOf(i), para.get(i));
					Log.e("arg" + i, para.get(i));
				}
			}
			Log.e("WS", "1");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.dotNet = true;
			Log.e("WS", "1");
			envelope.setOutputSoapObject(request);
			Log.e("WS", "2");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,
					10000);
			// tao ket noi
			Log.e("WS", "3");
			Log.e(SOAP_ACTION, URL);
			Log.e("action", SOAP_ACTION);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			// lay du lieu tra ve
			Log.e("WS", "4");
			if (isPrimitive == false) {
				resultSoap = (SoapObject) envelope.bodyIn;// envelope.getResponse();
				result.add((SoapObject) (resultSoap.getProperty(0)));
				return result;
			} else {
				try {

					Log.e("WS", "6");
					Log.e("WS", envelope.bodyIn.toString());
					resultSoap = (SoapObject) envelope.bodyIn;
					Log.e("WS", "6");
					result.add((SoapPrimitive) resultSoap.getProperty(0));
					Log.e("WS", "7");
					return result;
				} catch (Exception e) {
					Log.e("e", e.getMessage());
				}
			}
			Log.e("WS", "5");

		} catch (Exception e) {
			Log.e("WSerror", e.getMessage());
			return null;
		}
		return null;
		// xu ly du lieu vua nhan duoc
	}

	public ArrayList<Employee> getAllEmployee() {
		// tao soapobject de nhan ket qua tra ve
		ArrayList<Employee> result = new ArrayList<Employee>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getAllEmployee", null, false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if (root == null) {
			Log.e("123", "123");
			return result;
		}
		// xu ly du lieu vua nhan duoc

		for (int i = 0; i < root.getPropertyCount(); i++) {
			Employee newEmployee = new Employee();
			SoapObject newSoap = (SoapObject) root.getProperty(i);
			newEmployee.setPercent(Float.parseFloat(newSoap.getProperty(2)
					.toString()));
			newEmployee.setID_Employee(Integer.parseInt(newSoap.getProperty(0)
					.toString()));
			newEmployee.setstrName((newSoap.getProperty(12).toString()));
			newEmployee.setstrAddr((newSoap.getProperty(9).toString()));
			Log.e(newEmployee.getstrName(),
					String.valueOf(newEmployee.getID_Employee()));
			result.add(newEmployee);
		}
		Log.e(String.valueOf(result.size()), "123");
		return result;

	}

	public ArrayList<OrderExpense> getAllExpense(ArrayList<String> para) {
		// tao soapobject de nhan ket qua tra ve
		ArrayList<OrderExpense> result = new ArrayList<OrderExpense>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getListBetweenDayOrderExpense", para,
					false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if (root == null) {
			Log.e("123", "123");
			return result;
		}
		// xu ly du lieu vua nhan duoc

		for (int i = 0; i < root.getPropertyCount(); i++) {
			OrderExpense newExpense = new OrderExpense();
			SoapObject newSoap = (SoapObject) root.getProperty(i);
			newExpense.setId(Integer
					.parseInt(newSoap.getProperty(1).toString()));
			newExpense.setName((newSoap.getProperty(2).toString()));
			newExpense.setDate((newSoap.getProperty(0).toString()));
			newExpense.setMoney(Float.parseFloat(newSoap.getProperty(3)
					.toString()));
			Log.e(String.valueOf(newExpense.getId()),
					String.valueOf(newExpense.getName()));
			result.add(newExpense);
		}
		Log.e(String.valueOf(result.size()), "123");
		return result;

	}

	public ArrayList<Float> getFullReport(ArrayList<String> para) {
		ArrayList<Float> result = new ArrayList<Float>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getFullReport", para, false).get(0);
		} catch (Exception e) {
			return result;
		}
		for (int i = 0; i < root.getPropertyCount(); i++) {
			Float temp = Float.parseFloat(root.getProperty(i).toString());
			result.add(temp);
		}
		Log.e(String.valueOf(result.size()), "123");
		return result;
	}

	public ArrayList<Float> getEmployeeReport(ArrayList<String> para) {
		ArrayList<Float> result = new ArrayList<Float>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getEmployeeReport", para, false)
					.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		for (int i = 0; i < root.getPropertyCount(); i++) {
			Float temp = Float.parseFloat(root.getProperty(i).toString());
			result.add(temp);
		}
		Log.e(String.valueOf(result.size()), "123");
		return result;
	}

	/**
	 * Lay danh sach ItemTicket theo id ticket, co getListByIDTicket(int
	 * id_ticket);
	 * 
	 * @param para
	 *            Chi co 1 param: int id_ticket
	 * @return ArrayList<ItemTicket>
	 */
	public ArrayList<ItemTicket> getListItemTicketByIDTicket(
			ArrayList<String> para) {
		String s = para.get(0);
		ArrayList<ItemTicket> result = new ArrayList<ItemTicket>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getListItemTicketByIDTicket", para,
					false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return result;
		}
		for (int i = 0; i < root.getPropertyCount(); i++) {
			ItemTicket tmp = new ItemTicket();
			tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
			result.add(tmp);
		}

		return result;
	}

	/**
	 * Get Lis
	 * 
	 * @param para
	 * @return
	 */
	public ItemTicket getItemTicketById(ArrayList<String> para) {

		ItemTicket result = new ItemTicket();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getItemTicketByID", para, false)
					.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return result;
		}
		result.getInfoFromSoap(root);
		return result;
	}

	// Ticket
	/**
	 * 
	 * @param para
	 *            int id_emloyee, DateTime dayBegin, DateTime dayEnd
	 * @return
	 */
	public ArrayList<Ticket> getListTicketBetween(ArrayList<String> para) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getListBetween", para, false).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if (root == null) {
			Log.e("error", "khong doc duoc");
			return result;
		}
		for (int i = 0; i < root.getPropertyCount(); i++) {
			Ticket tmp = new Ticket();
			tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
			result.add(tmp);
		}
		return result;
	}

	public boolean deleteAllItemTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "deleteAllItemTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public boolean deleteItemTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "deleteItemTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public boolean deleteItemTicketByIDTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para,
				"deleteItemTicketByIDTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public int insertItemTicket(final ItemTicket itemticket) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(String.valueOf(itemticket.getID()));
				add(String.valueOf(itemticket.getID_SaleItem()));
				add(String.valueOf(itemticket.getID_Ticket()));
				add(String.valueOf(itemticket.getPrice()));
				add(String.valueOf(itemticket.getQuality()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "insertItemTicket");
		if (root == null)
			return -1;
		return Integer.parseInt(root.toString());
	}

	/**
	 * Them mot itemticket Duy thieu cai nay, q bo sung
	 * 
	 * @param para
	 *            itemticket
	 * @return true if done successfull ortherwise false
	 */
	public boolean updateItemTicket(final ItemTicket itemticket) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(String.valueOf(itemticket.getID()));
				add(String.valueOf(itemticket.getID_SaleItem()));
				add(String.valueOf(itemticket.getID_Ticket()));
				add(String.valueOf(itemticket.getPrice()));
				add(String.valueOf(itemticket.getQuality()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "updateItemTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	/*
	 * public boolean updateItemTicket(ArrayList<String> para) { SoapPrimitive
	 * root = getSoapPrimitive(para, "updateItemTicket"); if(root == null )
	 * return false; return Boolean.valueOf(root.toString()); }
	 */
	/**
	 * 
	 * @param para
	 * @return
	 */
	public int getIDTicketMax(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getIDTicketMax");
		if (root == null)
			return -1;
		return Integer.parseInt(root.toString());
	}

	/**
	 * 
	 * @param para
	 *            (int id)
	 * @return
	 */
	public ArrayList<Ticket> getListTicketByIDEmployee(ArrayList<String> para) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getListTicketByIDEmployee");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				Ticket tmp = new Ticket();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}

	/**
	 * 
	 * @param para
	 *            int id
	 * @return
	 */
	public boolean DeleteSaleItem(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "DeleteSaleItem");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public int getIDSaleItemMax(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getIDSaleItemMax");
		if (root == null)
			return -1;
		return Integer.valueOf(root.toString());
	}

	/**
	 * 
	 * @param para
	 * @return
	 */
	public ArrayList<SaleItem> getListSaleItem(ArrayList<String> para) {
		ArrayList<SaleItem> result = new ArrayList<SaleItem>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getListSaleItem");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				SaleItem tmp = new SaleItem();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}

	/**
	 * 
	 * @param para
	 *            int idcategory
	 * @return
	 */
	public ArrayList<SaleItem> getListSaleItemByIDCategory(
			ArrayList<String> para) {
		ArrayList<SaleItem> result = new ArrayList<SaleItem>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getListSaleItemByIDCategory");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				SaleItem tmp = new SaleItem();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}

	/**
	 * tra ve -2, dang ra la -1 nhung -1 la cho mac dinh la deduced
	 * 
	 * @param para
	 *            int idsaleitem
	 * @return
	 */
	public String getNameSaleItem(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getNameSaleItem");
		if (root == null)
			return "-2";
		String s = root.toString();
		return (root.toString());
	}

	/**
	 * tra ve -2, dang ra la -1 nhung -1 la cho mac dinh la deduced
	 * 
	 * @param para
	 *            int idsaleitem
	 * @return
	 */
	public String getTypeSaleItem(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getTypeSaleItem");
		if (root == null)
			return "-2";
		return (root.toString());
	}

	/**
	 * tra ve -2, dang ra la -1 nhung -1 la cho mac dinh la deduced
	 * 
	 * @param para
	 *            int idsaleitem
	 * @return
	 */
	public String getPriceSaleItem(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getPriceSaleItem");
		if (root == null)
			return "-2";
		return (root.toString());
	}

	/**
	 * 
	 * @param para
	 *            saleitem
	 * @return
	 */
	public int insertSaleItem(final SaleItem saleitem) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(String.valueOf(saleitem.getID()));
				add(String.valueOf(saleitem.getId_Category()));
				add(String.valueOf(saleitem.getId_Type()));
				add(String.valueOf(saleitem.getName()));
				add(String.valueOf(saleitem.getPrice()));
				add(String.valueOf(saleitem.getStatus()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "insertSaleItem");
		if (root == null)
			return -1;
		return Integer.parseInt(root.toString());
	}

	/**
	 * 
	 * @param para
	 *            saleitem
	 * @return
	 */
	public Boolean UpdateSaleItem(final SaleItem saleitem) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(String.valueOf(saleitem.getID()));
				add(String.valueOf(saleitem.getId_Category()));
				add(String.valueOf(saleitem.getId_Type()));
				add(String.valueOf(saleitem.getName()));
				add(String.valueOf(saleitem.getPrice()));
				add(String.valueOf(saleitem.getStatus()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "UpdateSaleItem");
		if (root == null)
			return false;
		return Boolean.parseBoolean(root.toString());

	}

	/**
	 * 
	 * @param para
	 *            idCategory
	 * @return
	 */
	public int getIDCategoryMax(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getIDCategoryMax");
		if (root == null)
			return -1;
		return Integer.parseInt(root.toString());

	}

	public ArrayList<Category> getListCategory(ArrayList<String> para) {
		ArrayList<Category> result = new ArrayList<Category>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getListCategory");
		for (int i = 0; i < root.getPropertyCount(); i++) {
			Category tmp = new Category();
			tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
			result.add(tmp);
		}
		return result;
	}

	/**
	 * Duy thieu ham nay, q bo sung
	 * 
	 * @param para
	 *            truyen vao idReport muon xoa
	 * @return true neu xoa thanh cong, ortherwise false
	 */
	public boolean deleteItemReport(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "deleteItemReport");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	/**
	 * 
	 * @param para
	 *            idItemReport
	 * @return
	 */
	// public Boolean deleteItemReport(ArrayList<Object> para) {
	// SoapPrimitive root = getSoapPrimitiveNew(para, "deleteItemReport");
	// if (root == null)
	// return false;
	// return Boolean.parseBoolean(root.toString());
	//
	// }

	public Boolean InsertReport(final ReportDTO report) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(report.getDate());
				add(String.valueOf(report.getId()));
				add(String.valueOf(report.getId_Employee()));
				add(String.valueOf(report.getId_saleitem()));
				add(String.valueOf(report.getId_type_method()));
				add(String.valueOf(report.getId_type_money()));
				add(String.valueOf(report.getMoney()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "InsertReport");
		if (root == null)
			return false;
		return Boolean.parseBoolean(root.toString());

	}

	public ArrayList<ReportDTO> GetIListtemReportWithEmployee(
			ArrayList<String> para) {
		ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "GetIListtemReportWithEmployee");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				ReportDTO tmp = new ReportDTO();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}

	// ticket
	public boolean deleteAllTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "deleteAllTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public boolean deleteTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "deleteTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public boolean insertTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "insertTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	public boolean updateTicket(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "updateTicket");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}

	// check info table
	public ArrayList<CheckInfoTable> getAllCheckInfoTable(
			ArrayList<String> para) {
		ArrayList<CheckInfoTable> result = new ArrayList<CheckInfoTable>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getAllCheckInfoTable");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				CheckInfoTable tmp = new CheckInfoTable();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	/**
	 * 
	 * @param DateTime timeBegin, DateTime timeEnd, int id_Employee
	 * @return 
	 */
	public ArrayList<CheckInfoTable> getAllCheckInfoWithEmployee(
			ArrayList<String> para) {
		ArrayList<CheckInfoTable> result = new ArrayList<CheckInfoTable>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getAllCheckInfoWithEmployee");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				CheckInfoTable tmp = new CheckInfoTable();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	public ArrayList<CheckInfoTable> getAllCheckInfoWithTime(
			ArrayList<String> para) {
		ArrayList<CheckInfoTable> result = new ArrayList<CheckInfoTable>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getAllCheckInfoWithTime");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				CheckInfoTable tmp = new CheckInfoTable();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	
	public boolean insertCheckInfoTable(final CheckInfoTable checkinfotable) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(checkinfotable.getCheckIn());
				add(checkinfotable.getCheckOut());
				add(String.valueOf(checkinfotable.getId()));
				add(String.valueOf(checkinfotable.getId_Employee()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "insertCheckInfoTable");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	//CheckTableBUS
	/**
	 * Lay danh sach tat ca cac checktable
	 * @param para null
	 * @return
	 */
	public ArrayList<CheckTable> getAllCheckTable(
			ArrayList<String> para) {
		ArrayList<CheckTable> result = new ArrayList<CheckTable>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getAllCheckTable");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				CheckTable tmp = new CheckTable();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	/**
	 * Lay Checktable theo id nhan vien
	 * @param para idEmployee
	 * @return
	 */
	public CheckTable getCheckTable(
			ArrayList<String> para) {
		CheckTable result = new CheckTable();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getCheckTable");
		result.getInfoFromSoap(root);
		return result;
	}
	/**
	 * 
	 * @param para id_Employee
	 * @return
	 */
	public boolean getCheckStatus(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getCheckStatus");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	/**
	 * 
	 * @param para DateTime arg0, DateTime arg1, bool arg2, int arg3, int arg4
	 * @return
	 */
	public boolean insertCheckTable(final CheckTable checktable) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(checktable.getCheckIn());
				add(checktable.getCheckOut());
				add(String.valueOf(checktable.isCheck_Status()));
				add(String.valueOf(checktable.getId()));
				add(String.valueOf(checktable.getId_Employee()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "insertCheckTable");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	/**
	 * 
	 * @param para DateTime arg0, DateTime arg1, bool arg2, int arg3, int arg4
	 * @return
	 */
	public boolean updateCheckIn(final CheckTable checktable) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(checktable.getCheckIn());
				add(checktable.getCheckOut());
				add(String.valueOf(checktable.isCheck_Status()));
				add(String.valueOf(checktable.getId()));
				add(String.valueOf(checktable.getId_Employee()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "updateCheckIn");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	/**
	 * 
	 * @param para DateTime arg0, DateTime arg1, bool arg2, int arg3, int arg4
	 * @return
	 */
	public boolean updateCheckOut(final CheckTable checktable) {
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(checktable.getCheckIn());
				add(checktable.getCheckOut());
				add(String.valueOf(checktable.isCheck_Status()));
				add(String.valueOf(checktable.getId()));
				add(String.valueOf(checktable.getId_Employee()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "updateCheckOut");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	//securityBus
	/**
	 * 
	 * @param para string code, int id
	 * @return
	 */
	public boolean checkLoginEmployee(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "checkLoginEmployee");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	/**
	 * 
	 * @param para string code, int id
	 * @return
	 */
	public boolean checkLoginOwner(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "checkLoginOwner");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	
	/**
	 * 
	 * @param para string code, int id
	 * @return
	 */
	public boolean updateOwnerCode(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "updateOwnerCode");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	public String getInforWithID(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "getInforWithID");
		if (root == null)
			return null;
		return (root.toString());
	}
	
	public String GetPointOwner(ArrayList<String> para) {
		SoapPrimitive root = getSoapPrimitive(para, "GetPointOwner");
		if (root == null)
			return null;
		return (root.toString());
	}
	public Employee getEmployeeWithId(
			ArrayList<String> para) {
		Employee result = new Employee();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(para, "getEmployeeWithId");
		result.getInfoFromSoap(root);
		return result;
	}
	public ArrayList<ItemInfo> getAllListItemInfo(ArrayList<String> para)
	{
		ArrayList<ItemInfo> result = new ArrayList<ItemInfo>();
		Log.e("getAllListItemInfo", "1");
		SoapObject root = getSoap(para, "getAllListItemInfo");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				ItemInfo tmp = new ItemInfo();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	public ArrayList<ItemInfo> getListItemInfoByDay(ArrayList<String> para)
	{
		ArrayList<ItemInfo> result = new ArrayList<ItemInfo>();
		Log.e("getListItemInfoByDay", "1");
		SoapObject root = getSoap(para, "getListItemInfoByDay");
		if(root!=null)
		{
			for (int i = 0; i < root.getPropertyCount(); i++) {
				ItemInfo tmp = new ItemInfo();
				tmp.getInfoFromSoap((SoapObject) (root.getProperty(i)));
				result.add(tmp);
			}
			return result;
		}
		return null;
	}
	public boolean addItemInfoToCalendar(final ItemInfo iteminfo)
	{
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(iteminfo.getStartday());
				add(iteminfo.getEndday());
				add(iteminfo.getText());
				add(Integer.toString(iteminfo.getA()));
				add(Integer.toString(iteminfo.getR()));
				add(Integer.toString(iteminfo.getG()));
				add(Integer.toString(iteminfo.getB()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "addItemInfoToCalendar");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	public boolean removeItemInfoFromCalendar(final ItemInfo iteminfo)
	{
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(iteminfo.getStartday());
				add(iteminfo.getEndday());
				add(iteminfo.getText());
				add(Integer.toString(iteminfo.getA()));
				add(Integer.toString(iteminfo.getR()));
				add(Integer.toString(iteminfo.getG()));
				add(Integer.toString(iteminfo.getB()));
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "removeItemInfoFromCalendar");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	public boolean updateItemInfoOfCalendar(final ItemInfo arg0,final ItemInfo arg1)
	{
		ArrayList<String> para = new ArrayList<String>() {
			{
				add(arg0.getStartday());
				add(arg0.getEndday());
				add(arg0.getText());
				add(Integer.toString(arg0.getA()));
				add(Integer.toString(arg0.getR()));
				add(Integer.toString(arg0.getG()));
				add(Integer.toString(arg0.getB()));
				add(arg1.getStartday());
				add(arg1.getEndday());
				add(arg1.getText());
				add(Integer.toString(arg1.getA()));
				add(Integer.toString(arg1.getR()));
				add(Integer.toString(arg1.getG()));
				add(Integer.toString(arg1.getB()));
				for(int i=0;i<this.size();i++)
				{
					Log.i("update i" +i,this.get(i));
				}
			}
		};
		SoapPrimitive root = getSoapPrimitive(para, "updateItemInfoOfCalendar");
		if (root == null)
			return false;
		Log.i("update iteminfo", "true");
		return Boolean.valueOf(root.toString());
	}
}
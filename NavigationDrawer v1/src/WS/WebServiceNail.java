package WS;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import DTO.Employee;
import DTO.OrderExpense;
import android.util.Log;

public class WebServiceNail {
	private static String METHOD_NAME_1 = "getAllEmployee";

	private static String METHOD_NAME_2 = "getEmployeeWithId";

	private static String METHOD_NAME_3 = "getIdLastEmployee";

	private static String METHOD_NAME_4 = "getDanhSachKetQua";

	private static String NAMESPACE = "http://tempuri.org/";

	private static String SOAP_ACTION_1 = NAMESPACE + METHOD_NAME_1;

	private static String SOAP_ACTION_2 = NAMESPACE + METHOD_NAME_2;

	private static String SOAP_ACTION_3 = NAMESPACE + METHOD_NAME_3;

	private static String SOAP_ACTION_4 = NAMESPACE + METHOD_NAME_4;

	private static final String mainURL = "http://10.0.2.2:57865/";
	private static String tagURL = "CustomerWS.asmx";
	private String URL;
	public WebServiceNail(String tag)
	{
		tagURL = tag;
		URL = mainURL + tagURL;
	}
	public ArrayList<Object> getData(String METHOD_NAME, ArrayList<String> para,boolean  isPrimitive)
	{
		ArrayList<Object> result = new ArrayList<Object>();
		SoapObject resultSoap = null;
		
		String SOAP_ACTION = NAMESPACE + METHOD_NAME;
		try {
			// tao soapObject de thuc thi ket noi
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			if( para != null)
			{
				for( int i = 0 ; i< para.size();i++)
				{
					request.addProperty("arg" +String.valueOf(i), para.get(i));
					Log.e("arg"+i,  para.get(i));
				}
			}
			Log.e("WS", "1");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

			envelope.dotNet = true;
			Log.e("WS", "1");
			envelope.setOutputSoapObject(request);
			Log.e("WS", "2");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,10000);
			//tao ket noi
			Log.e("WS", "3");
			Log.e(SOAP_ACTION,URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			//lay du lieu tra ve
			Log.e("WS", "4");
			if( isPrimitive == false)
			{
				resultSoap = (SoapObject) envelope.bodyIn;// envelope.getResponse();
				result.add((SoapObject)(resultSoap.getProperty(0)));
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
			Log.e("e1",e.getMessage());
			return null;
		}
		return null;
		//xu ly du lieu vua nhan duoc
	}
	public ArrayList<Employee> getAllEmployee() {
		// tao soapobject de nhan ket qua tra ve
		ArrayList<Employee> result = new ArrayList<Employee>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getAllEmployees", null, false ).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if( root == null)
		{
			Log.e("123","123");
			return result;
		}
		//xu ly du lieu vua nhan duoc
		
		for( int i = 0; i < root.getPropertyCount(); i++ )
		{
			Employee newEmployee = new Employee();
			SoapObject newSoap = (SoapObject)root.getProperty(i);
			newEmployee.setPercent(Float.parseFloat(newSoap.getProperty(0).toString()));
			newEmployee.setID_Employee(Integer.parseInt(newSoap.getProperty(1).toString()));
			newEmployee.setstrName((newSoap.getProperty(2).toString()));
			newEmployee.setstrAddr((newSoap.getProperty(3).toString()));
			Log.e(newEmployee.getstrName(), String.valueOf(newEmployee.getID_Employee()));
			result.add(newEmployee);
		}
		Log.e(String.valueOf(result.size()),"123");
		return result;

	}
	public ArrayList<OrderExpense> getAllExpense(ArrayList<String> para) {
		// tao soapobject de nhan ket qua tra ve
		ArrayList<OrderExpense> result = new ArrayList<OrderExpense>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getListBetweenDay", para, false ).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return result;
		}
		if( root == null)
		{
			Log.e("123","123");
			return result;
		}
		//xu ly du lieu vua nhan duoc
		
		for( int i = 0; i < root.getPropertyCount(); i++ )
		{
			OrderExpense newExpense = new OrderExpense();
			SoapObject newSoap = (SoapObject)root.getProperty(i);
			newExpense.setId(Integer.parseInt(newSoap.getProperty(0).toString()));
			newExpense.setName((newSoap.getProperty(1).toString()));
			newExpense.setDate((newSoap.getProperty(2).toString()));
			newExpense.setMoney(Float.parseFloat(newSoap.getProperty(3).toString()));
			Log.e(String.valueOf(newExpense.getId()), String.valueOf(newExpense.getName()));
			result.add(newExpense);
		}
		Log.e(String.valueOf(result.size()),"123");
		return result;

	}
}

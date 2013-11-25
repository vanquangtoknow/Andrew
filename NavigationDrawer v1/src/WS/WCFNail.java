package WS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import DTO.Employee;
import DTO.OrderExpense;
import android.util.Log;

public class WCFNail {

	private static String NAMESPACE = "http://tempuri.org/";

	private static final String URL = "http://posifyrt.cloudapp.net/NailService.svc";
	
	private String ISERVICE = "INailService/";

	public WCFNail() {
	}

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
			Log.e("e1", e.getMessage());
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
			root = (SoapObject) getData("getListBetweenDayOrderExpense", para, false)
					.get(0);
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
	public ArrayList<Float> getFullReport(ArrayList<String> para)
	{
		ArrayList <Float> result = new ArrayList<Float>();
		SoapObject root = null;
		try {
			root = (SoapObject) getData("getFullReport", para, false)
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
	public ArrayList<Float> getEmployeeReport(ArrayList<String> para)
	{
		ArrayList <Float> result = new ArrayList<Float>();
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
}

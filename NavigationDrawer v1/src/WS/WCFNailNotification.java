package WS;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class WCFNailNotification {
	private static String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://notificationpubservice.cloudapp.net/NofService.svc";

	private String ISERVICE = "INofService/";

	public WCFNailNotification() {
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
					SoapObject tmp =  (SoapObject) para.get(i);
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
			else
				Log.e("400", "null");
			Log.e("WS", "1");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.dotNet = true;
			Log.e("WS", "1");
			envelope.setOutputSoapObject(request);
			Log.e("WS", "2");
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,10000);
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

	public boolean sendNotification(ArrayList<String> para)
	{
		SoapPrimitive root = getSoapPrimitive(para, "sendNotification");
		if (root == null)
			return false;
		return Boolean.valueOf(root.toString());
	}
	
	public ArrayList<String> getAllNotification() {
		ArrayList<String> result = new ArrayList<String>();
		Log.e("gettbid", "1");
		SoapObject root = getSoap(null, "getAllNotification");
		for (int i = 0; i < root.getPropertyCount(); i++) {
			String tmp = "";
			tmp=  (root.getProperty(i)).toString();
			result.add(tmp);
		}
		return result;
	}
	
}

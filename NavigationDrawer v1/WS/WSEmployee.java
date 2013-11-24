package WS;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class WSEmployee {
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
	public WSEmployee(String tag)
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
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			//tao ket noi
			Log.e("WS", "3");
			androidHttpTransport.call(SOAP_ACTION, envelope);
			//lay du lieu tra ve
			Log.e("WS", "4");
			if( isPrimitive == false)
			{
				resultSoap = (SoapObject) envelope.bodyIn;// envelope.getResponse();
			}
			else
			{
				try {
					
				
				Log.e("WS", "6");
				Log.e("WS", envelope.bodyIn.toString());
				resultSoap = (SoapObject) envelope.bodyIn;
				Log.e("WS", "6");
				result.add((SoapPrimitive)resultSoap.getProperty(0));
				Log.e("WS", "7");
				return result;
				}
				 catch (Exception e) {
					 Log.e("e",e.getMessage());
					}
			}
			Log.e("WS", "5");

		} catch (IOException e) {
			Log.e("e",e.getMessage());

		} catch (XmlPullParserException e) {
			Log.e("e", e.getMessage());
		} catch (Exception e) {
			Log.e("e",e.getMessage());
		}
		//xu ly du lieu vua nhan duoc
		String res = "";
		SoapObject root = (SoapObject) resultSoap.getProperty(0);
		for( int i = 0; i < root.getPropertyCount(); i++ )
		{
			SoapObject Customer = (SoapObject)root.getProperty(i);
			SoapPrimitive Id = (SoapPrimitive)Customer.getProperty(0);
			res = res + "\n" + Id.toString();
			SoapPrimitive Name = (SoapPrimitive)Customer.getProperty(1);
			res = res + "\n" + Name.toString();
		}
		result.add(res);
		return result;
	}
	public String getAllEmployee() {
		// tao soapobject de nhan ket qua tra ve
		SoapObject resultSoap = null;

		try {
			// tao soapObject de thuc thi ket noi
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_1);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(

			SoapEnvelope.VER11);

			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			//tao ket noi
			androidHttpTransport.call(SOAP_ACTION_1, envelope);
			//lay du lieu tra ve
			resultSoap = (SoapObject) envelope.bodyIn;// envelope.getResponse();

		} catch (IOException e) {
			Log.e("e",e.getMessage());

		} catch (XmlPullParserException e) {
			Log.e("e", e.getMessage());
		} catch (Exception e) {
			Log.e("e",e.getMessage());
		}
		//xu ly du lieu vua nhan duoc
		String result = "";
		SoapObject root = (SoapObject) resultSoap.getProperty(0);
		for( int i = 0; i < root.getPropertyCount(); i++ )
		{
			SoapObject Customer = (SoapObject)root.getProperty(i);
			SoapPrimitive Id = (SoapPrimitive)Customer.getProperty(0);
			result = result + "\n" + Id.toString();
			SoapPrimitive Name = (SoapPrimitive)Customer.getProperty(1);
			result = result + "\n" + Name.toString();
		}
		
		return result;

	}

}

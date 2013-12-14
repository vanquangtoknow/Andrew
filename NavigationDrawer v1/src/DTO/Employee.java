package DTO;

import org.ksoap2.serialization.SoapObject;

public class Employee {

	public Employee()
	{
		ID_Employee = 1;
		strName = "Duy";
		strAddr = "123";
		strCity = "TuyHoa";
	}
	
	 private int ID_Employee;
     public int getID_Employee()
     {
     	return ID_Employee;
     }
     public void setID_Employee(int value)
     {
    	 ID_Employee = value;
     }
     private String strName;
     public String getstrName()
     {
     	return strName;
     }
     public void setstrName(String value)
     {
    	 strName = value;
     }
     private String strBirthName;
     public String getstrBirthName()
     {
     	return strBirthName;
     }
     public void setstrBirthName(String value)
     {
    	 strBirthName = value;
     }
     private String strAddr;
     public String getstrAddr()
     {
     	return strAddr;
     }
     public void setstrAddr(String value)
     {
    	 strAddr = value;
     }
     private String strCity;
     public String getstrCity()
     {
     	return strCity;
     }
     public void setstrCity(String value)
     {
    	 strCity = value;
     }
     private String strState;
     public String getstrState()
     {
     	return strState;
     }
     public void setstrState(String value)
     {
    	 strState = value;
     }
     private String strZip ;
     public String getstrZip()
     {
     	return strZip;
     }
     public void setstrZip(String value)
     {
    	 strZip = value;
     }
     private String strPhone ;
     public String getstrPhone()
     {
     	return strPhone;
     }
     public void setstrPhone(String value)
     {
    	 strPhone = value;
     }
     private int image ;
     public int getimage()
     {
     	return image;
     }
     public void setimage(int value)
     {
    	 image = value;
     }
     private String pathImage ;
     public String getpathImage()
     {
     	return pathImage;
     }
     public void setpathImage(String value)
     {
    	 pathImage = value;
     }
     private float Percent;
     public float getPercent()
     {
     	return Percent;
     }
     public void setPercent(float value)
     {
    	 Percent = value;
     }

     private boolean Support;
     public boolean getSupport()
     {
     	return Support;
     }
     public void setSupport(boolean value )
     {
    	 Support = value;
     }


     private float Money;
     public float getMoney()
     {
     	return Money;
     }
     public void setMoney(float value)
     {
    	 Money = value;
     }

     private int Time;
     public int getTime()
     {
     	return Time;
     }
     public void setTime(int value)
     {
    	 Time = value;
     }
   
     private boolean Status;
     public boolean getStatus()
     {
     	return Status;
     }
     public void setStatus(boolean value)
     {
    	 Status = value;
     }
     private SoapObject soap;
 	
	 	public SoapObject getSoap() {
	 		return soap;
	 	}
	 	public void setSoap(SoapObject soap) {
	 		this.soap = soap;
	 	}
     public void getInfoFromSoap(SoapObject root)
     {
 		this.soap = root;
 		this.setID_Employee(Integer.parseInt(root.getProperty(0).toString()));
 		//this.setimage(value);
 		this.setMoney(Float.parseFloat(root.getProperty(1).toString()));
 		//this.setpathImage(value);
 		this.setPercent(Float.parseFloat(root.getProperty(2).toString()));
 		this.setstrName(root.getProperty(10).toString());
 		this.setSoap(root);
 		//this.setStatus(value);
 		//this.setstrAddr(value);
 		//this.setstrCity(value);
 		//this.setstrPhone(value);
 		//this.setstrState(value)
 		//this.setstrZip(value);
 		//this.setSupport(value);
 		//this.setTime(value);
 		
     }
}

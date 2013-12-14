package DTO;

import org.ksoap2.serialization.SoapObject;

public class PersionalInformation {
	private int ID_Employee ;
	public int getID_Employee() {
		return ID_Employee;
	}
	public void setID_Employee(int iD_Employee) {
		ID_Employee = iD_Employee;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getBirth() {
		return Birth;
	}
	public void setBirth(String birth) {
		Birth = birth;
	}
	public String getStart() {
		return Start;
	}
	public void setStart(String start) {
		Start = start;
	}
	private String Code ;
	private String Birth ;
	private String Start ;
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
    	this.setBirth((root.getProperty(0).toString()));
    	this.setCode(root.getProperty(1).toString());
    	this.setID_Employee(Integer.parseInt(root.getProperty(2).toString()));
		this.setStart((root.getProperty(3).toString()));
		
    }
}

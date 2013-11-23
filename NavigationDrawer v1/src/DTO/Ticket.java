package DTO;

import org.ksoap2.serialization.SoapObject;

public class Ticket
{
    private int ID ;
    public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public int getID_Employee() {
		return ID_Employee;
	}
	public void setID_Employee(int iD_Employee) {
		ID_Employee = iD_Employee;
	}
	public String getCustomer() {
		return Customer;
	}
	public void setCustomer(String customer) {
		Customer = customer;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	private String Date ;
    private int ID_Employee ;
    private String Customer ;
    private String Code ;
    public void getInfoFromSoap(SoapObject root)
    {
    	this.setCode((root.getProperty(0).toString()));
    	this.setCustomer(root.getProperty(1).toString());
    	this.setDate(root.getProperty(2).toString());
    	this.setID(Integer.parseInt(root.getProperty(3).toString()));
    	this.setID_Employee(Integer.parseInt(root.getProperty(4).toString()));
    }
}

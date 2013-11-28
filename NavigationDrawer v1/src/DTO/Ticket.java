package DTO;

import org.ksoap2.serialization.SoapObject;

public class Ticket
{
	public Ticket(int id, String name)
	{
		this.ID = id;
		this.Customer = Customer;
	}
	public Ticket()
	{
		
	}
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
    private int Id_TypeMethod;
    public int getId_TypeMethod() {
		return Id_TypeMethod;
	}
	public void setId_TypeMethod(int id_TypeMethod) {
		Id_TypeMethod = id_TypeMethod;
	}
	public void getInfoFromSoap(SoapObject root)
    {
    	this.setCode((root.getProperty(0).toString()));
    	this.setCustomer(root.getProperty(1).toString());
    	this.setDate(root.getProperty(2).toString());
    	this.setID(Integer.parseInt(root.getProperty(3).toString()));
    	this.setID_Employee(Integer.parseInt(root.getProperty(4).toString()));
    	this.setId_TypeMethod(Integer.parseInt(root.getProperty(5).toString()));
    }
    
    public String view()
    {
    	String result ="";
    	result = result + "Code :" + this.getCode() + "/n";  
    	result = result + "Customer :" + this.getCustomer() + "/n";  
    	result = result + "Date :" + this.getDate() + "/n";  
    	result = result + "Id" + String.valueOf(this.getID()) + "/n";  
    	result = result + "Id Customer :" + String.valueOf(this.getID_Employee()) + "/n";  
    	return result;
    }
}

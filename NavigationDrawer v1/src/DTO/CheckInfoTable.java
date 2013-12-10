package DTO;

import org.ksoap2.serialization.SoapObject;

public class CheckInfoTable {
	private int Id ;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getId_Employee() {
		return Id_Employee;
	}
	public void setId_Employee(int id_Employee) {
		Id_Employee = id_Employee;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}
	private int Id_Employee ;
	private String CheckIn ;
	private String CheckOut ;
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
    	this.setCheckIn((root.getProperty(0).toString()));
    	this.setCheckOut((root.getProperty(1).toString()));
		this.setId(Integer.parseInt(root.getProperty(3).toString()));
		this.setId_Employee(Integer.parseInt(root.getProperty(4).toString()));
		
    }
}

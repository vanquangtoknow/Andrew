package DTO;

import java.io.Serializable;
import java.util.Date;

import org.ksoap2.serialization.SoapObject;


public class ReportDTO {
	private int id;
	private int id_Employee;

	private String date;

	private int id_type_money;

	private int id_type_method;
	private SoapObject soap;
	private float money;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_Employee() {
		return id_Employee;
	}

	public void setId_Employee(int id_Employee) {
		this.id_Employee = id_Employee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId_type_money() {
		return id_type_money;
	}

	public void setId_type_money(int id_type_money) {
		this.id_type_money = id_type_money;
	}

	public int getId_type_method() {
		return id_type_method;
	}

	public void setId_type_method(int id_type_method) {
		this.id_type_method = id_type_method;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getId_saleitem() {
		return id_saleitem;
	}

	public void setId_saleitem(int id_saleitem) {
		this.id_saleitem = id_saleitem;
	}

	private int id_saleitem;

	public void getInfoFromSoap(SoapObject root)
    {
		this.soap = root;
    	this.setDate((root.getProperty(0).toString()));
    	this.setId(Integer.parseInt(root.getProperty(1).toString()));
    	this.setId_Employee(Integer.parseInt(root.getProperty(2).toString()));
		this.setId_saleitem(Integer.parseInt(root.getProperty(3).toString()));
		this.setId_type_method(Integer.parseInt(root.getProperty(4).toString()));
		this.setId_type_money(Integer.parseInt(root.getProperty(5).toString()));
		this.setMoney(Integer.parseInt(root.getProperty(6).toString()));
    }
	
	public SoapObject getSoapObject()
	{
		return soap;
	}
    public ReportDTO()
    {
    	id = -2;
    	id_Employee = -2;
    	id_saleitem = -2 ;
    	id_type_method =  -2;
    	id_type_money = -2;
    	date = new Date().toGMTString();
    	money = -3;
    }

}

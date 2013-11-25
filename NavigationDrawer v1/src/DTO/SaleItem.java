package DTO;

import org.ksoap2.serialization.SoapObject;

public class SaleItem {

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getId_Category() {
		return Id_Category;
	}
	public void setId_Category(int id_Category) {
		Id_Category = id_Category;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public int getId_Type() {
		return Id_Type;
	}
	public void setId_Type(int id_Type) {
		Id_Type = id_Type;
	}
	private int ID ;
    private int Id_Category ;
    private String Name ;
    private float Price ;
    private int Id_Type ;
    
    public void getInfoFromSoap(SoapObject root)
    {
    	this.setID(Integer.parseInt(root.getProperty(0).toString()));
    	this.setId_Category(Integer.parseInt(root.getProperty(1).toString()));
    	this.setId_Type(Integer.parseInt(root.getProperty(2).toString()));
    	this.setName((root.getProperty(3).toString()));
    	this.setPrice(Float.parseFloat(root.getProperty(4).toString()));
    }
    
    
}

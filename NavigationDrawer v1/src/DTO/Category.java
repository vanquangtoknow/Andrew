package DTO;

import org.ksoap2.serialization.SoapObject;

public class Category {
	private int ID;
	private String Name;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	public void getInfoFromSoap(SoapObject root)
    {
    	this.setID(Integer.parseInt(root.getProperty(0).toString()));
    	this.setName(root.getProperty(1).toString());
    }
    public Category()
    {
    	
    }
}

package DTO;

import org.ksoap2.serialization.SoapObject;

public class ItemTicket {
	private int ID ;
	private int ID_SaleItem ;
	public int getID_SaleItem() {
		return ID_SaleItem;
	}
	public void setID_SaleItem(int iD_SaleItem) {
		ID_SaleItem = iD_SaleItem;
	}
	public int getID_Ticket() {
		return ID_Ticket;
	}
	public void setID_Ticket(int iD_Ticket) {
		ID_Ticket = iD_Ticket;
	}
	public int getQuality() {
		return Quality;
	}
	public void setQuality(int quality) {
		Quality = quality;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	private int ID_Ticket ;
	private int Quality ;
	private float Price ;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
    public void getInfoFromSoap(SoapObject root)
    {
    	this.setID(Integer.parseInt(root.getProperty(0).toString()));
    	this.setID_SaleItem(Integer.parseInt(root.getProperty(1).toString()));
    	this.setID_Ticket(Integer.parseInt(root.getProperty(2).toString()));
		this.setPrice(Integer.parseInt(root.getProperty(3).toString()));
		this.setQuality(Integer.parseInt(root.getProperty(4).toString()));
    }
    public ItemTicket()
    {
    	
    }

}

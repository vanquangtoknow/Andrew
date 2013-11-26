package DTO;

public class ItemTicketAdapter {
	private int ID_ItemTicket ;
	private String Type ;
	private String Descriptioon;
	private int Quality ;
	private float Price ;
	private boolean IsRowEmpty;
	
	public boolean isIsRowEmpty() {
		return IsRowEmpty;
	}
	public void setIsRowEmpty(boolean isRowEmpty) {
		IsRowEmpty = isRowEmpty;
	}
	public ItemTicketAdapter(int iditemticket, String type, String description, int quality, float price, boolean isempty)
	{
		this.ID_ItemTicket = iditemticket;
		this.Type = type;
		this.Descriptioon = description;
		this.Quality = quality;
		this.Price = price;
		this.IsRowEmpty = isempty;
	}
	public int getID_ItemTicket() {
		return ID_ItemTicket;
	}


	public void setID_ItemTicket(int iD_ItemTicket) {
		ID_ItemTicket = iD_ItemTicket;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public String getDescriptioon() {
		return Descriptioon;
	}


	public void setDescriptioon(String descriptioon) {
		Descriptioon = descriptioon;
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


	public ItemTicketAdapter() {
		// TODO Auto-generated constructor stub
	}

}

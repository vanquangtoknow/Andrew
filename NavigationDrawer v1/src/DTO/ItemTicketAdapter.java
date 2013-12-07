package DTO;

public class ItemTicketAdapter {
	private int ID_ItemTicket ;
	private int ID_SaleItem;
	private String Type ;
	private String Descriptioon;
	private int Quality ;
	private float Price ;
	// dung cho listAdd de phat hien ra item nao moi duoc add
	private boolean IsAdd;
	// dung de cap nhat so luong moi
	private int soluongcu;
	
	
	public int getSoluongcu() {
		return soluongcu;
	}
	public void setSoluongcu(int soluongcu) {
		this.soluongcu = soluongcu;
	}
	public int getID_SaleItem() {
		return ID_SaleItem;
	}
	public void setID_SaleItem(int iD_SaleItem) {
		ID_SaleItem = iD_SaleItem;
	}
	
	public boolean isIsAdd() {
		return IsAdd;
	}
	public void setIsAdd(boolean isAdd) {
		IsAdd = isAdd;
	}
	public ItemTicketAdapter(int iditemticket, String type, String description, int quality, float price, boolean isadd)
	{
		this.ID_ItemTicket = iditemticket;
		this.Type = type;
		this.Descriptioon = description;
		this.Quality = quality;
		this.Price = price;
		this.IsAdd = isadd;
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

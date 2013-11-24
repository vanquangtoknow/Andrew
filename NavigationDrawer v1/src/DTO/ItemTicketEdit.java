package DTO;

public class ItemTicketEdit {

	public ItemTicketEdit(String type, String quality, String description, String price)
	{
		
		this.tpye = type;
		this.description = description;
		this.price =price;
		this.quality = quality;
	}
	public ItemTicketEdit() {
		// TODO Auto-generated constructor stub
	}
	String tpye;
	String quality;
	String price;
	String description;
	public String getTpye() {
		return tpye;
	}
	public void setTpye(String tpye) {
		this.tpye = tpye;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}

package DTO;

public class Contacto {

	public Contacto() {
		// TODO Auto-generated constructor stub
	}
	private String Number;
	private String Nombre;
	private String BaImage;
	public Contacto(String number, String mombre, String maimage)
	{
		this.Number = number;
		this.Nombre = mombre;
		this.BaImage = maimage;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getBaImage() {
		return BaImage;
	}
	public void setBaImage(String baImage) {
		BaImage = baImage;
	}
	
}

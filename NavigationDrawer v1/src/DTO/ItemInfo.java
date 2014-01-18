package DTO;

import java.sql.Date;

import org.ksoap2.serialization.SoapObject;

public class ItemInfo {
	private String startday;
	private String endday;
	private String text;
	private int a;
	private int r;
	private int g;
	private int b;
	
	
	
	
	public String getStartday() {
		return startday;
	}

	public void setStartday(String startday) {
		this.startday = startday;
	}

	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public ItemInfo(String datestart, String dateend, String text,int a, int r, int g, int b )
	{
		this.startday = datestart;
		this.endday = dateend;
		this.text = text;
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public ItemInfo()
	{
		
	}
	public void getInfoFromSoap(SoapObject root) {
		this.setA(Integer.parseInt(root.getProperty(0).toString()));
		this.setB(Integer.parseInt(root.getProperty(1).toString()));
		this.setEndday(root.getProperty(2).toString());
		this.setG(Integer.parseInt(root.getProperty(3).toString()));
		this.setR(Integer.parseInt(root.getProperty(4).toString()));
		this.setStartday(root.getProperty(5).toString());
		this.setText(root.getProperty(6).toString());
	}
	

}

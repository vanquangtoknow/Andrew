package DTO;

import org.ksoap2.serialization.SoapObject;

public class NameAndAdd {
	private int ID_Employee;
	public int getID_Employee() {
		return ID_Employee;
	}
	public void setID_Employee(int iD_Employee) {
		ID_Employee = iD_Employee;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrBirthName() {
		return strBirthName;
	}
	public void setStrBirthName(String strBirthName) {
		this.strBirthName = strBirthName;
	}
	public String getStrAddr() {
		return strAddr;
	}
	public void setStrAddr(String strAddr) {
		this.strAddr = strAddr;
	}
	public String getStrCity() {
		return strCity;
	}
	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}
	public String getStrState() {
		return strState;
	}
	public void setStrState(String strState) {
		this.strState = strState;
	}
	public String getStrZip() {
		return strZip;
	}
	public void setStrZip(String strZip) {
		this.strZip = strZip;
	}
	public String getStrPhone() {
		return strPhone;
	}
	public void setStrPhone(String strPhone) {
		this.strPhone = strPhone;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getPathImage() {
		return pathImage;
	}
	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public boolean isSupport() {
		return support;
	}
	public void setSupport(boolean support) {
		this.support = support;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	private String strName ;
	private String strBirthName ;
	private String strAddr ;
	private String strCity ;
	private String strState ;
	private String strZip ;
	private String strPhone ;
	private int image ;
	private String pathImage ;
	private float percent;
	private boolean support;
	private float money;
	private int time;
	private boolean status;
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
		this.setID_Employee(Integer.parseInt(root.getProperty(0).toString()));
		this.setMoney(Float.parseFloat(root.getProperty(1).toString()));
		this.setPercent(Float.parseFloat(root.getProperty(2).toString()));
		this.setStatus(Boolean.parseBoolean(root.getProperty(3).toString()));
		this.setSupport(Boolean.parseBoolean(root.getProperty(4).toString()));
		this.setTime(Integer.parseInt(root.getProperty(5).toString()));
		this.setImage(Integer.parseInt(root.getProperty(6).toString()));
		this.setPathImage((root.getProperty(7).toString()));
		this.setStrAddr((root.getProperty(8).toString()));
		this.setStrBirthName((root.getProperty(9).toString()));
		this.setStrCity((root.getProperty(10).toString()));
		this.setStrName((root.getProperty(11).toString()));
		this.setStrPhone((root.getProperty(12).toString()));
		this.setStrState((root.getProperty(13).toString()));
		this.setStrZip((root.getProperty(14).toString()));
    }
}

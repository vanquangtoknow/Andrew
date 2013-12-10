package DTO;

import org.ksoap2.serialization.SoapObject;

public class JobDay {
	private String strName;
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public boolean isbStatus() {
		return bStatus;
	}
	public void setbStatus(boolean bStatus) {
		this.bStatus = bStatus;
	}
	public String getStrTimeStart() {
		return strTimeStart;
	}
	public void setStrTimeStart(String strTimeStart) {
		this.strTimeStart = strTimeStart;
	}
	public String getStrTimeEnd() {
		return strTimeEnd;
	}
	public void setStrTimeEnd(String strTimeEnd) {
		this.strTimeEnd = strTimeEnd;
	}
	public SoapObject getSoap() {
		return soap;
	}
	public void setSoap(SoapObject soap) {
		this.soap = soap;
	}
	private boolean bStatus;
	private String strTimeStart;
	private String strTimeEnd;
	private SoapObject soap;
	
	public void getInfoFromSoap(SoapObject root)
    {
		this.soap = root;
    	this.setbStatus(Boolean.parseBoolean(root.getProperty(0).toString()));
    	this.setStrName((root.getProperty(1).toString()));
    	this.setStrTimeEnd((root.getProperty(2).toString()));
		this.setStrTimeStart((root.getProperty(3).toString()));
    }
}

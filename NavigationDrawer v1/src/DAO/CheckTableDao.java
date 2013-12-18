package DAO;

import java.util.ArrayList;

import DTO.CheckTable;
import WS.WCFNail;

public class CheckTableDao {
	WCFNail nailservice = new WCFNail();
	
	public WCFNail getNailservice() {
		return nailservice;
	}
	public void setNailservice(WCFNail nailservice) {
		this.nailservice = nailservice;
	}
	public CheckTableDao() {
	}
	public ArrayList<CheckTable> getAllCheckTable()
	{
		ArrayList<CheckTable> ds = new ArrayList<CheckTable>();
		ds.addAll(nailservice.getAllCheckTable(null));
		return ds;
	}
	
}

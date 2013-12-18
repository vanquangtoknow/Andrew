package DAO;

import java.util.ArrayList;

import DTO.CheckInfoTable;
import WS.WCFNail;

public class CheckInfoTableDAO {

	WCFNail nailservice = new WCFNail();
	public CheckInfoTableDAO() {
	}
	public ArrayList<CheckInfoTable> getAllChekcTableInfoWithTime(final String time)
	{
		return nailservice.getAllCheckInfoWithTime(new ArrayList<String>(){{
			add(time);
		}});
	}
	public ArrayList<CheckInfoTable> getAllChekInfoWithEmployee(final int idEmployee, final String datestart, final String dateend)
	{
		return nailservice.getAllCheckInfoWithEmployee(new ArrayList<String>(){{
			add(datestart);
			add(dateend);
			add(Integer.toString(idEmployee));
		}});
	}

}

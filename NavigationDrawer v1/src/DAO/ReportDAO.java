package DAO;

import java.util.ArrayList;

import WS.WCFNail;
import DTO.ReportDTO;

public class ReportDAO {
	private  WCFNail nailservice = new WCFNail();
	public ReportDAO() 
	{
		
	}
	public boolean InsertReport(ReportDTO report)
	{
		return nailservice.InsertReport(report);
	}
	public ArrayList<ReportDTO> GetIListtemReportWithEmployee(final int idEmployee)
	{
		return nailservice.GetIListtemReportWithEmployee(new ArrayList<String>(){
        	{
        		add(Integer.toString(idEmployee));
        	}
        });
	}
	public boolean deleteItemReport(final int idReport)
	{
		return nailservice.deleteItemReport(new ArrayList<String>(){{
    		add(Integer.toString(idReport));
    	}});
	}
}

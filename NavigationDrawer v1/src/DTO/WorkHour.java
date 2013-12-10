package DTO;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;

public class WorkHour {
	private int ID_Employee;

	public int getID_Employee() {
		return ID_Employee;
	}

	public void setID_Employee(int iD_Employee) {
		ID_Employee = iD_Employee;
	}

	public String getStrWorkHour() {
		return strWorkHour;
	}

	public void setStrWorkHour(String strWorkHour) {
		this.strWorkHour = strWorkHour;
	}





	private String strWorkHour;

	public ArrayList<JobDay> getJobdays() {
		return jobdays;
	}

	public void setJobdays(ArrayList<JobDay> jobdays) {
		this.jobdays = jobdays;
	}





	private ArrayList<JobDay> jobdays;

	public WorkHour() {

	}

	public SoapObject getSoap() {
		return soap;
	}

	public void setSoap(SoapObject soap) {
		this.soap = soap;
	}

	private SoapObject soap;

	public void getInfoFromSoap(SoapObject root) {
		this.soap = root;
		this.setID_Employee(Integer.parseInt(root.getProperty(0).toString()));
		SoapObject tmp = (SoapObject) root.getProperty(1);
		this.setJobdays(new ArrayList<JobDay>());
		for( int i=0; i< tmp.getPropertyCount() ; i++)
		{
			JobDay jobday = new JobDay();
			jobday.getInfoFromSoap((SoapObject) tmp.getProperty(i));
			jobdays.add(jobday);
		}
		this.setStrWorkHour((root.getProperty(2).toString()));
	}
}

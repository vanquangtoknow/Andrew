package DTO;

import org.ksoap2.serialization.SoapObject;

import DAO.EmployeeDAO;
import WS.WCFNail;

public class CheckTable {
	EmployeeDAO employeeDao = new EmployeeDAO();
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getId_Employee() {
		return Id_Employee;
	}
	public void setId_Employee(int id_Employee) {
		Id_Employee = id_Employee;
	}
	public boolean isCheck_Status() {
		return Check_Status;
	}
	public void setCheck_Status(boolean check_Status) {
		Check_Status = check_Status;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}
	private int Id ;
	private int Id_Employee ;
	private boolean Check_Status ;
	private String CheckIn ;
	private String CheckOut ;
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
    	this.setCheckIn((root.getProperty(0).toString()));
    	this.setCheckOut((root.getProperty(1).toString()));
    	this.setCheck_Status(Boolean.parseBoolean(root.getProperty(2).toString()));
		this.setId(Integer.parseInt(root.getProperty(3).toString()));
		this.setId_Employee(Integer.parseInt(root.getProperty(4).toString()));
		
    }
	public CheckTableTemp convertToCheckTableTemp(WCFNail nailservice)
	{
		CheckTableTemp temp = new CheckTableTemp();
		Employee employee = new Employee();
		employee = employeeDao.getEmployeeWithId(this.getId_Employee());
		temp.setId_Employee(employee.getID_Employee());
		temp.setCheckIn(this.getCheckIn());
		temp.setCheckOut(this.getCheckOut());
		temp.setEmployeeName(employee.getstrName());
		temp.setIdChecktable(this.getId());
		return temp;
	}
}

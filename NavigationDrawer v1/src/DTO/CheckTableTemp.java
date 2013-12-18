package DTO;

import DAO.EmployeeDAO;
import WS.WCFNail;

public class CheckTableTemp {
	
	public CheckTableTemp() {
	}
	private String employeeName;
	private int IdChecktable ;
	private int Id_Employee ;
	private boolean Check_Status ;
	private String CheckIn ;
	private String CheckOut ;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getIdChecktable() {
		return IdChecktable;
	}
	public void setIdChecktable(int idChecktable) {
		IdChecktable = idChecktable;
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
	
}

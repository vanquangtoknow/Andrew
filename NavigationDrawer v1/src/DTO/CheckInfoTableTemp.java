package DTO;

public class CheckInfoTableTemp {

	public CheckInfoTableTemp() {
	}
	private String employeeName;
	private int IdChecktable ;
	private int Id_Employee ;
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

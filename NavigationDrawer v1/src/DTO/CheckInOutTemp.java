package DTO;

public class CheckInOutTemp 
{
	 private int ID_Employee;
	 private String strName;
	 private boolean Check;
	 
	public boolean isCheck() {
		return Check;
	}

	public void setCheck(boolean check) {
		Check = check;
	}

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

	public CheckInOutTemp() {
		// TODO Auto-generated constructor stub
	}

}

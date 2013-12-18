package DTO;

public class TrackingTemp {
	public TrackingTemp() {
	}
	private String time;
	private int idEmployee;
	private String nameEmployee;
	private float total;
	private boolean isView = false;
	
	public void setView(boolean isView) {
		this.isView = isView;
	}
	public TrackingTemp(String time, int idEmployee, String nameEmployee, float total, boolean isView)
	{
		this.time = time;
		this.idEmployee = idEmployee;
		this.nameEmployee = nameEmployee;
		this.isView = isView;
		this.total = total;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getNameEmployee() {
		return nameEmployee;
	}
	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public boolean getIsView() {
		return isView;
	}
	public void setIsView(boolean isView) {
		this.isView = isView;
	}
	
}

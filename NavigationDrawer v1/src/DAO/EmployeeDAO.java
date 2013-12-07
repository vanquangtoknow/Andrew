package DAO;

import java.util.ArrayList;

import WS.WCFNail;

import DTO.Employee;

public class EmployeeDAO {
	WCFNail nailservice = new WCFNail();
	public EmployeeDAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Employee> getAllEmployeeDAO()
	{
		ArrayList<Employee> ds = new ArrayList<Employee>();
		ds.addAll(nailservice.getAllEmployee());
		return ds;
	}
}

package DAO;

import java.util.ArrayList;

import WS.WCFNail;

import DTO.Employee;

public class EmployeeDAO {
	WCFNail nailservice = new WCFNail();
	public EmployeeDAO() {
	}
	public ArrayList<Employee> getAllEmployeeDAO()
	{
		ArrayList<Employee> ds = new ArrayList<Employee>();
		ds.addAll(nailservice.getAllEmployee());
		return ds;
	}
	public Employee getEmployeeWithId(final int id)
	{
		Employee employee = new Employee();
		return nailservice.getEmployeeWithId(new ArrayList<String>(){{
			add(Integer.toString(id));
		}});
	}
}

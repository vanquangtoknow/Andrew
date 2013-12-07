package DAO;

import java.util.ArrayList;

import DTO.Category;
import WS.WCFNail;

public class CategoryDAO {
	private WCFNail nailservice = new WCFNail();
	public CategoryDAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Category> getListCategory()
	{
		return nailservice.getListCategory(null);
	}
	
}

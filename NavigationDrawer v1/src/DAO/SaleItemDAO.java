package DAO;

import java.util.ArrayList;

import DTO.SaleItem;
import WS.WCFNail;

public class SaleItemDAO {
	private WCFNail nailservice = new WCFNail();
	public SaleItemDAO() {
		// TODO Auto-generated constructor stub
	}
	public String getNameSaleItem(final int idsaleitem)
	{
		return nailservice.getNameSaleItem(new ArrayList<String>(){{
			add(Integer.toString(idsaleitem));
		}});
	}
	public String getTypeSaleItem(final int idsaleitem)
	{
		return nailservice.getTypeSaleItem(new ArrayList<String>(){{
			add(Integer.toString(idsaleitem));
		}});
	}
	public ArrayList<SaleItem> getListSaleItemByIDCategory(final int idCate)
	{
		return nailservice.getListSaleItemByIDCategory(new ArrayList<String>(){{
			add(Integer.toString(idCate));
		}});
	}
}

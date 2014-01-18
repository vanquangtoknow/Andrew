package DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.ListCalendarAdapter;
import DTO.ItemInfo;
import WS.WCFNail;

public class CalendarDAO {
	private WCFNail nailservice = new WCFNail();
	public CalendarDAO() {
	}
	public ArrayList<ItemInfo> getAllListItemInfo()
	{
		ArrayList<ItemInfo> ds = nailservice.getAllListItemInfo(null);
		return ds;
	}
	public ArrayList<ItemInfo> getListItemInfoByDay(final String date)
	{
		ArrayList<ItemInfo> ds = nailservice.getListItemInfoByDay(new ArrayList<String>(){{
			add(date);
		}});
		return ds;
	}
	 public boolean addItemInfoToCalendar(ItemInfo iteminfo)
	 {
		 return nailservice.addItemInfoToCalendar(iteminfo);
	 }
     public boolean removeItemInfoFromCalendar(ItemInfo iteminfo)
     {
    	 return nailservice.removeItemInfoFromCalendar(iteminfo);
     }
     public boolean updateItemInfoOfCalendar(ItemInfo arg0, ItemInfo arg1)
     {
    	 return nailservice.updateItemInfoOfCalendar(arg0, arg1);
     }

}

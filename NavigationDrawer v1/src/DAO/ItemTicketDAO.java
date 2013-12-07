package DAO;

import java.util.ArrayList;

import DTO.ItemTicket;
import DTO.Ticket;
import WS.WCFNail;

public class ItemTicketDAO {
	private  WCFNail nailservice = new WCFNail();
	public ItemTicketDAO() {
		// TODO Auto-generated constructor stub
	}
	public  ArrayList<ItemTicket> getListItemTicketByIDTicket(final int id)
	{
		ArrayList<ItemTicket> dsItemTicket = new ArrayList<ItemTicket>();
		dsItemTicket.addAll(nailservice.getListItemTicketByIDTicket(new ArrayList<String>() {
			{
				add(Integer.toString(id));
			}
		}));
		return dsItemTicket;
	}
	public ItemTicket getItemTicketById(final int id)
	{
		//ItemTicket itemticket = null;
		return nailservice.getItemTicketById(new ArrayList<String>(){{
			add(Integer.toString(id));
		}});
	}
	public boolean updateItemTicket(ItemTicket itemticket)
	{
		return nailservice.updateItemTicket(itemticket);
	}
	public boolean deleteItemTicket(final int idItemTicket)
	{
		return nailservice.deleteItemTicket(new ArrayList<String>(){{
			add(Integer.toString(idItemTicket));
		}});
	}
	public int insertItemTicket(ItemTicket itemticket)
	{
		return nailservice.insertItemTicket(itemticket);
	}
	public void deleteItemTicketByIDTicket(final int id)
	{
		nailservice.deleteItemTicketByIDTicket(new ArrayList<String>(){{
			add(Integer.toString(id));
		}});
	}
}

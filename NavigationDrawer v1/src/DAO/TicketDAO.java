package DAO;

import java.util.ArrayList;

import WS.WCFNail;
import DTO.Ticket;

public class TicketDAO {
	private  WCFNail nailservice = new WCFNail();
	public TicketDAO() {
		// TODO Auto-generated constructor stub
	}
	public  ArrayList<Ticket> getListTicketByIDEmployee(final int id)
	{
		ArrayList<Ticket> dsTicket = new ArrayList<Ticket>();
		dsTicket.addAll(nailservice.getListTicketByIDEmployee(new ArrayList<String>() {
			{
				add(Integer.toString(id));
			}
		}));
		return dsTicket;
	}
	public boolean deleteTicket(final int idticket)
	{
		return nailservice.deleteTicket(new ArrayList<String>(){{
			add(Integer.toString(idticket));
		}});
	}
}

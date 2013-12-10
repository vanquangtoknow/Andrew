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
	/**
	 * Lay danh sach cac ticket tu ngay bat dau va ngay ket thuc cua mot nhan vien
	 * @param idEmployee
	 * @param datebegin
	 * @param dateend
	 * @return
	 */
	public ArrayList<Ticket> getListTicketBetween(final int idEmployee,final String datebegin,final String dateend)
	{
		ArrayList<Ticket> dsTicket = new ArrayList<Ticket>();
		dsTicket.addAll(nailservice.getListTicketBetween(new ArrayList<String>() {
			{
				add(Integer.toString(idEmployee));
				add(datebegin);
				add(dateend);
			}
		}));
		return dsTicket;
	}
}

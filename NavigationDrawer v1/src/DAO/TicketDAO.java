package DAO;

import java.util.ArrayList;

import android.util.Log;

import WS.WCFNail;
import DTO.ItemTicket;
import DTO.Ticket;

public class TicketDAO {
	private  WCFNail nailservice = new WCFNail();
	private ItemTicketDAO itemTicketDAO = new ItemTicketDAO();
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
	public float getTotalMomneyOfEmployeeId(final int id, final String startday, final String endday)
	{
		ArrayList<Ticket> dsTicket = getListTicketBetween(id, startday, endday);
		float tongT = 0;
		Log.i("getTotalMomneyOfEmployeeId","Size"+ dsTicket.size() +"IdTicket: " + id + " datestart: "+startday+" endday:  "+endday);
		for (int j = 0; j < dsTicket.size(); j++)
        {
			Log.i("getTotalMomneyOfEmployeeId","IdTicket: " + dsTicket.get(j).getID());
            ArrayList<ItemTicket> dsItemTicket = itemTicketDAO.getListItemTicketByIDTicket(dsTicket.get(j).getID());
            for (int k = 0; k < dsItemTicket.size(); k++)
            {
            	Log.i("getTotalMomneyOfEmployeeId", "IdTicket has itemticket id:  "+dsItemTicket.get(k).getID());
                tongT = tongT + dsItemTicket.get(k).getPrice() * dsItemTicket.get(k).getQuality();
            }
            Log.i("getTotalMomneyOfEmployeeId", "IdTicket has total:  "+tongT);
        }
		return tongT;
	}
}

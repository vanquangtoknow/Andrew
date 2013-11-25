package Adapter;

import java.util.ArrayList;

import DTO.Item;
import DTO.ItemTicket;
import DTO.ItemTicketEdit;
import WS.WCFNail;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class TicketEditAdapter extends BaseAdapter {
	private Context context; 
	private ArrayList<ItemTicket> array;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	public TicketEditAdapter(Context context, ArrayList<ItemTicket> array) {
		this.array = array;
		this.context = context;
	}
	
	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return array.get(pos);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("Adapter-getview1", String.valueOf(position));
		if( convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
			convertView = infalInflater.inflate(R.layout.adapter_row_ticketedit, null);
		}
		int ID = array.get(position).getID() ;
		final int ID_SaleItem = array.get(position).getID_SaleItem() ;
		int ID_Ticket = array.get(position).getID_Ticket() ;
		final int Quality = array.get(position).getQuality() ;
		float Price =  array.get(position).getPrice()  ;
		final TextView type = (TextView)convertView.findViewById(R.id.tvtype);
		final TextView quality = (TextView)convertView.findViewById(R.id.tvquality);
		final TextView description = (TextView)convertView.findViewById(R.id.tvdescription);
		final TextView price = (TextView)convertView.findViewById(R.id.tvprice);
		if(ID==-1&&ID_SaleItem==-1&&ID_Ticket==-1&&Quality==-1&&Price==-1)
		{
			type.setText("Type");
			quality.setText("Quality");
			description.setText("Description");
			price.setText("Price");
		}
		else
		{
			if(ID==-2&&ID_SaleItem==-2&&ID_Ticket==-2&&Quality==-2&&Price==-2)
			{
				type.setText("");
				quality.setText("");
				description.setText("");
				price.setText("");
			}
			else
			{
				Thread a = new Thread()
				{
					@Override
					public void run() {
						WCFNail nailservice = new WCFNail();
						int aa = ID_SaleItem;
						String s_description = nailservice.getNameSaleItem(new ArrayList<String>(){{
							add(Integer.toString(ID_SaleItem));
						}});
						String s_type = nailservice.getTypeSaleItem(new ArrayList<String>(){{
							add(Integer.toString(ID_SaleItem));
						}});
						String s_price = nailservice.getPriceSaleItem(new ArrayList<String>(){{
							add(Integer.toString(ID_SaleItem));
						}});
						type.setText(s_type);
						quality.setText(Integer.toString(Quality));
						description.setText(s_description);
						price.setText(s_price);
						}
					
				};
				a.start();
				
			}
		}
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}


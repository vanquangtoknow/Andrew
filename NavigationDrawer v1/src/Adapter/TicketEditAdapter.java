package Adapter;

import java.util.ArrayList;

import DTO.Item;
import DTO.ItemTicket;
import DTO.ItemTicketEdit;
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
		Log.e("Adapter- get TicketEdit adapter", String.valueOf(position));
		TextView type = (TextView)convertView.findViewById(R.id.tvtype);
		//type.setText(array.get(position).getTpye());
		type.setText("Sale ID" + array.get(position).getID_SaleItem());
		TextView quality = (TextView)convertView.findViewById(R.id.tvquality);
		quality.setText("So luong" + Integer.toString(array.get(position).getQuality()));
		TextView description = (TextView)convertView.findViewById(R.id.tvdescription);
		//description.setText();
		description.setText("Gia ca" + array.get(position).getPrice());
		TextView price = (TextView)convertView.findViewById(R.id.tvprice);
		price.setText("Id ticket" + array.get(position).getID_Ticket());
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}


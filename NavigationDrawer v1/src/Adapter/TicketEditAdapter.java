package Adapter;

import java.util.ArrayList;

import DTO.ItemTicketAdapter;
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
	private ArrayList<ItemTicketAdapter> array;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	public TicketEditAdapter(Context context, ArrayList<ItemTicketAdapter> array) {
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
		int s_idticket = array.get(position).getID_ItemTicket() ;
		String s_description  = array.get(position).getDescriptioon();
		int s_quality = array.get(position).getQuality() ;
		float s_price =  array.get(position).getPrice()  ;
		String s_type = array.get(position).getType();
		TextView type = (TextView)convertView.findViewById(R.id.tvtype);
		TextView quality = (TextView)convertView.findViewById(R.id.tvquality);
		TextView description = (TextView)convertView.findViewById(R.id.tvdescription);
		TextView price = (TextView)convertView.findViewById(R.id.tvprice);
		if(s_idticket==-1&&s_quality==-1&&s_price==-1&&s_description.equals("-1"))
		{
			type.setText("Type");
			quality.setText("Quality");
			description.setText("Description");
			price.setText("Price");
		}
		else
		{
			if(s_idticket==-2&&s_quality==-2&&s_price==-2&&s_description.equals("-2"))
			{
				type.setText("");
				quality.setText("");
				description.setText("");
				price.setText("");
			}
			else
			{
				type.setText(s_type);
				quality.setText(Integer.toString(s_quality));
				description.setText(s_description);
				price.setText(Float.toString(s_price));
				
			}
		}
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}


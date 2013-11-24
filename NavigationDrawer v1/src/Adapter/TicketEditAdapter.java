package Adapter;

import java.util.ArrayList;

import DTO.Item;
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
	private ArrayList<ItemTicketEdit> array;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	public TicketEditAdapter(Context context, ArrayList<ItemTicketEdit> array) {
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
		type.setText(array.get(position).getTpye());
		TextView quality = (TextView)convertView.findViewById(R.id.tvquality);
		quality.setText(array.get(position).getQuality());
		TextView description = (TextView)convertView.findViewById(R.id.tvdescription);
		description.setText(array.get(position).getDescription());
		TextView price = (TextView)convertView.findViewById(R.id.tvprice);
		price.setText(array.get(position).getQuality());
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}


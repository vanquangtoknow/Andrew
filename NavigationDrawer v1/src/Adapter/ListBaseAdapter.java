package Adapter;

import java.util.ArrayList;

import com.example.android.navigationdrawerexample.R;

import DTO.Employee;
import DTO.Item;
import DTO.Ticket;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter{
	private Context context; 
	//private ArrayList<Item> array;
	private ArrayList<Object> array;
	private int type;
	private int selectedItem;
	public void initListBaseAdapter(int type)
	{
		this.type = type;
	}
    public void setSelectedItem(int position) {
        selectedItem = position;
    }
	@Override
	public int getCount()
	{
		return array.size();
	}
	public ListBaseAdapter(Context context, ArrayList<?> array) {
		this.array = (ArrayList<Object>)array;
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
			convertView = infalInflater.inflate(R.layout.row_item, null);
		}
		Log.e("Adapter-getview2", String.valueOf(position));
		TextView name = (TextView)convertView.findViewById(R.id.personname);
		
		switch (type) {
		case 1:
			Employee employee = (Employee)array.get(position);
			name.setText(employee.getstrName());
			break;

		default:
			Ticket ticket = (Ticket) array.get(position);
			name.setText(ticket.getCode().trim());
			break;
		}
        if (position == selectedItem)
        {
            name.setTextColor(Color.BLUE);
        }
        else
        {
        	name.setTextColor(Color.BLACK);
        }
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

}

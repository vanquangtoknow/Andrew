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
import android.widget.ImageView;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter{
	private Context context; 
	
	private ArrayList<Object> array;
	private int typeList;
	private int typeView;
	private int selectedItem;
	/** Neu typeList = 1 => listemployees, typeList = 2 => listTickets
	 * Neu typeView =1 thi view base_row_item, typeView =2 thi view la row_item
	 * @param typeList loai cua list
	 * @param typeView loai cua view
	 * 
	 */
	public void initListBaseAdapter(int typeList, int typeView)
	{
		this.typeList = typeList;
		this.typeView = typeView;
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
			switch (typeView) {
			case 1:
				convertView = infalInflater.inflate(R.layout.base_row_item, null);
				ImageView image = (ImageView) convertView.findViewById(R.id.imageView1);
				image.setBackgroundResource(R.drawable.tick_employee);
				break;
			case 2:
				convertView = infalInflater.inflate(R.layout.base_row_item, null);
				ImageView image1 = (ImageView) convertView.findViewById(R.id.imageView1);
				image1.setBackgroundResource(R.drawable.ticket_sale);
			default:
				break;
			}
		}
		TextView name = (TextView)convertView.findViewById(R.id.personname);
		
		switch (typeList) {
		case 1:
			Employee employee = (Employee)array.get(position);
			name.setText(employee.getstrName());
			break;
		case 2:
			Ticket ticket = (Ticket) array.get(position);
			name.setText(ticket.getCode().trim() + "idTickect: " +ticket.getID());
			break;
		default:
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

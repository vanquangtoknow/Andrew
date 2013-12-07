package Adapter;

import java.util.ArrayList;

import DTO.Category;
import DTO.CheckInOutTemp;
import DTO.Employee;
import DTO.SaleItem;
import DTO.Ticket;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ListCheckinCheckoutAdapter extends BaseAdapter {
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
	public ListCheckinCheckoutAdapter(Context context, ArrayList<?> array) 
	{
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
		
		if( convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			switch (typeView) {
			case 1:
				convertView = infalInflater.inflate(R.layout.base_row_item, null);
				ImageView image = (ImageView) convertView.findViewById(R.id.imageView1);
				image.setBackgroundResource(R.drawable.tick_employee);
				break;
			default:
				break;
			}
		}
		TextView name = (TextView)convertView.findViewById(R.id.personname);
		switch (typeList) {
		case 1:
			CheckInOutTemp checkinout = (CheckInOutTemp)array.get(position);
			name.setText(checkinout.getStrName());
			if(checkinout.isCheck()==true)
			{
				name.setTextColor(Color.GREEN);
			}
			else
			{
				name.setTextColor(Color.BLACK);
			}
			break;
		default:
			break;
		}
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}
}

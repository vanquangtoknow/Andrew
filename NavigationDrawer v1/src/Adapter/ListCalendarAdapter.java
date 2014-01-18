package Adapter;

import java.util.ArrayList;

import DTO.ItemInfo;
import DTO.TrackingTemp;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ListCalendarAdapter extends BaseAdapter{
	private ArrayList<ItemInfo> array;
	private int selectedItem;
	private Context context;
	public ListCalendarAdapter() {
	}

	@Override
	public int getCount() {
		return array.size();
	}
	public void setSelectedIndex(int ind)
    {
		selectedItem = ind;
    }
	public ListCalendarAdapter(Context context, ArrayList<ItemInfo> array) {
		this.array = array;
		this.context = context;
	}
	@Override
	public ItemInfo getItem(int pos) {
		return array.get(pos);
	}
	@Override
	public long getItemId(int position) {
		//return array.get(position).getIdEmployee();
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null)
			{
				LayoutInflater infalInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.row_calendar, null);
			}
			TextView number = (TextView)convertView.findViewById(R.id.tvNumber);
			TextView technician = (TextView)convertView.findViewById(R.id.tvTechnician);
			TextView timestart = (TextView)convertView.findViewById(R.id.tvtimestart);
			TextView timeend = (TextView)convertView.findViewById(R.id.tvtimeend);
			Log.i("listviewCalendar", position + "");
			if(position==0)
			{
				number.setText("Number");
				technician.setText("Technician");
				timestart.setText("Time start");
				timeend.setText("Time end");
			}
			else
			{
				number.setText(Integer.toString(position));
				technician.setText(array.get(position).getText());
				timestart.setText(array.get(position).getStartday().toString());
				timeend.setText(array.get(position).getEndday().toString());
			}
		return convertView;
	}


}

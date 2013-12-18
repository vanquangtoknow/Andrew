package Adapter;

import java.util.ArrayList;

import DTO.TrackingTemp;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ListTrackingTempAdapter extends BaseAdapter {
	private ArrayList<TrackingTemp> array;
	private int selectedItem;
	private Context context;
	public ListTrackingTempAdapter() {
	}

	@Override
	public int getCount() {
		return array.size();
	}
	public void setSelectedIndex(int ind)
    {
		selectedItem = ind;
    }
	public ListTrackingTempAdapter(Context context, ArrayList<TrackingTemp> array) {
		this.array = array;
		this.context = context;
	}
	@Override
	public TrackingTemp getItem(int pos) {
		return array.get(pos);
	}
	@Override
	public long getItemId(int position) {
		return array.get(position).getIdEmployee();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null)
			{
				LayoutInflater infalInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.row_tracking, null);
			}
			TextView number = (TextView)convertView.findViewById(R.id.tvNumber);
			TextView time = (TextView)convertView.findViewById(R.id.tvTime);
			TextView employee = (TextView)convertView.findViewById(R.id.tvEmployee);
			TextView total = (TextView)convertView.findViewById(R.id.tvTotal);
			if(position==0)
			{
				number.setText("Number");
				time.setText("Time");
				employee.setText("Technician");
				total.setText("Total");
			}
			else
			{
				number.setText(""+position);
				time.setText(array.get(position).getTime());
				if(array.get(position).getIsView()==true)
				{
					employee.setTextColor(Color.RED);
					employee.setText(array.get(position).getNameEmployee() + "(v)");
				}else
				{
					employee.setText(array.get(position).getNameEmployee());
					employee.setTextColor(Color.BLACK);
				}
				total.setText(""+array.get(position).getTotal());
			}
		return convertView;
	}


}

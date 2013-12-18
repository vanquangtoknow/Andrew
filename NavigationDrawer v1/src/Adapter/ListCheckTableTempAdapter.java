package Adapter;
import java.util.ArrayList;

import com.example.android.navigationdrawerexample.R;

import DTO.CheckTableTemp;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListCheckTableTempAdapter extends BaseAdapter {
	private ArrayList<CheckTableTemp> array;
	private int selectedItem;
	private Context context;
	public ListCheckTableTempAdapter() {
	}

	@Override
	public int getCount() {
		return array.size();
	}
	public void setSelectedIndex(int ind)
    {
		selectedItem = ind;
    }
	public ListCheckTableTempAdapter(Context context, ArrayList<CheckTableTemp> array) {
		this.array = array;
		this.context = context;
	}
	@Override
	public CheckTableTemp getItem(int pos) {
		return array.get(pos);
	}
	@Override
	public long getItemId(int position) {
		return array.get(position).getIdChecktable();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_checktable, null);
		}
		TextView number = (TextView)convertView.findViewById(R.id.tvNumber);
		TextView employee = (TextView)convertView.findViewById(R.id.tvEmployee);
		TextView timein = (TextView)convertView.findViewById(R.id.tvTimein);
		TextView timeout = (TextView)convertView.findViewById(R.id.tvTimeout);
		if(position==0)
		{
			number.setText("Number");
			employee.setText("Technician");
			timein.setText("Time in");
			timeout.setText("Time out");
			
		}
		else
		{
			number.setText(""+position);
			employee.setText(array.get(position).getEmployeeName());
			timein.setText(array.get(position).getCheckIn());
			timeout.setText(array.get(position).getCheckOut());
		}
		/*if(selectedItem != 0 && position == selectedItem)
        {
			convertView.setBackgroundColor(Color.DKGRAY);
        }
        else
        {
        	convertView.setBackgroundColor(Color.WHITE);
        }*/
		
		return convertView;
	}

}

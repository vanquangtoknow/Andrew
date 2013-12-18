package Adapter;

import java.util.ArrayList;

import DTO.CheckInfoTableTemp;
import DTO.CheckTableTemp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ListCheckInfoTableTempAdapter extends BaseAdapter {
	private ArrayList<CheckInfoTableTemp> array;
	private int selectedItem;
	private Context context;
	public ListCheckInfoTableTempAdapter() {
	}

	@Override
	public int getCount() {
		return array.size();
	}
	public void setSelectedIndex(int ind)
    {
		selectedItem = ind;
    }
	public ListCheckInfoTableTempAdapter(Context context, ArrayList<CheckInfoTableTemp> array) {
		this.array = array;
		this.context = context;
	}
	@Override
	public CheckInfoTableTemp getItem(int pos) {
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
		if(array.get(position).getId_Employee()==-Integer.MAX_VALUE)
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
		return convertView;
	}


}

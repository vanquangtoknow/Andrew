package Adapter;
import java.util.ArrayList;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;

import DTO.Employee;
import DTO.Item;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class personListAdapter extends BaseAdapter {
	private Context context; 
	private ArrayList<Employee> array;
	private int selectedIndex = -1;
    private int selectedColor = Color.parseColor("#25A7D3");
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	
	public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

	public personListAdapter(Context context, ArrayList<Employee> array) {
		this.array = array;
		this.context = context;
	}
	
	@Override
	public Employee getItem(int pos) {
		// TODO Auto-generated method stub
		return array.get(pos);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return array.get(position).getID_Employee();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("pAdapter-getview1", String.valueOf(position));
		
		if( convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_person, null);
		}
		Log.e("pAdapter-getview2", String.valueOf(position));
		TextView name = (TextView)convertView.findViewById(R.id.personname);
		name.setText(array.get(position).getstrName());
		
		if(selectedIndex != -1 && position == selectedIndex)
        {
			convertView.setBackgroundColor(selectedColor);
        }
        else
        {
        	convertView.setBackgroundColor(Color.WHITE);
        }
		
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}

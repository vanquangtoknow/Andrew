package Adapter;
import java.util.ArrayList;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;

import DTO.Item;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	private Context context; 
	private ArrayList<Item> array;
	private int selectedItem;
    public void setSelectedItem(int position) {
        selectedItem = position;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	public ListAdapter(Context context, ArrayList<Item> array) {
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
			convertView = infalInflater.inflate(R.layout.row_item, null);
		}
		
		Log.e("Adapter-getview2", String.valueOf(position));
		TextView name = (TextView)convertView.findViewById(R.id.personname);
		name.setText(array.get(position).name);
		TextView value = (TextView)convertView.findViewById(R.id.value);
		value.setText(array.get(position).value);
        if (position == selectedItem)
        {
            value.setTextColor(Color.BLUE);
        }
        else
        {
        	value.setTextColor(Color.BLACK);
        }
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}

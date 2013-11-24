package Adapter;
import java.util.ArrayList;

import com.example.android.navigationdrawerexample.R;
import com.example.android.navigationdrawerexample.R.id;
import com.example.android.navigationdrawerexample.R.layout;

import DTO.Item;
import DTO.OrderExpense;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExpenseAdapter extends BaseAdapter {
	private Context context; 
	private ArrayList<OrderExpense> array;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}
	public ExpenseAdapter(Context context, ArrayList<OrderExpense> array) {
		this.array = array;
		this.context = context;
		String a ="";
	}
	
	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return array.get(pos);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return array.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("Adapter-getview1", String.valueOf(position));
		if( convertView == null)
		{
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_expense, null);
		}
		Log.e("Adapter-getviewexpense", String.valueOf(position));
		TextView name = (TextView)convertView.findViewById(R.id.exname);
		TextView date = (TextView)convertView.findViewById(R.id.exdate);
		TextView price = (TextView)convertView.findViewById(R.id.exprice);
		TextView id = (TextView)convertView.findViewById(R.id.exId);
		if (array.get(position).getId() == -2) {
			id.setText("ID");
			date.setText("Date");
			name.setText("Name");
			price.setText("Price");
		} else {
			if (array.get(position).getId() == -1) {
				id.setText("-----");
				date.setText("-----");
			} else {
				id.setText(String.valueOf(array.get(position).getId()));
				date.setText(array.get(position).getDate());
			}
			name.setText(array.get(position).getName());
			price.setText(String.valueOf(array.get(position).getMoney()));
		}
		return convertView;
	}
	public void Clear()
	{
		array.clear();
	}

	
}

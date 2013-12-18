package Adapter;

import java.util.ArrayList;

import Adapter.AutoCompleteCheckInfoTable.ContactoHolder;
import DTO.CheckInfoTableTemp;
import DTO.Employee;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class AutoCompleteEmployee extends ArrayAdapter<Employee> implements Filterable {

	Context context;
	int layoutResourceId;
	ArrayList<Employee> data = new ArrayList<Employee>();
	ArrayList<Employee> temp = new ArrayList<Employee>();
	public AutoCompleteEmployee(Context context, int layoutResourceId,
			ArrayList<Employee> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	@Override
	public int getCount(){
	    return temp.size();
	}
	@Override
    public Employee getItem(int position) {
        return temp.get(position);
    }

	@Override
	public Filter getFilter() {
		Filter myFilter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					temp = findata(constraint.toString());
					filterResults.values = temp;
					filterResults.count = temp.size();
					Log.i("SIZE", ""+ temp.size());
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence contraint,
					FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return myFilter;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ContactoHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ContactoHolder();
			holder.contactoInfo = (TextView) row
					.findViewById(R.id.personname);
			holder.contactoImg = (ImageView) row
					.findViewById(R.id.imageView1);

			row.setTag(holder);
		} else {
			holder = (ContactoHolder) row.getTag();
		}
		Employee ap = temp.get(position);
			if (ap.getstrName().length() > 0 && ap.getstrName().charAt(0) == '#')
				holder.contactoInfo.setText("abc");
			else
				holder.contactoInfo.setText(ap.getstrName());
			return row;
			
	}
	public ArrayList<Employee> findata(String search)
	{
		String lowersearh = search.toLowerCase();
		ArrayList<Employee> list_result = new ArrayList<Employee>();
		for(int i=0;i<data.size();i++)
		{
			String text = data.get(i).getstrName().toLowerCase();
			Log.i("Compare", lowersearh +">>>" + text);
			if(text.startsWith(lowersearh))
			{
				Log.i("finded","aad" + data.get(i).getstrName());
				list_result.add(data.get(i));
			}
		}
		return list_result;
	}
	static class ContactoHolder {
		TextView contactoInfo;
		ImageView contactoImg;
	}
}
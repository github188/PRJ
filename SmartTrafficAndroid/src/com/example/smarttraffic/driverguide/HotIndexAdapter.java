package com.example.smarttraffic.driverguide;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeFavor;
import com.example.smarttraffic.util.TextViewUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotIndexAdapter extends BaseAdapter
{
	List<HotIndex> data;
	Context context;
	
	public HotIndexAdapter(Context context,List<HotIndex> data)
	{
		this.data=data;
		this.context=context;

	}

	@Override
	public int getCount() {
		return data.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return position==0?null:data.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_zone_index,null);

		if(position==0)
		{
			convertView.setBackgroundColor(0xffaeaeae);
			TextViewUtil.setText(convertView, R.id.item1, "热点名称");
			TextViewUtil.setText(convertView, R.id.item2, "指数");
			TextViewUtil.setText(convertView, R.id.item3, "拥堵等级");
			
		}
		else
		{
			TextViewUtil.setText(convertView, R.id.item1, data.get(position-1).getCbdName());
			
			TextViewUtil.setTextForIndex(convertView, R.id.item3, R.id.item2, data.get(position-1).getTpiLevel(), data.get(position-1).getTpi());
//			TextViewUtil.setText(convertView, R.id.item2,  data.get(position-1).getTpi());
//			TextViewUtil.setText(convertView, R.id.item3,  data.get(position-1).getTpiLevel());
		}
		
		return convertView;
	}
	

	

	

}


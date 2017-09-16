package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smarttraffic.R;

public class RealAirAdapter extends BaseAdapter
{
	private Context context;
	private List<Air> data;
	private int kind;
	
	public RealAirAdapter(Context c,List<Air> data)
	{
		this(c, data,1);
	}
	
	
	public RealAirAdapter(Context c,List<Air> data,int kind)
	{
		this.context=c;
		this.data=data;
		this.kind=kind;
	}
	
	@Override
	public int getCount() {
		
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		if(kind==1)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_train_realtime, null);
			
			Holder tickets=new Holder();
			tickets.station=(TextView)convertView.findViewById(R.id.list_train_station);
			tickets.planstart=(TextView)convertView.findViewById(R.id.list_train_planstart);
			tickets.planend=(TextView)convertView.findViewById(R.id.list_train_planend);
			tickets.realstart=(TextView)convertView.findViewById(R.id.list_train_realstart);
			tickets.realend=(TextView)convertView.findViewById(R.id.list_train_realend);
			tickets.retain=(TextView)convertView.findViewById(R.id.list_train_retain);
	
			tickets.station.setText(data.get(position).getStation());
			tickets.planstart.setText(data.get(position).getPlanStart());
			tickets.planend.setText(data.get(position).getPlanEnd());
			tickets.realstart.setText(data.get(position).getRealStart());
			tickets.realend.setText(data.get(position).getRealReach());
			tickets.retain.setText(data.get(position).getRetain());
		}
		else if(kind==2)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_bus_realtime, null);
			
			Holder tickets=new Holder();
			tickets.station=(TextView)convertView.findViewById(R.id.listview_first);
			tickets.planstart=(TextView)convertView.findViewById(R.id.listview_second);
			tickets.planend=(TextView)convertView.findViewById(R.id.listview_third);
			tickets.retain=(TextView)convertView.findViewById(R.id.listview_forth);
	
			tickets.station.setText(data.get(position).getStation());
			tickets.planstart.setText(data.get(position).getPlanStart());
			tickets.planend.setText(data.get(position).getPlanEnd());
			tickets.retain.setText(data.get(position).getRetain());

		}
		return convertView;
	}
	
	public void update(List<Air> object)
	{
		this.data=null;
		this.data=object;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data=new ArrayList<Air>();
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView station;
		public TextView planstart;
		public TextView planend;
		public TextView realstart;
		public TextView realend;
		public TextView retain;

	}
}

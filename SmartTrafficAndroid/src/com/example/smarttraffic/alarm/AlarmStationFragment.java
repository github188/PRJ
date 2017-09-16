package com.example.smarttraffic.alarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.SmartBusFavorActivity;
import com.example.smarttraffic.smartBus.SmartBusNearbyStationMapActivity;
import com.example.smarttraffic.smartBus.adapter.StationHistoryAdapter;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.smartBus.db.SmartBusHistoryDB;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.SmartStationInputviewListener;
import com.example.smarttraffic.view.VoiceListener;

/**
 * 
 * @author Administrator zhou
 *
 */
public class AlarmStationFragment extends Fragment {
	
	StationHistoryAdapter historyAdapter;
	ListView historyListView;
	InputListView stationListView;
	SmartStationInputviewListener smartStationInputviewListener;
	Button searchButton;
	TextView clearTextView;
	ImageView exchangeImageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_smart_bus_station,container, false);
	
		initView(rootView);
		return rootView;
	}
	
	/**
	 * 初始化视图
	 * @param rootView
	 */
	private void initView(View rootView)
	{
		historyListView=(ListView)rootView.findViewById(R.id.trip_driving_self_history);
		new BaseAdapterLongClickWrapper(historyListView);
		searchButton=(Button)rootView.findViewById(R.id.trip_driving_self_search);
		
		stationListView=(InputListView)rootView.findViewById(R.id.trip_driving_self_end);	
		stationListView.setHint("请输入站点名称");
		stationListView.setTextWatcher(inputWordLister);
		smartStationInputviewListener=new SmartStationInputviewListener(stationListView);
		smartStationInputviewListener.setListener(new InputListViewListener[]
				{new VoiceListener(getActivity(), stationListView),
				new inputFavorListener(),
				new nearbyListener()});
		
		clearTextView=(TextView)rootView.findViewById(R.id.smart_bus_station_history_clear);
		
		onclick click=new onclick();
		searchButton.setOnClickListener(click);
		clearTextView.setOnClickListener(click);
		
		historyListView.setOnItemClickListener(new onHistoryItemClick());
	}
	
	/**
	 * 历史记录内容点击事件监听
	 */
	class onHistoryItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			BusStationForQueryByName temp=historyAdapter.getData().get(position);
			search(temp.getId());
//			saveHistory(historyAdapter.getData().get(position));
		}
	}
	
	/**
	 * 视图点击监听事件
	 *
	 */
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				
			case R.id.smart_bus_station_history_clear:
				deleteHistory();
				break;
				
			case R.id.trip_driving_self_search:				
				search();
				break;
			}			
		}
	}
	
	private void search(int id)
	{
		Bundle bundle=new Bundle();
		bundle.putInt(AlarmLineSelectActivity.SMART_BUS_STATTON_ID, id);
		StartIntent.startIntentWithParam(getActivity(), AlarmLineSelectActivity.class, bundle);
	}
	
	private void search()
	{	
		String str=stationListView.getText();
		if(str.equals(""))
			Toast.makeText(getActivity(), "输入内容不能为空", Toast.LENGTH_SHORT).show();
		else
		{
			Bundle bundle=new Bundle();
			bundle.putString(SelectStationActivity.SELECT_STATION_NAME, str);
			StartIntent.startIntentWithParam(getActivity(), SelectStationActivity.class, bundle);
		}
	}
	
	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		SmartBusHistoryDB dbOperate=new SmartBusHistoryDB(getActivity());
		
		List<BusStationForQueryByName> data=dbOperate.selectForStationHistory();
		
		updateHistoryOrSuggestion(true, data);
		
		dbOperate.CloseDB();
	}
	
	/**
	 * 删除搜索历史记录
	 */
	private void deleteHistory()
	{
		SmartBusHistoryDB dbOperate=new SmartBusHistoryDB(getActivity());
		dbOperate.deleteAll(ContentType.SMART_BUS_STATION_HISTORY);
		dbOperate.CloseDB();
		
		getActivity().findViewById(R.id.smart_bus_station_history_layout).setVisibility(View.GONE);
	}
	
	/**
	 * 保存搜索历史记录
	 */
//	private void saveHistory(BusStationForQueryByName stationInfo)
//	{		
//		if(stationInfo!=null)
//		{
//			SmartBusHistoryDB dbOperate=new SmartBusHistoryDB(getActivity());
//			
//			if(!dbOperate.isHistory(stationInfo))
//			{
//				dbOperate .insert(stationInfo);				
//			}
//			dbOperate.CloseDB();
//		}
//	}
	
	private void updateHistoryOrSuggestion(boolean ishistory,List<BusStationForQueryByName> data)
	{
		if(historyListView.getAdapter()==null)
		{
			historyAdapter=new StationHistoryAdapter(getActivity(), data);
			historyListView.setAdapter(historyAdapter);
		}
		else
		{
			historyAdapter.update(data);
		}
		
		if(historyAdapter.getCount()==0)
		{
			getActivity().findViewById(R.id.smart_bus_station_history_layout).setVisibility(View.GONE);
		}
		else
		{
			clearTextView.setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.smart_bus_station_history_layout).setVisibility(View.VISIBLE);
		}
		
		if(ishistory)
		{
			getActivity().findViewById(R.id.smart_bus_station_history_clear).setVisibility(View.VISIBLE);
			((TextView)getActivity().findViewById(R.id.smart_bus_station_history_name)).setText("历史记录");
		}
		else
		{
			getActivity().findViewById(R.id.smart_bus_station_history_clear).setVisibility(View.GONE);
			((TextView)getActivity().findViewById(R.id.smart_bus_station_history_name)).setText("提示");
		}
	}
	
	class nearbyListener implements InputListViewListener
	{
		@Override
		public void inputSelectListener()
		{
			StartIntent.startIntent(getActivity(), SmartBusNearbyStationMapActivity.class);
			
		}
	}
	
	class inputFavorListener implements InputListViewListener
	{		
		@Override
		public void inputSelectListener()
		{
			Bundle bundle=new Bundle();
			bundle.putInt(SmartBusFavorActivity.SMART_BUS_FAVOR_FIRST_PAGE, 2);
			StartIntent.startIntentWithParam(getActivity(), SmartBusFavorActivity.class,bundle);
		}
	}
	
	String tempInput;
	private void addSuggestion(String str)
	{
		tempInput=str;
		new HttpThread(
			new BaseSearch(){

			@Override
			public Object parse(String data)
			{
				
				String data1=DoString.parseThreeNetString(data);
				
				BusStationForQueryByNameTemp result=JSON.parseObject(data1, BusStationForQueryByNameTemp.class);
				
				return result;
			}}, 
			new BaseRequest(){

				@Override
				public PostParams CreatePostParams()
				{
					PostParams postParams=new PostParams();
					postParams.setUrl(HttpUrlRequestAddress.SMART_BUS_STATION_SUUGESTION_URL);
					Map<String, Object> d=new HashMap<String, Object>();
					d.put("stationName", tempInput);
					postParams.setParams(d);
					
					return postParams;				
				}

				},
			new UpdateView()	
			{	
					@Override
				public void update(Object data)
				{
						updateHistoryOrSuggestion(true,((BusStationForQueryByNameTemp)data).getStationList());
				}
			}).start();
	}
	
	private TextWatcher inputWordLister=new TextWatcher()
	{
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
			
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
			if(s.toString().equals(""))
			{
				loadHistory();
			}
			else
			{
				addSuggestion(s.toString());
			}
		}
	};
	
	@Override
	public void onResume()
	{
		loadHistory();
		
		super.onResume();
	}
}


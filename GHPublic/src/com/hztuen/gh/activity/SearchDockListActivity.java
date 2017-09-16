package com.hztuen.gh.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gh.modol.DockModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.DuckAreaAdapter;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter;
import com.hztuen.gh.activity.Adapter.SearchShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/*
 * 码头信息查询界面
 */
public class SearchDockListActivity extends Activity implements OnClickListener {

	private EditText edit_search;
	private ListView list_dock_name,list_dock_name_area,list_dock_record;
	private RelativeLayout relative_detail_info;
	private RelativeLayout relative_title_final;
	private RelativeLayout relative_base_info, relative_cred_info,
	relative_break_rules, relative_deduct_points, relativie_baogang,
	relative_danger_record;
	private ArrayList<String> douckName = new ArrayList<String>();
	private SearchShipNameAdapter nameAdapter,duck_search_record_adapter;
	private ImageView img_clear;
	private RelativeLayout relative_case;
	private LinearLayout liner_case;
	private ListView listview_start;
	public ArrayList<String> from_lists;
	private PopShipNameAdapter fromAdapter;
	public PopupWindow popupWindowArea;
	private View contentView;
	private List<DockModel> modellist = new ArrayList<DockModel>();
    private DuckAreaAdapter dockAreaAdapyer;
    private TextView wharftitle;
    private Editor edit_record;
    
    
    private List<String> duck_record_list=new ArrayList<String>();//码头历史记录列表
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_dock);
		
		init();
		
		
		//码头搜索历史列表轻量级数据库
		SharedPreferences shared_duck_record= getSharedPreferences("duck_search_record", 
				Activity.MODE_PRIVATE); 
		
		edit_record = shared_duck_record.edit(); 
		
		
		String Duck_name =shared_duck_record.getString("duck_record", ""); 
		
		if(Duck_name!=null)

		{
		
			try {
				duck_record_list=Util.String2SceneList(Duck_name);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void init() {
		edit_search = (EditText) findViewById(R.id.text1_context);
		wharftitle = (TextView) findViewById(R.id.wharftitle);
		//搜索输入框获取焦点与失去焦点事件监听
		edit_search.setOnFocusChangeListener(new android.view.View.
				OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					//获取焦点
					popupWindowArea.dismiss();
					modellist.clear();
					douckName.clear();
					
					if("".equals(edit_search.getText().toString()))//edit获取焦点且内容为空的时候显示历史记录
					{

						
						
						list_dock_record.setVisibility(View.VISIBLE);
						duck_search_record_adapter = new SearchShipNameAdapter(getApplicationContext(),duck_record_list);
						list_dock_record.setAdapter(duck_search_record_adapter);
						setListViewHeight(list_dock_record);
					}
				//	duck_record_list.clear();
				} else {
					//失去焦点
					//showPopAre();
					
					
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);   
			        imm.hideSoftInputFromWindow(edit_search.getWindowToken(),0);  
					
				}
			}
		});
		
		nameAdapter = new SearchShipNameAdapter(getApplicationContext(),douckName);
		relative_case=(RelativeLayout)findViewById(R.id.relative1);

		list_dock_record=(ListView)findViewById(R.id.list_dock_record);//历史记录列表
		
	
		View viewfooter = getLayoutInflater().inflate(
				R.layout.list_footer_clear, null);
		
		//清空历史点击事件
		viewfooter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edit_record.putString("duck_record", "");
				edit_record.commit();
				duck_record_list.clear();
				duck_search_record_adapter.notifyDataSetChanged();
				
			}
		});
	
		list_dock_record.addFooterView(viewfooter);

		list_dock_name = (ListView) findViewById(R.id.list_dock_name);//按提示文字查询的港口列表
	
		
		list_dock_name_area=(ListView)findViewById(R.id.list_dock_name_area);//按地理位置查询的港口列表
		liner_case = (LinearLayout) findViewById(R.id.linear_case);
		Intent intent = getIntent();
		
		relative_detail_info = (RelativeLayout) findViewById(R.id.relative_detail_info);// 被隐藏的部分
		
		/**
		 * 这个是为了从我的码头版跳转过来的时候隐藏部分功能界面
		 * */
		if(intent.getStringExtra("wharfname") == null){
			//不做处理
		}else{
			liner_case.setVisibility(View.GONE);
			relative_detail_info.setVisibility(View.VISIBLE);
			wharftitle.setText(intent.getStringExtra("wharfname"));
		}
		relative_base_info = (RelativeLayout) findViewById(R.id.relative2);// 基本信息
		relative_base_info.setOnClickListener(this);

		relative_cred_info = (RelativeLayout) findViewById(R.id.relative3);// 船舶证书
		relative_cred_info.setOnClickListener(this);



		relative_deduct_points = (RelativeLayout) findViewById(R.id.relative5);// 诚信扣分
		relative_deduct_points.setOnClickListener(this);



		relativie_baogang = (RelativeLayout) findViewById(R.id.relative6); // 报港记录
		relativie_baogang.setOnClickListener(this);

		relative_danger_record = (RelativeLayout) findViewById(R.id.relative8); // 危货申报
		relative_danger_record.setOnClickListener(this);

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);

		img_clear=(ImageView)findViewById(R.id.img_clear);
		img_clear.setOnClickListener(this);
		img_clear.setVisibility(View.INVISIBLE);


		dockAreaAdapyer=new DuckAreaAdapter(getApplicationContext(), modellist);
		
		
		//监听文字变化
				edit_search.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub

						list_dock_name.setVisibility(View.VISIBLE);
						list_dock_name_area.setVisibility(View.GONE);
						relative_detail_info.setVisibility(View.GONE);
						popupWindowArea.dismiss();
						
						if("".equals(edit_search.getText().toString()))
						{
							img_clear.setVisibility(View.INVISIBLE);
							
						}else{
						img_clear.setVisibility(View.VISIBLE);
						GetDockListTask();
						}
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
		
//		
//		// 软键盘点击事件
//		edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEND
//						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//					// do something;
//
//					list_dock_name.setVisibility(View.VISIBLE);
//					list_dock_name_area.setVisibility(View.GONE);
//					popupWindowArea.dismiss();
//					GetDockListTask();
//					return true;
//				}
//				return false;
//			}
//		});
//		
		
		

		// 按地域获取的列表item点击事件
		list_dock_name_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				relative_detail_info.setVisibility(View.GONE);
				list_dock_name.setVisibility(View.GONE);
				list_dock_name_area.setVisibility(View.GONE);
				SystemStatic.Wharfname=modellist.get(position).getwharfname();
				
				
				//如果历史列表中又相同的历史记录，移除原来的记录，添加新的记录
				for(int i=0;i<duck_record_list.size();i++)
				{
					if(duck_record_list.get(i).toString().equals(modellist.get(position).getwharfname().toString()))
					{
						duck_record_list.remove(i);
					}
				}
				
					duck_record_list.add(0,modellist.get(position).getwharfname());
					try {
						edit_record.putString("duck_record", Util.SceneList2String(duck_record_list));
						edit_record.commit();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
					
				
				Intent intent=new Intent(getApplicationContext(),DuckDetailsActivity.class);
				startActivity(intent);

			}
		});
		
		
		
		
		
		
		//历史列表item点击事件
		list_dock_record.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						relative_detail_info.setVisibility(View.GONE);
						list_dock_name.setVisibility(View.GONE);
						list_dock_name_area.setVisibility(View.GONE);
						
						SystemStatic.Wharfname=duck_record_list.get(position).toString();//设置码头名字为历史记录里的名字
						
						
						
						Intent intent=new Intent(getApplicationContext(),DuckDetailsActivity.class);
						startActivity(intent);

					}
				});
				
				
				
		
		
		
		
		// 点击listview的item
				list_dock_name.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Log.i("GH_TEXT", "item点击事件");
					//	edit_search.setText(douckName.get(position).toString());
						relative_detail_info.setVisibility(View.GONE);
						list_dock_name.setVisibility(View.GONE);
						list_dock_name_area.setVisibility(View.GONE);
						
							SystemStatic.Wharfname=douckName.get(position).toString();
							
							//如果历史列表中又相同的历史记录，移除原来的记录，添加新的记录
							for(int i=0;i<duck_record_list.size();i++)
							{
								if(duck_record_list.get(i).toString().equals(douckName.get(position).toString()))
								{
									duck_record_list.remove(i);
								}
							}
							duck_record_list.add(0,douckName.get(position).toString());
							try {
								edit_record.putString("duck_record", Util.SceneList2String(duck_record_list));
								edit_record.commit();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						
						Intent intent=new Intent(getApplicationContext(),DuckDetailsActivity.class);
						startActivity(intent);

					}
				});
	}

	@Override
	public void onClick(View v) {
		/**
		 * 这个是为了从我的码头版跳转过来的时候隐藏部分功能界面
		 * intent.getStringExtra("wharfname")
		 * 我的  码头版传过来的参数
		 * 功能界面记得传参
		 * */
		Intent intent = getIntent();
		String wharfname = null;
		if(intent.getStringExtra("wharfname") == null){
			//不处理
		}else{
			wharfname = intent.getStringExtra("wharfname");
		}
		switch (v.getId()) {
		// 基本信息
		case R.id.relative2:
//			if(wharfname == null){
//				SystemStatic.Wharfname = edit_search.getText().toString();
//			}else{
//				SystemStatic.Wharfname = wharfname;
//			}
			
			Intent intent_base_info = new Intent(getApplicationContext(),
					DuckDetailsActivity.class);
			startActivity(intent_base_info);
			break;
			// 返回按钮
		case R.id.relative_title_final:
			finish();
			break;
			// 船舶证书
		case R.id.relative3:
			
			Intent intent_cred_info=new Intent();
			intent_cred_info.setClass(getApplicationContext(),CredCardInfoActivity.class);
			intent_cred_info.putExtra("type", "码头证书");
			
			SystemStatic.Wharfname=wharftitle.getText().toString();
			startActivity(intent_cred_info);
			break;



			// 诚信扣分
		case R.id.relative5:
			
			Intent intent_deduct_info = new Intent();
					
			
			intent_deduct_info.setClass(getApplicationContext(), DeductPointsActivity.class);
			intent_deduct_info.putExtra("PointsType", "码头扣分");
			startActivity(intent_deduct_info);
			break;

		//	危险货物作业申报信息
		case R.id.relative6:
			if(wharfname == null){
				SystemStatic.Wharfname = edit_search.getText().toString();
			}else{
				SystemStatic.Wharfname = wharfname;
			}
			Intent intent_baogang = new Intent(getApplicationContext(),
					DuckDangersRecordActivity.class);
			startActivity(intent_baogang);
			break;

			//普通货物作业报告信息
		case R.id.relative8:
			if(wharfname == null){
				SystemStatic.searchShipName = edit_search.getText().toString();
			}else{
				SystemStatic.searchShipName = wharfname;
			}
			Intent intent_danger = new Intent(getApplicationContext(),
					DangersLetInActivity.class);
			startActivity(intent_danger);
			break;
			// 点击清除按钮
		case R.id.img_clear:
			popupWindowArea.dismiss();
			
			
			relative_detail_info.setVisibility(View.GONE);
			list_dock_name.setVisibility(View.GONE);
			list_dock_name_area.setVisibility(View.GONE);
			list_dock_record.setVisibility(View.GONE);
			
			
			modellist.clear();
			douckName.clear();
			edit_search.setText("");
			
			
			showPopAre();
			edit_search.clearFocus();//失去焦点
			
			
			
			
			
			
			
			
			break;
		default:
			break;
		}

	}

	// 按提示文字获取码头列表
	private void GetDockListTask() {

		// TODO Auto-generated method stub

		// 访问网络

		String tip = edit_search.getText().toString();
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Tip=" + tip);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.wharfnames;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {

						douckName.clear();
						list_dock_record.setVisibility(View.GONE);
						JSONObject res = new JSONObject(result);
						// JSONObject res=JSONObject.
						JSONArray data = res.getJSONArray("data");
						// shipName=ParseUtil.parseDataToCollection(res,"data",ArrayList);

						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "无信息",
									Toast.LENGTH_SHORT).show();
						} else {
							String name = data.toString().replace("[", "")
									.replace("]", "").replace("\"", "");

							String[] aa = name.split("\\,");

							for (int i = 0; i < aa.length; i++) {
								douckName.add(aa[i]);
							}
							list_dock_name.setAdapter(nameAdapter);
							setListViewHeight(list_dock_name);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}

	// 计算listview高度

	public void setListViewHeight(ListView listView) {

		// 获取ListView对应的Adapter

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	
	
	//地区弹出框
	private void showPopAre()
	{
		String ac0 = "杭州";
		String ac1 = "内河";
		String ac2 = "余杭";
		String ac3 = "富阳";
		String ac4 = "桐庐";
		String ac5 = "萧山";
		String ac6 = "建德";
		String ac7 = "淳安";
		String ac8 = "临安";
		
		from_lists = new ArrayList<String>();
		from_lists.add(ac0);
		from_lists.add(ac1);
		from_lists.add(ac2);
		from_lists.add(ac3);
		from_lists.add(ac4);
		from_lists.add(ac5);
		from_lists.add(ac6);
		from_lists.add(ac7);
		from_lists.add(ac8);
		
		contentView = getLayoutInflater().inflate(R.layout.pop_start,
				null);// 港口

		listview_start = (ListView) contentView
				.findViewById(R.id.listview_start);


		fromAdapter = new PopShipNameAdapter(this, from_lists, 6);
		listview_start.setAdapter(fromAdapter);

		LinearLayout parent6 = (LinearLayout) this
				.findViewById(R.id.search_dock_father);// 父窗口view
		popupWindowArea = new PopupWindow(contentView,
				parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
		// popupWindowArea.setFocusable(true);
		// popupWindowArea.setOutsideTouchable(true);

		listview_start.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
					View view, int position, long id) {
				GetDockListTaskByArea(from_lists.get(position));
				
				
				
				popupWindowArea.dismiss();
				
				list_dock_name_area.setVisibility(View.VISIBLE);
				relative_detail_info.setVisibility(View.GONE);
				list_dock_name.setVisibility(View.GONE);
				list_dock_record.setVisibility(View.GONE);

			}
		});
		popupWindowArea.setTouchable(true);
		popupWindowArea.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
			}
		});

		WindowManager.LayoutParams lp6 = getWindow().getAttributes();
		lp6.alpha = 1f;
		getWindow().setAttributes(lp6);

		popupWindowArea.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow()
						.getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});


		popupWindowArea.showAsDropDown(liner_case, 0, 0);
	} 
	//加载完activity后弹出弹出框
	@Override  
	public void onWindowFocusChanged(boolean hasFocus) {  
		super.onWindowFocusChanged(hasFocus);  
		if(hasFocus ) {  
			//地区弹出框
			Intent intent = getIntent();
			if(intent.getStringExtra("wharfname") == null){
				showPopAre();
			}else{
//				//不做处理
			}
			
		}  
	}  

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		if(popupWindowArea.isShowing()){
//			popupWindowArea.dismiss();
//		}
	}
	
	
	
	// 按城市取码头列表
		private void GetDockListTaskByArea(String area) {

			// TODO Auto-generated method stub

			// 访问网络

			
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Area=" + area);

			
			
			edit_record.putString("duck_record", area);
			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.wharflist;
			if (!toUrl.equals("")) {
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
						KJLoger.debug("===" + headers.get("Set-Cookie"));
						// Log.i(TAG+":kymjs---http", new String(t));
						String result = new String(t).trim();
						try {

						

							JSONObject res = new JSONObject(result);
							// JSONObject res=JSONObject.
							JSONArray data = res.getJSONArray("data");
							

							Log.i("GH_TEXT", data.length() + "我是数据的size");
							if (data.length() == 0) {
								Toast.makeText(getApplicationContext(), "无信息",
										Toast.LENGTH_SHORT).show();
							} else {
							
								
//								  {
//							            "wharfid": 1,
//							            "wharfname": "乌镇码头",
//							            "wharfnum": "1154879",
//							            "operations": "36",
//							            "city": "嘉兴",
//							            "area": "桐乡"
//							        }
							  
								modellist.clear();
								for (int i = 0; i < data.length(); i++) {
									JSONObject temp = data.getJSONObject(i);
									DockModel model = new DockModel();

									model.setwharfname(temp.getString("wharfname"));
									model.setwharfnum(temp.getString("wharfnum"));
									
										
									modellist.add(model);

								}
								list_dock_name_area.setAdapter(dockAreaAdapyer);

							}

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {
						super.onFailure(errorNo, strMsg);
					}
				});
			}
		}

}
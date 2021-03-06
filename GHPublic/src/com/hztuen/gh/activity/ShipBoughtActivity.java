package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.gh.modol.ShipBoughtModel; 
import com.handmark.pulltorefresh.library.PullToRefreshBase; 
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.ShipBoughtAdapter; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
/**
 * 船货圈船舶出售
 * @author Administrator
 *
 */
public class ShipBoughtActivity extends AbActivity implements OnClickListener
{
	//private ListView lv_ship_bought;
	private RelativeLayout relative_title_final;
	private List<ShipBoughtModel> modellist = new ArrayList<ShipBoughtModel>();
	private ShipBoughtAdapter shipboughtAdapter;
	
	
	private PullToRefreshListView lv_ship_bought;
	private GridView mGridView;
	
	private int pagenumber=1;//当前页
	private int totolrows = 0;//数据总量
	private int pageSize = 10;//每页默认条数
	
	public PopupWindow popupWindowArea;
	private View contentView;
	private ImageButton image_add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_bought);
		AbDialogUtil.showProgressDialog(ShipBoughtActivity.this, 0,"加载中...");
		init();

	}

	private void init() {
		shipboughtAdapter = new ShipBoughtAdapter(getApplicationContext(),
				modellist);
		GetBoughtTask();
		lv_ship_bought = (PullToRefreshListView)findViewById(R.id.lv_ship_bought);
	//	mGridView = lv_ship_bought.getRefreshableView();

		
		image_add=(ImageButton)findViewById(R.id.image_add);
		image_add.setOnClickListener(this);
		
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		
		
		lv_ship_bought.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				modellist.clear();	
				pagenumber=1;
				GetBoughtTask();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pagenumber++;
				if((pagenumber-1)*pageSize >= totolrows){
					Toast.makeText(getApplicationContext(), "已加载完全部数据", Toast.LENGTH_SHORT).show();
					lv_ship_bought.postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	lv_ship_bought.onRefreshComplete();
			            }
			        }, 1000);
				}else {
					GetBoughtTask();
				}

			}

		});
			
	}

	// 查询船舶出售列表
	private void GetBoughtTask() 
	{ 
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("FromID="+"-1");
		 aa.add("ToID="+"-1"); 
		 aa.add("GoodsTypeid=" +"-1"); 
		aa.add("TradetypeID=" +4); 		
		aa.add("ShipTypeid=" +"-1");

		HttpParams params = null;
		try 
		{
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.baseUrl+"TradeListByTerms";
		if (!toUrl.equals("")) 
		{
			kjh.post(toUrl, params, false, new HttpCallBack() 
			{
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t)
				{
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try {
						
						lv_ship_bought.onRefreshComplete(); 
						JSONArray data = new JSONArray(result); 
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "最近无发布信息",
									Toast.LENGTH_SHORT).show();
						} else {
							for (int i = 0; i < data.length(); i++) {
								JSONObject temp = data.getJSONObject(i);
								ShipBoughtModel model = new ShipBoughtModel();

								model.setid(temp.getString("id"));
								model.settitle(temp.getString("titile"));
								JSONObject jsonObject=temp.getJSONObject("tradeShiptypeEN");
								model.setshiptype(jsonObject.getString("shiptype"));
								model.setshipname(temp.getString("shipname"));
								model.settons(temp.getString("load"));
								model.setrentprice(temp.getString("price"));
								model.setlinkman(temp.getString("linkman"));
								model.settel(temp.getString("tel"));
								model.setremark(temp.getString("remark"));
								model.setpostdate(temp.getString("postdate"));
								model.setshipage(temp.getString("age")); 
								modellist.add(model);

							}
							AbDialogUtil.removeDialog(ShipBoughtActivity.this);
							lv_ship_bought.setAdapter(shipboughtAdapter);
						}
						// newsAdapter.pre(newslist);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg)
				{ 
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_add:
			if("0".equals(SystemStatic.usertypeId))
			{
				Toast.makeText(this, "您尚未登录，请先登录", Toast.LENGTH_LONG).show();
				break;
			}
			contentView = getLayoutInflater().inflate(R.layout.pop_ship_circle_add, null);
			 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
			 TextView send_goods_msg = (TextView) contentView.findViewById(R.id.send_goods_msg);
			 TextView send_ship_msg = (TextView) contentView.findViewById(R.id.send_ship_msg);
			 TextView send_rent_msg = (TextView) contentView.findViewById(R.id.send_rent_msg);
			 TextView send_bought_msg = (TextView) contentView.findViewById(R.id.send_bought_msg);
			 TextView my_send_record = (TextView) contentView.findViewById(R.id.my_send_record);
			 
			 
			 TextView textView10=(TextView)contentView.findViewById(R.id.textView10);
			 textView10.setVisibility(View.GONE);
			 my_send_record.setVisibility(View.GONE);
			 pop_dis6.setOnClickListener(new itemClick());
			 send_goods_msg.setOnClickListener(new itemClick());
			 send_ship_msg.setOnClickListener(new itemClick());
			 send_rent_msg.setOnClickListener(new itemClick());
			 send_bought_msg.setOnClickListener(new itemClick());


				LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.father_ship_bought);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0, parent6.getHeight()*1/8);
			break;

		default:
			break;
		}
		
	}
	
	
	
	class itemClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//点击取消按钮
			case R.id.pop_dis:
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindowArea.dismiss();									
				break;
			//点击船舶出租信息
			case R.id.send_rent_msg:
				Intent intent_ship_rent_msg=new Intent(getApplicationContext(),SendRentMsgActivity.class);
				startActivity(intent_ship_rent_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布货源信息
			case R.id.send_goods_msg:
				Intent intent_good_res_msg=new Intent(getApplicationContext(),SendGoodsMsgActivity.class);
				startActivity(intent_good_res_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布船源信息
			case R.id.send_ship_msg:
				Intent intent_ship_res_msg=new Intent(getApplicationContext(),SendShipResMsgActivity.class);
				startActivity(intent_ship_res_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布船舶出售信息
			case R.id.send_bought_msg:
				Intent intent_ship_sell_msg=new Intent(getApplicationContext(),SendShipSellActivity.class);
				startActivity(intent_ship_sell_msg);
				popupWindowArea.dismiss();	
				break;

			default:
				break;
			}
			
		}
		
	}


}

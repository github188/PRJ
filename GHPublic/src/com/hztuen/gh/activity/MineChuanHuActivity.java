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
import android.content.Intent;
import android.os.Bundle;  
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ab.activity.AbActivity;  
import com.ab.util.AbDialogUtil;
import com.gh.modol.MineShipModel;
import com.gh.modol.MyShipItemStateModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.MineChuanHuAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

public class MineChuanHuActivity extends AbActivity implements OnClickListener 
{
	private RelativeLayout relative_title_final;
	private List<MineShipModel> modellist = new ArrayList<MineShipModel>();
	private List<MyShipItemStateModel> modelliststate = new ArrayList<MyShipItemStateModel>();
	private MineChuanHuAdapter myShipAdapter;

	private ListView myShipList;
	private Button btn_add;
	private ImageButton btn_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_chuanhu);
		AbDialogUtil.showProgressDialog(MineChuanHuActivity.this, 0, "加载中...");
		init();
	}

	private void init()
	{
		myShipList = (ListView) findViewById(R.id.my_ship_list);

		myShipAdapter = new MineChuanHuAdapter(getApplicationContext(),modellist);

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		btn_setting = (ImageButton) findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(this);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
		
		

	}
	@Override
	public void onResume()
	{
		super.onResume();
		GetMyShipListTask();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.relative_title_final:
			finish();
			break;
			// 点击添加按钮
		case R.id.btn_add:			
			Intent intent = new Intent(getApplicationContext(), RegistSecondPageActivity.class);
			intent.putExtra("username", SystemStatic.userName);
			intent.putExtra("mode", 1);
			startActivity(intent);
			break;

			// 点击设置按钮
		case R.id.btn_setting:
			Intent intent_setting = new Intent(getApplicationContext(),
					MineSettingActivity.class);
			startActivity(intent_setting);
			break;

		default:
			break;
		}

	}

	// 获取我的船舶列表
	private void GetMyShipListTask() {
		// TODO Auto-generated method stub
		// 访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>(); 
		aa.add("Username=" + SystemStatic.userName);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		kjh.post(contants.myshiplist, params, false, new HttpCallBack()
		{
			@Override
			public void onSuccess(Map<String, String> headers, byte[] t) {
				super.onSuccess(headers, t);
				modellist.clear();
				// 获取cookie
				KJLoger.debug("===" + headers.get("Set-Cookie")); 
				String result = new String(t).trim();
				try 
				{
					JSONObject res = new JSONObject(result);
					final JSONArray data = res.getJSONArray("data"); 
					if (data.length() == 0) {
						Toast.makeText(getApplicationContext(), "",
								Toast.LENGTH_SHORT).show();
					} else {
						for (int i = 0; i < data.length(); i++)
						{
							final JSONObject temp = data.getJSONObject(i);
							MineShipModel model = new MineShipModel();
							String idString=temp.getString("shipid");
							model.setshipid(temp.getString("shipid"));
							model.setshipname(temp.getString("shipname"));
							model.setregnumber(temp.getString("regnumber"));
							JSONObject stateObject=temp.getJSONObject("shipstatus");
							String status=stateObject.getString("id");
							model.status.statusnameString=status; 
							/*model.setfirstregnum(temp.getString("firstregnum"));
							model.setssrn(temp.getString("ssrn"));
							model.setenglishname(temp.getString("englishname"));
							model.sethomeport(temp.getString("homeport"));
							model.setshiptype(temp.getString("shiptype"));
							model.setmanufacturer(temp.getString("manufacturer"));
							model.setbuiltdate(temp.getString("builtdate"));
							model.setimo(temp.getString("imo"));*/
							modellist.add(model); 
							//	getItemStateTask(modellist.get(i).getshipname(),data.length());
						}

						for(int i=0;i<modellist.size();i++)
						{
							getItemStateTask(i,modellist.get(i),data.length());
						}

					}
				} catch (Exception e1) {
					AbDialogUtil.removeDialog(MineChuanHuActivity.this);
					e1.printStackTrace();
				}
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);
				AbDialogUtil.removeDialog(MineChuanHuActivity.this);
			}
		});
	}

	private void getItemStateTask(final int i,MineShipModel mMineShipModel,final int size) 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Shipname=" + mMineShipModel.getshipname());
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		kjh.post(contants.shipcheck, params, false, new HttpCallBack() {
			@Override
			public void onSuccess(Map<String, String> headers, byte[] t) {
				super.onSuccess(headers, t);
				// 获取cookie
				KJLoger.debug("===" + headers.get("Set-Cookie"));
				// Log.i(TAG+":kymjs---http", new String(t));
				String result = new String(t).trim();
				try {

					JSONObject res = new JSONObject(result);
					JSONObject ress = res.getJSONObject("result");
					JSONObject dataList = ress.getJSONObject("dataList"); 
					if (dataList.length() == 0) {
						Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT).show();
					} else {
						JSONArray json_jiaofei = dataList.getJSONArray("data4");
						String jiaofei = json_jiaofei.getString(1).toString();
						
						//String jiaofei_reason= json_jiaofei.getString(2).toString();

						JSONArray json_weizhang = dataList.getJSONArray("data0");
						String weizhang = json_weizhang.getString(1).toString();

						JSONArray json_zhengshu = dataList.getJSONArray("data2");
						String zhengshu = json_zhengshu.getString(1).toString();

						MyShipItemStateModel modestate = new MyShipItemStateModel();
						modestate.setjiaofei(jiaofei);
						modestate.setweizhang(weizhang);
						modestate.setzhengshu(zhengshu);
					//	modestate.setjiaofei_reason(jiaofei_reason);
						modelliststate.add(modestate);
//						System.out.println("船名是"+mMineShipModel.getshipname());
						modellist.get(i).setmMyShipItemStateModels(modestate);
					}
					if(modelliststate.size() == size)
					{
						myShipList.setAdapter(myShipAdapter);
					}
					AbDialogUtil.removeDialog(MineChuanHuActivity.this);
				} catch (Exception e1) {
					e1.printStackTrace();
					AbDialogUtil.removeDialog(MineChuanHuActivity.this);
				}
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);  
				MyShipItemStateModel modestate = new MyShipItemStateModel();
				modestate.setjiaofei("N/A");
				modestate.setweizhang("N/A");
				modestate.setzhengshu("N/A");
				modelliststate.add(modestate);
				if(modelliststate.size()==size)
				{
					myShipList.setAdapter(myShipAdapter);
				}
				AbDialogUtil.removeDialog(MineChuanHuActivity.this);
			}
		});
	}

}
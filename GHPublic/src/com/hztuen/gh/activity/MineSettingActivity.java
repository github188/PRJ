package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager; 
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener; 
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.Contact;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.CheckVersion;
import com.hztuen.lvyou.utils.DownloadFile;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;
import com.kince.saundprogressbar.widget.SaundProgressBar;

/*
 * 我的设置界面
 */
public class MineSettingActivity extends Activity implements OnClickListener
{
	private RelativeLayout relative_title_final;
	private RelativeLayout relative_phone, relative_passbor, relative_update,
			relative_about_us, relative_erwei;
	private TextView tv_user_name, tv_user_phone;
	private Button exit_login;
	private int my_Version=0;
	private CheckVersion my_CheckVersion;
	public PopupWindow popupWindowArea,popupWindowProgress;
	private Button btn_confirm;
	private View contentView;
	private ProgressBar myProgress;
	public static MineSettingActivity instance;
	private int FileLength;
	private int DownedFileLength=0;
	private SaundProgressBar mPbar;

	private DownloadFile my_down;
	public static final int UPDATE_HAS_NO_VERSION = 0;
	public static final int UPDATE_HAS_NEW_VERSION = 1;
	public static final int UPDATE_DOWN_VERSION = 2;
	public static final int UPDATE_CREATE_PROGRESS = 3;
	public static final int UPDATE_PROGRESS = 4;

	private String visitormarker = "";
	
	private RelativeLayout relative_hiden;
	 public  Handler handler=new Handler()
	    {
	    	 public void handleMessage(Message msg)
	 	    {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				
				
				//下载进度 
				case 4:
					mPbar.setProgress(msg.arg1);
				
					break;
					
				//下载完成	
				case 2:
					Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_LONG).show();
					
				
					popupWindowProgress.dismiss();
					my_down=new DownloadFile();
					my_down.installAPK(Environment.getExternalStorageDirectory() +"/TT.apk",getApplicationContext());
					break;
				
				//文件长度	
				case 3:
					FileLength=msg.arg1;
					mPbar.setMax(FileLength);
					
					break;
					
				default:
					break;
				}
			}	
			}
	    	 
	    };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_setings);
		init();

	}

	private void init()
	{
		tv_user_name = (TextView) findViewById(R.id.text1_context);
		tv_user_name.setText(SystemStatic.userName);
		tv_user_phone = (TextView) findViewById(R.id.text2_context);
		tv_user_phone.setText(SystemStatic.phoneNum);
		instance=this;
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);

		relative_phone = (RelativeLayout) findViewById(R.id.relative2);
		relative_phone.setOnClickListener(this);

		relative_passbor = (RelativeLayout) findViewById(R.id.relative3);
		relative_passbor.setOnClickListener(this);

		relative_update = (RelativeLayout) findViewById(R.id.relative5);
		relative_update.setOnClickListener(this);

		relative_about_us = (RelativeLayout) findViewById(R.id.relative6);
		relative_about_us.setOnClickListener(this);

		relative_erwei = (RelativeLayout) findViewById(R.id.relative15);
		relative_erwei.setOnClickListener(this);

		exit_login = (Button) findViewById(R.id.exit_login);
		exit_login.setOnClickListener(this);
		
		my_Version=getVersion(getApplicationContext());
		myProgress=(ProgressBar)findViewById(R.id.my_progress);

		relative_hiden = (RelativeLayout) findViewById(R.id.relative_hiden);

		visitormarker = getIntent().getStringExtra("visitormarker");
		
		/*游客部分功能隐藏*/
		if(null != visitormarker && visitormarker.equals("visitormarker")){
			relative_hiden.setVisibility(View.GONE);
			exit_login.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击返回
		case R.id.relative_title_final:
			finish();
			break;
		// 点击手机号
		case R.id.relative2:
			Intent intent_change_phone = new Intent(getApplicationContext(), ChangePhoneNumActivity.class);
			startActivity(intent_change_phone);
			break;
		// 点击修改密码
		case R.id.relative3:
			Intent intent_change_pass = new Intent(getApplicationContext(),ChangePassWordActivity.class);
			startActivity(intent_change_pass);
			break;
		// 点击检查更新
		case R.id.relative5:
			VersionCheckTask();
			break;
		// 点击关于我们
		case R.id.relative6:
			Intent intent_about_us = new Intent(getApplicationContext(),AboutUsActivity.class);
			startActivity(intent_about_us);
			break;
		// 点击二维码
		case R.id.relative15:
			Intent intent_qr = new Intent(getApplicationContext(),QRcodeActivity.class);
			startActivity(intent_qr);
			break;

		// 点击退出登录按钮
		case R.id.exit_login:
			SharedPreferences mShare = getSharedPreferences(Contact.user_ache, 0);
			Editor edit = mShare.edit();
			edit.putBoolean("flag", true);
			edit.commit();
			
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static int getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			int version = info.versionCode;
			return  version;
		} catch (Exception e) {
			e.printStackTrace();
			//return context.getString(R.string.can_not_find_version_name);
			return 0;
		}
	}

	
	
	
	//版本号检测
		private void VersionCheckTask() 
		{
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("VersionNum=" +String.valueOf(my_Version));
			aa.add("type=" +"1");//安卓

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.versioncheck;
			if (!toUrl.equals("")) {
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
						KJLoger.debug("===" + headers.get("Set-Cookie"));
						 
						String result = new String(t).trim();
						try {

							JSONObject res = new JSONObject(result);
							String resultcode=res.getString("resultcode");
							String resultdesc=res.getString("resultdesc");
							
							
							
							
							
							if("0".equals(resultcode))
							{
								Toast.makeText(getApplicationContext(), resultdesc, Toast.LENGTH_SHORT).show();
							
							}else if("1".equals(resultcode))	
							{
								
								JSONObject obj=res.getJSONObject("obj");
								String apkurl=contants.baseUrl+obj.getString("address");
								String update_content=obj.getString("log");
								String versionNumnew=obj.getString("versionNum");
								Toast.makeText(getApplicationContext(), "有新版本", Toast.LENGTH_SHORT).show();
								
								if(my_Version<Integer.valueOf(versionNumnew))
								{
									showPopWindow(apkurl,update_content);
								}
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
		
		private void showPopWindow(final String url,String update_content)
		{
			 contentView = getLayoutInflater().inflate(R.layout.pop_banben, null);
			 Button pop_dis7 = (Button) contentView.findViewById(R.id.btn_confirm);
			 Button btn_next=(Button)contentView.findViewById(R.id.btn_next);
			 
			 TextView tv_update_content=(TextView)contentView.findViewById(R.id.update_content);
			 
			 if(update_content==null||"".equals(update_content)){
				 tv_update_content.setText("更新版本内容");
			 }else{
				 tv_update_content.setText(update_content);
			 }
			 
			 btn_next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popupWindowArea.dismiss();
					
				}
			});
			
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						
						my_CheckVersion=new CheckVersion(url,Environment.getExternalStorageDirectory() + "/TT.apk");
						my_CheckVersion.run();

						popupWindowArea.dismiss();
						showProgressWindow(my_CheckVersion);
						//myProgress.setVisibility(View.VISIBLE);
					
					}
				});

				LinearLayout parent7 = (LinearLayout) this.findViewById(R.id.father_mine);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent7.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				popupWindowArea.setBackgroundDrawable(new PaintDrawable());
				
				
				 
				
				popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				WindowManager.LayoutParams lp7 = getWindow().getAttributes();
				lp7.alpha = 0.5f;
				getWindow().setAttributes(lp7);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent7, Gravity.CENTER, 0, 0);
			
		}

		
		
		private void showProgressWindow(final CheckVersion check)
		{
			 contentView = getLayoutInflater().inflate(R.layout.pop_progress, null);
			
			 TextView check_cancel=(TextView)contentView.findViewById(R.id.check_cancel);
			 check_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popupWindowProgress.dismiss();
					
				}
			});
			
			 
			 mPbar = (SaundProgressBar) contentView.findViewById(R.id.regularprogressbar);
				
				
				Drawable indicator = getResources().getDrawable(
						R.drawable.progress_indicator);
				Rect bounds = new Rect(0, 0, indicator.getIntrinsicWidth() + 5,
						indicator.getIntrinsicHeight());
				indicator.setBounds(bounds);

				mPbar.setProgressIndicator(indicator);
				mPbar.setProgress(0);
				mPbar.setVisibility(View.VISIBLE);

				LinearLayout parent7 = (LinearLayout) this.findViewById(R.id.father_mine);//父窗口view  
				popupWindowProgress = new PopupWindow(contentView, parent7.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowProgress.setFocusable(true);
				popupWindowProgress.setOutsideTouchable(true);
				popupWindowProgress.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				popupWindowProgress.setBackgroundDrawable(new PaintDrawable());
				WindowManager.LayoutParams lp7 = getWindow().getAttributes();
				lp7.alpha = 0.5f;
				getWindow().setAttributes(lp7);
				
				popupWindowProgress.setFocusable(true); // 设置PopupWindow可获得焦点
				popupWindowProgress.setTouchable(true); // 设置PopupWindow可触摸
				
				
				 
				popupWindowProgress.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						
						check.dissThread();
						
					}
				});
				
				popupWindowProgress.showAtLocation(parent7, Gravity.CENTER, 0,0);
			
		}
		
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

		
}
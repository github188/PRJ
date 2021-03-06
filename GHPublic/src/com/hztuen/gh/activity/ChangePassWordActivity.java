package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ChangePassWordActivity extends Activity implements OnClickListener {

	private RelativeLayout relative_title_final;
	private EditText ed_old_password, ed_new_password;
	private Button btn_confirm;
	private ImageView regist_eye, regist_eye2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_passbor);
		init();
	}

	private void init() {
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);

		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);

		regist_eye = (ImageView) findViewById(R.id.regist_eye);
		regist_eye.setOnTouchListener(ontouch);

		regist_eye2 = (ImageView) findViewById(R.id.regist_eye2);
		regist_eye2.setOnTouchListener(ontouch);

		ed_old_password = (EditText) findViewById(R.id.ed_old_password);
		ed_new_password = (EditText) findViewById(R.id.ed_new_password);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击返回按钮
		case R.id.relative_title_final:
			finish();
			break;

		// 点击确定按钮
		case R.id.btn_confirm:
			ConfirmChange();
			break;

		

		default:
			break;
		}

	}
	
	
	public OnTouchListener ontouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub


			switch (v.getId()) {
			case R.id.regist_eye:
				if (event.getAction() == MotionEvent.ACTION_DOWN)  
				{   
					regist_eye.setImageResource(R.drawable.icon_eye_open);
					ed_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

					return true;  
				}else if (event.getAction() == MotionEvent.ACTION_UP){  
					ed_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
					regist_eye.setImageResource(R.drawable.icon_eye_close);
					return true;  
				}
				break;
				
			case R.id.regist_eye2:
				if (event.getAction() == MotionEvent.ACTION_DOWN)  
				{   
					regist_eye2.setImageResource(R.drawable.icon_eye_open);
					ed_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

					return true;  
				}else if (event.getAction() == MotionEvent.ACTION_UP){  
					ed_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
					regist_eye2.setImageResource(R.drawable.icon_eye_close);
					return true;  
				}
				break;
			default:
				break;
			}
			return false;
		}
	};
	
	
	
	
	public void ConfirmChange(){
		
		String passold = ed_old_password.getText().toString();
		String passnew = ed_new_password.getText().toString();
		
		
		if(passold.equals("")){
			Toast.makeText(getApplicationContext(), "请输原密码！", Toast.LENGTH_SHORT).show();
			return;
		}
		if(passnew.equals("")){
			Toast.makeText(getApplicationContext(), "请输入新密码！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		
		//访问网络
				KJHttp kj = new KJHttp();
				List<String> aa = new ArrayList<String>();

				aa.add("username="+SystemStatic.userName);
				aa.add("pswold="+passold);
				aa.add("pswnew="+passnew);
				//访问地址
				org.kymjs.kjframe.http.HttpParams params = null;
				try {
					params = Util.prepareKJparams(aa);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//访问地址
				String toUrl = contants.changepsw;
				if(params == null){
					//提示参数制造失败
					Util.getTip(getApplicationContext(), "构造参数失败");
				}else if(!toUrl.equals("")){
					kj.post(toUrl, params, false, new HttpCallBack(){

						@Override
						public void onSuccess(Map<String, String> headers, byte[] t) {
							// TODO Auto-generated method stub
							super.onSuccess(headers, t);
							
							// 获取cookie
							KJLoger.debug("===" + headers.get("Set-Cookie"));
						
							String result = new String(t).trim();
							
							try{
								JSONObject res = new JSONObject(result);
								int resultcode = res.getInt("resultcode");
								if(resultcode==1){
									Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
									
									finish();
								}else if(resultcode==0){
									Toast.makeText(getApplicationContext(), "原密码错误", Toast.LENGTH_SHORT).show();
								}
							}catch(Exception e){
								//e.printStackTrace();
								Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
							}
						}
						
						
						
					});
				}//else这里结束

		
		
	}

}

package com.example.smarttraffic.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarttraffic.voice.VoiceDialog;
import com.example.smarttraffic.voice.VoiceJsonParser;
import com.example.smarttraffic.voice.VoiceManager;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;

/**
 * 语音输入监听函数
 * @author Administrator zhou
 *
 */
public class VoiceEditTextListener
{
	private EditText editText;
	private Context context;
	
	public VoiceEditTextListener(Context context,EditText editText)
	{
		this.editText=editText;
		this.context=context;
	}
	
	Dialog vd;
	VoiceManager voiceManager;
	
	public void inputListener()
	{
		VoiceDialog voiceDialog=new VoiceDialog(context);
		vd= voiceDialog.getVoiceDialog(cancel);
		vd.show();
		editText.setText("");
		voiceManager=new VoiceManager(context);
		voiceManager.start(mRecoListener);
		
	}
	
	public OnClickListener cancel=new OnClickListener()
	{	
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if(vd.isShowing())
				vd.cancel();
			voiceManager.cancel();
		}
	};
	
	private RecognizerListener mRecoListener = new RecognizerListener()
	{
		@Override
		public void onBeginOfSpeech() 
		{			
		}

		@Override
		public void onEndOfSpeech() 
		{			
		}

		@Override
		public void onError(SpeechError arg0) 
		{	
			if(vd.isShowing())
				vd.cancel();
			Toast.makeText(context, "语音输入失败", Toast.LENGTH_LONG).show();			
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, String arg3) 
		{		
		}

		@Override
		public void onResult(RecognizerResult result, boolean isLast) 
		{
			editText.setText(editText.getText()+VoiceJsonParser.parseIatResult(result.getResultString()));
		
			if(vd.isShowing())
				vd.cancel();
		}

		@Override
		public void onVolumeChanged(int arg0) 
		{

		}
	};
}
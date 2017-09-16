package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

/**
 * 自定义驾校输入控件
 * @author Administrator zhou
 *
 */
public class DrivingInputviewListener
{
	String[] dataStrings;
	int[] dataImages;
	InputListViewListener[] listeners;
	
	InputListView inputListView;
	
	public DrivingInputviewListener(InputListView inputListView)
	{
		this.inputListView=inputListView;
		dataStrings=new String[]{"我的位置","语音输入","收藏驾校"};
		dataImages=new int[]{R.drawable.input_mylocation_icon,R.drawable.input_speech_icon,R.drawable.input_favorites_icon};
		
	}
	
	public void setListener(InputListViewListener[] listeners)
	{
		this.listeners=listeners;
		
		inputListView.updateView(dataStrings, dataImages, listeners);
	}
}

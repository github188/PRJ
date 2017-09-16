package com.example.smarttraffic.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownService extends Service
{

	private final int period=1000*60;
	
	private Timer timer;
	
	DownBackgroud downBackgroud;
	
	private TimerTask  task= new TimerTask()
	{
		@Override
		public void run()
		{
			try
			{
				FavorDBOperate dbOperate=new FavorDBOperate(getApplicationContext());
				
				System.out.println("down service create");
				
				List<AlarmInfo> list=dbOperate.selectForAlarmInfo(ContentType.SMART_BUS_ALARM_DOWN_FAVOR);
				
				List<AlarmInfo> info=new ArrayList<AlarmInfo>();
				
				for(AlarmInfo a:list)
				{
					if(a.isOpen())
						info.add(a);
				}
				
				if(info.size()==0)
				{
					this.cancel();
					DownService.this.stopSelf();
				}
				
				downBackgroud.setData(info);
				
				dbOperate.CloseDB();
				
				System.out.println("down service timer");
				downBackgroud.controlAll();
			}
			catch(Exception e)
			{
			}
		}
		
	};
	
	@Override
	public void onCreate()
	{
		super.onCreate();
				
		downBackgroud=new DownBackgroud(new ArrayList<AlarmInfo>(), getApplicationContext());
		
		timer=new Timer();
		timer.schedule(task, 5000, period);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public void onDestroy()
	{
		System.out.println("down service destroy");
		super.onDestroy();
		timer.cancel();
	}

}

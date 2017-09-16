package com.huzhouport.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpInterface;
import com.huzhouport.common.util.HttpUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateAppManager
{
	private static final String TAG = "UpdateAppManager";
	// �ļ��ָ���
	private static final String FILE_SEPARATOR = "/";
	// ���sdcard���·��
	private static final String FILE_PATH = Environment
			.getExternalStorageDirectory()
			+ FILE_SEPARATOR
			+ "autoupdate"
			+ FILE_SEPARATOR;
	// ����Ӧ�ô��ȫ·��
	private static final String FILE_NAME = FILE_PATH + "huzhouupdate.apk";
	// ����Ӧ�ð汾���
	private static final int UPDARE_TOKEN = 0x29;
	// ׼����װ�°汾Ӧ�ñ��
	private static final int INSTALL_TOKEN = 0x31;

	private Context context;
	private String message = "��⵽���������°汾���������������£�";
	// �Ի�Ϊ������hotalk.apkΪ��
	private String spec = HttpUtil.BASE_URL + "HuZhouMobi.apk";
	// ����Ӧ�õĶԻ���
	private Dialog dialog;
	// ����Ӧ�õĽ�����
	private ProgressBar progressBar;
	// �������ĵ�ǰ�̶�ֵ
	private int curProgress;
	// �û��Ƿ�ȡ������
	private boolean isCancel;

	public UpdateAppManager(Context context)
	{
		this.context = context;
	}

	public int getVerCode()
	{
		int verCode = -1;
		try
		{
			verCode = context.getPackageManager().getPackageInfo(
					"com.example.huzhouport", 0).versionCode;
		} catch (NameNotFoundException e)
		{
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}

	public String getVerName()
	{
		String verName = "";
		try
		{
			verName = context.getPackageManager().getPackageInfo(
					"com.example.huzhouport", 0).versionName;
		} catch (NameNotFoundException e)
		{
			Log.e(TAG, e.getMessage());
		}
		return verName;

	}

	public int GetNetVersion()
	{
		HttpInterface oHttpInterface;
		oHttpInterface = new HttpInterface(HttpUtil.BASE_URL + "ver.json");
		return oHttpInterface.GetIntValue("verCode");
	}

	private final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case UPDARE_TOKEN:
				progressBar.setProgress(curProgress);
				break;

			case INSTALL_TOKEN:
				installApp();
				break;
			}
		}
	};

	/**
	 * ���Ӧ�ø�����Ϣ
	 */
	public void checkUpdateInfo()
	{
		showNoticeDialog();
	}

	/**
	 * ��ʾ��ʾ���¶Ի���
	 */
	private void showNoticeDialog()
	{
		new AlertDialog.Builder(context).setTitle("�����汾����").setMessage(message)
				.setPositiveButton("����", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// ���������ַ
						Editor oEditor = context.getSharedPreferences("IpData",
								0).edit();
						oEditor.clear();
						oEditor.commit();

						dialog.dismiss();
						showDownloadDialog();
					}
				}).setNegativeButton("�Ժ���˵", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				}).create().show();
	}

	/**
	 * ��ʾ���ؽ��ȶԻ���
	 */
	private void showDownloadDialog()
	{
		View view = LayoutInflater.from(context).inflate(R.layout.progress_bar,
				null);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("�����汾����");
		builder.setView(view);
		builder.setNegativeButton("ȡ��", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				isCancel = true;
			}
		});
		dialog = builder.create();
		dialog.show();
		downloadApp();
	}

	/**
	 * �����°汾Ӧ��
	 */
	private void downloadApp()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				URL url = null;
				InputStream in = null;
				FileOutputStream out = null;
				HttpURLConnection conn = null;
				try
				{
					url = new URL(spec);
					conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					long fileLength = conn.getContentLength();
					in = conn.getInputStream();
					File filePath = new File(FILE_PATH);
					if (!filePath.exists())
					{
						filePath.mkdir();
					}
					out = new FileOutputStream(new File(FILE_NAME));
					byte[] buffer = new byte[1024];
					int len = 0;
					long readedLength = 0l;
					while ((len = in.read(buffer)) != -1)
					{
						// �û������ȡ������ť�������ж�
						if (isCancel)
						{
							break;
						}
						out.write(buffer, 0, len);
						readedLength += len;
						curProgress = (int) (((float) readedLength / fileLength) * 100);
						handler.sendEmptyMessage(UPDARE_TOKEN);
						if (readedLength >= fileLength)
						{
							dialog.dismiss();
							// ������ϣ�֪ͨ��װ
							handler.sendEmptyMessage(INSTALL_TOKEN);
							break;
						}
					}
					out.flush();
				} catch (Exception e)
				{
					e.printStackTrace();
				} finally
				{
					if (out != null)
					{
						try
						{
							out.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					if (in != null)
					{
						try
						{
							in.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					if (conn != null)
					{
						conn.disconnect();
					}
				}
			}
		}).start();
	}

	/**
	 * ��װ�°汾Ӧ��
	 */
	private void installApp()
	{
		File appFile = new File(FILE_NAME);
		if (!appFile.exists())
		{
			return;
		}
		// ��ת���°汾Ӧ�ð�װҳ��
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + appFile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
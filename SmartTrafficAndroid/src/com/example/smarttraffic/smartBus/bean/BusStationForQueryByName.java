package com.example.smarttraffic.smartBus.bean;

import java.util.List;

public class BusStationForQueryByName 
{
	private int historyID;
	private int Id;
	private String Name;
	private String Rename;
	private double Longitude;
	private double Latitude;
	private int Distance;
	private String QrCode;
	private String StationType;
	
	private List<LineOnStation> List;

	public int getHistoryID()
	{
		return historyID;
	}

	public void setHistoryID(int historyID)
	{
		this.historyID = historyID;
	}

	public int getId()
	{
		return Id;
	}

	public void setId(int id)
	{
		Id = id;
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public String getRename()
	{
		return Rename;
	}

	public void setRename(String rename)
	{
		Rename = rename;
	}

	public double getLongitude()
	{
		return Longitude;
	}

	public void setLongitude(double longitude)
	{
		Longitude = longitude;
	}

	public double getLatitude()
	{
		return Latitude;
	}

	public void setLatitude(double latitude)
	{
		Latitude = latitude;
	}

	public int getDistance()
	{
		return Distance;
	}

	public void setDistance(int distance)
	{
		Distance = distance;
	}

	public String getQrCode()
	{
		return QrCode;
	}

	public void setQrCode(String qrCode)
	{
		QrCode = qrCode;
	}

	public String getStationType()
	{
		return StationType;
	}

	public void setStationType(String stationType)
	{
		StationType = stationType;
	}

	public List<LineOnStation> getList()
	{
		return List;
	}

	public void setList(List<LineOnStation> list)
	{
		List = list;
	}

	
}

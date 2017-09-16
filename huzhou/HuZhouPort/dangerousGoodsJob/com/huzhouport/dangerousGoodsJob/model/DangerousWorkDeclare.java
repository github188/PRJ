package com.huzhouport.dangerousGoodsJob.model;

public class DangerousWorkDeclare implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int declareID;
	private String shipName;
	private String declareTime;
	private int startingPort;
	private int arrivalPort;
	private String cargoTypes;
	private String dangerousLevel;
	private String wharfID;
	private String workTIme;
	private int carrying;
	private int reviewUser;
	private int reviewResult;
	private String reviewOpinion;
	private String reviewTime;
	public int getDeclareID() {
		return declareID;
	}
	public void setDeclareID(int declareID) {
		this.declareID = declareID;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getDeclareTime() {
		return declareTime;
	}
	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}
	public int getStartingPort() {
		return startingPort;
	}
	public void setStartingPort(int startingPort) {
		this.startingPort = startingPort;
	}
	public int getArrivalPort() {
		return arrivalPort;
	}
	public void setArrivalPort(int arrivalPort) {
		this.arrivalPort = arrivalPort;
	}
	public String getCargoTypes() {
		return cargoTypes;
	}
	public void setCargoTypes(String cargoTypes) {
		this.cargoTypes = cargoTypes;
	}
	public String getDangerousLevel() {
		return dangerousLevel;
	}
	public void setDangerousLevel(String dangerousLevel) {
		this.dangerousLevel = dangerousLevel;
	}


	public String getWorkTIme() {
		return workTIme;
	}
	public void setWorkTIme(String workTIme) {
		this.workTIme = workTIme;
	}
	public int getCarrying() {
		return carrying;
	}
	public void setCarrying(int carrying) {
		this.carrying = carrying;
	}
	public int getReviewUser() {
		return reviewUser;
	}
	public void setReviewUser(int reviewUser) {
		this.reviewUser = reviewUser;
	}
	public int getReviewResult() {
		return reviewResult;
	}
	public void setReviewResult(int reviewResult) {
		this.reviewResult = reviewResult;
	}
	public String getReviewOpinion() {
		return reviewOpinion;
	}
	public void setReviewOpinion(String reviewOpinion) {
		this.reviewOpinion = reviewOpinion;
	}
	public String getWharfID() {
		return wharfID;
	}
	public void setWharfID(String wharfID) {
		this.wharfID = wharfID;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	
}
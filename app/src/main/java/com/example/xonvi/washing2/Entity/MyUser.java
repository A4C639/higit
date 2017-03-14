package com.example.xonvi.washing2.Entity;

//用户的个人信息
public class MyUser {
	
	private int id;
	private String username;
	private String userpass;
	private int balance;
	private String portrait;
	private int score;

	
	public MyUser() {
	
	}
	
	
	
	
	
	public MyUser(int id, String username, String userpass, int balance,
			String portrait,int score) {
		this.id = id;
		this.username = username;
		this.userpass = userpass;
		this.balance = balance;
		this.portrait = portrait;
		this.score =score;
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}


	@Override
	public String toString() {
		return "MyUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				", userpass='" + userpass + '\'' +
				", balance=" + balance +
				", portrait='" + portrait + '\'' +
				'}';
	}
}

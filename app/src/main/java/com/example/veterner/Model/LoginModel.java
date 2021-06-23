package com.example.veterner.Model;

public class LoginModel{
	private boolean tf;
	private String mailadres;
	private String parola;
	private String id;
	private String text;
	private String username;

	public boolean isTf(){
		return tf;
	}



	public String getMailadres(){
		return mailadres;
	}

	public String getParola(){
		return parola;
	}

	public String getId(){
		return id;
	}

	public void setTf(boolean tf) {
		this.tf = tf;
	}

	public void setMailadres(String mailadres) {
		this.mailadres = mailadres;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText(){
		return text;
	}

	public String getUsername(){
		return username;
	}
}

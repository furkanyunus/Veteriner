package com.example.veterner.Model;

public class RegisterPojo{
	private boolean tf;
	private String text;

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public void setText(String text){
		this.text = text;
	}


	public boolean isTf(){
		return tf;
	}

	public String getText(){
		return text;
	}


	@Override
	public String toString(){
		return
				"RegisterPojo{" +
						"tf = '" + tf + '\'' +
						",text = '" + text + '\'' +
						"}";
	}

}

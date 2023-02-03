
package com.fy.demo.helpers;

public class LDAPPerson {
	private String name;
	private String address;
	private String password;
	
	public LDAPPerson(){}
	
	public LDAPPerson(String name, String password, String address){
		this.name = name;
		this.password = password;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

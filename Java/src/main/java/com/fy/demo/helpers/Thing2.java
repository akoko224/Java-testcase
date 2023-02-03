
package com.fy.demo.helpers;

public class Thing2 implements ThingInterface {

	@Override
	public String doSomething(String i) {
		if (i == null) return "";
		String r = new StringBuilder(i).toString();
		return r;
	}
}

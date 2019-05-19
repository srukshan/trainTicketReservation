package com.smartimpulse.trainapi.service;

import java.util.Arrays;
import java.util.List;

public class DialogPaymentService {
	private final List<String> DB = Arrays.asList(new String[] {
			"076623960,1234",
			"072847283,5578",
			"073587375,9012",
			"072461824,3456"
	});
	public boolean doPayment(String telNo, int pin, String price) {
		if(DB.contains(String.join(",",telNo,Integer.toString(pin)))) {
			return true;
		}else {
			return false;
		}
	}
}

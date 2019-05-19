package com.smartimpulse.trainapi.service;

import java.util.Arrays;
import java.util.List;

public class AuthorizenetPaymentService {
	private final List<String> DB = Arrays.asList(new String[] {
			"Sachith Rukshan,1234 5678 9012 3456,354,12/05",
			"Sasindu Lakshitha,1934 5578 9212 3456,384,02/05",
			"Dinali Sewwandi,1234 5678 9012 3456,354,11/05",
			"Gnana Paala,1234 5678 9012 3456,354,12/10"
	});
	
	public boolean doPayment(String cName, String cardNo, String cvc, String exp, double price) {
		if(DB.contains(String.join(",",
				cName, 
				cardNo,
				cvc,
				exp))) {
			return true;
		}else {
			return false;
		}
	}
}

package com.smartimpulse.trainapi.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MessagingController {
	
	public String sendSms(@RequestBody MessageFormat msg) {
		try {
			// Construct data
			String apiKey = "apikey=" + "N4s38uNa1/0-p0j0AD1AHiAAge0fnMY7eZqj6V7QiQ";
			String message = "&message=" + msg.getMessage();
			String sender = "&sender=" + msg.getSender();
			String numbers = "&numbers=" + msg.getNumber();
	
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
	
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
}
class MessageFormat{
	private String message;
	private String sender;
	private String number;
	/**
	 * @param message
	 * @param sender
	 * @param number
	 */
	public MessageFormat(String message, String sender, String number) {
		super();
		this.message = message;
		this.sender = sender;
		this.number = number;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}

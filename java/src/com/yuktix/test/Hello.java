package com.yuktix.test;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class Hello {
	public static void main(String[] args)  throws Exception{
		System.out.println("Hello world!");
		ResourceBundle bundle = ResourceBundle.getBundle("sensordb",Locale.US);
		String accountName = bundle.getString("azure.account.name") ;
		String accountKey = bundle.getString("azure.account.key") ;
		System.out.println("account name = [" + accountName + "]");
		System.out.println("account key = [" + accountKey + "]");
		
		Date d_max = new Date(Long.MAX_VALUE) ;
		System.out.println(" Date.Max = " + d_max.getTime());
		for(int i =0 ; i< 10 ;i ++) {
			Thread.sleep(1);
			Date d_now = new Date();
			long diff = d_max.getTime()-d_now.getTime() ;
			//String rowKey = StringUtils.leftPad(diff, 10, "0");
			String rowKey = String.format("%019d", diff);
			System.out.println(rowKey);
		}
		
		String rowKey = String.format("%019d", 124);
		System.out.println(rowKey);
		
		
	}
}


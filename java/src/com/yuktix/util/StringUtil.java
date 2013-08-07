package com.yuktix.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.yuktix.rest.exception.ArgumentException;

public class StringUtil {
	
	// splitter from guava library
	private static final Splitter SPACE_SPLITTER = Splitter.on(' ')
		       .trimResults()
		       .omitEmptyStrings();
	
	public static String getCanonicalName(String input) {
		
		Iterator<String> out = SPACE_SPLITTER.split(input).iterator();
		List<String> tokens = new ArrayList<String>();
		
		while(out.hasNext()) {
			String token = out.next().toLowerCase() ;
			tokens.add(token) ;
		}
		
		Joiner joiner = Joiner.on(";").skipNulls();
		String name = joiner.join(tokens) ;
		return name ;
	}
	
	public static void null_check(String name, String value) {
		if(StringUtils.isEmpty(value)) {
			throw new ArgumentException(name,"value is null") ;
		}
	}
}

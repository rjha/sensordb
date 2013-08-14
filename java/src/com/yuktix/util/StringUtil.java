package com.yuktix.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.yuktix.rest.exception.ArgumentException;

public class StringUtil {
	
	// splitter/joiner from guava library
	public static final Splitter SPACE_SPLITTER = Splitter.on(' ')
		       .trimResults()
		       .omitEmptyStrings();
	
	public static final Splitter COMMA_SPLITTER = Splitter.on(',')
		       .trimResults()
		       .omitEmptyStrings();
	
	public static final Splitter SEMI_COLON_SPLITTER = Splitter.on(';')
		       .trimResults()
		       .omitEmptyStrings();
	
	public static final Joiner SEMI_COLON_JOINER = Joiner.on(";").skipNulls();
	public static final Joiner COMMA_JOINER = Joiner.on(",").skipNulls();
	
	public static String getCanonicalName(String input) {
		
		Iterator<String> out = SPACE_SPLITTER.split(input).iterator();
		List<String> tokens = new ArrayList<String>();
		
		while(out.hasNext()) {
			String token = out.next().toLowerCase() ;
			tokens.add(token) ;
		}
		
		String name = SEMI_COLON_JOINER.join(tokens) ;
		return name ;
	}
	
	public static void null_check(String name, String value) {
		if(StringUtils.isEmpty(value)) {
			String message = String.format("%s is null", name);
			throw new ArgumentException(message) ;
		}
	}
	
}

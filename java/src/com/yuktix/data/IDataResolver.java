package com.yuktix.data;

import java.util.HashMap;

public interface IDataResolver {
	
	HashMap<String,Object> resolve(HashMap<String,Object> map) throws Exception;
}

/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taobao.teaey.proxy;


/**
 * StringUtils
 * 
 * @author qian.lei
 */

public final class StringUtils {

	/**
	 * join string.
	 * 
	 * @param array String array.
	 * @return String.
	 */
	public static String join(String[] array)
	{
		if( array.length == 0 ) return "";
		StringBuilder sb = new StringBuilder();
		for( String s : array )
			sb.append(s);
		return sb.toString();
	}

	

	private StringUtils(){}
}
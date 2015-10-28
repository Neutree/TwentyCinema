package com.function;

import java.util.StringTokenizer;

public class GetTokens {
	public static String[] getTokens(String input){
	    int i = 0;
	    StringTokenizer st = new StringTokenizer(input);
	    int numTokens = st.countTokens();
	    String[] tokenList = new String[numTokens];
	    while (st.hasMoreTokens()){
	        tokenList[i] = st.nextToken();
	        i++;
	    }
	    return(tokenList);
	  }
}

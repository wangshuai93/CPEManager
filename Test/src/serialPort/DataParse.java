package serialPort;

import java.util.regex.Matcher;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class DataParse {

	public static String getValidData(String data) {
		int start = data.indexOf("~");
		int end = data.indexOf("^");
		if (start != -1 && end != -1) {
			String validData = data.substring(data.indexOf("~") + 1,
					data.lastIndexOf("^"));
			System.out.println("getvalidData:  " + validData);
			return validData;
			
		} else {
			return null;
		}

	}
	
	public static String getEthValidData(String eth,String data){
		
		int start = data.indexOf(eth);
		int end = data.indexOf("^");
		for (int i = start ; i < data.length(); i++) {
			if(data.charAt(i) == '^'){
			   end = i;
			   break;
			}
			
		}
		
       System.out.println(eth+"****start:"+start+"  end"+end +data);
		
		if (start != -1 && end != -1 && start < end) {
			
			String validData = data.substring(start+eth.length()+1,end);
			System.out.println(eth+"****start:"+start+"  end"+end+ "validData="+validData);
			return validData;
		} 
		
//		if(start != -1 && end != -1 && start > end){
//			end = data.lastIndexOf("^");
//			String validData = data.substring(start+eth.length()+1,end);
//			System.out.println(eth+"ws****start:"+start+"  end"+end+ "validData="+validData);
//			return validData;
//		}
		return null;
	}
	public static String GBK2Unicode(String str){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr1 = (char)str.charAt(i);
			if (!isNeedConvert(chr1)) {
				result.append(chr1);
				continue;
				
			}
			result.append("\\u"+Integer.toHexString((int)chr1));
			
		}
		
		return result.toString();
		
	}
	public static boolean isNeedConvert(char para){
		return ((para&(0x00FF))!=para);
		
	}

	public static boolean isChineseChar(String str){
		boolean temp = false;
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
			
		}
		return temp;
	}

}

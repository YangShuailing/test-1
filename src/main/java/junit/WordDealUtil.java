package junit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDealUtil {
	
	/*
	 * https://www.ibm.com/developerworks/cn/java/j-lo-junit4/
	 * �� Java �������ƣ�ÿ�����ʵ�ͷ��ĸ��д������
	 * ���ݿ�������ϰ�߽��и�ʽ��
	 * ��ʽ���������ΪСд��ĸ������ʹ���»��߷ָ���������
	 * 
	 * ���磺employeeInfo ������ʽ��֮���Ϊ employee_info 
	 * 
	 * @param name 	 Java ��������
	 */ 
	
	
	public static String wordFormat4DB(String name){ 
		 Pattern p = Pattern.compile("[A-Z]"); 
		 Matcher m = p.matcher(name); 
		 StringBuffer sb = new StringBuffer(); 
		
		 while(m.find()){ 
			 m.appendReplacement(sb, "_"+m.group()); 
		 } 
		 return m.appendTail(sb).toString().toLowerCase(); 
	 }
	
	// �޸ĺ�ķ��� wordFormat4DB 
	 /** 
		 * �� Java �������ƣ�ÿ�����ʵ�ͷ��ĸ��д������
		 * ���ݿ�������ϰ�߽��и�ʽ��
		 * ��ʽ���������ΪСд��ĸ������ʹ���»��߷ָ���������
		 * ������� name Ϊ null���򷵻� null 
		 * 
		 * ���磺employeeInfo ������ʽ��֮���Ϊ employee_info 
		 * 
		 * @param name Java ��������
		 */ 
		 public static String wordFormat4DB_1(String name){ 
			
			 if(name == null){ 
				 return null; 
			 } 
			
			 Pattern p = Pattern.compile("[A-Z]"); 
			 Matcher m = p.matcher(name); 
			 StringBuffer sb = new StringBuffer(); 
			
			 while(m.find()){ 
				 if(m.start() != 0) 
					 m.appendReplacement(sb, ("_"+m.group()).toLowerCase()); 
			 } 
			 return m.appendTail(sb).toString().toLowerCase(); 
		 }
	
	
	
	
	

}

package test;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {
	public static void main(String[] args) {
		
		/*
		 * �����ǲ���trim������Ч��������
		 */
		String str = "Test Base64";
		//����֮ǰ���ù������������ô���£�
//		String notTrim = Base64.encodeBase64String(str.getBytes());
		
		//
		byte[] notTrim = Base64.encodeBase64Chunked(str.getBytes());
		
		byte[] Trim = Base64.encodeBase64Chunked(str.getBytes());
		byte[] tt = Base64.encodeBase64(str.getBytes());
		
		System.out.println(notTrim);
		System.out.println(Trim);
		
	}
	

}

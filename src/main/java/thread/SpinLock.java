package thread;

import java.util.Date;

public class SpinLock {
	/*
	 * http://www.tianshouzhi.com/api/tutorials/mutithread/111
	 */
	public static volatile String sharedVariable;//�������
	
	public static void main(String[] args) {
		//����һ���߳�ִ������
		new Thread(){
			@Override
			public void run(){
				try{
					Thread.sleep(2000);//������������������ߴ���
//					synchronized (SpinLock.class) {   //�˴�ֻ�������̣߳����߳̽϶࣬��ü���synchronized��ȷ��ͬһʱ��ֻ��һ���̷߳���
						sharedVariable =  "hello";
//					}
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
		
		System.out.println("start time:" + new Date());
		
		//�����������ǲ��Ͻ���ѭ����������������
		while(sharedVariable == null){
//			System.out.println("aa");
		}
		
		
		
		System.out.println(sharedVariable);
		
		//�����������Ԥ�ڽ����Ӧ���Ǹ�2��֮�����end time ????????
		System.out.println("end time:" + new Date());
	}
	
}

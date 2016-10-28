package thread;

import java.util.ArrayList;
import java.util.List;

public class TestRunnable implements Runnable{
	 /** �߳��� */
    private String threadName;


    public TestRunnable(String threadName) {
        this.threadName = threadName;
    }


    public void run() {
        System.out.println( "[" + threadName + "] Running !" );
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> lists = new ArrayList<Thread>();
        for(int i=0; i<5; i++){
            Thread thread = new Thread(new TestRunnable("���߳�" + (i + 100)));
            lists.add(thread);
            thread.start();
        }
        System.out.println("���߳�����,�ȴ��������߳�ִ�����");
        for(Thread thread : lists){
            // ���ע�͵�thread.join(),������ main�߳� �� �������߳� thread��������,������ȴ����߳���ɺ���ִ��
            thread.join();
        }
        System.out.println("�����߳�ִ�����!");
    }

}

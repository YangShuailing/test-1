package thread;

import java.util.Date;

public class SpinLock1 {
    /*
     * http://www.tianshouzhi.com/api/tutorials/mutithread/111
     * ��BUG
     */
    private static String sharedVariable;//�������

    public static void main(String[] args) {
        //����һ���߳�ִ������
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//������������������ߴ���
                    sharedVariable = "hello";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("start time:" + new Date());
        //�����������ǲ��Ͻ���ѭ����������������
        while (sharedVariable == null) {
//			System.out.println("bug?");
        }

        System.out.println(sharedVariable);

        //�����������Ԥ�ڽ����Ӧ���Ǹ�2��֮�����end time ????????
        System.out.println("end time:" + new Date());
    }


}

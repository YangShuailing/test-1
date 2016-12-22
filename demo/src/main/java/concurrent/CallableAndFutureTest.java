package concurrent;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFutureTest {
	//http://www.tianshouzhi.com/api/tutorials/mutithread/111
	public static void main(String[] args) {
		System.out.println("start time:" + new Date());
		ExecutorService service = Executors.newSingleThreadExecutor();
		//Future��Callable�еķ��ͣ����Ƿ���ֵ������
		Future<String> future = service.submit(() -> {
            Thread.sleep(2000);
            return "Hello";
        });
 
		try {
			String result = future.get();// �÷���������������ȴ�ִ�����
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end time:" + new Date());
		service.shutdown();
	}

}

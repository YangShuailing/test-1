package socket.demo1;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Author: Johnny
 * Date: 2016/11/27
 * Time: 19:58
 */
public class AttendSocetListener implements ServletContextListener {
    private SocketThread socketThread;

    public void contextDestroyed(ServletContextEvent arg0) {
        if (null != socketThread && !socketThread.isInterrupted()) {
            socketThread.closeSocketServer();
            socketThread.interrupt();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        if (null == socketThread) {
            socketThread = new SocketThread(null);
            socketThread.start();
        }
    }
}

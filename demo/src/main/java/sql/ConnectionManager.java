package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

public class ConnectionManager {

    /**
     * ʹ�õ���ģʽ�������ӳأ��ͻ���ֻ��ͨ��ConnecitonManager.getInstance()�õ�һ��ʵ����
     * ���ͻ��˹ر�ʱ��Ӧ�õ���release()�����ر����е����ݿ����ӣ�����������������������
     */
    private static final int TIME_BETWEEN_RETRIES = 500; // 0.5 second

    // static variable
    static private ConnectionManager instance = null; // The single instance

    // instance variable
    private DBConnectionPool pool = null;

    /**
     * ˽�й��췽�� ��ʼ�����ݿ������
     */
    private ConnectionManager() {
        DBOptions option = new DBOptions();
        String driverClassName = option.getDriverClassName();
        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception e) {
            System.out.println("fatal ERROR:   ConnectionManager: Unable to load driver = "
                    + driverClassName);
        }

        String url = option.getDatabaseURL();
        String user = option.getDatabaseUser();
        String password = option.getDatabasePassword();
        int maxConnection = option.getMaxConnection();

        //always new the pool because pool is an instance variable
        pool = new DBConnectionPool(url, user, password, maxConnection);
    }

    /**
     * ����һ��������ʵ���������������ǵ�һ�α����þʹ���һ���µ�ʵ��
     *
     * @return ConnectionManager The single instance.
     */
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * ����һ�����ӡ�
     * ���û�п���ʹ�õ����ӣ�������������û�дﵽ��󣬾ʹ���һ���µ�����
     *
     * @return Connection The connection or null
     */
    Connection getConnection() {
        return pool.getConnection();
    }

    /**
     * ����һ�����ӡ�
     * ���û�п���ʹ�õ����ӣ�������������û�дﵽ��󣬾ʹ���һ���µ����ӣ�
     * ����������ﵽ�����ֵ����ָ����ʱ��ȴ���������ʱ�����������ͷ��ˣ��ͷ���һ�����ӣ�
     * ���û�����ӱ��ͷţ��ͷ���null
     *
     * @param time The number of milliseconds to wait
     * @return Connection The connection or null
     */
    Connection getConnection(long time) {
        return pool.getConnection(time);
    }

    /**
     * �ر����п��ŵ����ݿ�����
     *
     * @return true if the pool is empty and balance false if the pool has
     * returned some connection to outside
     */
    boolean release() {
        return pool.release();
    }

    /**
     * ���ӳ�ʹ���ڲ�����ʵ��
     */
    class DBConnectionPool {
        private int checkedOut = 0;

        private Vector freeConnections = new Vector();

        private int maxConn = 0;

        private String password = null;

        private String URL = null;

        private String user = null;

        /**
         * Creates new connection pool. NOTE: new an instance of this class is
         * lightweight since it does not create any connections
         *
         * @param URL      The JDBC URL for the database
         * @param user     The database user, or null
         * @param password The database user password, or null
         * @param maxConn  The maximal number of connections, or 0 for no limit
         */
        public DBConnectionPool(String URL, String user, String password,
                                int maxConn) {
            this.URL = URL;
            this.user = user;
            this.password = password;
            this.maxConn = maxConn;
        }

        /**
         * Checks in a connection to the pool. Notify other Threads that may be
         * waiting for a connection.
         *
         * @param con The connection to check in
         */
        synchronized void freeConnection(Connection con) {
            if (con != null) {//make sure that the connection is not null
                if (checkedOut <= 0) {
                    try {
                        System.out.println("ConnectionManager: about to close the orphan connection.");
                        con.close();
                    } catch (SQLException ex) {
                    }
                } else {
                    freeConnections.addElement(con);
                    checkedOut--;
                    notifyAll();
                }
            }
        }

        /**
         * Checks out a connection from the pool. If no free connection is
         * available, a new connection is created unless the max number of
         * connections has been reached. If a free connection has been closed by
         * the database, it's removed from the pool and this method is called
         * again recursively.
         */
        synchronized Connection getConnection() {
            Connection con = null;

            while ((freeConnections.size() > 0) && (con == null)) {
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        System.out.println("Removed bad connection in DBConnectionPool.");
                        con = null; // to make the while loop to continue
                    }
                } catch (SQLException e) {
                    con = null; // to make the while loop to continue
                }
            } // while

            // cannot get any connection from the pool
            if (con == null) {
                if (maxConn == 0 || checkedOut < maxConn) {
                    // maxConn = 0 means unlimited connections
                    con = newConnection();
                }
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        /**
         * ����һ�����ӡ�
         * ���û�п���ʹ�õ����ӣ�������������û�дﵽ��󣬾ʹ���һ���µ����ӣ�
         * ����������ﵽ�����ֵ����ָ����ʱ��ȴ���������ʱ�����������ͷ��ˣ��ͷ���һ�����ӣ�
         * ���û�����ӱ��ͷţ��ͷ���null
         *
         * @param timeout The timeout value in milliseconds
         */
        Connection getConnection(long timeout) {
            long startTime = System.currentTimeMillis();
            Connection con;
            while ((con = getConnection()) == null) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= timeout) {
                    return null;
                }

                long timeToWait = timeout - elapsedTime;
                //ÿ�εȴ���ʱ�䲻�ܳ���TIME_BETWEEN_RETRIES
                if (timeToWait > TIME_BETWEEN_RETRIES)
                    timeToWait = TIME_BETWEEN_RETRIES;
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException e) {
                }
            }
            return con;
        }

        /**
         * �ر����п����õ�����
         *
         * @return true if the pool is empty and balance false if the pool has
         * returned some connection to outside
         */
        synchronized boolean release() {
            boolean retValue = true;
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Cannot close connection in DBConnectionPool.");
                }
            }
            freeConnections.removeAllElements();
            if (checkedOut != 0) {
                retValue = false;
                System.out.println("ConnectionManager: the built-in connection pool is not balanced.");
            }
            checkedOut = 0;
            return retValue;
        }

        /**
         * ʹ��ָ�����û��������봴��һ�����ݿ�����
         */
        private synchronized Connection newConnection() {
            Connection con = null;
            try {
                //������½�����ᳫʹ��
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                } else {
                    con = DriverManager.getConnection(URL, user, password);
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(
                        "Cannot create a new connection in DBConnectionPool. URL = "
                                + URL + e.getMessage());
                return null;
            }
            return con;
        }
    }
}
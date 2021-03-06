package project.mvcDemo.util;

public class DbSessionFactory {
    private static final ThreadLocal<DbSession> threadLocal = new ThreadLocal<>();

    private DbSessionFactory() {
        throw new AssertionError();
    }

    /**
     * 打开会话
     *
     * @return DbSession对象
     */
    public static DbSession openSession() throws DbSessionException {
        DbSession session = threadLocal.get();
        if (session == null) {
            session = new DbSession();
            threadLocal.set(session);
        }
        session.open();
        return session;
    }

    /**
     * 关闭会话
     */
    public static void closeSession() throws DbSessionException {
        DbSession session = threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
            session.close();
        }
    }
}

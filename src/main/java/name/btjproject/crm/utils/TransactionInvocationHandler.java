package name.btjproject.crm.utils;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionInvocationHandler implements InvocationHandler {
    private final Object target;

    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
        Object resObj;
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        try {
            resObj = method.invoke(target, args);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            throw e.getCause();
        } finally {
            SqlSessionUtil.closeSqlSessionAndThreadLocal(sqlSession);
        }
        return resObj;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
package name.btjproject.crm.utils;

public class ServiceFactory {
    private ServiceFactory() {}

    public static Object getService(Object target) {
        return new TransactionInvocationHandler(target).getProxy();
    }
}

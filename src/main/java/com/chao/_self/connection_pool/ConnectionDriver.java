package com.chao._self.connection_pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author W.Chao
 * @date 2019/8/21 16:11
 **/
public class ConnectionDriver {
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
				new Class[]{Connection.class}, new ConnectionHandler());
	}

	private static class ConnectionHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.SECONDS.sleep(10);
			}
			return null;
		}
	}
}

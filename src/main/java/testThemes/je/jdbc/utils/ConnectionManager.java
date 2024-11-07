package testThemes.je.jdbc.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

	private static final String URL_KEY = "db.url";
	private static final String USERNAME_KEY = "db.username";
	private static final String PASSWORD_KEY = "db.password";
	private static final int DEFAULT_POOL_SIZE = 10;
	private static final String POOL_SIZE_KEY= "db.pool.size";
	private static BlockingQueue<Connection> pool;
	
	static {
		initConnectionPool();
	}
	
	private static void initConnectionPool() {
		String poolsize = PropetriesUtil.get(POOL_SIZE_KEY);
		int size = poolsize ==null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolsize);
		pool = new ArrayBlockingQueue<Connection>(size);
		
		for (int i = 0; i < size; i++) {
			Connection connection = open();
			Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), 
					new Class[] {Connection.class}, 
					(proxy, method, args) -> method.getName().equals("close") ? 
					pool.add((Connection) proxy) : 
						method.invoke(connection, args));
			pool.add(proxyConnection);
		}
	}

	private static Connection open() {
		try {
			return DriverManager.getConnection(PropetriesUtil.get(URL_KEY), 
					PropetriesUtil.get(USERNAME_KEY), 
					PropetriesUtil.get(PASSWORD_KEY));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Connection get() {
		try {
			return pool.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	

	public ConnectionManager() {

	}

}

/**
 * 
 */
package com.kingmed.pl.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 数据库连接获取
 * 
 * @author Zhong, An-Jing
 * 
 */
public class ConnectManager {

	private static Connection conn;
	private static ConnectManager instance;
	/**
	 * 连接串 0：url 1: user 2: password
	 */
	private String[] str;
	private Logger log =   Logger.getLogger(this.getClass().getName());
	public static synchronized ConnectManager getInstance() {
		if (instance == null) {
			instance = new ConnectManager();
		}
		return instance;
	}
	/**
	 * 默认构造函数， 从与当前路径下的配置文件中加载配置信息
	 */
	private ConnectManager() {
//		InputStream inputstream = ConnectManager.class
//				.getResourceAsStream("connection.properties");
		log.info("ConnectManager Constructor start");
		InputStream inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream("connection.properties");
		Properties p = new Properties();
		try {
			p.load(inputstream);
			str = new String[] { p.getProperty("jdbc.url"),
					p.getProperty("jdbc.user"), p.getProperty("jdbc.password") };
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("ConnectManager Constructor end");
	}

	public ConnectManager(String[] str) {
		log.info("ConnectManager Constructor2 start");
		this.str = str;
		log.info("ConnectManager Constructor2 end");
	}

	/**
	 * 获取连接
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private synchronized Connection getConnection() throws Exception {
		log.info("ConnectManager::getConnection start");
		if (conn != null){
			log.info("connection reused");
			return conn;
		}
		if (str == null || str.length < 3)
			return null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		log.info("ConnectManager::getConnection end");
		log.info("connection recreated");
		return DriverManager.getConnection(str[0], str[1], str[2]);
	}

	/**
	 * release the connection
	 * 
	 * @throws SQLException
	 */
	public void free(Statement st, ResultSet rs, boolean conClose)
			throws SQLException {
		log.info("ConnectManager::free start");
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (st != null) {
			st.close();
			st = null;
		}
		if (conClose && conn != null) {
			log.info("ConnectManager::free  connection closed");
			conn.close();
			conn = null;
		}
		log.info("ConnectManager::free end");
	}
	
	/**
	 * 执行查询语句，并基于反射机制，返回列表
	 * 
	 * @param sql
	 * @param cla
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> query2(String sql, LinkedHashMap<Integer, String> param, Class<T> cla) throws Exception {
		log.info("ConnectManager::query start");
		if( "".equals(sql) || null == sql ) {
			log.warn("EMPTY SQL:"+sql);
			return new ArrayList<T>();
		}
		Method[] ms = cla.getMethods();
		Map<String, Method> _m = new HashMap<String, Method>();
		for (Method m : ms) {
			if (m.getName().toUpperCase().startsWith("SET"))
				_m.put(m.getName().substring(3).toUpperCase(), m);
		}
		List<T> l = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			st = getConnection().prepareStatement(sql);
			
			if(param != null){
//				Class clazz = st.getClass();
//				Method method = null;
				for(Entry<Integer, String> en : param.entrySet()){
//					if(String.class == en.getValue().getClass()){
//						method = clazz.getDeclaredMethod("setString", new Class[] { Integer.TYPE, String.class });
//					}
					
					//目前只支持String类型的参数
					st.setString(en.getKey(), en.getValue());
				}
			}
			
			rs = st.executeQuery();
			List<String> headers = new ArrayList<String>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				headers.add(rsmd.getColumnName(i).toUpperCase());
			}
			while (rs.next()) {
				T obj = cla.newInstance();
				for (String s : headers) {
					if(_m.get(s)==null){//去除sql colum中obj没有的字段
						continue;
					}
					_m.get(s).invoke(obj, rs.getObject(s));
				}
				l.add(obj);
			}
		} catch (SQLException e) {
			log.error(e.getSQLState());
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			free(st, rs, true);
		}
		log.info("ConnectManager::query end");
		return l;
	}

	/**
	 * 执行查询语句，并基于反射机制，返回列表
	 * 
	 * @param sql
	 * @param cla
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> query(String sql, Class<T> cla) throws Exception {
		log.info("ConnectManager::query start");
		if( "".equals(sql) || null == sql ) {
			log.warn("EMPTY SQL:"+sql);
			return new ArrayList<T>();
		}
		Method[] ms = cla.getMethods();
		Map<String, Method> _m = new HashMap<String, Method>();
		for (Method m : ms) {
			if (m.getName().toUpperCase().startsWith("SET"))
				_m.put(m.getName().substring(3).toUpperCase(), m);
		}
		List<T> l = new ArrayList<T>();
		ResultSet rs = null;
		Statement st = null;
		try {
			st = getConnection().createStatement();
			rs = st.executeQuery(sql);
			List<String> headers = new ArrayList<String>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				headers.add(rsmd.getColumnName(i).toUpperCase());
			}
			while (rs.next()) {
				T obj = cla.newInstance();
				for (String s : headers) {
					if(_m.get(s)==null){//去除sql colum中obj没有的字段
						continue;
					}
					_m.get(s).invoke(obj, rs.getObject(s));
				}
				l.add(obj);
			}
		} catch (SQLException e) {
			log.error(e.getSQLState());
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			free(st, rs, true);
		}
		log.info("ConnectManager::query end");
		return l;
	}

	/**
	 * 通过文件，把sql语句单独的拿出来，进行配置。
	 * @author Zhong, An-Jing
	 *
	 */
	public static class SQLManager {
		/**
		 * 缓存所有的sql文件
		 */
		private static Map<String, String> sqls = new HashMap<String, String>();

		/**
		 * 获取指定名称的sql内容
		 * @param id
		 * @return
		 */
		public static String getSql(String id) {
			String sql = (String) sqls.get(id);
			if (sql == null) {
				sql = readFromFile(id);
				sqls.put(id, sql);
			}
			return sql;
		}

		/**
		 * 读取与Fetch4AData相同架包路径下的指定文件内容
		 * @param id
		 * @return
		 */
		private static String readFromFile(String id) {
			// System.out.println(resourceClass.getName());
			String content = "";
			InputStream inputstream = null;
			InputStreamReader reader = null;
			BufferedReader br = null;
			try {
				String sqlfile = id + ".sql";
				//inputstream = SQLFile.class.getResourceAsStream(sqlfile);
				inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(sqlfile);
				if (inputstream == null) {
					return null;
				}
				reader = new InputStreamReader(inputstream, "utf-8");

				br = new BufferedReader(reader);
				String line = null;
				while ((line = br.readLine()) != null) {
					content += line + "\n";
				}
			} catch (IOException ex2) {
				ex2.printStackTrace();
			} finally {
				if (inputstream != null) {
					try {
						inputstream.close();
					} catch (IOException ex) {
					}
				}
			}
			return content;
		}
	}
}

package downpage.jdbc;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DbUtil {
	private static DataSource ds;
	private static QueryRunner runner;
	static {
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://127.0.0.1:3306/ptyaodian?Unicode=true&characterEncoding=utf-8");
		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("");
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		ds = new DataSource();
		ds.setPoolProperties(p);
		runner = new QueryRunner();
	}

	private static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * ɾ��
	 * @param tableName
	 * @param id
	 * @return
	 */
	public static boolean delete(String tableName, long id) {
		String sql = "delete from " + tableName + " where id=?";
		int i = 0;
		boolean flag = false;
		Connection con = null;
		try {
			con = getConnection();
			i = runner.update(con, sql, id);
			if (i >= 0) {
				flag = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return flag;
	}

	/**
	 * �޸�/��� 
	 * @param sql
	 * @param pring
	 * @return
	 */
	public static boolean update(String sql, Object pring[]) {
		int i = 0;
		boolean flag = false;
		Connection con = null;
		try {
			con = getConnection();
			i = runner.update(con, sql, pring);
			if (i >= 0) {
				flag = true;
			}
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * ��ѯ
	 * @param sql
	 * @param rsh
	 * @return
	 */
	public static List<?> query(String sql, ResultSetHandler<?> rsh) {  
        List<?> result = null;  
        Connection con = null;
        try {  
        	con = getConnection();
        	result = (List<?>) runner.query(con, sql, rsh);
        } catch (SQLException e) {            
            e.printStackTrace();  
        } finally {
        	if (con!=null) {
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return result;  
    }  
	
	/**
	 * ��ҳ��ѯ
	 * @param sql
	 * @param rsh
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static List<?> query(String sql, ResultSetHandler<?> rsh, int page, int pageSize) {  
        List<?> result = null;  
        Connection con = null;
        try {  
        	con = getConnection();
        	result = (List<?>) runner.query(con, sql + " limit " + page*pageSize + "," + pageSize, rsh);
        } catch (SQLException e) {            
            e.printStackTrace();  
        } finally {
        	if (con!=null) {
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return result;  
    } 
	
	/**
	 * ���id��ѯ
	 * @param clasz
	 * @param id
	 * @return
	 */
	public static Object get(Class<?> clasz, int id) {
		Connection con = null;
		Object obj = null;
        try {  
        	con = getConnection();
        	@SuppressWarnings({ "unchecked", "rawtypes" })
			List<?>result = (List<?>) runner.query(con, 
					"select * from " + clasz.getSimpleName().toLowerCase() + " where id=" + id, 
					new BeanListHandler(clasz));
        	if (result!=null && result.size()>0) {
        		obj = result.get(0);
        	}
        } catch (SQLException e) {            
            e.printStackTrace();  
        } finally {
        	if (con!=null) {
        		try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
        return obj;  		
	}
}
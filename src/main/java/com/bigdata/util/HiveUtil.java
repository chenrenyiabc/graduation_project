package com.bigdata.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HiveUtil {

	// 数据库连接
	private Connection conn = null;
	// statement对象 - 会话对象
	private Statement statement = null;
	// ResultSet 对象
	private ResultSet rs = null;

	PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
	String host = propertiesUtil.readPropertyByKey("hostName");
	String user = propertiesUtil.readPropertyByKey("hadoopUser");


	public HiveUtil() {
		open();
	}

	public HiveUtil(String dbName){
		open();
		changeDatabase(dbName);
	}

	static {
		try {
			// 1.加载驱动
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void open() {
		try {
			// 2.打开连接
			conn = DriverManager.getConnection("jdbc:hive2://" + host + ":10010/",user,"");
			// 3.获得操作对象 - 会话
			statement = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库 - 用户注册时调用
	 * 
	 * @param databaseName 根据用户标识生成的数据库名称
	 */
	public void createDatabase(String databaseName) {
		try {
			statement.execute("create database " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换数据库 - 只对当前会话有效
	 * 
	 * @param databaseName 目标数据库名称
	 */
	public void changeDatabase(String databaseName) {
		try {
			statement.execute("use " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得当前数据库中的数据列表 - 注意切换数据库
	 * 
	 * @return 数据表名称的集合
	 */
	public List<String> getTaleList() {
		List<String> list = new ArrayList<>();
		try {
			rs = statement.executeQuery("show tables");
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得数据表的简要信息
	 * 
	 * @param tableName 数据表名称
	 * @return 列名及列的数据类型
	 */
	public List<String> getTableInfo(String tableName) {
		List<String> list = new ArrayList<>();
		try {
			rs = statement.executeQuery("desc " + tableName);
			while (rs.next()) {
				list.add(rs.getString(1) + "\t" + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 获取数据表前十条的预览数据
	 * 
	 * @param tableName 数据表名称
	 * @return 数据表预览数据
	 */
	public List<String> getTableData(String tableName) {
		List<String> list = new ArrayList<>();
		try {
			int size = getTableInfo(tableName).size();
			rs = statement.executeQuery("select * from " + tableName + " limit 10");
			while (rs.next()) {
				String line = "";
				for (int i = 1; i <= size; i++) {
					line += rs.getString(i) + "\t";
				}
				list.add(line);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得查询sql执行后的返回结果
	 * 
	 * @param sql 用户自定义sql
	 * @return sql执行结果集中的所有数据
	 */
	public List<String> getResultData(String sql) {
		List<String> list = new ArrayList<>();
		// String databaseName = "data_flow";
		// 生成一个对于当前流程唯一的中间表名称
		// 如果流程会反复执行则先删除该表再创建
		String tableName = "data_flow_1";
		sql = "create table " + tableName + " as " + sql;
		try {
			// 执行查询语句，同时使用一个表进行记录
			statement.execute(sql);
			// 获得中间表的列信息 - 取决于用户执行sql的结果集结构
			int size = getTableInfo(tableName).size();
			rs = statement.executeQuery("select * from " + tableName);
			while (rs.next()) {
				String line = "";
				for (int i = 1; i <= size; i++) {
					line += rs.getString(i) + "\t";
				}
				list.add(line);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 删除对应用户的表
	 * */
	public boolean delTable(String tableName){
		try{
			return statement.execute("drop table " + tableName);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得查询sql执行后的返回结果
	 * 
	 * @param sql 用户自定义Hive_sql
	 * @param tableName 存放结果的表
	 * @return 运行状态1成功，0失败
	 */
	public boolean runHQL(String sql,String tableName) {
		sql = "create table " + tableName + " as " + sql;
		try {
			statement.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 执行Hive语句
	 * @param cmd 需要执行的命令
	 * @return 是否成功
	 */
	public boolean excuteCmd(String cmd) {
		boolean flag = false;
		try {
			statement.execute(cmd);
			flag = true;
			System.out.println("命令执行成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 关闭
	 * */
	public void close(){
		try{
			if(rs != null)
				rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if(conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

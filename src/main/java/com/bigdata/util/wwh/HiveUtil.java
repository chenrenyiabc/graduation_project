package com.bigdata.util.wwh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class HiveUtil {

	private Statement statement = null;
	private ResultSet rs = null;
	PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
	String host = propertiesUtil.readPropertyByKey("hostName");
	String user = propertiesUtil.readPropertyByKey("hadoopUser");


	public HiveUtil() {
		open();
	}

	static {
		try {
			// 加载驱动
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void open() {
		try {
			// 打开连接指定库(需要在服务端开启端口服务)

			Connection connection = DriverManager.getConnection("jdbc:hive2://" + host + ":10010/",user,"");
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建数据库，用户注册时使用
	 * 
	 * @param databaseName
	 *            根据用户表示生成的用户名
	 */
	public void createDatabase(String databaseName) {
		try {
			statement.execute("create database " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 切换数据库
	 * 
	 * @param databaseName
	 */
	public void changeDatabase(String databaseName) {
		try {
			statement.execute("use " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回查询的表列表
	 * 
	 * @return
	 */
	public List<String> getTableList() {
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
	 * 查看某个表的结构
	 * 
	 * @param tableName
	 * @return
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
	 * 简单查询表的前10条数据
	 * 
	 * @param tableName
	 * @return
	 */
	public List<String> getTableData(String tableName) {
		List<String> list = new ArrayList<>();
		int size = getTableInfo(tableName).size();
		try {
			rs = statement.executeQuery("select * from " + tableName + " limit 10");
			while (rs.next()) {
				String line = "";
				for (int i = 1; i <= size; i++) {
					line += rs.getString(i) + "\t";
				}	
				list.add(line.substring(0, line.length()-1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取sql执行的返回结果
	 * @param sql
	 * @return 返回所有数据
	 */
	public List<String> getResultData(String sql){
		List<String> list = new ArrayList<>();
		String databaseName = "data_flow";   //设置数据库
		String tableName = databaseName + "." + "data_flow_1"; //设置数据表
		sql = "create table " + tableName + " as " + sql;
		try {
			statement.execute(sql);
			int size = getTableInfo(tableName).size();
			ResultSet rs = statement.executeQuery("select * from "+ tableName);
			while (rs.next()) {
				String line = "";
				for (int i = 1; i <= size; i++) {
					line += rs.getString(i) + "\t";
				}	
				list.add(line.substring(0, line.length()-1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 插入数据
	 * 
	 * @param isOverwrite
	 *            是否覆盖原有数据，false为追加
	 * @param isLocal
	 *            是否本地linux上传，false为hdfs
	 * @param dataAddr
	 *            源数据地址
	 * @param targetTable
	 *            目标表
	 * @return 返回是否载入数据成功
	 */
	public boolean loadData(boolean isOverwrite, boolean isLocal, String dataAddr, String targetTable) {
		boolean flag = false;
		String overWrite = isOverwrite ? "OVERWRITE" : "";
		String Local = isLocal ? "LOCAL" : "";
		String sql = "load data " + Local + " inpath '" + dataAddr + "' " + overWrite + " into table " + targetTable;
		System.out.println(sql);
		try {
			statement.execute(sql);
			flag = true;
			System.out.println("载入数据成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 创建表
	 * 
	 * @param tableName
	 *            表名
	 * @param colnames
	 *            列名数组
	 * @param coltype
	 *            列类型数组
	 * @param delimiter
	 *            行分隔符
	 * @return 是否建表成功
	 */
	public boolean createTable(String tableName, String[] colnames, String[] coltype, String delimiter) {
		boolean flag = false;
		int num = colnames.length;
		String temp = "";
		for (int i = 0; i < num; i++) {
			temp += colnames[i] + " " + coltype[i] + ",";
		}
		temp = temp.substring(0, temp.length() - 1);
		if (getTableList().contains(tableName)) {
			flag = false;
			System.out.println("表已经存在");
		} else {
			String sql = "create table " + tableName + "(" + temp + ") " + "row format delimited fields terminated by '"
					+ delimiter + "'";
			System.out.println(sql);
			try {
				statement.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			flag = true;
			System.out.println("创建表成功");
		}
		return flag;
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
}

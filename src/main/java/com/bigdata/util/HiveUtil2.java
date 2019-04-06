package com.bigdata.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Hive 工具类
 * */
public class HiveUtil2 {
	
	//数据库连接	
	private Connection conn = null;
	
	//statement对象 - 会话对象
	private Statement st = null;
	
	//ResultSet 对象
	private ResultSet rs = null;
	PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
	String host = propertiesUtil.readPropertyByKey("hostName");

	String user = propertiesUtil.readPropertyByKey("hadoopUser");
	
	static{
		try {
			//1. 加载驱动
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 只打开连接
	 * */
	public HiveUtil2(){
		open();
	}
	
	/**
	 * 打开连接并且切换到指定的数据库
	 * @param dbName 已经存在的数据库名称
	 * */
	public HiveUtil2(String dbName) {
		open();//打开连接
		changeDB(dbName);//切换数据库
	}
	
	/**
	 * 打开连接
	 * */	
	private void open(){
		try {
			//2. 打开连接
			//不指定数据库默认使用default数据库
			//如果这一步失败了，就去检查下url有没有问题，如果没有问题，去检查下hive的远程连接有没有开启
			//hive远程开启命令：
			//nohup hive --service hiveserver2 --hiveconf hive.server2.thrift.port=10010 &
			conn = DriverManager.getConnection("jdbc:hive2://" + host + ":10010/",user,"");
			st = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 切换数据库
	 * */
	public void changeDB(String dbName){
		try {
			//切换数据库
			st.execute("use " + dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得当前数据库中的数据列表 - 注意切换数据库
	 * @return 数据表名称的集合
	 */
	public List<String> getTaleList() {
		List<String> list = new ArrayList<>();
		try {
			ResultSet rs = st.executeQuery("show tables");
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获得数据表的简要信息
	 * @param tableName 数据表名称
	 * @return 列名及列的数据类型
	 */
	public List<String> getTableInfo(String tableName){
		List<String> list = new ArrayList<>();
		try {
			ResultSet rs = st.executeQuery("desc " + tableName);
			while (rs.next()) {
				list.add(rs.getString(1) + "\t" + rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 创建新的数据库
	 * */
	public void createDB(String dbName){
		try {
			st.execute("create database " + dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除对应用户的表
	 * */
	public boolean delTable(String tableName){
		try{
			return st.execute("drop table " + tableName);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
				if(st != null)
					st.close();
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

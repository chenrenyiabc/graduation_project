package com.bigdata.util.wwh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {

	// 0.定义工具类相关组件
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Integer intResult = -1;

	/**
	 * 在构造方法中执行必须的步骤，如:打开连接
	 */
	private DBUtil() {
		open();
	}

	private static DBUtil dbUtil;

	public static synchronized DBUtil getDBUtil(){
		if(dbUtil == null){
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}

	/**
	 * 通过配置文件的方式打开连接
	 */
	public void open() {
		try {
			// 2.打开数据库连接

			ComboPooledDataSource cpds = new ComboPooledDataSource();
			connection = cpds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 执行更新操作
	 * 
	 * @param sql 需要执行的sql语句
	 * 
	 * @return 数据库中变更的条数，通常用于判断该操作是否成功执行
	 */
	public int executeUpdate(String sql, Object[] objects) {
		try {
			// 3.通过传入的参数初始化preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			// 当传入的数组非空时则需要进行赋值操作
			if (objects != null) {
				setPreparedStatement(objects);
			}
			// 4.5.操作数据库并接收返回结果
			intResult = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			intResult = -1;
		} finally {
			return intResult;
		}
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql 需要执行的sql语句
	 * 
	 * @return ResultSet对象
	 */
	public ResultSet executeQuery(String sql, Object[] objects) {
		try {
			// 3.通过传入的参数初始化preparedStatement
			preparedStatement = connection.prepareStatement(sql);
			// 当传入的数组非空时则需要进行赋值操作
			if (objects != null) {
				setPreparedStatement(objects);
			}
			// 4.5.操作数据库并接收返回结果
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			resultSet = null;
		} finally {
			return resultSet;
		}
	}

	/**
	 * 对于已经初始化完毕的preparedStatement进行参数赋值
	 * 
	 * @param objects
	 */
	public void setPreparedStatement(Object[] objects) {
		// for循环遍历参数数组
		for (int i = 0; i < objects.length; i++) {
			try {
				// 参数设置的下标与数组下标相差1
				preparedStatement.setObject(i + 1, objects[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 6.释放资源
	public void close() {
	/*	try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}

package com.bigdata.dao.datasource;

import java.util.List;

import com.bigdata.bean.DataSource;
import com.bigdata.util.DBUtils;

/**
 * 数据源dao
 * */
public class DataSourceDao {

	private DBUtils util = DBUtils.getDBUtils();
	
	/**
	 * 获取数据源列表
	 * */
	public List<DataSource> getDataSourceList(Integer userId){
		return util.query("select * from data_source where userid=?", DataSource.class, userId);
	}
	
	/**
	 * 获取数据源列表
	 * */
	public List<DataSource> getDataSourceList(Integer userId, Integer groupId){
		return util.query("select * from data_source where userid=? and groupid=?", DataSource.class, userId, groupId);
	}
	
	/**
	 * 通过数据源的id获取 
	 * */
	public DataSource getDataSourceById(Integer userId, Integer id){
		return util.queryObject("select * from data_source where userid=? and id=?", DataSource.class, userId, id);
	}
	
	/**
	 * 通过数据源的id获取 
	 * */
	public DataSource getDataSourceById(Integer id){
		return util.queryObject("select * from data_source where id=?", DataSource.class, id);
	}
	
	/**
	 * 移动数据源
	 * */
	public boolean moveDataSource(Integer destGroupId, Integer id, Integer userId){
		return util.update("update data_source set groupId=? where id=? and userId=?", destGroupId, id, userId);
	}
	
	/**
	 * 删除数据源
	 * 
	 * 现在只做了mysql中的数据删除，没有做hive以及hdfs上的删除，后面需要关联
	 * */
	public boolean deleteDataSource(Integer id, Integer userId){
		return util.update("delete from data_source where id=? and userId=?", id, userId);
	}
	
	/**
	 * 添加数据源
	 * */
	public boolean addDatasource(DataSource ds){
		String sql = "insert into data_source(userid,name,groupid,type,tablename,tabledesc,createtime,hdfspath) values(?,?,?,?,?,?,?,?)";
		return util.update(sql, ds.getUserId(), ds.getName(), ds.getGroupid(), ds.getType(), ds.getTableName(), ds.getTableDesc(), ds.getCreatetime(), ds.getHdfsPath());
	}
	
	/**
	 * 关闭
	 * */
	public void close(){
		util.close();
	}

	public DataSource getDataSourceByName(String source_name, int userId) {
		String sql = "select id from data_source where name =? and userid=?";
		return util.queryObject(sql, DataSource.class, source_name, userId);
	}
}

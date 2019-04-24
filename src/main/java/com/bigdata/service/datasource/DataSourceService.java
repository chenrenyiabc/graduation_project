package com.bigdata.service.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.bean.DataSource;
import com.bigdata.dao.datasource.DataSourceDao;
import com.bigdata.service.datagroup.DataGroupService;
import com.bigdata.util.HDFSUtil;
import com.bigdata.util.HiveUtil;
import com.bigdata.util.PropertiesUtil;

/**
 * 数据源service
 * */
public class DataSourceService {
	
	private DataSourceDao dsDao = new DataSourceDao();
	
	/**
	 * 获取map
	 * */
	public Map<String, List<DataSource>> getDataSourceList(Integer userId){
		if(userId == null)
			return null;
		List<DataSource> list = dsDao.getDataSourceList(userId);
		Map<String, List<DataSource>> map = new HashMap<>();
		for (DataSource dataSource : list) {
			String key = dataSource.getGroupid() + "";
			if(map.containsKey(key)){
				map.get(key).add(dataSource);
			}else{
				List<DataSource> value = new ArrayList<>();
				value.add(dataSource);
				map.put(key, value);
			}
		}
		return map;
	}
	
	/**
	 * 获取列表的json字符串
	 * */
	public String getDataSourceListStr(Integer userId){
		return JSONObject.toJSONString(getDataSourceList(userId));
	}
	
	/**
	 * 列表转成json字符串
	 * */
	public String getDataSourceListStr(Map<String, List<DataSource>> datasources){
		return JSONObject.toJSONString(datasources);
	}
	
	/**
	 * 移动数据源
	 * */
	public boolean moveDataSource(Integer id,Integer userId, Integer destGroupId){
		if(id == null || userId == null || destGroupId == null)
			return false;
		return dsDao.moveDataSource(destGroupId, id, userId);
	}
	
	/**
	 * 获取数据源列表
	 * */
	public List<DataSource> getDataSourceList(Integer userId, Integer groupId){
		if(userId == null || groupId == null)
			return null;
		return dsDao.getDataSourceList(userId, groupId);
	}
	
	/**
	 * 通过数据源的id获取 
	 * */
	public DataSource getDataSourceById(Integer userId, Integer id){
		if(userId == null || id == null)
			return null;
		return dsDao.getDataSourceById(userId, id);
	}
	
	/**
	 * 删除数据源
	 * 
	 * 现在只做了mysql中的数据删除，没有做hive以及hdfs上的删除，后面需要关联
	 * */
	public boolean deleteDataSource(Integer id, Integer userId){
		System.out.println("id:" + id + ",userID:" + userId);
		if(id == null || userId == null)
			return false;
		
		//这里需要删除HDFS和Hive上的
		//先获取该数据源信息
		DataSource ds = getDataSourceById(userId, id);
		System.out.println(ds);
		boolean result = ds != null ? (deleteDatasource(ds, userId) ? dsDao.deleteDataSource(id, userId) : false) : false;
		System.out.println(result);
		
		return result;
	}
	
	//删除数据源信息
	public boolean deleteDatasource(DataSource ds, Integer userId){
		boolean flag = false;
		if(ds.getType() == 0){//HDFS
			flag = new HDFSUtil(new PropertiesUtil("system.properties").readPropertyByKey("hostName")).delete2(ds.getHdfsPath(), true);
		}else{//hive类型
			//切换到对应的数据库，并且执行删除表操作
			flag = new HiveUtil("user" + userId).delTable(ds.getTableName());
		}
		System.out.println("flag:"+flag);
		return flag;
	}
	
	/**
	 * 添加数据源，type 为0 hdfsPath不能为空
	 * type 为1 tablename 和 tabledesc不能为空
	 * tabledesc是表的数据结构，形式：id_int,name_string这样的类型
	 * */
	public boolean addDataSource(DataSource ds){
		if(ds == null)
			return false;
//		if(ds.getGroupid() == null) {
		DataGroupService dGroupService = new DataGroupService();
		int groupId = dGroupService.getGroupIdByUserId(ds.getUserId());
		dGroupService.close();
		ds.setGroupid(groupId);
//		}
		return dsDao.addDatasource(ds);
	}
	
	/**
	 * 关闭
	 * */
	public void close(){
		dsDao.close();
	}

	public DataSource getDataSourceByName(String source_name, int userId) {
		return dsDao.getDataSourceByName(source_name, userId);
	}
}

package com.bigdata.service.datagroup;

import java.util.List;

import com.bigdata.bean.DataGroup;
import com.bigdata.bean.DataSource;
import com.bigdata.dao.datagroup.DataGroupDao;
import com.bigdata.dao.datasource.DataSourceDao;
import com.bigdata.service.datasource.DataSourceService;

public class DataGroupService {
	
	private DataGroupDao groupDao = new DataGroupDao();
	
	/**
	 * 添加分组
	 * */
	public int addGroup(DataGroup group){
		if(group == null || group.getUserId() == null || group.getName() == null)
			return 0;
		if("".equals(group.getName()))
			return 0;
		return groupDao.isExist(group) ? 1 : groupDao.addGroup(group) ? 2 : 0;
	}
	
	/**
	 * 删除分组
	 * */
	public boolean deleteGroup(DataGroup group){
		if(group == null)
			return false;
		if(group.getId() == null || group.getUserId() == null)
			return false;
		
		//这里删除组的话需要把该组下的所有数据源都清空
		//先获取数据
		DataSourceService dsService = new DataSourceService();
		List<DataSource> datasources = dsService.getDataSourceList(group.getUserId(), group.getId());
		//如果获取到的数据为空或者没有记录则不需要进行删除操作
		if(datasources != null && datasources.size() > 0){
			for (DataSource dataSource : datasources) {
				dsService.deleteDatasource(dataSource, group.getUserId());
			}
		}
		
		return groupDao.deleteGroup(group);
	}
	
	/**
	 * 修改分组名称
	 * */
	public int modify(DataGroup group){
		return isNotEmpty(group) ? (groupDao.isExist(group) ? 1 : groupDao.modify(group) ? 2 : 0) : 0;
	}
	
	/**
	 * 查询分组列表
	 * 传入用户id
	 * */
	public List<DataGroup> getDataGroups(Integer userId){
		return userId != null ? groupDao.getDataGroups(userId) : null;
	}
	
	public int getGroupIdByUserId(Integer userId) {
		return groupDao.getGroupIdByUserId(userId);
	}
	
	/**
	 * 关闭连接
	 * */
	public void close(){
		groupDao.close();
	}
	
	/**
	 * 判断是否为空
	 * */
	public boolean isNotEmpty(DataGroup group){
		if(group == null)
			return false;
		if(group.getId() == null || group.getUserId() == null)
			return false;
		if(group.getName() == null || "".equals(group.getName()))
			return false;
		return true;
	}

}

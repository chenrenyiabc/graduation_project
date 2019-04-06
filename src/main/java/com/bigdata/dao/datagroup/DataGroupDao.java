package com.bigdata.dao.datagroup;

import java.util.List;

import com.bigdata.bean.DataGroup;
import com.bigdata.util.DBUtils;

public class DataGroupDao {

	private DBUtils util = DBUtils.getDBUtils();
	
	/**
	 * 添加分组
	 * */
	public boolean addGroup(DataGroup group){
		return util.update("insert into data_group(name,userid) values(?,?)", group.getName(), group.getUserId());
	}
	
	/**
	 * 删除分组
	 * */
	public boolean deleteGroup(DataGroup group){
		return util.update("delete from data_group where id=? and userid=?", group.getId(), group.getUserId());
	}
	
	/**
	 * 修改分组名称
	 * */
	public boolean modify(DataGroup group){
		return util.update("update data_group set name=? where id=? and userid=?", group.getName(), group.getId(), group.getUserId());
	}
	
	/**
	 * 查询用户下是否存在该组
	 * */
	public boolean isExist(DataGroup group){
		return util.queryInt("select count(*) from data_group where name=? and userId=?", group.getName(), group.getUserId()) > 0;
	}
	
	/**
	 * 查询分组列表
	 * */
	public List<DataGroup> getDataGroups(Integer userId){
		return util.query("select g.id,g.name,count(s.id) as userid from data_group g left JOIN data_source s on g.id=s.groupid and g.userId=s.userid where g.userId=? GROUP BY g.id ", DataGroup.class, userId);
	}
	
	/**
	 * 查询用户的最小groupid
	 * */
	public int getGroupIdByUserId(Integer userId) {
		return util.queryInt("select min(id) from data_group where userid=?", userId);
	}
	
	/**
	 * 关闭
	 * */
	public void close(){
		util.close();
	}	
	
}

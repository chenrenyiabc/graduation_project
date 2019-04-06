package com.bigdata.bean;

import com.alibaba.fastjson.JSONObject;

public class DataSource {
	
	private Integer id;
	
	private Integer userId;
	
	private String name;
	
	private Integer groupid;
	
	private Integer type;
	
	private String tableName;
	
	private String tableDesc;//id_int,name_string
	
	private String createtime;
	
	private String hdfsPath;	
	
 	public DataSource() {
		super();
	}

	public DataSource(Integer id, Integer userId, String name, Integer groupid, Integer type, String tableName,
			String tableDesc, String createtime, String hdfsPath) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.groupid = groupid;
		this.type = type;
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.createtime = createtime;
		this.hdfsPath = hdfsPath;
	}

	//HDFS
	public DataSource(Integer userId, String name, Integer type,
			String createtime, String hdfsPath) {
		super();
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.createtime = createtime;
		this.hdfsPath = hdfsPath;
	}
	
	//HIVE
	public DataSource(Integer userId, String name, Integer type,
			String tableName, String tableDesc, String createtime) {
		super();
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.tableName = tableName;
		this.tableDesc = tableDesc;
		this.createtime = createtime;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getHdfsPath() {
		return hdfsPath;
	}

	public void setHdfsPath(String hdfsPath) {
		this.hdfsPath = hdfsPath;
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
}

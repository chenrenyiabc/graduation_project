package com.bigdata.bean;

public class DataFlow {
	
	
	public DataFlow() {

	}
	
	public DataFlow(String name, Integer userId, Integer flow_status, Integer source_id, Integer flow_type,
			String hive_sql, Integer mr_id, String result_table, String result_path) {
		super();
		this.name = name;
		this.userId = userId;
		this.flow_status = flow_status;
		this.source_id = source_id;
		this.flow_type = flow_type;
		this.hive_sql = hive_sql;
		this.mr_id = mr_id;
		this.result_table = result_table;
		this.result_path = result_path;
	}
	
	public DataFlow(Integer id, String name, Integer userId, Integer flow_status, Integer source_id, Integer flow_type,
			String hive_sql, Integer mr_id, String result_table, String result_path) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.flow_status = flow_status;
		this.source_id = source_id;
		this.flow_type = flow_type;
		this.hive_sql = hive_sql;
		this.mr_id = mr_id;
		this.result_table = result_table;
		this.result_path = result_path;
	}

	private Integer id;
	private String name;
	private Integer userId;
	private Integer flow_status;
	private Integer source_id;
	private Integer flow_type;
	private String hive_sql;
	private Integer mr_id;
	private String result_table;
	private String result_path;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getFlow_status() {
		return flow_status;
	}
	public void setFlow_status(Integer flow_status) {
		this.flow_status = flow_status;
	}
	public Integer getSource_id() {
		return source_id;
	}
	public void setSource_id(Integer source_id) {
		this.source_id = source_id;
	}
	public Integer getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(Integer flow_type) {
		this.flow_type = flow_type;
	}
	public String getHive_sql() {
		return hive_sql;
	}
	public void setHive_sql(String hive_sql) {
		this.hive_sql = hive_sql;
	}
	public Integer getMr_id() {
		return mr_id;
	}
	public void setMr_id(Integer mr_id) {
		this.mr_id = mr_id;
	}
	public String getResult_table() {
		return result_table;
	}
	public void setResult_table(String result_table) {
		this.result_table = result_table;
	}
	public String getResult_path() {
		return result_path;
	}
	public void setResult_path(String result_path) {
		this.result_path = result_path;
	}
	@Override
	public String toString() {
		return "DataFlow [id=" + id + ", name=" + name + ", userId=" + userId + ", flow_status=" + flow_status
				+ ", source_id=" + source_id + ", flow_type=" + flow_type + ", hive_sql=" + hive_sql + ", mr_id="
				+ mr_id + ", result_table=" + result_table + ", result_path=" + result_path + "]";
	}
	
	
}

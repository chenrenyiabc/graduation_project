package com.bigdata.bean;

public class FlowList {

	private Integer id;
	private String name;
	private String userName;
	private String flow_status;
	private String sourceName;
	private String flow_type;
	private String hive_sql;
	private String algorithm_name;
	private String result_table;
	private String result_path;
	
	public FlowList() {
		super();
	}

	public FlowList(Integer id, String name, String userName, Integer flow_status, String sourceName, Integer flow_type,
			String hive_sql, String algorithm_name, String result_table, String result_path) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		switch (flow_status) {
		case 0:
			this.flow_status = "未开始";
			break;
		case 1:
			this.flow_status = "运行中";
			break;
		case 2:
			this.flow_status = "已完成";
			break;
		default:
			this.flow_status = "运行异常";
			break;
		}
		this.sourceName = sourceName;
		switch (flow_type) {
		case 0:
			this.flow_type= "MapReduce"; 
			break;
		case 1:
			this.flow_type= "Hive_SQL"; 
			break;
		default:
			this.flow_type= " ";
			break;
		}
		this.hive_sql = hive_sql;
		this.algorithm_name = algorithm_name;
		this.result_table = result_table;
		this.result_path = result_path;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFlow_status() {
		return flow_status;
	}

	public void setFlow_status(String flow_status) {
		this.flow_status = flow_status;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}

	public String getHive_sql() {
		return hive_sql;
	}

	public void setHive_sql(String hive_sql) {
		this.hive_sql = hive_sql;
	}

	public String getAlgorithm_name() {
		return algorithm_name;
	}

	public void setAlgorithm_name(String algorithm_name) {
		this.algorithm_name = algorithm_name;
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
	
	
}

package com.bigdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.FlowList;
import com.bigdata.util.DBUtils;

public class DataFLowDao {

	private DBUtils dUtils = DBUtils.getDBUtils();
	private ResultSet rs = null;

	/**
	 * 根据流程id获取相应流程
	 * @param id 流程id
	 * @return DataFlow
	 */
	public DataFlow getFlow(int id) {
		DataFlow dataFlow = null;
		rs = dUtils.queryResult("SELECT * FROM data_flow WHERE id="+id, null);
		try {
			rs.next();
			dataFlow = new DataFlow(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getString(10));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFlow;
	}

	/**
	 * 获取流程列表
	 * @return List<FlowList>
	 */
	public List<FlowList> getFlowList(int userId) {
		List<FlowList> list = new ArrayList<>();
		rs = dUtils.queryResult(
				"SELECT data_flow.id,data_flow.`name`,`user`.`name`,data_flow.flow_status,data_source.`name`,data_flow.flow_type,data_flow.hive_sql,`algorithm`.algorithm_name,data_flow.result_table,data_flow.result_path "
						+ "FROM data_flow LEFT JOIN `user` ON data_flow.userId=`user`.id LEFT JOIN data_source ON data_source.id=data_flow.source_id LEFT JOIN `algorithm` ON data_flow.mr_id=`algorithm`.id "
						+"where data_flow.userId="+userId
						+ " GROUP BY data_flow.id",
				null);
		try {
			while (rs.next()) {
				list.add(new FlowList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据id删除相应流程
	 * @param id 流程id
	 * @return 状态
	 */
	public Integer delete(Integer id) {
		return dUtils.update("DELETE FROM data_flow WHERE id =" + id, null) ? 1 : 0;
	}
	
	/**
	 * 根据id删除相应流程
	 * @param id 流程id
	 * @param state 状态,0未开始，1开始，3成功，其他异常
	 * @return 运行状态
	 */
	public Integer chageState(Integer id,Integer state) {
		return dUtils.update("UPDATE data_flow SET flow_status=" + state + " WHERE id=" + id, null) ? 1 : 0;
	}

	/**
	 * 关闭对象
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			dUtils.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

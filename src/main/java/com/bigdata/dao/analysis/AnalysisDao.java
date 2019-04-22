package com.bigdata.dao.analysis;

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.DataSource;
import com.bigdata.util.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisDao {

    DBUtils dbUtils = DBUtils.getDBUtils();

    public List<String> queryDataSource(int userId) {
        ResultSet rs = dbUtils.queryResult("select name from data_source where type=0 and userid=?", userId);

        List<String> dataSource = new ArrayList<>();

        try {
            while(rs.next()) {
                dataSource.add(rs.getString(1));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return dataSource;
    }

    public Boolean saveFlow(String name, int userId, int flow_status, int source_id, int flow_type, String hive_sql, Object o, String result_table, String result_path) {
        String sql = "insert into data_flow values(null,?,?,?,?,?,?,?,?,?)";
        return dbUtils.update(sql, name, userId, flow_status, source_id, flow_type, hive_sql, o, result_table, result_path);

    }

    public Boolean saveFlowHQL(String name, int userId, int flow_status, int flow_type, String hive_sql, String result_table) {
        String sql = "insert into data_flow values(null,?,?,?,null,?,?,null,?,null)";
        return dbUtils.update(sql, name, userId, flow_status, flow_type, hive_sql, result_table);
    }

    public DataFlow queryExistFlow(String flowId) {
        String sql = "select * from data_flow where id=?";
        return dbUtils.queryObject(sql, DataFlow.class, flowId);
    }

    public Boolean updateMRFlow(String process_name, String chooseData, String chooseAlgorithm, String resultPath, String flowId) {
        String sql = "update data_flow set name=?,source_id=?,mr_id=?,result_path=? where id=?";
        return dbUtils.update(sql, process_name, chooseData, chooseAlgorithm, resultPath, flowId);
    }

    public Boolean updateHQLFlow(String name, String hive_sql, String result_table, String flowId) {
        String sql = "update data_flow set name=?,hive_sql=?,result_table=? where id=?";
        return dbUtils.update(sql, name, hive_sql, result_table, flowId);
    }
}

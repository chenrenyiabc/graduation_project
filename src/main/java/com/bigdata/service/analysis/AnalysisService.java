package com.bigdata.service.analysis;

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.DataSource;
import com.bigdata.dao.analysis.AnalysisDao;

import java.util.List;

public class AnalysisService {

    AnalysisDao ad = new AnalysisDao();

    public List<String> queryDataSource(int userId) {
        return ad.queryDataSource(userId);
    }

    public Boolean saveFlow(String name, int userId, int flow_status, int source_id, int flow_type, String hive_sql, Object o, String result_table, String result_path) {
        return ad.saveFlow(name, userId, flow_status, source_id, flow_type, hive_sql, o, result_table, result_path);
    }

    public Boolean saveFlowHQL(String name, int userId, int flow_status, int flow_type, String hive_sql, String result_table) {
        return ad.saveFlowHQL(name, userId, flow_status, flow_type, hive_sql, result_table);
    }

    public DataFlow queryExistFlow(String flowId) {
        return ad.queryExistFlow(flowId);
    }

    public Boolean updateMRFlow(String process_name, String chooseData, String chooseAlgorithm, String resultPath, String flowId) {
        return ad.updateMRFlow(process_name, chooseData, chooseAlgorithm, resultPath, flowId);
    }

    public Boolean updateHQLFlow(String name, String hive_sql, String result_table, String flowId) {
        return ad.updateHQLFlow(name, hive_sql, result_table, flowId);
    }
}

package com.bigdata.dao.analysis;

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
}

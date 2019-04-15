package com.bigdata.service.analysis;

import com.bigdata.bean.DataSource;
import com.bigdata.dao.analysis.AnalysisDao;

import java.util.List;

public class AnalysisService {

    AnalysisDao ad = new AnalysisDao();

    public List<String> queryDataSource(int userId) {
        return ad.queryDataSource(userId);
    }
}

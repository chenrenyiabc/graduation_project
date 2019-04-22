package com.bigdata.service.analysis;

import com.bigdata.dao.analysis.HiveDao;

import java.util.List;

public class HiveService {

    HiveDao hiveDao = new HiveDao();

    public List<String> queryHQLTableList(String s) {
        return hiveDao.queryHQLTableList(s);
    }

    public List<String> queryTableField(String s, String tableName) {
        return hiveDao.queryTableField(s, tableName);
    }
}

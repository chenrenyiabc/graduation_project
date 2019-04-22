package com.bigdata.dao.analysis;

import com.bigdata.util.HiveUtil;

import java.util.List;

public class HiveDao {

    HiveUtil hiveUtil = new HiveUtil();

    public List<String> queryHQLTableList(String s) {
        hiveUtil.changeDatabase(s);
        return hiveUtil.getTaleList();

    }

    public List<String> queryTableField(String s, String tableName) {
        hiveUtil.changeDatabase(s);
        return hiveUtil.getTableInfo(tableName);
    }
}

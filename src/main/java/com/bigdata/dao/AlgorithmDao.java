package com.bigdata.dao;

import com.bigdata.bean.Algorithm;
import com.bigdata.util.DBUtils;

public class AlgorithmDao {

	private DBUtils util = DBUtils.getDBUtils();
	
	/**
	 * 通过算法id获取算法信息
	 * @param mr_id 算法id
	 * @return
	 */
	public Algorithm getAlgorithmPath(Integer mr_id) {
		return util.queryObject("select * from algorithm where id=?", Algorithm.class, mr_id);
	}
	
	public void close(){
		util.close();
	}

}

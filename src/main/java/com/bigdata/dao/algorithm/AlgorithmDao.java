package com.bigdata.dao.algorithm;

import com.bigdata.bean.Algorithm;
import com.bigdata.util.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmDao {

	private DBUtils util = DBUtils.getDBUtils();

	public void close(){
		util.close();
	}
	
	/**
	 * 通过算法id获取算法信息
	 * @param mr_id 算法id
	 * @return
	 */
	public Algorithm getAlgorithmPath(Integer mr_id) {
		return util.queryObject("select * from algorithm where id=?", Algorithm.class, mr_id);
	}
	


    public List<Algorithm> getAlgorithm() {
		return util.query("select * from algorithm", Algorithm.class);
    }


	public Boolean updateAlgorithm(String algoId, String algorithm_name, String algorithm_path, String algorithm_class) {
		return util.update("update algorithm set algorithm_name=?,algorithm_path=?,algorithm_class=? where id=?", algorithm_name, algorithm_path, algorithm_class, algoId);
	}

	public Boolean newAlgorithm(String algorithm_name, String algorithm_path, String algorithm_class) {
		return util.update("insert into algorithm values(null,?,?,?)", algorithm_name, algorithm_path, algorithm_class);
	}



	public Boolean deleteAlgorithm(String algoId) {
		return util.update("delete from algorithm where id=?", algoId);
	}

	public List<String> queryAlgoName() {
		ResultSet rs = util.queryResult("select algorithm_name from algorithm");
		List<String> algorithm = new ArrayList<>();
		try {
			while(rs.next()) {
				algorithm.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return algorithm;
	}
}

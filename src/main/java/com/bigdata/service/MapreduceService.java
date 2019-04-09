package com.bigdata.service;

import com.bigdata.bean.Algorithm;
import com.bigdata.bean.DataSource;
import com.bigdata.dao.algorithm.AlgorithmDao;
import com.bigdata.dao.datasource.DataSourceDao;
import com.bigdata.util.PropertiesUtil;
import com.bigdata.util.RemoteUtil;

public class MapreduceService {

	public MapreduceService() {
	}



	/**
	 * 运行MR流程
	 * @param userId 用户ID
	 * @param source_id 源ID
	 * @param mr_id 算法ID
	 * @param result_path 结果路径
	 * @return 是否运行成功
	 */
	public boolean runFlow(Integer userId,Integer source_id, Integer mr_id, String result_path) {
		DataSourceDao dataSourceDao = new DataSourceDao();
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		System.out.println(userId);
		System.out.println(source_id);
		DataSource dataSource = dataSourceDao.getDataSourceById(source_id);
		AlgorithmDao aDao = new AlgorithmDao();
		Algorithm algorithm = aDao.getAlgorithmPath(mr_id);
		
		System.out.println(dataSource);
		// 接收参数 - 源文件路径
        String inputPath = dataSource.getHdfsPath();
        // 接收参数 - jar包路径
        System.out.println(algorithm);
        String algorithm_name = algorithm.getAlgorithm_name();
        String mainClass = propertiesUtil.readPropertyByKey(algorithm_name);
        System.out.println(mainClass);


        
        // 初始化配置文件读取工具类

        
        // 初始化远程登录Linux执行命令工具类
        String host = propertiesUtil.readPropertyByKey("hostName");
        String userName = propertiesUtil.readPropertyByKey("hadoopUser");
        String userPwd = propertiesUtil.readPropertyByKey("hadoopPwd");
        RemoteUtil remoteUtil = new RemoteUtil(host, userName, userPwd);
        
        //执行hadoop命令
        String jarPath = propertiesUtil.readPropertyByKey("jarPath");
        String hadoopBin = propertiesUtil.readPropertyByKey("hadoopBinHome");
        String cmd = hadoopBin + "/hadoop jar " + jarPath + " " + mainClass + " " + inputPath + " " + result_path;
        System.out.println(cmd);
        String result = remoteUtil.execute(cmd);
        System.out.println(result);

		dataSourceDao.close();
		aDao.close();
		return result.contains("Congratulations!");
	}
	
}

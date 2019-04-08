package com.bigdata.service.user;

import com.bigdata.bean.DataGroup;
import com.bigdata.bean.User;
import com.bigdata.dao.user.UserDao;
import com.bigdata.service.datagroup.DataGroupService;
import com.bigdata.util.HDFSUtil;
import com.bigdata.util.HiveUtil;
import com.bigdata.util.PropertiesUtil;

public class UserService {
	
	private UserDao userDao = new UserDao();
	
	//登录
	public User login(User user){
		if(user == null)
			return user;
		if(user.getUsername() == null || "".equals(user.getUsername()))
			return null;
		if(user.getPassword() == null || "".equals(user.getPassword()))
			return null;
		else
			return userDao.login(user);
	}
	
	//注册 0 代表成功 1 失败 2重复
	public int regist(User user){
		int result = 1;
		if(user != null){
			String username = user.getUsername();
			String password = user.getPassword();
			String name = user.getName();
			if(username != null && !"".equals(username) && password != null && !"".equals(password)
				 &&	name != null && !"".equals(name)){
				//说明没有查询到
				if(userDao.getUserByUsername(username) == null){
					boolean flag = userDao.regist(user);
					result = flag ? 0 : 1;
				}else{//重复了
					result = 2;
				}
			}
		}
		
		//这里需要创建用户目录空间和hive表
		if(result == 0){
			//先获取id
			int id = userDao.getId(user);
			
			//创建默认分组
			DataGroupService dgService = new DataGroupService();
			dgService.addGroup(new DataGroup(null, "default", id));
			
			//创建目录
			PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
			HDFSUtil hdfsUtil = new HDFSUtil(propertiesUtil.readPropertyByKey("hostName"));
			hdfsUtil.mkdirs2(propertiesUtil.readPropertyByKey("userDataDir") + id, true);
			
			//创建hive库
			HiveUtil hiveUtil = new HiveUtil();
			hiveUtil.changeDatabase("user" + id);
		}
		
		return result;
	}
	
	//重置密码
	public boolean resetPassword(User user){
		boolean flag = false;
		if(user != null){
			String username = user.getUsername();
			String password = user.getPassword();
			String name = user.getName();
			if(username != null && !"".equals(username) && password != null && !"".equals(password)
				 &&	name != null && !"".equals(name)){
				//说明这个用户和姓名是匹配的
				if(userDao.getUserByNameAndUsername(user) != null){
					flag = userDao.resetPassword(user);
				}
			}
		}
		return flag;
	}
	
	//修改密码
	public boolean modifyPass(User user){
		if(user == null || user.getId() == null)
			return false;
		return userDao.modifyPass(user);
	}
	
	public void close(){
		userDao.close();
	}

}

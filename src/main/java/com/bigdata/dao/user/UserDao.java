package com.bigdata.dao.user;

import com.bigdata.bean.User;
import com.bigdata.util.DBUtils;

public class UserDao {
	
	private DBUtils util = DBUtils.getDBUtils();

	//登录
	public User login(User user){
		String sql = "select * from user where username=? and password=?";
		return util.queryObject(sql, User.class, user.getUsername(), user.getPassword());
	}
	
	//注册验重
	public User getUserByUsername(String username){
		String sql = "select * from user where username=?";
		return util.queryObject(sql, User.class, username);
	}
	
	//注册
	public boolean regist(User user){
		System.out.println("fffff:" + user);
		String sql = "insert into user(username,name,password) values(?,?,?)";
		return util.update(sql, user.getUsername(), user.getName(), user.getPassword());
	}
	
	//获取对应的id
	public int getId(User user){
		String sql = "select id from user where username=? and name=? and password=?";
		return util.queryInt(sql, user.getUsername(), user.getName(), user.getPassword());
	}
	
	//重置密码
	public boolean resetPassword(User user){
		String sql = "update user set password=? where username=? and name=?";
		return util.update(sql, user.getPassword(), user.getUsername(), user.getName());
	}
	
	//验证用户名和姓名是否符合
	public User getUserByNameAndUsername(User user){
		String sql = "select * from user where username=? and name=?";
		return util.queryObject(sql, User.class, user.getUsername(), user.getName());
	}
	
	//修改密码
	public boolean modifyPass(User user){
		String sql = "update user set password=? where id=?";
		return util.update(sql, user.getPassword(), user.getId());
	}
	
	//关闭
	public void close(){
		util.close();
	}
}

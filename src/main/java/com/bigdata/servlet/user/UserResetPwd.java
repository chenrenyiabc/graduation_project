package com.bigdata.servlet.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.bean.User;
import com.bigdata.service.user.UserService;
import com.bigdata.util.RequestUtil;

@WebServlet("/user/resetpwd")
public class UserResetPwd extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		User user = RequestUtil.get(User.class, req);
		String password2 = req.getParameter("password2");
		boolean flag = false;
		if(password2 != null && password2.equals(user.getPassword())){
			UserService us = new UserService();
			flag = us.resetPassword(user);
			us.close();
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", flag);
		resp.getWriter().print(JSONObject.toJSONString(map));
	}
	
}

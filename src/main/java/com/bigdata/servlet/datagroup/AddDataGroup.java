package com.bigdata.servlet.datagroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.bigdata.bean.DataGroup;
import com.bigdata.bean.User;
import com.bigdata.service.datagroup.DataGroupService;
import com.bigdata.util.RequestUtil;

@WebServlet("/datagroup/add")
public class AddDataGroup extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置编码
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//从session中获取user
		User user = (User)req.getSession().getAttribute("user");
		//request请求参数封装成实体类
		DataGroup group = RequestUtil.get(DataGroup.class, req);
		//设置group的userid
		group.setUserId(user.getId());
		DataGroupService service = new DataGroupService();
		//添加group
		int flag = service.addGroup(group);//0 失败 1重复 2成功
		Map<String, Object> result = new HashMap<>();
		//如果添加成功，重新查询分组信息，并且放入session
		if(flag == 2){
			List<DataGroup> list = service.getDataGroups(user.getId());
			req.getSession().setAttribute("groups", list);
			result.put("groups", list);
		}
		result.put("result", flag);
		service.close();
		resp.getWriter().print(JSONObject.toJSONString(result));
		resp.getWriter().close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}

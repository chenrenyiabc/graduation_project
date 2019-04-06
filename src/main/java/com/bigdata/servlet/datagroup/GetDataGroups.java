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

@WebServlet("/datagroup/getGroups")
public class GetDataGroups extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//设置编码
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//从session中获取user
		User user = (User)req.getSession().getAttribute("user");
		DataGroupService service = new DataGroupService();
		// 获取分组列表
		// 这里获取的结构是[{id,name,userid}] 这里的userid不是用户id了，而是该组下的数据源数量
		List<DataGroup> list = service.getDataGroups(user.getId());
		service.close();
		Map<String, Object> result = new HashMap<>();
		//如果添加成功，重新查询分组信息，并且放入session
			
		req.getSession().setAttribute("groups", list);
		result.put("groups", list);
		resp.getWriter().print(JSONObject.toJSONString(result));
		resp.getWriter().close();
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}

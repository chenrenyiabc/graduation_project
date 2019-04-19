package com.bigdata.servlet.datasource;

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
import com.bigdata.bean.DataSource;
import com.bigdata.bean.User;
import com.bigdata.service.datagroup.DataGroupService;
import com.bigdata.service.datasource.DataSourceService;

@WebServlet("/datasource/delete")
public class DeleteDatasource extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置编码
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//从session中获取user
		User user = (User)req.getSession().getAttribute("user");
		
		Integer id = Integer.valueOf(req.getParameter("id"));
		
		DataSourceService service = new DataSourceService();
		boolean flag = service.deleteDataSource(id, user.getId());
		Map<String, Object> result = new HashMap<>();
		//如果删除成功，重新查询分组信息和数据源列表//放置于session中
		if(flag){
			Map<String, List<DataSource>> datasources = service.getDataSourceList(user.getId());
			req.getSession().setAttribute("datasources", datasources);
			result.put("datasources", datasources);
			
			DataGroupService dgService = new DataGroupService();
			List<DataGroup> list = dgService.getDataGroups(user.getId());
			dgService.close();
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

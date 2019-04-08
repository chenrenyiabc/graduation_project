package com.bigdata.servlet.flowManage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.User;
import com.bigdata.dao.DataFLowDao;
import com.bigdata.service.MapreduceService;
import com.bigdata.util.HiveUtil;
import com.bigdata.util.PropertiesUtil;

@WebServlet("/RunFlow")
public class RunFlow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
	String userOutputHdfs = propertiesUtil.readPropertyByKey("userOutputHdfs");

	public RunFlow() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Integer id = Integer.parseInt(request.getParameter("id"));
		int userId = ((User)request.getSession().getAttribute("user")).getId();
		DataFLowDao dFLowDao = new DataFLowDao();
		PrintWriter out = null;
		out = response.getWriter();
		String result = "开始";
		dFLowDao.chageState(id, 1);
		out.write(result);
		if (out != null) {
			out.close();
		}

		DataFlow df = dFLowDao.getFlow(id);
		switch (df.getFlow_type()) {
		case 0:// mr
			MapreduceService mapreduceService = new MapreduceService();
			boolean bl = mapreduceService.runFlow(df.getUserId(), df.getSource_id(), df.getMr_id(),
					userOutputHdfs  + "user" + df.getUserId()+ "/" + df.getResult_path());
			System.out.println(bl);
			if (bl) {
				dFLowDao.chageState(id, 2);
			} else {
				dFLowDao.chageState(id, 3);
			}
			break;
		case 1:// hql

			HiveUtil hUtil = new HiveUtil();
			//hUtil.changeDatabase("user" + df.getUserId());
			hUtil.changeDatabase("user"+userId);
			if (hUtil.runHQL(df.getHive_sql(), df.getResult_table())) {
				dFLowDao.chageState(id, 2);
			} else {
				dFLowDao.chageState(id, 3);
			}
			hUtil.close();
			break;
		default:
			dFLowDao.chageState(id, 3);
			break;
		}

		dFLowDao.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

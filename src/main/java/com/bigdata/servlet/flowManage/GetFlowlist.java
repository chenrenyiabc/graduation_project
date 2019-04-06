package com.bigdata.servlet.flowManage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.FlowList;
import com.bigdata.bean.User;
import com.bigdata.dao.DataFLowDao;
import com.bigdata.util.wwh.DBUtil;
import com.google.gson.Gson;

@WebServlet("/GetFlowlist")
public class GetFlowlist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetFlowlist() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		DataFLowDao dFLowDao = new DataFLowDao();
		Gson gs = new Gson();
		
		PrintWriter out = null;
		out = response.getWriter();
		out.write(gs.toJson(dFLowDao.getFlowList(((User)request.getSession().getAttribute("user")).getId())));

		if (out != null) {
			out.close();
		}
		dFLowDao.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

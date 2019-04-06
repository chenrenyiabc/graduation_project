package com.bigdata.servlet.resultDataShow;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.User;
import com.bigdata.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getResultTables
 */
@WebServlet("/getResultTables")
public class getResultTables extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getResultTables() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		DBUtils dbUtils = DBUtils.getDBUtils();
		List<String> tableList = new ArrayList<>();
		List<String> pathList = new ArrayList<>();
		
		int userId = ((User) request.getSession().getAttribute("user")).getId();
		
		String sql = "select result_table from data_flow where result_table is not null and userid=" + userId;
		ResultSet tableRs = dbUtils.queryResult(sql);
		sql = "select result_path from data_flow where result_path is not null and userid=" + userId;
		ResultSet pathRs = dbUtils.queryResult(sql);
		try {
			while(tableRs.next()) {
				tableList.add(tableRs.getString("result_table"));
			}
			while(pathRs.next()) {
				pathList.add(pathRs.getString("result_path"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map<String, List<String>> map = new HashMap<>();
		map.put("table", tableList);
		map.put("path", pathList);
		out.print(JSONArray.fromObject(map).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

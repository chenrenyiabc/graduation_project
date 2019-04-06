package com.bigdata.servlet.resultDataShow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.User;
import com.bigdata.util.*;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetResultTableColumns
 */
@WebServlet("/GetResultTableColumns")
public class GetResultTableColumns extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetResultTableColumns() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int userId = ((User) request.getSession().getAttribute("user")).getId();
		String name = request.getParameter("tableName");
		HiveUtil hiveUtil = new HiveUtil();
		hiveUtil.changeDatabase("user"+userId);
		List<String> list = hiveUtil.getTableInfo(name);
		
		out.print(JSONArray.fromObject(list).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

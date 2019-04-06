package com.bigdata.servlet.flowManage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.dao.DataFLowDao;

/**
 * Servlet implementation class DelFlow
 */
@WebServlet("/DelFlow")
public class DelFlow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DelFlow() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		Integer id = Integer.parseInt(request.getParameter("id"));

		DataFLowDao dFLowDao = new DataFLowDao();
		PrintWriter out = null;
		out = response.getWriter();
		out.write(dFLowDao.delete(id).toString());

		if (out != null) {
			out.close();
		}
		dFLowDao.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

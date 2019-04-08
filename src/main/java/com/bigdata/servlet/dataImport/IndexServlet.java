package com.bigdata.servlet.dataImport;

import com.bigdata.util.PropertiesUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//读取配置文件信息
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		String hadoopUser = propertiesUtil.readPropertyByKey("hadoopUser");
		String HadoopDir = propertiesUtil.readPropertyByKey("HadoopDir");
		String hiveDatabase = propertiesUtil.readPropertyByKey("hiveDatabase");
		//创建一个Session对象保存用户信息
		HttpSession session = request.getSession();
		session.setAttribute("hadoopUser", hadoopUser);
		session.setAttribute("HadoopDir", HadoopDir);
		session.setAttribute("hiveDatabase", hiveDatabase);
		//传递给index页面
		request.getRequestDispatcher("index.jsp").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

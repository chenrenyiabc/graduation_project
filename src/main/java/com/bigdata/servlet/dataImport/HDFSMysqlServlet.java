package com.bigdata.servlet.dataImport;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.User;
import com.bigdata.util.DBUtils;
import com.bigdata.util.PropertiesUtil;
import com.bigdata.util.RemoteUtil;
import com.bigdata.util.TimeUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class HDFSMysqlServlet
 */           
@WebServlet("/HDFSMysqlServlet")
public class HDFSMysqlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HDFSMysqlServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;CharSet=UTF-8");

		TimeUtil selfUtil = new TimeUtil();
		DBUtils dbUtils = DBUtils.getDBUtils();
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");

		String hostName = propertiesUtil.readPropertyByKey("hostName");
		String hadoopUser = propertiesUtil.readPropertyByKey("hadoopUser"); // 远程用户名
		String hadoopPwd = propertiesUtil.readPropertyByKey("hadoopPwd"); // 远程账号密码


		// 公用变量
		ResultSet list = null;
		String tableName = null;
		List<String> tableList = new ArrayList<>();
		String cmd = "source .bash_profile && ";
		String format = "yyyy-MM-dd hh:mm:ss"; 
		String dateStr = "";

		// 使用远程登录，执行sqoop
		String jdbcUrl = "jdbc:" + "mysql" + "://" + hostName + ":" + 3306 + "/";
		RemoteUtil remoteUtil = new RemoteUtil(hostName, hadoopUser, hadoopPwd); // 远程登录主机

		User user = (User) request.getSession().getAttribute("user");
		String method = request.getParameter("method");
		switch (method) {

		case "showMysqldata":
			tableName = request.getParameter("tableName");
			// 进入数据库
			dbUtils.update("use graduation_project");
			// 首先获取数据表的列名和列数
			list = dbUtils.queryResult("desc " + tableName);
			String colnames = "";

			int count = 0;
			try {
				while (list.next()) {
					colnames += list.getString(1) + " ";
					count++;
				}
				// 真正查询的结果(使用map结构进行保存，key列名，value数据)
				List result = new ArrayList<>();
				result.add(colnames);
				list = dbUtils.queryResult("select * from " + request.getParameter("tableName") + " limit 10");
				while (list.next()) {
					List tempList = new ArrayList<>();
					for (int i = 1; i <= count; i++) {
						tempList.add(list.getString(i));
					}
					String temp = "";
					for (Object object : tempList) {
						temp += object + " ";
					}
					result.add(temp);
				}
				// 把数据转化成列表保存(方便在页面上进行展示)
				response.getWriter().write(JSONArray.fromObject(result).toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "showMysqlTableInput":
			// 进入数据库
			dbUtils.update("use graduation_project");
			// 查看所有的表
			list = dbUtils.queryResult("show tables");
			ArrayList tablelist = new ArrayList<>();
			tablelist.add(" ");
			try {
				while (list.next()) {
					tablelist.add(list.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.getWriter().write(JSONArray.fromObject(tablelist).toString());
			break;

		case "showMysqlColumnInput":
			// 进入数据库
			dbUtils.update("use graduation_project");
			// 出去表中所有的列
			list = dbUtils.queryResult("desc " + request.getParameter("tableName"));
			ArrayList tablelist2 = new ArrayList<>();
			try {
				while (list.next()) {
					tablelist2.add(list.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.getWriter().write(JSONArray.fromObject(tablelist2).toString());
			break;

		case "inputMysql2HdfsSingle":
			//根据Mysql表导出数据到HDFS
			//获取表名
			tableName = request.getParameter("tableName");
			//获取列名
			String[] columns = request.getParameterValues("columns");
			String column = "";
			for (String string : columns) {
				column += string + ",";
			}
			column = column.substring(0, column.length() - 1);
			//获取目录(自动生成目录，需传入不存在的目录)
			String targetDir = request.getParameter("targetDir");
			//拼接命令
			cmd += "sqoop import  --connect " + jdbcUrl + "bigdatademo" + " --username " + " root " + " --password "
					+ " root " + " --table " + tableName + " --target-dir " + targetDir + " --columns " + column
					+ " -m 1 ";
			System.out.println(cmd);
			remoteUtil.execute(cmd);
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
			dbUtils.update("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),targetDir+selfUtil.getTime(),1,0,null,null,dateStr,targetDir});
			
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			break;
		
		case "inputMysql2HdfsSelf":
			//自定义导出Mysql数据到HDFS(需要输入sqool命令)
			String selfCmd = request.getParameter("selfCmd");
			System.out.println(cmd + selfCmd);
			remoteUtil.execute(cmd + selfCmd);
			
			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
			dbUtils.update("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),selfCmd.split("--target-dir")[1].split(" ")[1]+selfUtil.getTime(),1,0,null,null,dateStr,selfCmd.split("--target-dir")[1].split(" ")[1]});
			
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			break;
			
		default:
			break;
		}
		
		dbUtils.close();

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

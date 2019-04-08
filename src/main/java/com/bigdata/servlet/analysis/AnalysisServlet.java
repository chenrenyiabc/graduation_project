package com.bigdata.servlet.analysis;

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

import com.bigdata.bean.DataFlow;
import com.bigdata.bean.User;
import com.bigdata.util.DBUtils;
import com.bigdata.util.HiveUtil2;
import com.bigdata.util.PropertiesUtil;
import com.bigdata.util.RemoteUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AnalysisServlet
 */
@WebServlet("/AnalysisServlet")
public class AnalysisServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnalysisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		
		DBUtils dbUtil = DBUtils.getDBUtils();
		//PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		//String hostName = propertiesUtil.readPropertyByKey("hostName");
		
		//RemoteUtil remoteUtil = new RemoteUtil("SZ01", "bigdata", "bigdata");
		HiveUtil2 hiveUtil = null;
		
		String method = request.getParameter("method");
		int userId = ((User)request.getSession().getAttribute("user")).getId();
		if("query_mr_flow".equals(method)) {
			Map<String, List> map = new HashMap<>();
			String sql1 = "select name from data_source where type=0 and userid=?";
			ResultSet rs1 = dbUtil.queryResult(sql1,userId);
			List<String> dataSource = new ArrayList<>();
			try {
				while(rs1.next()) {
					dataSource.add(rs1.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put("dataSource", dataSource);
			
			String sql2 = "select algorithm_name from algorithm";
			ResultSet rs2 = dbUtil.queryResult(sql2);
			List<String> algorithm = new ArrayList<>();
			try {
				while(rs2.next()) {
					algorithm.add(rs2.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			map.put("algorithm", algorithm);
			
			out.write(JSONArray.fromObject(map).toString());
		}else if("save_flow".equals(method)) {
		
			String name = request.getParameter("name");
			int flow_status = Integer.parseInt(request.getParameter("flow_status"));
			int source_id = Integer.parseInt(request.getParameter("source_id"));
			int flow_type = Integer.parseInt(request.getParameter("flow_type"));
			String hive_sql = request.getParameter("hive_sql");
			String mr_id_string = request.getParameter("mr_id");
			int mr_id_int = 0;
			boolean id_int_string = (mr_id_string == null || "null".equals(mr_id_string));
			if(!id_int_string) {
				mr_id_int = Integer.parseInt(request.getParameter("mr_id"));
			}
			// hive计算流程保存在hive表中，如果此项不为空，则需要在hive中创建表
			String result_table = request.getParameter("result_table");
			// hive计算流程保存在hdfs目录中，如果此项不为空，则需在hdfs中创建文件
			String result_path = request.getParameter("result_path");
			
			//String sql = "insert into data_flow values(null,'"+ name +"',"+ userId +","+ flow_status +","+ source_id +","+ flow_type +",null,"+ mr_id +",null,'"+ result_path +"')";
			String sql = "insert into data_flow values(null,?,?,?,?,?,?,?,?,?)";
			boolean result = dbUtil.update(sql, new Object[]{name,userId,flow_status,source_id,flow_type,hive_sql,id_int_string ? mr_id_string : mr_id_int,result_table,result_path});
			if(result) {
				out.write("success");
			}
		//	out.write(result);
		}else if("save_flow_hql".equals(method)) {
			String name = request.getParameter("name");
			
			int flow_status = Integer.parseInt(request.getParameter("flow_status"));
			int flow_type = Integer.parseInt(request.getParameter("flow_type"));
			String hive_sql = request.getParameter("hive_sql");
			// hive计算流程保存在hive表中，如果此项不为空，则需要在hive中创建表
			String result_table = request.getParameter("result_table");
			
			String sql = "insert into data_flow values(null,?,?,?,null,?,?,null,?,null)";
			boolean result = dbUtil.update(sql, new Object[]{name,userId,flow_status,flow_type,hive_sql,result_table});
			System.out.println(result);
			if(result) {
				out.write("success");
			}
		}else if("query_hql_flow".equals(method)) {
			hiveUtil = new HiveUtil2();
			hiveUtil.changeDB("user"+userId);
			List<String> tableList = hiveUtil.getTaleList();
			System.out.println(tableList);
			
			out.write(JSONArray.fromObject(tableList).toString());
		}else if("query_table_field".equals(method)) {
			hiveUtil = new HiveUtil2();
			hiveUtil.changeDB("user"+userId);
			String tableName = request.getParameter("tableName");
			List<String> tableFieldList = hiveUtil.getTableInfo(tableName);
			for (String string : tableFieldList) {
				System.out.println(string);
			}

			out.write(JSONArray.fromObject(tableFieldList).toString());
		}else if("query_exist_flow".equals(method)) {
			String flowId = request.getParameter("flowId");
			String sql1 = "select * from data_flow where id=?";
			DataFlow dataFlow = dbUtil.queryObject(sql1, DataFlow.class, flowId);
			String sql2 = "select name from data_source where type=0 and userid=?";
			ResultSet rs1 = dbUtil.queryResult(sql2, userId);
			List<String> dataSource = new ArrayList<>();
			try {
				while(rs1.next()) {
					dataSource.add(rs1.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql3 = "select algorithm_name from algorithm";
			ResultSet rs3 = dbUtil.queryResult(sql3);
			List<String> algorithm = new ArrayList<>();
			try {
				while(rs3.next()) {
					algorithm.add(rs3.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			hiveUtil = new HiveUtil2();
			hiveUtil.changeDB("user" + userId);
			List<String> hqlTableList = hiveUtil.getTaleList();

			request.getSession().setAttribute("hqlTableList", hqlTableList);
			request.getSession().setAttribute("dataFlow", dataFlow);
			request.getSession().setAttribute("dataSource", dataSource);
			request.getSession().setAttribute("algorithm", algorithm);
			request.getSession().setAttribute("flowId", flowId);
			if(dataFlow.getFlow_type() == 0) {
				response.sendRedirect("analysis/modifyMRAnalysis.jsp");
			}else {
				response.sendRedirect("analysis/modifyHQLAnalysis.jsp");
			}
			
		}else if("update_mr_flow".equals(method)) {
			String flowId = request.getParameter("flowId");
			String process_name = request.getParameter("process_name");
			String chooseData = request.getParameter("chooseData");
			String chooseAlgorithm = request.getParameter("chooseAlgorithm");
			String resultPath = request.getParameter("resultPath");
			
			System.out.println(flowId + " " + process_name + " " + chooseData + " " + chooseAlgorithm + " " + resultPath);
			String sql = "update data_flow set name=?,source_id=?,mr_id=?,result_path=? where id=?";
			dbUtil.update(sql, process_name, chooseData, chooseAlgorithm, resultPath, flowId);
			out.write("<script>alert('修改MR流程成功！');location.href='flowManage/flowManage.html'</script>");
		}else if("update_hql_flow".equals(method)) {
			String flowId = request.getParameter("flowId");
			String name = request.getParameter("name");
			String hive_sql = request.getParameter("hive_sql");
			String result_table = request.getParameter("result_table");
			
			System.out.println(flowId + " " + name + " " + hive_sql + " " + result_table);
			String sql = "update data_flow set name=?,hive_sql=?,result_table=? where id=?";
			boolean result = dbUtil.update(sql, name, hive_sql, result_table, flowId);
			if(result) {
				out.write("success");
			}
		}else if("tohql".equals(method)) {
			hiveUtil = new HiveUtil2();
			hiveUtil.changeDB("user"+userId);
			List<String> tableList = hiveUtil.getTaleList();
			System.out.println(tableList);
			List<String> firstTableField = null;
			if(tableList.size() != 0){
				firstTableField = hiveUtil.getTableInfo(tableList.get(0));
			}
			request.getSession().setAttribute("firstTableField", firstTableField);
			request.getSession().setAttribute("tableList", tableList);
			response.sendRedirect("analysis/analysisHQL.jsp");
		}else if("query_source_id".equals(method)) {
			String source_name = request.getParameter("source_name");
			String sql = "select id from data_source where name =? and userid=?";
			ResultSet rs = dbUtil.queryResult(sql, source_name,userId);
			
			String source_id = null;
			try {
				while(rs.next()) {
					source_id = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.write(source_id);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
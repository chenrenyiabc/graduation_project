package com.bigdata.servlet.resultDataShow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata.bean.User;
import com.bigdata.util.HDFSUtil;
import com.bigdata.util.HiveUtil;
import com.bigdata.util.PropertiesUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetResultServlet
 */
@WebServlet("/GetResultServlet")
public class GetResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetResultServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;CharSet=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String method = request.getParameter("method");

		int userId = ((User) request.getSession().getAttribute("user")).getId();
		Map<String, List<String>> map = new TreeMap<>();
		
		//连接到hive
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		HDFSUtil hdfsUtil = new HDFSUtil(propertiesUtil.readPropertyByKey("hostName"));
		HiveUtil hiveUtil = new HiveUtil();
		hiveUtil.changeDatabase("user"+userId);
		
		String path = propertiesUtil.readPropertyByKey("userOutputHdfs");
		String result = "";
		String sql = "";
		
		List<String> keys = new ArrayList<>();
		List<String> values = new ArrayList<>();
		List<String> list = new ArrayList<>();
		int length = 0;
		List<String> lists[] = null;
		switch (method) {
		case "path":
			path = path +"user" + userId + "/" +name + "/part-r-00000";
			System.out.println("path:" + path);
			result = hdfsUtil.read(path);
			System.out.println("result:" + result);
			String[] lines = result.split("\n");		
			length = lines[0].split("\t").length;
			lists = new ArrayList[length];
			for(int i = 0;i<lists.length;i++) {
				lists[i] = new ArrayList<>();
			}
			for (String string : lines) {
				String[] temp = string.split("\t");
				for(int i = 0;i< length;i++) {
					lists[i].add(temp[i]);
				}
			}
			for (int i = 0; i < lists.length; i++) {
				map.put("" + i, lists[i]);
			}
			break;
		case "table":
			list = hiveUtil.getTableData(name);
			List<String> columns = hiveUtil.getTableInfo(name);
			length = columns.size();
			lists = new ArrayList[length];
			for(int i = 0;i<lists.length;i++) {
				lists[i] = new ArrayList<>();
			}
			for (String item : list) {
				String[] temp = item.split("\t");
				for(int i = 0; i< length;i++) {
					lists[i].add(temp[i]);
				}
			}
			for (int i = 0; i < lists.length; i++) {
				map.put(columns.get(i).split("\t")[0], lists[i]);
			}
			break;
		default:
			break;
		}
		System.out.println();
		System.out.println(JSONObject.fromObject(map).toString());
		out.print(JSONObject.fromObject(map).toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

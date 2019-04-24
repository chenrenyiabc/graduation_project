package com.bigdata.servlet.dataImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bigdata.bean.DataSource;
import com.bigdata.bean.User;
import com.bigdata.service.datasource.DataSourceService;
import com.bigdata.util.*;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class HiveServlet
 */
@WebServlet("/HiveServlet")
@MultipartConfig
public class HiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//中文乱码
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain; charset=UTF-8");
		
		//工具类
		HiveUtil hiveUtil = new HiveUtil();
		TimeUtil selfUtil = new TimeUtil();
		DBUtils dbUtils = DBUtils.getDBUtils();
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		String host = propertiesUtil.readPropertyByKey("hostName");
		String hostName = propertiesUtil.readPropertyByKey("hostName");
		String hadoopUser = propertiesUtil.readPropertyByKey("hadoopUser"); // 远程用户名
		String hadoopPwd = propertiesUtil.readPropertyByKey("hadoopPwd"); // 远程账号密码
		HDFSUtil hdfsUtil = new HDFSUtil(host);
		int userId = ((User) request.getSession().getAttribute("user")).getId();
		//公用变量
		List<String> resultList = new ArrayList<>();
		String tableName = null;
		String cmd = "source .bash_profile && ";
		String format = "yyyy-MM-dd hh:mm:ss"; 
		String dateStr = "";
		String tableStructureDataSource = "";
		
		// 使用远程登录，执行sqoop
		String jdbcUrl = "jdbc:" + "mysql" + "://" + hostName + ":" + 3306 + "/";
		RemoteUtil remoteUtil = new RemoteUtil(hostName, hadoopUser, hadoopPwd); // 远程登录主机
		
		User user = (User) request.getSession().getAttribute("user");
		String method = request.getParameter("method");
		switch (method) {
		case "showtable":
			//展示Hive数据表
			//切换到用户数据库
			hiveUtil.changeDatabase("user"+userId);
			resultList = hiveUtil.getTaleList();
			resultList.add(0, " "); //插入一个空格
			response.getWriter().write(JSONArray.fromObject(resultList).toString());
			break;
			
		case "showdata":
			//展示表中的数据
			//切换到用户数据库
			hiveUtil.changeDatabase("user"+userId);
			tableName = request.getParameter("tableName");
			//获取数据表中的字段
			String colnames = "";
			for (String string : hiveUtil.getTableInfo(tableName)) {
				colnames +=string + "\t";
			}
			colnames = colnames.substring(0, colnames.length()-1);
			resultList = hiveUtil.getTableData(tableName);
			resultList.add(0, colnames);
			response.getWriter().write(JSONArray.fromObject(resultList).toString());
			break;
		
		case "uploadFile2Hive":
			tableName = request.getParameter("tableName");
			System.out.println(tableName);
			boolean isAuto = request.getParameter("addcolumn").equals("autoColumn")?true:false;
			boolean isOverwrite = request.getParameter("overwrite").equals("overwrite")?true:false;
			String delimiter = request.getParameter("delimiter"); //设置分隔符
			String[] columnTypes = request.getParameterValues("coltypes");  
			
			//切换到用户数据库
			hiveUtil.changeDatabase("user"+userId);
			
			//使用Part对象接受文件(把文件上传到本地临时目录，再上传到hdfs的临时目录)
			Part part = request.getPart("uploadFile"); //获取文件
			String fileName = "user" + userId + selfUtil.getTime();
			String filePath = "C:/Users/enyin/Desktop" + File.separator + fileName; // 设置上传的文件路径及名称
			part.write(filePath);
			
			//是否进行首行自动获取
			String createTableCmd = "create table if not exists " + tableName + "(";
			 
			if (!isAuto) {
				//手动输入列信息
				String[] columnNames = request.getParameterValues("colnames"); 
				for (int i = 0; i < columnNames.length; i++) {
					createTableCmd += columnNames[i] + " " + columnTypes[i] + ",";
					//表结构保存(用于数据元)
					tableStructureDataSource += columnNames[i] + "_" + columnTypes[i] + ",";
				}
				tableStructureDataSource = tableStructureDataSource.substring(0, tableStructureDataSource.length() - 1);
				createTableCmd = createTableCmd.substring(0, createTableCmd.length() - 1);
				createTableCmd += ") row format delimited fields terminated by '" + delimiter + "'";
			}else {
				//读取文件的首行自动获取列信息
				BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
				String tableInfo = bufferedReader.readLine(); //读取文件首行
				for (int i = 0; i < columnTypes.length; i++) {
					createTableCmd += tableInfo.split(delimiter)[i] + " " + columnTypes[i] + ",";
					tableStructureDataSource +=  tableInfo.split(delimiter)[i] + "_" + columnTypes[i] + ",";
				}
				tableStructureDataSource = tableStructureDataSource.substring(0, tableStructureDataSource.length() - 1);
				createTableCmd = createTableCmd.substring(0, createTableCmd.length() - 1);
				createTableCmd += ") row format delimited fields terminated by '" + delimiter
						+ "' tblproperties(\"skip.header.line.count\"=\"1\")";
				bufferedReader.close();
			}
			//上传至HDFS
			hdfsUtil.upLoad(true, false, new String[] { filePath }, propertiesUtil.readPropertyByKey("userDataDir") + "/"+"user"+ userId+ "/" + fileName );
			//创建Hive表
			hiveUtil.excuteCmd(createTableCmd);
			//载入数据到Hive中
			String loadToHiveCmd = "load data inpath '" + propertiesUtil.readPropertyByKey("userDataDir") + "/"+"user"+ userId + "/" + fileName + "' " + (isOverwrite ? "overwrite " : "")
					+ "into table " + tableName;
			hiveUtil.excuteCmd(loadToHiveCmd);
//			request.getRequestDispatcher("hive_show.jsp").forward(request, response);
						
			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
//			dbUtil.executeUpdate("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),tableName,1,1,tableName,tableStructureDataSource,dateStr,null});		
			DataSourceService dService = new DataSourceService();
			dService.addDataSource(new DataSource(user.getId(), tableName, 1, tableName, tableStructureDataSource, dateStr));
			dService.close();
			
			response.sendRedirect("main/dataImport/hive_show.jsp");
			break;
			
		case "inputMysql2HdfsSingle":
			//从数据库中进行表选择导入
			//获取表名
			tableName = request.getParameter("tableName");
			//获取列名
			String[] columns = request.getParameterValues("columns");
			String column = "";
			for (String string : columns) {
				column += string + ",";
			}
			column = column.substring(0, column.length() - 1);
			//获取Hive目录表()
			String targetHiveTable = request.getParameter("targetHiveTable");
			//拼接命令
			cmd += "sqoop import  --connect " + jdbcUrl + "graduation_project" + " --username " + " root " + " --password "
					+ " root " + " --hive-import " +" --table " + tableName + " --hive-database " + " user" + userId  + " --columns " + column
					+ " --hive-table " + targetHiveTable + " -m 1 ";
			System.out.println(cmd);
			remoteUtil.execute(cmd);
			
			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
//			dbUtil.executeUpdate("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),targetHiveTable,1,1,targetHiveTable,column,dateStr,null});		
			DataSourceService dService2 = new DataSourceService();
			dService2.addDataSource(new DataSource(user.getId(), targetHiveTable, 1, targetHiveTable, column, dateStr));
			dService2.close();
			
			response.sendRedirect("main/dataImport/hive_show.jsp");
			break;
			
		case "inputMysql2HiveSelf":
			//自定义导出Mysql数据到Hive(需要输入sqool命令)
			String selfCmd = request.getParameter("selfCmd");
			remoteUtil.execute(cmd + selfCmd);
			
			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
			dbUtils.update("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),selfCmd.split("--hive-table")[1].split(" ")[1]+selfUtil.getTime(),1,1,selfCmd.split("--hive-table")[1].split(" ")[1],selfCmd.split("--columns")[1].split(" ")[1],dateStr,null});
			
			response.sendRedirect("main/dataImport/hive_show.jsp");
			break;
			
		case "showHiveColumns":
			//切换到用户数据库
			hiveUtil.changeDatabase("test");
			//展示表中的的列
			resultList = hiveUtil.getTableInfo(request.getParameter("HiveTablename"));
			response.getWriter().write(JSONArray.fromObject(resultList).toString());
			break;
			
		case "hivequeryselect":
			//先不做简单统计方法
			//切换到用户数据库
			hiveUtil.changeDatabase("test");
			String sourceTablename = request.getParameter("hiveTableshow");
			String queryResultTablename = request.getParameter("queryResultTablename");
			String[] columnsList = request.getParameterValues("xialaColumns");
			
			String cmdSQL = "create table if not exists " + queryResultTablename + " as select ";
			for(int i =0;i<columnsList.length;i++) {
				cmdSQL += columnsList[i].split("\t")[0] + ",";
			} 
			cmdSQL = cmdSQL.substring(0, cmdSQL.length()-1) + " from " + sourceTablename;
			System.out.println(cmdSQL);
			hiveUtil.excuteCmd(cmdSQL);
			
			request.getRequestDispatcher("hive_show.jsp").forward(request, response);
			break;
		
		case "hivequeryself":
			//用户自定义个Hive语句
			//切换到用户数据库
			hiveUtil.changeDatabase("test");
			hiveUtil.excuteCmd(request.getParameter("hivequeryselfText"));
			System.out.println(request.getParameter("hivequeryselfText"));
			request.getRequestDispatcher("hive_show.jsp").forward(request, response);
			break;
			
		default:
			break;
		}
		dbUtils.close();
//		hiveUtil.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

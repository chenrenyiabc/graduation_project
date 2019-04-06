package com.bigdata.servlet.dataImport;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bigdata.bean.DataSource;
import com.bigdata.bean.DirFileBean;
import com.bigdata.bean.User;
import com.bigdata.service.datasource.DataSourceService;
import com.bigdata.util.wwh.DBUtil;
import com.bigdata.util.wwh.HDFSUtil;
import com.bigdata.util.wwh.PropertiesUtil;
import com.bigdata.util.wwh.RemoteUtil;
import com.bigdata.util.wwh.SelfUtil;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class uploadSerlvet
 */
@WebServlet("/HDFSFileServlet")
@MultipartConfig
public class HDFSFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HDFSFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置编码 
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;CharSet=UTF-8"); 
		// 进行从本地上传文件的操作
		// 读取配置文件信息
		SelfUtil selfUtil = new SelfUtil();
		PropertiesUtil propertiesUtil = new PropertiesUtil("system.properties");
		String host = propertiesUtil.readPropertyByKey("hostName");
		String hadoopUser = propertiesUtil.readPropertyByKey("hadoopUser");
		String hadoopPwd = propertiesUtil.readPropertyByKey("hadoopPwd");
		int userId = ((User) request.getSession().getAttribute("user")).getId();
		String HadoopDir = propertiesUtil.readPropertyByKey("userDataDir") + "/user" + userId;
		HDFSUtil hdfsUtil = new HDFSUtil(host);
		RemoteUtil remoteUtil = new RemoteUtil(host, hadoopUser, hadoopPwd); // 远程登录主机
		DBUtil dbUtil = DBUtil.getDBUtil();
		
		//常用定义
		String format = "yyyy-MM-dd hh:mm:ss"; 
		String dateStr = "";
		//定义Po
		DirFileBean dirFileBean = null;
		
		User user = (User) request.getSession().getAttribute("user");
		boolean flag;
		String method = request.getParameter("method");
		
		switch (method) {
		case "mkdir":
			flag = hdfsUtil.mkdirs(request.getParameter("idzhiname") + "/" + request.getParameter("dirname"), true);
//			String mkdirIsSuccess = flag?"文件夹创建成功":"文件夹创建失败";
			
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			break;
			
		case "deldir":
			flag = hdfsUtil.delete(request.getParameter("idzhiname"), true);
			
//			String deldirIsSuccess = flag?"删除成功":"删除失败";
//			request.setAttribute("deldirIsSuccess", deldirIsSuccess);
//			request.getRequestDispatcher("frame_showmsg.jsp").forward(request, response);
			
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			break;
			
		case "upload":
			// 使用Part对象接受文件(把文件保存到本地的临时目录)先用windows进行测试
			Part part = request.getPart("uploadFile");
			String fileName = "" + userId + selfUtil.getTime();
			String filePath = File.separator + "home" + File.separator + "chenry" + File.separator + fileName; // 设置上传的文件路径及名称
			part.write(filePath);
			// 把本地的临时文件上传至hdfs(hdfs的目录需存在，否则会认为是文件)
			// 参数设置是否删除原文件，是否覆盖目标文件
			boolean isOverWrite = request.getParameter("isoverwrite").equals("overwrite")?true:false;
			//判断是否上传成功
			flag = hdfsUtil.upLoad(true, isOverWrite, new String[] { filePath }, request.getParameter("dirName") + "/" + request.getParameter("fileNewName"));
			String fileUploadisSuccess = flag == true ? "数据上传成功" : "数据上传失败";
//			request.setAttribute("fileUploadisSuccess", fileUploadisSuccess);
//			request.getRequestDispatcher("frame_showmsg.jsp").forward(request, response);

			//保存到数据元中
			dateStr = new SimpleDateFormat(format).format(new Date()); 
//			dbUtil.executeUpdate("insert into data_source values (?,?,?,?,?,?,?,?,?)", new Object[] {null,user.getId(),request.getParameter("fileNewName")+selfUtil.getTime(),1,0,null,null,dateStr,request.getParameter("dirName") + "/" + request.getParameter("fileNewName")});
			DataSourceService dService2 = new DataSourceService();
			dService2.addDataSource(new DataSource(user.getId(), request.getParameter("fileNewName")+selfUtil.getTime(), 0, dateStr, request.getParameter("dirName") + "/" + request.getParameter("fileNewName")));
			dService2.close();
			
			response.sendRedirect("main/dataImport/hdfs_show.jsp");
			break;
			
		case "showDir":
			String forwordDir = null;
			List<String> dirList = null;
			if(request.getParameter("dir").equals("indexpage")) {
				//hdfs用户目录首页加载
				dirList = hdfsUtil.getFileInfo("/");
				forwordDir = "/";
			}else {
				dirList = hdfsUtil.getFileInfo(request.getParameter("dir"));
				forwordDir = request.getParameter("dir");
			}
			//把最终结果保存到May结构下(key:父目录，values:目录对象数组)
			Map<String, List> mapDirList = new HashMap<>();
			List<DirFileBean> listDirFileBean = new ArrayList<>();
			for (String dirListTmp : dirList) {
				String[] tmp =  dirListTmp.split("\t");
				listDirFileBean.add(new DirFileBean(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4]));
			}
			mapDirList.put(forwordDir,listDirFileBean);
			for (Entry<String, List> dirFileBean2 : mapDirList.entrySet()) {
				System.out.println(dirFileBean2);
			}
			response.getWriter().write(JSONArray.fromObject(mapDirList).toString());
			break;
			
		case "getMrsourceFileAlgorithm":
			// 获取用用可以进行Mr的数据和Mr算法
			String HdfsInputFile = propertiesUtil.readPropertyByKey("dataInputHdfs");
			//获取该目录下所有可进行Mr算法的数据文件夹名称(Mr程序输入必须是文件夹，执行文件夹下所有数据)
			List<Object> resultList = new ArrayList<>();
			for (String SoureFliesAllMsg : hdfsUtil.getFileInfo(HdfsInputFile)) {
				if(SoureFliesAllMsg.split("\t")[0].equals("目录")){
					resultList.add(SoureFliesAllMsg.split("\t")[4]);
				}
			}
			//拥有的算法先在Jsp中写死
			response.getWriter().write(JSONArray.fromObject(resultList).toString());
			break;
			
		case "runMr":
			//运行Mr算法
			String jarPath = propertiesUtil.readPropertyByKey("jarPath");//存放在Linux中
			String MrjAlgorithm = request.getParameter("MrjAlgorithm"); // 算法名称
			String inputPath = propertiesUtil.readPropertyByKey("dataInputHdfs") + request.getParameter("inputPathMr"); // 源数据目录
			String outputPath = propertiesUtil.readPropertyByKey("dataOutputHdfs") + selfUtil.getTime() + "/" + MrjAlgorithm;// 输出目录
			String cmd = propertiesUtil.readPropertyByKey("hadoopBinHome") + "hadoop jar " + jarPath + " "
					+ propertiesUtil.readPropertyByKey(MrjAlgorithm) + " " + inputPath + " " + outputPath;
			System.out.println(cmd);
			//在Eclipse中没有日志，只能把cmd复制到shell中进行测试
			String tempResult = remoteUtil.execute(cmd); // 执行hadoopMR，能够读取标准输出的流，自定义了一个Congratulations标识
			// 判断是否执行成功
			String result = "";             //记录最后结果
			if (tempResult.contains("Congratulations")) {
				// 执行hdfs命令 查看结果文件
				result = hdfsUtil.read(outputPath + "/part-r-00000");
			} else {
				result = "task error";
			}
//			System.out.println(result);
			request.setAttribute("result", result.replaceAll("\n", "<br/>"));
			request.getRequestDispatcher("hdfs_show.jsp").forward(request, response);
			response.getWriter().write(result);
		default:
			break;
		}
		dbUtil.close();
		

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

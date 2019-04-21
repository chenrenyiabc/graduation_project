package com.bigdata.servlet.algorithm;

import com.bigdata.bean.Algorithm;
import com.bigdata.bean.User;
import com.bigdata.service.algorithm.AlgorithmService;
import com.bigdata.util.DBUtils;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/AlgorithmServlet")
public class AlgorithmServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlgorithmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String method = request.getParameter("method");
        int userId = ((User)request.getSession().getAttribute("user")).getId();

        AlgorithmService as = new AlgorithmService();

        if("query_algorithm".equals(method)){
            List<Algorithm> list = as.getAlgorithm();
            System.out.println(JSONArray.fromObject(list).toString());
            out.write(JSONArray.fromObject(list).toString());
        } else if("query_exist_algorithm".equals(method)){
            Integer algoId = Integer.parseInt(request.getParameter("algoId"));
            System.out.println(algoId);
            Algorithm algorithm = as.getAlgorithmById(algoId);
            System.out.println(algorithm);
            request.setAttribute("algorithm", algorithm);
            request.setAttribute("algoId", algoId);
            //response.sendRedirect("main/algorithmManage/modifyAlgorithm.jsp");
            //request.getRequestDispatcher("main/algorithmManage/algorithm.jsp").forward(request,response);
            //response.sendRedirect("main/algorithmManage/algorithm.jsp");
            out.write(JSONArray.fromObject(algorithm).toString());
            //out.write(JSONArray.fromObject(algoId).toString());
        } else if("modifyAlgorithm".equals(method)){
            String algoId = request.getParameter("algoId");
            String algorithm_name = request.getParameter("algorithm_name");
            String algorithm_path = request.getParameter("algorithm_path");
            String algorithm_class = request.getParameter("algorithm_class");
            System.out.println("[" + algoId + " " + algorithm_name + " " + algorithm_path + " " + algorithm_class + "]");
            boolean result = as.updateAlgorithm(algoId, algorithm_name, algorithm_path, algorithm_class);
            if (result){
                out.write("<script>alert('修改算法成功！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            } else {
                out.write("<script>alert('修改算法失败！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            }
        } else if("newAlgorithm".equals(method)){
            String algorithm_name = request.getParameter("algorithm_name");
            String algorithm_path = request.getParameter("algorithm_path");
            String algorithm_class = request.getParameter("algorithm_class");

            boolean result = as.newAlgorithm(algorithm_name, algorithm_path, algorithm_class);

            if (result){
                out.write("<script>alert('添加算法成功！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            } else {
                out.write("<script>alert('添加算法失败！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            }
        } else if("deleteAlgorithm".equals(method)){
            String algoId = request.getParameter("algoId");
            boolean result = as.deleteAlgorithm(algoId);

            if (result){
                out.write("<script>alert('删除算法成功！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            } else {
                out.write("<script>alert('删除算法失败！');location.href='main/algorithmManage/algorithm.jsp'</script>");
            }
        }
    }
}

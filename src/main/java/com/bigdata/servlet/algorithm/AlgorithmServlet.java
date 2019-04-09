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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();

        String method = request.getParameter("method");
        int userId = ((User)request.getSession().getAttribute("user")).getId();

        AlgorithmService as = new AlgorithmService();

        if("query_algorithm".equals(method)){
            List<Algorithm> list = as.getAlgorithm();
            out.write(JSONArray.fromObject(list).toString());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

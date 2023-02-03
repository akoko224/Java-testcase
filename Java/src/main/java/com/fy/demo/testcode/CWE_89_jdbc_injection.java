package com.fy.demo.testcode;

import com.fy.demo.helpers.DatabaseHelper;
import com.fy.demo.helpers.SeparateClassRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-89: JDBC Injection sqli
@WebServlet("/CWE_89_jdbc_injection")
public class CWE_89_jdbc_injection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        SeparateClassRequest scr = new SeparateClassRequest(request);
        String param = scr.getTheParameter("vector");
        if (param == null) param = "";

        String bar = doSomething(param);

        try {
            String sql = "SELECT TOP 1 userid from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";

            java.util.Map results = DatabaseHelper.JDBCtemplate.queryForMap(sql);
            java.io.PrintWriter out = response.getWriter();
            out.write("Your results are: ");
            //		System.out.println("Your results are");
            out.write(org.owasp.esapi.ESAPI.encoder().encodeForHTML(results.toString()));
            //		System.out.println(results.toString());
        } catch (org.springframework.dao.DataAccessException e) {
            if (DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
                return;
            } else throw new ServletException(e);
        }
    }  // end doPost

    private static String doSomething(String param) throws ServletException, IOException {

        String bar = "";
        if (param != null) {
            StringBuilder b = new StringBuilder("user_");
            b.append(param);
            bar = b.substring(0, -4);
        }

        return bar;
    }
}
//2453
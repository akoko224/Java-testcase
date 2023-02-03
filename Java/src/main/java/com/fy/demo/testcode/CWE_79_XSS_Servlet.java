
package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-79: XSS in Servlet
@WebServlet("/CWE_79_XSS_Servlet")
public class CWE_79_XSS_Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String param = request.getParameter("vector");
        if (param == null) param = "";

        String bar = doSomething(param);

        int length = 1;
        if (bar != null) {
            length = bar.length();
            response.getWriter().write(bar, 0, length);
        }
    }  // end doPost

    private static String doSomething(String param) throws ServletException, IOException {
        String bar;
        // Simple ? condition that assigns param to bar on false condition
        int num = 106;
        bar = (7 * 42) - num > 200 ? "This should never happen" : param;
        return bar;
    }
}
//2136


package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-501: Trust Boundary Violation. The application mixes trusted and untrusted data in session attributes.
@WebServlet("/CWE_501_trust_boundary")
public class CWE_501_trust_boundary extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String param = request.getHeader("vector");
        if (param == null) param = "";
        param = java.net.URLDecoder.decode(param, "UTF-8");

        if (param == null) param = "";

        String bar = new Test().doSomething(param);

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String^,java.lang.Object)
        request.getSession().setAttribute(bar, "10340");

        response.getWriter().println("Item: '" + com.fy.demo.helpers.Utils.encodeForHTML(bar)
                + "' with value: '10340' saved in session.");
    }  // end doPost

    private class Test {

        public String doSomething(String param) throws ServletException, IOException {

            StringBuilder sbxyz7858 = new StringBuilder(param);
            String bar = sbxyz7858.append("_SafeStuff").toString();

            return bar;
        }
    } // end innerclass Test

} // end DataflowThruInnerClass
// 1081
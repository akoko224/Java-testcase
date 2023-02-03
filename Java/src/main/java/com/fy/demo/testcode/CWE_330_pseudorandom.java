
package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//  feysh.cwej.cwe-330 Pseudorandom
@WebServlet("/CWE_330_pseudorandom")
public class CWE_330_pseudorandom extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        long l = new java.util.Random().nextLong();
        String rememberMeKey = Long.toString(l);

        String user = "Logan";
        String fullClassName = this.getClass().getName();
        String testCaseNumber = fullClassName.substring(fullClassName.lastIndexOf('.') + 1 + "DemoTest".length());
        user += testCaseNumber;

        String cookieName = "rememberMe" + testCaseNumber;

        boolean foundUser = false;
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; !foundUser && i < cookies.length; i++) {
                javax.servlet.http.Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    if (cookie.getValue().equals(request.getSession().getAttribute(cookieName))) {
                        foundUser = true;
                    }
                }
            }
        }

        if (foundUser) {
            response.getWriter().println("Welcome back: " + user + "<br/>");
        } else {
            javax.servlet.http.Cookie rememberMe = new javax.servlet.http.Cookie(cookieName, rememberMeKey);
            rememberMe.setSecure(true);
            rememberMe.setPath("/demo/" + this.getClass().getSimpleName());
            request.getSession().setAttribute(cookieName, rememberMeKey);
            response.addCookie(rememberMe);
            response.getWriter().println("<br/>");
        }

        response.getWriter().println("Weak Randomness Test java.util.Random.nextLong() executed");
    }  // end doPost

    private class Test {

        public String doSomething(String param) throws ServletException, IOException {

            String bar;

            // Simple if statement that assigns param to bar on true condition
            int num = 196;
            if ((500 / 42) + num > 200)
                bar = param;
            else bar = "This should never happen";
            return bar;
        }
    } // end innerclass Test

} // end DataflowThruInnerClass
// 1788

package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-614: Cookie without the secure flag
@WebServlet("/CWE_614_secure_cookie")
public class CWE_614_secure_cookie extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        com.fy.demo.helpers.SeparateClassRequest scr = new com.fy.demo.helpers.SeparateClassRequest(request);
        String param = scr.getTheValue("vector");

        String bar = doSomething(param);

        byte[] input = new byte[1000];
        String str = "?";
        Object inputParam = param;
        if (inputParam instanceof String) str = ((String) inputParam);
        if (inputParam instanceof java.io.InputStream) {
            int i = ((java.io.InputStream) inputParam).read(input);
            if (i == -1) {
                response.getWriter().println("This input source requires a POST, not a GET. Incompatible UI for the InputStream source.");
                return;
            }
            str = new String(input, 0, i);
        }
        // Cookie without the secure flag. Cookie without the secure flag could be sent in clear text if a HTTP URL is visited
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("SomeCookie", str);

        cookie.setSecure(false);
        cookie.setPath("/demo/" + this.getClass().getSimpleName());

        response.addCookie(cookie);

        response.getWriter().println("Created cookie: 'SomeCookie': with value: '"
                + org.owasp.esapi.ESAPI.encoder().encodeForHTML(str) + "' and secure flag set to: false");
    }  // end doPost

    private static String doSomething(String param) throws ServletException, IOException {

        String bar = org.springframework.web.util.HtmlUtils.htmlEscape(param);

        return bar;
    }
}
// 2710
package com.fy.demo.testcode;

import com.fy.demo.helpers.ThingFactory;
import com.fy.demo.helpers.ThingInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/CWE_93_CRLF")
public class CWE_93_CRLF extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String param = request.getParameter("value");
        if (param == null) param = "";

        String bar = new Test().doSomething(request, param);

        javax.servlet.http.Cookie rememberMe =
                new javax.servlet.http.Cookie("cookieName", bar);
        rememberMe.setSecure(true);
        rememberMe.setHttpOnly(true);
        rememberMe.setPath(request.getRequestURI()); // i.e., set path to JUST this servlet
        response.addCookie(rememberMe);
        response.getWriter().println("remembered with cookie");
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a22205 = param; // assign
            StringBuilder b22205 = new StringBuilder(a22205); // stick in stringbuilder
            b22205.append(" SafeStuff"); // append some safe content
            b22205.replace(
                    b22205.length() - "Chars".length(),
                    b22205.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map22205 = new java.util.HashMap<String, Object>();
            map22205.put("key22205", b22205.toString()); // put in a collection
            String c22205 = (String) map22205.get("key22205"); // get it back out
            String d22205 = c22205.substring(0, c22205.length() - 1); // extract most of it
            String e22205 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d22205.getBytes()))); // B64 encode and decode it
            ThingInterface thing = ThingFactory.createThing();
            String g22205 = e22205;
            String bar = thing.doSomething(g22205);

            return bar;
        }
    } // end innerclass Test
}

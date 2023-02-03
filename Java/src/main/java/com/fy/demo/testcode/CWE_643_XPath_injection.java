
package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-643: XPath Injection
@WebServlet("/CWE_643_XPath_injection")
public class CWE_643_XPath_injection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String queryString = request.getQueryString();
        String paramval = "vector" + "=";
        int paramLoc = -1;
        if (queryString != null) paramLoc = queryString.indexOf(paramval);
        if (paramLoc == -1) {
            response.getWriter().println("getQueryString() couldn't find expected parameter '" + "vector" + "' in query string.");
            return;
        }

        String param = queryString.substring(paramLoc + paramval.length()); // 1st assume "vector" param is last parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what comes after.
        int ampersandLoc = queryString.indexOf("&", paramLoc);
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length(), ampersandLoc);
        }
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = new Test().doSomething(param);

        try {
            java.io.FileInputStream file = new java.io.FileInputStream(com.fy.demo.helpers.Utils.getFileFromClasspath("employees.xml", this.getClass().getClassLoader()));
            javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDocument = builder.parse(file);
            javax.xml.xpath.XPathFactory xpf = javax.xml.xpath.XPathFactory.newInstance();
            javax.xml.xpath.XPath xp = xpf.newXPath();

            response.getWriter().println("Your query results are: <br/>");
            String expression = "/Employees/Employee[@emplid='" + bar + "']";
            response.getWriter().println(xp.evaluate(expression, xmlDocument) + "<br/>");

        } catch (javax.xml.xpath.XPathExpressionException e) {
            // OK to swallow
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        }
    }  // end doPost

    private class Test {

        public String doSomething(String param) throws ServletException, IOException {

            String bar = "";
            if (param != null) {
                java.util.List<String> valuesList = new java.util.ArrayList<String>();
                valuesList.add("safe");
                valuesList.add(param);
                valuesList.add("moresafe");

                valuesList.remove(0); // remove the 1st safe value

                bar = valuesList.get(0); // get the param value
            }

            return bar;
        }
    } // end innerclass Test

} // end DataflowThruInnerClass
// 1736
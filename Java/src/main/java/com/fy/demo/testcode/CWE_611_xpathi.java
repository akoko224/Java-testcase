
package com.fy.demo.testcode;

import com.fy.demo.helpers.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//  feysh.cwej.cwe-611: XML parsing vulnerable to XXE (DocumentBuilder
@WebServlet("/CWE_611_xpathi")
public class CWE_611_xpathi extends HttpServlet {

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

        String bar = doSomething(param);

        try {
            java.io.FileInputStream file = new java.io.FileInputStream(Utils.getFileFromClasspath("employees.xml", this.getClass().getClassLoader()));
            javax.xml.parsers.DocumentBuilderFactory builderFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDocument = builder.parse(file);
            javax.xml.xpath.XPathFactory xpf = javax.xml.xpath.XPathFactory.newInstance();
            javax.xml.xpath.XPath xp = xpf.newXPath();

            String expression = "/Employees/Employee[@emplid='" + bar + "']";

            response.getWriter().println("Your query results are: <br/>");
            org.w3c.dom.NodeList nodeList = (org.w3c.dom.NodeList) xp.compile(expression).evaluate(xmlDocument, javax.xml.xpath.XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element value = (org.w3c.dom.Element) nodeList.item(i);
                response.getWriter().println(value.getTextContent() + "<br/>");
            }
        } catch (javax.xml.xpath.XPathExpressionException e) {
            // OK to swallow
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.out.println("XPath expression exception caught and swallowed: " + e.getMessage());
        }
    }  // end doPost

    private static String doSomething(String param) throws ServletException, IOException {

        String bar = "safe!";
        java.util.HashMap<String, Object> map3695 = new java.util.HashMap<String, Object>();
        map3695.put("keyA-3695", "a Value"); // put some stuff in the collection
        map3695.put("keyB-3695", param); // put it in a collection
        map3695.put("keyC", "another Value"); // put some stuff in the collection
        bar = (String) map3695.get("keyB-3695"); // get it back out

        return bar;
    }
}
// 1974
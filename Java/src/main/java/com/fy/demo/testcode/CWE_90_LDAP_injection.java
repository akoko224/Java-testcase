
package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-90: Potential LDAP Injection
@WebServlet("/CWE_90_LDAP_injection")
public class CWE_90_LDAP_injection extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String param = "";
        java.util.Enumeration<String> headers = request.getHeaders("vector");
        if (headers.hasMoreElements()) {
            param = headers.nextElement(); // just grab first element
        }
        param = java.net.URLDecoder.decode(param, "UTF-8");


        String bar = doSomething(param);

        com.fy.demo.helpers.LDAPManager ads = new com.fy.demo.helpers.LDAPManager();
        try {
            response.setContentType("text/html");
            String base = "ou=users,ou=system";
            javax.naming.directory.SearchControls sc = new javax.naming.directory.SearchControls();
            sc.setSearchScope(javax.naming.directory.SearchControls.SUBTREE_SCOPE);
            String filter = "(&(objectclass=person))(|(uid=" + bar + ")(street={0}))";
            Object[] filters = new Object[]{"The streetz 4 Ms bar"};

            javax.naming.directory.DirContext ctx = ads.getDirContext();
            javax.naming.directory.InitialDirContext idc = (javax.naming.directory.InitialDirContext) ctx;
            javax.naming.NamingEnumeration<javax.naming.directory.SearchResult> results =
                    idc.search(base, filter, filters, sc);
            while (results.hasMore()) {
                javax.naming.directory.SearchResult sr = (javax.naming.directory.SearchResult) results.next();
                javax.naming.directory.Attributes attrs = sr.getAttributes();

                javax.naming.directory.Attribute attr = attrs.get("uid");
                javax.naming.directory.Attribute attr2 = attrs.get("street");
                if (attr != null) {
                    response.getWriter().write("LDAP query results:<br>"
                            + " Record found with name " + attr.get() + "<br>"
                            + "Address: " + attr2.get() + "<br>");
                    System.out.println("record found " + attr.get());
                }
            }
        } catch (javax.naming.NamingException e) {
            throw new ServletException(e);
        } finally {
            try {
                ads.closeDirContext();
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }  // end doPost

    private static String doSomething(String param) throws ServletException, IOException {

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
}
//2036
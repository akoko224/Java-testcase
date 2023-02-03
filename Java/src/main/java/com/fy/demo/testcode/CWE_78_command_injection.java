
package com.fy.demo.testcode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// feysh.cwej.cwe-78: Potential Command Injection
@WebServlet("/CWE_78_command_injection")
public class CWE_78_command_injection extends HttpServlet {

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

        String bar = new Test().doSomething(param);

        String cmd = "";
        String a1 = "";
        String a2 = "";
        String[] args = null;
        String osName = System.getProperty("os.name");

        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe";
            a2 = "/c";
            cmd = com.fy.demo.helpers.Utils.getOSCommandString("echo");
            args = new String[]{a1, a2, cmd, bar};
        } else {
            a1 = "sh";
            a2 = "-c";
            cmd = com.fy.demo.helpers.Utils.getOSCommandString("ping -c1");
            args = new String[]{a1, a2, cmd, bar};
        }

        Runtime r = Runtime.getRuntime();

        try {
            Process p = r.exec(args);
            com.fy.demo.helpers.Utils.printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println("Problem executing cmdi - TestCase");
            throw new ServletException(e);
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
// 1286
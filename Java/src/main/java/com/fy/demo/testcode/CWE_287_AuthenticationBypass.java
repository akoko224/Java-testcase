package com.fy.demo.testcode;

import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;

public class CWE_287_AuthenticationBypass {

    public void foo() {
        new StaticBasicParserPool().setIgnoreComments(false); // Noncompliant {{Change "setIgnoreComments" to "true" or remove the call to "setIgnoreComments" to prevent the authentication bypass.}}
        new StaticBasicParserPool().setIgnoreComments(true); // Compliant

        new BasicParserPool().setIgnoreComments(false); // Noncompliant {{Change "setIgnoreComments" to "true" or remove the call to "setIgnoreComments" to prevent the authentication bypass.}}
        new BasicParserPool().setIgnoreComments(true); // Compliant

        // OpenSAML3 is OK
        new net.shibboleth.utilities.java.support.xml.BasicParserPool().setIgnoreComments(false);
    }

}

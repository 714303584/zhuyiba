package com.shorturl.util;


import com.nmtx.doc.core.api.springmvc.SpringMVCApiDocConfig;

public class SpringDocBuilder {

    public static void main(String[] args) {
        SpringMVCApiDocConfig doc = new SpringMVCApiDocConfig();
        doc.setConfigFilePath("jdoc.properties");
        doc.start();
    }
}

package com.tamako.allapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionUtil {
    public static String getVersion() {
        try (InputStream input = VersionUtil.class.getClassLoader().getResourceAsStream("version.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                return prop.getProperty("version");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        System.out.println("Version: " + getVersion());
    }
}
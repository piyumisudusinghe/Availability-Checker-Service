package org.availabilitycheckerservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigReader
{
    private static final String CONFIG_FILE = "config.properties";
    public static Properties availServiceAppConfigs = new Properties();
    private static boolean isLoadConfigs = false;
    public static synchronized void loadProperties() {
        if (!isLoadConfigs) {
            try {
                // Get the external configuration directory from system property
                String externalConfigDir = System.getProperty("externalConfigDir");

                if (externalConfigDir == null || externalConfigDir.isEmpty()) {
                    Logger.getLogger(AvailServiceConstant.LOG_CODE).log(Level.SEVERE, "External configuration directory not provided.");
                }

                // Construct the path to the config.properties file
                String configFilePath = externalConfigDir + File.separator + CONFIG_FILE;

                try (InputStream input = new FileInputStream(configFilePath)) {
                    availServiceAppConfigs.load(input);
                }
            } catch (Exception e) {
                Logger.getLogger(AvailServiceConstant.LOG_CODE).log(Level.SEVERE, "Error loading properties: " + e.getMessage());
            }
            isLoadConfigs = true;
        }
    }
}

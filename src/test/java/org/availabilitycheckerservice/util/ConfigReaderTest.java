package org.availabilitycheckerservice.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest
{
    private static final String EXISTING_PROPERTY_KEY = "existingKey";
    private static final String EXISTING_PROPERTY_VALUE = "existingValue";
    private static final String NON_EXISTING_PROPERTY_KEY = "nonExistingKey";

    @BeforeAll
    static void setup() throws IOException
    {
        // Create a temporary directory for externalConfigDir
        Path tempDirPath = Files.createTempDirectory("externalConfigDir");

        // Create a temporary config.properties file with existing property
        File configFile = new File(tempDirPath.toFile(), "config.properties");
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(EXISTING_PROPERTY_KEY + "=" + EXISTING_PROPERTY_VALUE);
        }
        System.setProperty("externalConfigDir", tempDirPath.toString());
    }

    @Test
    void loadProperties_ValidConfigFile_ShouldLoadProperties()
    {
        ConfigReader.loadProperties();
        String existingValue = ConfigReader.availServiceAppConfigs.getProperty(EXISTING_PROPERTY_KEY);
        String nonExistingValue = ConfigReader.availServiceAppConfigs.getProperty(NON_EXISTING_PROPERTY_KEY);

        assertNotNull(ConfigReader.availServiceAppConfigs.entrySet());
        assertEquals(EXISTING_PROPERTY_VALUE, existingValue);
        assertNull(nonExistingValue);
    }

    @Test
    void loadProperties_InvalidConfigFile_ShouldNotThrowException()
    {
        System.setProperty("externalConfigDir", "invalidDirectory");
        assertDoesNotThrow(ConfigReader::loadProperties);
    }

}
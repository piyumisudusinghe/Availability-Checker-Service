package org.availabilitycheckerservice.service;

import org.availabilitycheckerservice.util.AvailServiceConstant;
import org.availabilitycheckerservice.util.ConfigReader;

import java.util.logging.Logger;

public class AvailabilityService
{
    public static final Logger logger = Logger.getLogger(AvailServiceConstant.LOG_CODE);

    static
    {
        ConfigReader.loadProperties();
    }
}

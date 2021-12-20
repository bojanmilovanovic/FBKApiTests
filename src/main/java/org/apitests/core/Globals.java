package org.apitests.core;

import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeSuite;

import java.util.ResourceBundle;

public class Globals extends TestListenerAdapter {

    public static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("org.apitests." + ResourceBundle.getBundle("org.apitests.config").getString("environment"));

    //Global environment variables read from the property file
    public static String PROTOCOL = resourceBundle.getString("protocol");
    public static String HOST = resourceBundle.getString("host");
    public static String TENANT = resourceBundle.getString("tenant");
    public static String LOGIN_NAME = resourceBundle.getString("loginName");
    public static String PARTNER_ID = resourceBundle.getString("partnerId");
    public static String FUNDING_ID = resourceBundle.getString("fundingId");
    public static String FUNDING_MONITORING_ID = resourceBundle.getString("fundingMonitoringId");
    public static String FUNDING_EXTERNAL_ID = resourceBundle.getString("fundingExternalId");
    public static String TASK_ID = resourceBundle.getString("taskId");
    public static String ASSIGNEE_ID = resourceBundle.getString("assigneeId");
    public static String USER_UUID = resourceBundle.getString("userUUID");
    public static String FORMROUTE_ID = resourceBundle.getString("formrouteId");
    public static String SID = resourceBundle.getString("sid");
    public static String TASK_FORMROUTE_ID = resourceBundle.getString("taskFormRouteId");

    //Local variables assigned through tests
    public static String USER_ID = "";
    public static String DYNAMIC_FORM_ID = "";
    public static String TOKEN_VALUE = "";
    public static String TOKEN_TYPE = "";

    //Used to setup the globals according to environment selected in Jenkins
    public Globals(){
        Reporter.log("Global variables are being instantiated", true);
        ResourceBundle resourceBundleJ = ResourceBundle
                .getBundle("org.apitests." + System.getenv("environment"));
        Reporter.log("Global variables are being instantiated for environment "+System.getenv("environment"), true);
        PROTOCOL = resourceBundleJ.getString("protocol");
        HOST = resourceBundleJ.getString("host");
        TENANT = resourceBundleJ.getString("tenant");
        LOGIN_NAME = resourceBundleJ.getString("loginName");
        PARTNER_ID = resourceBundleJ.getString("partnerId");
        FUNDING_ID = resourceBundleJ.getString("fundingId");
        FUNDING_MONITORING_ID = resourceBundleJ.getString("fundingMonitoringId");
        FUNDING_EXTERNAL_ID = resourceBundleJ.getString("fundingExternalId");
        TASK_ID = resourceBundleJ.getString("taskId");
        ASSIGNEE_ID = resourceBundleJ.getString("assigneeId");
        USER_UUID = resourceBundleJ.getString("userUUID");
        FORMROUTE_ID = resourceBundleJ.getString("formrouteId");
        SID = resourceBundleJ.getString("sid");
        TASK_FORMROUTE_ID = resourceBundleJ.getString("taskFormRouteId");
    }



}
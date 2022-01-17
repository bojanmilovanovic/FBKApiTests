package org.apitests.core;

import org.testng.TestListenerAdapter;

import java.util.ResourceBundle;

public class Globals extends TestListenerAdapter {

    public static ResourceBundle resourceBundle = ResourceBundle
            .getBundle("org.apitests." + ResourceBundle.getBundle("org.apitests.config").getString("environment"));

    //Global environment variables read from the property file initially for local run
    public static String PROTOCOL = resourceBundle.getString("protocol");
    public static String HOST = resourceBundle.getString("host");
    public static String TENANT = resourceBundle.getString("tenant");
    public static String LOGIN_NAME = resourceBundle.getString("loginName");
    public static String PARTNER_ID = resourceBundle.getString("partnerId");
    public static String FUNDING_ID = resourceBundle.getString("fundingId");
    public static String FUNDING_ADDITIONAL_TABS_ID = resourceBundle.getString("fundingAdditionaTabsId");
    public static String FUNDING_EXTERNAL_ID = resourceBundle.getString("fundingExternalId");
    public static String TASK_ID = resourceBundle.getString("taskId");
    public static String ASSIGNEE_ID = resourceBundle.getString("assigneeId");
    public static String USER_UUID = resourceBundle.getString("userUUID");
    public static String FORMROUTE_ID = resourceBundle.getString("formrouteId");
    public static String SID = resourceBundle.getString("sid");
    public static String TASK_FORMROUTE_ID = resourceBundle.getString("taskFormRouteId");
    public static String USER_TOKEN = resourceBundle.getString("userToken");

    //Local variables assigned through tests
    public static String TOKEN_VALUE = "";
    public static String TOKEN_TYPE = "";
    public static String USER_ID = "";
    public static String DYNAMIC_FORM_ID = "";
    public static String DOWNLOAD_DOCUMENT = "";
    public static String FOLDER_NAME = "";

}
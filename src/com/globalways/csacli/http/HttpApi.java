package com.globalways.csacli.http;

public class HttpApi {

	private static final String HOME_URI = "http://123.57.132.7:8081";
	private static final String VERSION = "/v1";

	public static final String ACCOUNT_LOGIN = HOME_URI + VERSION + "";

	public static final String HONG_ID_GET_BY_PAGE = HOME_URI + VERSION + "/admins/members";
	public static final String HONG_ID_GET_ALL = HOME_URI + VERSION + "/admins/members/all";

	public static final String HONG_ID_GENERATE = HOME_URI + VERSION + "/admins/members/generate";

	public static final String STORE_NEW_STORE = HOME_URI + "/v2/merchants/stores";
	public static final String STORE_LIST = HOME_URI + "/v2/merchants/stores";
	public static final String STORE_UPDATE = HOME_URI + "/v2/merchants/stores/:sid";
	public static final String STORE_DETAIL = HOME_URI + "/v2/merchants/stores/:sid";
	
	public static final String INDUSTRY_LIST = HOME_URI + "/v2/joint/industries/all";
}

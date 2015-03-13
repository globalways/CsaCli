package com.globalways.csacli.http;

public class HttpApi {

	private static final String HOME_URI = "http://123.57.132.7:8081";
	private static final String VERSION = "/v1";

	public static final String ACCOUNT_LOGIN = HOME_URI + VERSION + "";

	public static final String HONG_ID_GET_BY_PAGE = HOME_URI + VERSION + "/admins/members";
	public static final String HONG_ID_GET_ALL = HOME_URI + VERSION + "/admins/members/all";

	public static final String HONG_ID_GENERATE = HOME_URI + VERSION + "/admins/members/generate";
}

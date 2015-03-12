package com.globalways.csacli.http.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.globalways.csacli.entity.HongIdEntity;
import com.globalways.csacli.http.HttpApi;
import com.globalways.csacli.http.HttpClientDao.ErrorCode;
import com.globalways.csacli.http.HttpClientDao.HttpClientUtilCallBack;
import com.globalways.csacli.http.HttpCode;
import com.globalways.csacli.http.HttpUtils;

public class HongIdManager {

	private static HongIdManager hongIdManager;

	private HongIdManager() {
	}

	public static HongIdManager getInstance() {
		if (hongIdManager == null) {
			hongIdManager = new HongIdManager();
		}
		return hongIdManager;
	}

	/**
	 * this function make sure HttpUtils is singleton.
	 *
	 * @return
	 */
	private Object readResolve() {
		return hongIdManager;
	}

	private static final int PAGE_SIZE = 20;
	private int page = 1;

	public void getHongIdEntityList(final boolean isRefresh, final ManagerCallBack<List<HongIdEntity>> callBack) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("fields", fields);
		// params.put("member_type", 0);
		params.put("size", PAGE_SIZE);
		params.put("page", page);
		HttpUtils.getInstance().sendGetRequest(HttpApi.HONG_ID_GET_BY_PAGE, 1, params,
				new HttpClientUtilCallBack<String>() {
					@Override
					public void onSuccess(String url, long flag, String returnContent) {
						super.onSuccess(url, flag, returnContent);
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(returnContent);
							int code = jsonObject.getInt("code");
							if (code == HttpCode.SUCCESS) {
								List<HongIdEntity> list = null;
								if (null != callBack) {
									callBack.onSuccess(list);
								}
								if (isRefresh) {
									page = 1;
								} else {
									page++;
								}
							} else {
								if (null != callBack) {
									callBack.onFailure(code, jsonObject.getString("msg"));
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(String url, long flag, ErrorCode errorCode) {
						super.onFailure(url, flag, errorCode);
						if (null != callBack) {
							callBack.onFailure(errorCode.code(), errorCode.msg());
						}
					}
				});
	}
}

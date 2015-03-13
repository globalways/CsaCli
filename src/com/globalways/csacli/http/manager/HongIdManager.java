package com.globalways.csacli.http.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.globalways.csacli.Config;
import com.globalways.csacli.entity.HongIdEntity;
import com.globalways.csacli.http.HttpApi;
import com.globalways.csacli.http.HttpClientDao.ErrorCode;
import com.globalways.csacli.http.HttpClientDao.HttpClientUtilCallBack;
import com.globalways.csacli.http.HttpCode;
import com.globalways.csacli.http.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

	/**
	 * 会员类型
	 * 
	 * @author James
	 *
	 */
	public enum MemberType {
		/** 用户 */
		USER(1),
		/** 机构会员 */
		COMPANY(2);
		private MemberType(int type) {
			this.type = type;
		}

		private int type;

		public int getType() {
			return type;
		}
	}

	/**
	 * 扩展HongId
	 * 
	 * @param minNum
	 * @param maxNum
	 * @param num
	 * @param type
	 * @param callBack
	 */
	public void expandHongId(int min_id, int max_id, int count, MemberType member_type,
			final ManagerCallBack<String> callBack) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("min_id", min_id);
		params.put("max_id", max_id);
		params.put("count", count);
		params.put("member_type", member_type.getType());
		HttpUtils.getInstance().sendPostRequest(HttpApi.HONG_ID_GENERATE, 1, params,
				new HttpClientUtilCallBack<String>() {
					@Override
					public void onSuccess(String url, long flag, String returnContent) {
						super.onSuccess(url, flag, returnContent);
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(returnContent);
							int code = jsonObject.getJSONObject(Config.STATUS).getInt(Config.CODE);
							if (code == HttpCode.SUCCESS) {
								if (null != callBack) {
									callBack.onSuccess(null);
								}
							} else {
								if (null != callBack) {
									callBack.onFailure(code,
											jsonObject.getJSONObject(Config.STATUS).getString(Config.MSG));
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

	public void getHongIdEntityList(final boolean isRefresh, final ManagerCallBack<List<HongIdEntity>> callBack) {
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("fields", fields);
		// params.put("search", search);
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
							int code = jsonObject.getJSONObject(Config.STATUS).getInt(Config.CODE);
							if (code == HttpCode.SUCCESS) {
								Gson gson = new Gson();
								List<HongIdEntity> list = new ArrayList<HongIdEntity>();
								list = gson.fromJson(jsonObject.getString(Config.BODY),
										new TypeToken<List<HongIdEntity>>() {
										}.getType());
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
									callBack.onFailure(code,
											jsonObject.getJSONObject(Config.STATUS).getString(Config.MSG));
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

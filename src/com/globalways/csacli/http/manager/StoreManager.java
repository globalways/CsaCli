package com.globalways.csacli.http.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.globalways.csacli.Config;
import com.globalways.csacli.entity.IndustryEntity;
import com.globalways.csacli.entity.StoreEntity;
import com.globalways.csacli.http.HttpApi;
import com.globalways.csacli.http.HttpClientDao.ErrorCode;
import com.globalways.csacli.http.HttpClientDao.HttpClientUtilCallBack;
import com.globalways.csacli.http.HttpCode;
import com.globalways.csacli.http.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 商铺manager
 * 
 * @author james
 *
 */
public class StoreManager {

	private static StoreManager storeManager;

	private StoreManager() {
	}

	public static StoreManager getInstance() {
		if (storeManager == null) {
			storeManager = new StoreManager();
		}
		return storeManager;
	}

	/**
	 * this function make sure HttpUtils is singleton.
	 *
	 * @return
	 */
	private Object readResolve() {
		return storeManager;
	}

	/** 店铺类型 */
	public enum StoreType {
		/** 个体户 */
		NORMAL(1),
		/** 连锁店 */
		CHAIN(2),
		/** 免税店 */
		FREE_DUTY(3);
		private StoreType(int type) {
			this.type = type;
		}

		private int type;

		public int getType() {
			return type;
		}
	}

	/** 店铺状态 */
	public enum StoreStatus {
		/** 未锁定 */
		UNLOCK(1),
		/** 锁定 */
		LOCK(2);
		private StoreStatus(int type) {
			this.type = type;
		}

		private int type;

		public int getType() {
			return type;
		}
	}

	/**
	 * 新建商铺，或为连锁店添加加盟店
	 * 
	 * @param pid
	 *            父商铺ID；选填，默认值0
	 * @param store_name
	 *            商铺名称；必填
	 * @param store_sub
	 *            商铺副标题；必填
	 * @param store_desc
	 *            商铺描述；选填
	 * @param industry_name
	 *            行业分类名称；必填
	 * @param store_address
	 *            商铺地址；必填
	 * @param store_password
	 *            商铺管理密码：必填
	 * @param store_avatar
	 *            商铺图片地址，多张图片用英文逗号分割；选填
	 * @param store_phone
	 *            商铺联系方式；选填
	 * @param store_email
	 *            商铺联系方式；选填
	 * @param product_hot_limit
	 *            商品标记为hot的阀值；选填
	 * @param delivery_distance
	 *            送货的最远距离，单位：m；选填
	 * @param delivery_price
	 *            送货的价格，单位：分；选填
	 * @param delivery_flag
	 *            送货标识，1可送货，2不送货；选填，默认2
	 * @param canvass_price
	 *            揽货价格，单位：分；选填
	 * @param canvass_flag
	 *            揽货标识，1可揽货，2不可以；选填，默认2
	 * @param store_news
	 *            商铺当前公告；选填
	 * @param status
	 *            店铺状态；必填
	 * @param store_type
	 *            店铺类型；必填
	 * @param callBack
	 */
	public void addStore(int pid, String store_name, String store_sub, String store_desc, String industry_name,String store_password,
			String store_address, String store_avatar, String store_phone,String store_email, int product_hot_limit,
			int delivery_distance, int delivery_price, int delivery_flag, int canvass_price,
			int canvass_flag, String store_news, StoreStatus status, StoreType store_type,
			final ManagerCallBack<String> callBack) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (pid != 0) {
			params.put("pid", pid);
		}
		params.put("password", store_password);
		params.put("store_name", store_name);
		params.put("store_sub", store_sub);
		if (store_desc != null && !store_desc.isEmpty()) {
			params.put("store_desc", store_desc);
		}
		params.put("industry_name", industry_name);
		params.put("store_address", store_address);
		if (store_avatar != null && !store_avatar.isEmpty()) {
			params.put("store_avatar", store_avatar);
		}
		if (store_phone != null && !store_phone.isEmpty()) {
			params.put("store_phone", store_phone);
		}
		if (store_email != null && !store_email.isEmpty()) {
			params.put("store_email", store_email);
		}
		if (product_hot_limit != 0) {
			params.put("product_hot_limit", product_hot_limit);
		}
		if (delivery_distance != 0) {
			params.put("delivery_distance", delivery_distance);
		}
		if (delivery_price != 0) {
			params.put("delivery_price", delivery_price);
		}
		params.put("delivery_flag", delivery_flag);
		if (delivery_price != 0) {
			params.put("delivery_price", delivery_price);
		}
		params.put("canvass_flag", canvass_flag);
		if (store_news != null && !store_news.isEmpty()) {
			params.put("store_news", store_news);
		}
		if (status != null) {
			params.put("status", status.getType());
		}
		if (store_type != null) {
			params.put("store_type", store_type.getType());
		}
		HttpUtils.getInstance().sendPostRequest(HttpApi.STORE_NEW_STORE, 1, params,
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

	private static final int PAGE_SIZE = 20;
	private int page = 1;

	/**
	 * 分页获取商铺列表
	 * 
	 * @param isRefresh
	 *            刷新还是加载更多
	 * @param callBack
	 */
	public void getStoreList(final boolean isRefresh, final ManagerCallBack<List<StoreEntity>> callBack) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", isRefresh ? 1 : page);
		params.put("size", PAGE_SIZE);
		HttpUtils.getInstance().sendGetRequest(HttpApi.STORE_LIST, 1, params, new HttpClientUtilCallBack<String>() {
			@Override
			public void onSuccess(String url, long flag, String returnContent) {
				super.onSuccess(url, flag, returnContent);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(returnContent);
					int code = jsonObject.getJSONObject(Config.STATUS).getInt(Config.CODE);
					if (code == HttpCode.SUCCESS) {
						Gson gson = new Gson();
						List<StoreEntity> list = new ArrayList<StoreEntity>();
						list = gson.fromJson(jsonObject.getString(Config.BODY), new TypeToken<List<StoreEntity>>() {
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
							callBack.onFailure(code, jsonObject.getJSONObject(Config.STATUS).getString(Config.MSG));
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

	/**
	 * 获取所有行业列表数据
	 * 
	 * @param callBack
	 */
	public void getIndustryList(final ManagerCallBack<List<IndustryEntity>> callBack) {
		HttpUtils.getInstance().sendGetRequest(HttpApi.INDUSTRY_LIST, 1, null, new HttpClientUtilCallBack<String>() {
			@Override
			public void onSuccess(String url, long flag, String returnContent) {
				super.onSuccess(url, flag, returnContent);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(returnContent);
					int code = jsonObject.getJSONObject(Config.STATUS).getInt(Config.CODE);
					if (code == HttpCode.SUCCESS) {
						Gson gson = new Gson();
						List<IndustryEntity> list = new ArrayList<IndustryEntity>();
						list = gson.fromJson(jsonObject.getString(Config.BODY), new TypeToken<List<IndustryEntity>>() {
						}.getType());
						if (null != callBack) {
							callBack.onSuccess(list);
						}
					} else {
						if (null != callBack) {
							callBack.onFailure(code, jsonObject.getJSONObject(Config.STATUS).getString(Config.MSG));
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

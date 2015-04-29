package com.globalways.csacli.ui.store;

import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.StoreEntity;
import com.globalways.csacli.http.manager.ManagerCallBack;
import com.globalways.csacli.http.manager.StoreManager;
import com.globalways.csacli.tools.MyLog;
import com.globalways.csacli.ui.BaseFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 商铺管理
 * 
 * @author james
 *
 */
public class StoreActivity extends BaseFragmentActivity implements OnClickListener, OnRefreshListener<ListView>,
		OnItemClickListener {
	private static final String TAG = StoreActivity.class.getSimpleName();

	private TextView textLeft, textCenter, textRight;

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private StoreListAdapter storeListAdapter;
	private StoreDetailFragment storeDetailFragment;
	private StoreAddShopFragment storeAddShopFragment;
	private View dialogContainer;
	
	//search widget
	private EditText mEtSearch = null;// 输入搜索内容
	private Button mBtnClearSearchText = null;// 清空搜索信息的按钮
	private LinearLayout mLayoutClearSearchText = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_split_screen);
		initView();

		// 初始化完成后加载StoreList数据
		refreshListView.setRefreshing();
		loadStoreList(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			finish();
			break;
		case R.id.textRight:
			showAddStoreView();
			break;
		}
	}

	private void showAddStoreView() {
		if (storeAddShopFragment == null) {
			storeAddShopFragment = new StoreAddShopFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.dialogContainer, storeAddShopFragment)
					.show(storeAddShopFragment).commit();
		}
		dialogContainer.setVisibility(View.VISIBLE);
	}

	public void hideAddStoreView() {
		dialogContainer.setVisibility(View.GONE);
	}

	/** 如果单击Store列表中的单项，就将该position的实体传递给detail类，detail类刷新界面 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MyLog.d(TAG, "StoreList click position: " + position);
		storeDetailFragment.setEntity(storeListAdapter.getItemByPosition(position - 1));
		storeListAdapter.setChooseItem(position - 1);
	}

	/** PullToRefreshListView的下拉或上拉监听接口 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> arg0) {
		loadStoreList(refreshListView.isHeaderShown());
	}

	/** 加载StoreList数据，isRefresh为true时，刷新；isRefresh为false时，加载更多 */
	private void loadStoreList(final boolean isRefresh) {
		MyLog.d(TAG, "begin loadStoreList, is refresh: " + isRefresh);
		StoreManager.getInstance().getStoreList(isRefresh, new ManagerCallBack<List<StoreEntity>>() {
			@Override
			public void onSuccess(List<StoreEntity> returnContent) {
				super.onSuccess(returnContent);
				MyLog.d(TAG, "loadStoreList success.");
				storeListAdapter.setData(isRefresh, returnContent);
				storeDetailFragment.setEntity(storeListAdapter.getItemByPosition(0));
				refreshListView.onRefreshComplete();
			}

			@Override
			public void onFailure(int code, String msg) {
				super.onFailure(code, msg);
				MyLog.d(TAG, "loadStoreList error code: " + code + ", msg: " + msg);
				Toast.makeText(StoreActivity.this, msg, Toast.LENGTH_SHORT).show();
				refreshListView.onRefreshComplete();
			}
		});
	}

	/** 初始化UI、设置监听 */
	private void initView() {
		textLeft = (TextView) findViewById(R.id.textleft);
		textLeft.setText("返回");
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);

		textCenter = (TextView) findViewById(R.id.textCenter);
		textCenter.setText("商铺管理");
		textCenter.setVisibility(View.VISIBLE);

		textRight = (TextView) findViewById(R.id.textRight);
		textRight.setText("添加商铺");
		textRight.setVisibility(View.VISIBLE);
		textRight.setOnClickListener(this);

		refreshListView = (PullToRefreshListView) findViewById(R.id.refreshListView);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		listView = refreshListView.getRefreshableView();
		storeListAdapter = new StoreListAdapter(this);
		listView.setAdapter(storeListAdapter);
		listView.setOnItemClickListener(this);
		storeDetailFragment = new StoreDetailFragment();
		dialogContainer = findViewById(R.id.dialogContainer);
		dialogContainer.setVisibility(View.GONE);

		getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer, storeDetailFragment)
				.show(storeDetailFragment).commit();
		
		
		
		
		// search 
		
		mEtSearch = (EditText) findViewById(R.id.et_search);
		mBtnClearSearchText = (Button) findViewById(R.id.btn_clear_search_text);
		mLayoutClearSearchText = (LinearLayout) findViewById(R.id.layout_clear_search_text);
		mEtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int textLength = mEtSearch.getText().length();
				if (textLength > 0) {
					mLayoutClearSearchText.setVisibility(View.VISIBLE);
				} else {
					mLayoutClearSearchText.setVisibility(View.GONE);
				}
			}
		});

		mBtnClearSearchText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEtSearch.setText("");
				mLayoutClearSearchText.setVisibility(View.GONE);
			}
		});
		mEtSearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					Toast.makeText(StoreActivity.this,
							mEtSearch.getText().toString().trim(),
							Toast.LENGTH_LONG).show();
				}
				return false;
			}
		});
		
		
		
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (dialogContainer.isShown()) {
				if (storeAddShopFragment.isProgressing()) {
					storeAddShopFragment.cancelDialog();
					hideAddStoreView();
				} else {
					hideAddStoreView();
				}
				return true;
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}

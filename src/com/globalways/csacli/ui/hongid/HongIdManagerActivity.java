package com.globalways.csacli.ui.hongid;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.HongIdEntity;
import com.globalways.csacli.http.manager.HongIdManager;
import com.globalways.csacli.http.manager.ManagerCallBack;
import com.globalways.csacli.tools.MyLog;
import com.globalways.csacli.ui.BaseFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * HongId管理界面
 * 
 * @author James
 *
 */
public class HongIdManagerActivity extends BaseFragmentActivity implements OnClickListener, OnItemClickListener,
		OnRefreshListener<ListView> {
	private static final String TAG = HongIdManagerActivity.class.getSimpleName();

	private TextView textLeft, textCenter;

	private PullToRefreshListView refreshListView;
	private ListView listView;
	private HongIdListAdapter hongIdAdapter;
	private HongIdDetailFragment hongIdDetailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_split_screen);
		initView();

		// 初始化完成后加载HongId数据
		refreshListView.setRefreshing();
		loadHongId(true);
	}

	/** 如果单击HongId列表中的单项，就将该position的实体传递给detail类，detail类刷新界面 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		MyLog.d(TAG, "HongIdList click position: " + position);
		hongIdDetailFragment.setEntity(hongIdAdapter.getItemByPosition(position - 1));
		hongIdAdapter.setChooseItem(position - 1);
	}

	/** PullToRefreshListView的下拉或上拉监听接口 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> arg0) {
		loadHongId(refreshListView.isHeaderShown());
	}

	/** 加载HongId数据，isRefresh为true时，刷新；isRefresh为false时，加载更多 */
	private void loadHongId(final boolean isRefresh) {
		MyLog.d(TAG, "begin loadHongIdList, is refresh: " + isRefresh);
		HongIdManager.getInstance().getHongIdEntityList(isRefresh, new ManagerCallBack<List<HongIdEntity>>() {
			@Override
			public void onSuccess(List<HongIdEntity> returnContent) {
				super.onSuccess(returnContent);
				MyLog.d(TAG, "loadHongIdList success.");
				hongIdAdapter.setData(isRefresh, returnContent);
				refreshListView.onRefreshComplete();

				hongIdDetailFragment.setEntity(hongIdAdapter.getItemByPosition(0));
				hongIdAdapter.setChooseItem(0);
			}

			@Override
			public void onFailure(int code, String msg) {
				super.onFailure(code, msg);
				MyLog.d(TAG, "loadHongIdList error code: " + code + ", msg: " + msg);
				Toast.makeText(HongIdManagerActivity.this, msg, Toast.LENGTH_SHORT).show();
				refreshListView.onRefreshComplete();
			}
		});
	}

	/** 初始化View和一些实例 */
	private void initView() {
		textLeft = (TextView) findViewById(R.id.textleft);
		textLeft.setText("返回");
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);

		textCenter = (TextView) findViewById(R.id.textCenter);
		textCenter.setText("HongId管理");
		textCenter.setVisibility(View.VISIBLE);

		refreshListView = (PullToRefreshListView) findViewById(R.id.refreshListView);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		listView = refreshListView.getRefreshableView();
		hongIdAdapter = new HongIdListAdapter(this);
		listView.setAdapter(hongIdAdapter);
		listView.setOnItemClickListener(this);
		hongIdDetailFragment = new HongIdDetailFragment();

		getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer, hongIdDetailFragment)
				.show(hongIdDetailFragment).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			finish();
			break;
		}
	}

}

package com.globalways.csacli.ui.hongid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.globalways.csacli.R;
import com.globalways.csacli.ui.BaseFragmentActivity;

/**
 * HongId使用情况界面，主要是图表以及管理入口
 * 
 * @author James
 *
 */
public class HongIdActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView textLeft, textCenter;
	private Button btnExpand, btnDetail;
	private View layoutContainer;

	private HongIdExpandFragment hongIdExpandFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hongid_activity);
		initView();
		initData();
	}

	/** 加载数据 */
	private void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			HongIdActivity.this.finish();
			break;
		case R.id.btnExpand:
			layoutContainer.setVisibility(View.VISIBLE);
			break;
		case R.id.btnDetail:
			startActivity(new Intent(this, HongIdManagerActivity.class));
			break;
		}
	}

	/** 取消扩展HongId */
	public void hideHongIdExpand() {
		layoutContainer.setVisibility(View.GONE);
	}

	/** 扩展HongId成功，刷新UI */
	public void expandeFinish() {
		layoutContainer.setVisibility(View.GONE);
		initData();
	}

	/** 初始化UI、设置监听 */
	private void initView() {
		textLeft = (TextView) findViewById(R.id.textleft);
		textLeft.setText("返回");
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);

		textCenter = (TextView) findViewById(R.id.textCenter);
		textCenter.setText("Hongid使用情况");
		textCenter.setVisibility(View.VISIBLE);

		btnExpand = (Button) findViewById(R.id.btnExpand);
		btnExpand.setOnClickListener(this);
		btnDetail = (Button) findViewById(R.id.btnDetail);
		btnDetail.setOnClickListener(this);

		layoutContainer = findViewById(R.id.layoutContainer);
		layoutContainer.setVisibility(View.GONE);

		hongIdExpandFragment = new HongIdExpandFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer, hongIdExpandFragment)
				.show(hongIdExpandFragment).commit();
	}

}

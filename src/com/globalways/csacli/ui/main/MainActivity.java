package com.globalways.csacli.ui.main;

import com.globalways.csacli.R;
import com.globalways.csacli.ui.BaseActivity;
import com.globalways.csacli.ui.hongid.HongIdActivity;
import com.globalways.csacli.ui.store.StoreActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnClickListener {

	private Button btnHongIdManager, btnStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHongIdManager:
			startActivity(new Intent(this, HongIdActivity.class));
			break;
		case R.id.btnStore:
			startActivity(new Intent(this, StoreActivity.class));
			break;
		}
	}

	private void initView() {
		btnHongIdManager = (Button) findViewById(R.id.btnHongIdManager);
		btnHongIdManager.setOnClickListener(this);
		btnStore = (Button) findViewById(R.id.btnStore);
		btnStore.setOnClickListener(this);
	}

}

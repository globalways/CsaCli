package com.globalways.csacli.ui.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.StoreEntity;
import com.globalways.csacli.ui.BaseFragment;

public class StoreDetailFragment extends BaseFragment {

	private View storeDetailView;
	private TextView textStoreId, textStoreName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == storeDetailView) {
			storeDetailView = inflater.inflate(R.layout.store_detail_fragment, container, false);
		}
		return storeDetailView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void setEntity(StoreEntity entity) {
		textStoreId.setText(entity.getId() + "");
		textStoreName.setText(entity.getStore_name());
	}

	private void initView() {
		textStoreId = (TextView) storeDetailView.findViewById(R.id.textStoreId);
		textStoreName = (TextView) storeDetailView.findViewById(R.id.textStoreName);
	}

}

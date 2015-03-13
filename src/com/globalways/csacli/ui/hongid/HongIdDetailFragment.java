package com.globalways.csacli.ui.hongid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.HongIdEntity;
import com.globalways.csacli.ui.BaseFragment;

/**
 * 
 * HongId单条详情界面
 * 
 * @author James
 *
 */
public class HongIdDetailFragment extends BaseFragment {
	private static final String TAG = HongIdDetailFragment.class.getSimpleName();

	private HongIdEntity entity;
	private TextView textHongId;
	private View hongIdDetailView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == hongIdDetailView) {
			hongIdDetailView = inflater.inflate(R.layout.hongid_manager_detail_fragment, container, false);
		}
		return hongIdDetailView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	/** 初始化View */
	private void initView() {
		textHongId = (TextView) hongIdDetailView.findViewById(R.id.textHongId);
	}

	/** 根据实体值刷新View */
	public void setEntity(HongIdEntity entity) {
		this.entity = entity;
		textHongId.setText("HongId: " + entity.getHong_id());
	}
}

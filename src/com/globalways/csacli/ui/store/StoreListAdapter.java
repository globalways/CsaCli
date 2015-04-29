package com.globalways.csacli.ui.store;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.StoreEntity;

public class StoreListAdapter extends BaseAdapter {

	private List<StoreEntity> list = null;
	private StoreEntity chooseStore;
	private Context context;

	public StoreListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setData(boolean isRefresh, List<StoreEntity> list) {
		if (isRefresh) {
			this.list = list;
			chooseStore = list.get(0);
		} else {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void setChooseItem(int position) {
		chooseStore = list.get(position);
		notifyDataSetChanged();
	}

	public StoreEntity getItemByPosition(int position) {
		if (null != list) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null != list) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ItemView mItemView;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.store_list_item, null);
			mItemView = new ItemView();
			findView(mItemView, convertView);
			convertView.setTag(mItemView);
		} else {
			mItemView = (ItemView) convertView.getTag();
		}
		if (chooseStore.getStore_id() == list.get(position).getStore_id()) {
			mItemView.textStoreId.setTextColor(context.getResources().getColor(R.color.base_black_333333));
		} else {
			mItemView.textStoreId.setTextColor(context.getResources().getColor(R.color.base_black_999999));
		}
		mItemView.textStoreId.setText("StoreId: " + list.get(position).getStore_id());
		mItemView.textStoreName.setText("StoreName: " + list.get(position).getStore_name());
		return convertView;
	}

	private class ItemView {
		TextView textStoreId, textStoreName;
	}

	public void findView(ItemView itemView, View convertView) {
		itemView.textStoreId = (TextView) convertView.findViewById(R.id.textStoreId);
		itemView.textStoreName = (TextView) convertView.findViewById(R.id.textStoreName);
	}
}

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
import com.globalways.csacli.entity.IndustryEntity;

public class StoreIndustryAdapter extends BaseAdapter {

	private List<IndustryEntity> list = null;
	private Context context;

	public StoreIndustryAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setData(List<IndustryEntity> list) {
		this.list = list;
		notifyDataSetChanged();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.store_industry_list_item, null);
			mItemView = new ItemView();
			findView(mItemView, convertView);
			convertView.setTag(mItemView);
		} else {
			mItemView = (ItemView) convertView.getTag();
		}
		mItemView.textIndustryName.setText(list.get(position).getIndustry_name());
		return convertView;
	}

	private class ItemView {
		TextView textIndustryName;
	}

	public void findView(ItemView itemView, View convertView) {
		itemView.textIndustryName = (TextView) convertView.findViewById(R.id.textIndustryName);
	}
}

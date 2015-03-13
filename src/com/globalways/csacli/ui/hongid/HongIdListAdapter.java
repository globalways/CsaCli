package com.globalways.csacli.ui.hongid;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.HongIdEntity;

public class HongIdListAdapter extends BaseAdapter {

	private List<HongIdEntity> list = null;
	private HongIdEntity chooseHongId;
	private Context context;

	public HongIdListAdapter(Context context) {
		super();
		this.context = context;
	}

	public void setData(boolean isRefresh, List<HongIdEntity> list) {
		if (isRefresh) {
			this.list = list;
			chooseHongId = list.get(0);
		} else {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void setChooseItem(int position) {
		chooseHongId = list.get(position);
		notifyDataSetChanged();
	}

	public HongIdEntity getItemByPosition(int position) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.hongid_list_item, null);
			mItemView = new ItemView();
			findView(mItemView, convertView);
			convertView.setTag(mItemView);
		} else {
			mItemView = (ItemView) convertView.getTag();
		}
		if (chooseHongId.getHong_id() == list.get(position).getHong_id()) {
			mItemView.textHongId.setTextColor(context.getResources().getColor(R.color.base_black_333333));
		} else {
			mItemView.textHongId.setTextColor(context.getResources().getColor(R.color.base_black_999999));
		}
		mItemView.textHongId.setText("HongId: " + list.get(position).getHong_id());
		mItemView.textNick.setText("Nick: " + list.get(position).getNick_name());
		mItemView.textEmail.setText("Email: " + list.get(position).getEmail());
		mItemView.textTel.setText("Tel: " + list.get(position).getTel());
		return convertView;
	}

	private class ItemView {
		TextView textHongId, textNick, textTel, textEmail;
	}

	public void findView(ItemView itemView, View convertView) {
		itemView.textHongId = (TextView) convertView.findViewById(R.id.textHongId);
		itemView.textNick = (TextView) convertView.findViewById(R.id.textNick);
		itemView.textTel = (TextView) convertView.findViewById(R.id.textTel);
		itemView.textEmail = (TextView) convertView.findViewById(R.id.textEmail);
	}
}

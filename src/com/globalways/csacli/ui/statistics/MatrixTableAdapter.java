package com.globalways.csacli.ui.statistics;

import com.globalways.csacli.R;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MatrixTableAdapter<T> extends BaseTableAdapter {

	private final static int WIDTH_DIP = 110;
	private final static int HEIGHT_DIP = 32;

	private android.view.View.OnClickListener mItemClickListener;
	private final Context context;
	private LayoutInflater mInflater;

	private T[][] table;

	private final int width;
	private final int height;

	public MatrixTableAdapter(Context context) {
		this(context, null);
	}

	public MatrixTableAdapter(Context context, T[][] table) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		Resources r = context.getResources();

		width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_DIP, r.getDisplayMetrics()));
		height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DIP, r.getDisplayMetrics()));

		setInformation(table);
	}

	public void setInformation(T[][] table) {
		this.table = table;
	}

	@Override
	public int getRowCount() {
		return table.length - 1;
	}

	@Override
	public int getColumnCount() {
		return table[0].length - 1;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent) {
		switch (getItemViewType(row, column)) {
		case 0:
			return getFirstHeader(row, column, convertView, parent);
		case 1:
			return getHeader(row, column, convertView, parent);
		case 2:
			return getBody(row, column, convertView, parent);
		default:
			return getBody(row, column, convertView, parent);
		}
	}
	
	/**
	 * first of table,means store name
	 * @param row
	 * @param column
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_table_header_first, parent, false);
		}
		((TextView) convertView.findViewById(android.R.id.text1)).setText(table[row + 1][column + 1].toString());
		
		if(mItemClickListener != null)
		{
			convertView.setOnClickListener(mItemClickListener);
		}
		return convertView;
	}

	/**
	 * teble header
	 * @param row
	 * @param column
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View getHeader(int row, int column, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_table_header, parent, false);
		}
		((TextView) convertView.findViewById(R.id.tv_table_header_store_name)).setText(table[row + 1][column + 1].toString());
		return convertView;
	}

	/**
	 * table body
	 * @param row
	 * @param column
	 * @param convertView
	 * @param parent
	 * @return
	 */
	private View getBody(int row, int column, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TextView(context);
			((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
		}
		((TextView) convertView).setText(table[row + 1][column + 1].toString());
		return convertView;
	}

	
	/**
	 * when click on store name in header row
	 * @param listener
	 */
	public void setOnHeaderStoreNameClickListener(android.view.View.OnClickListener listener)
	{
		mItemClickListener = listener;
	}
	
	
	@Override
	public int getHeight(int row) {
		return height;
	}

	@Override
	public int getWidth(int column) {
		return width;
	}

	@Override
	public int getItemViewType(int row, int column) {
		int itemViewType = 0;
		//store name
		if (row == -1 && column == -1) {
			itemViewType = 0;
	    //table header
		} else if (row == -1) {
			itemViewType = 1;
		} else if (row > -1) {
			itemViewType = 2;
		} 
		return itemViewType;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

}

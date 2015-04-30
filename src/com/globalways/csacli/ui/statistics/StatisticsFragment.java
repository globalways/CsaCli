package com.globalways.csacli.ui.statistics;

import java.util.Calendar;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.globalways.csacli.R;
import com.inqbarna.tablefixheaders.TableFixHeaders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class StatisticsFragment extends Fragment implements OnClickListener, OnDateSetListener{

	
	private Button btnStatStartDate, btnStatEndtDate;
	private View layoutView;
	private WebView mWebView;
	private MatrixTableAdapter<String> matrixTableAdapter;
	private DatePickerDialog mDatePickerDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatePickerDialog = new DatePickerDialog();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(null == layoutView)
		{
			layoutView = inflater.inflate(R.layout.store_statistics_fragment, container, false);
			initWebView();
		}
		return layoutView;
	}
	
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView()
	{
		
		//widget
		btnStatStartDate = (Button) layoutView.findViewById(R.id.btnStatStartDate);
		btnStatStartDate.setOnClickListener(this);
		btnStatEndtDate = (Button) layoutView.findViewById(R.id.btnStatEndtDate);
		btnStatEndtDate.setOnClickListener(this);
		
		
		mWebView = (WebView) layoutView.findViewById(R.id.wv_statistics);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		mWebView.setWebChromeClient(new WebChromeClient());
		
		
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				
			}
			
		});
		//init data
		mWebView.loadUrl("file:///android_asset/baidu_echarts/store_statistics.html");
		TableFixHeaders tableFixHeaders = (TableFixHeaders) layoutView.findViewById(R.id.statistics_table);
		
		String[][] data = new String[][]{
				{
					"商铺名称",
					"营业额",
					"销量",
					"环途收入",
					 },
			{
					"Lorem",
					"sed",
					"do",
					"eiusmod",
					},
			{
					"ipsum",
					"irure",
					"occaecat",
					"enim",
					 },
			{
					"dolor",
					"fugiat",
					"nulla",
					"reprehenderit",
					 },
			{
					"sit",
					"consequat",
					"laborum",
					"fugiat",
					 },
		};
		
		matrixTableAdapter = new MatrixTableAdapter<String>(getActivity(), data);
		tableFixHeaders.setAdapter(matrixTableAdapter);
		
		final String[][] data2 = new String[][]{
				{
					"商铺名称",
					"营业额",
					"销量",
					"环途收入",
					 },
			{
					"store1",
					"1343.4",
					"do",
					"eiusmod",
					},
			{
					"ipsum",
					"irure",
					"occaecat",
					"enim",
					 },
			{
					"dolor",
					"fugiat",
					"nulla",
					"reprehenderit",
					 },
			{
					"sit",
					"consequat",
					"laborum",
					"fugiat",
					 },
		};
		
		//custom item click
		matrixTableAdapter.setOnHeaderStoreNameClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//change data
				matrixTableAdapter.setInformation(data2);
				matrixTableAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), "click store name", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int date[] = new int[3];
		switch (v.getId()) {
		
		case R.id.btnStatStartDate:
			date = getButtonDate(btnStatStartDate);
			mDatePickerDialog.initialize(this, date[0], date[1], date[2], false);
			mDatePickerDialog.show(getFragmentManager(), "START_DATE");
			break;
			
		case R.id.btnStatEndtDate:
			date = getButtonDate(btnStatEndtDate);
			mDatePickerDialog.initialize(this, date[0], date[1], date[2], false);
			mDatePickerDialog.show(getFragmentManager(), "END_DATE");
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year,
			int month, int day) {
			String tag = datePickerDialog.getTag();
			switch (tag) {
			case "START_DATE" :
				setButtonText(btnStatStartDate, year, month, day);
				break;
			case "END_DATE" :
				setButtonText(btnStatEndtDate, year, month, day);
				break;
			default:
				break;
			}
		
	}
	
	private int[] getButtonDate(Button button)
	{
		int[] result = new int[3];
		String[] str = button.getText().toString().split("-");
		for(int i=0;i< str.length;i++)
		{
			result[i] = Integer.parseInt(str[i]);
		}
		return result;
	}
	private void setButtonText(Button button, int year, int month, int day)
	{
		StringBuilder sb = new StringBuilder(String.valueOf(year));
		sb.append("-").
		append(String.valueOf(month)).
		append("-").
		append(String.valueOf(day));
		button.setText(sb);
	}
	
}

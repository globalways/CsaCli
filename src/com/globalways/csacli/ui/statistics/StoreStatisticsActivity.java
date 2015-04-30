package com.globalways.csacli.ui.statistics;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.globalways.csacli.R;
import com.globalways.csacli.ui.BaseFragmentActivity;

public class StoreStatisticsActivity extends BaseFragmentActivity implements
		OnClickListener {

	
	private final int BAR = 1;
	private final int PIE = 2;
	
	
	private TextView textLeft,textCenter,textRight;
	private WebView mWebView;
	private Button btnTurnover, btnSales, btnHuantuRevenue;
	
	private StatisticsFragment statisticFragment;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.store_statistics_activity);
		
		initView();
	}
	
	public enum StatType{
		
		/**
		 * 营业额
		 */
		TURNOVER(1),
		/**
		 * 销售量
		 */
		SALES(2),
		/**
		 * 环途收入
		 */
		HUANTUREVENUE(3);
		private int type;
		private StatType(int type)
		{
			this.type = type;
		}
		@Override
		public String toString() {
			return String.valueOf(this.type);
		}
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			this.finish();
			break;
		case R.id.btnTurnover:
			//resetWebView(BAR, "['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]");
			break;
		default:
			break;
		}

	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	private void initView()
	{
		
		// title bar
		textLeft = (TextView) findViewById(R.id.textleft);
		textLeft.setText("返回");
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);

		textCenter = (TextView) findViewById(R.id.textCenter);
		textCenter.setText("商铺统计");
		textCenter.setVisibility(View.VISIBLE);

		/*textRight = (TextView) findViewById(R.id.textRight);
		textRight.setText("添加商铺");
		textRight.setVisibility(View.VISIBLE);
		textRight.setOnClickListener(this);*/
		
		btnTurnover = (Button) findViewById(R.id.btnTurnover);
		btnTurnover.setOnClickListener(this);
		btnSales = (Button) findViewById(R.id.btnSales);
		btnSales.setOnClickListener(this);
		btnHuantuRevenue = (Button) findViewById(R.id.btnHuantuRevenue);
		btnHuantuRevenue.setOnClickListener(this);
		
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		statisticFragment = new StatisticsFragment();
		ft.add(R.id.viewContainer, statisticFragment).show(statisticFragment);
		ft.commit();
		
		
	}
	
	private void resetWebView(final int type, final String data)
	{
		String url = "";
		Object functions = null;
		switch(type)
		{
			case BAR : 
				url = "file:///android_asset/www/store_statistics_bar.html";
				functions = new Object()
				{
					@JavascriptInterface
					public void clickOnAndroid() { 
						Handler mHandler = new Handler();
		                mHandler.post(new Runnable() { 

		                    public void run() { 

		                        Toast.makeText(StoreStatisticsActivity.this, "测试调用java", Toast.LENGTH_LONG).show();

		                    } 

		                }); 

		            } 
				};
				break;
			default:break;
		}
				
		mWebView.addJavascriptInterface(functions,"Fun");
		
		mWebView.setWebViewClient(new WebViewClient(){
					
					@Override
					public void onPageStarted(WebView view, String url, Bitmap favicon) {
						super.onPageStarted(view, url, favicon);
						//Toast.makeText(HongIdActivity.this, "start load", Toast.LENGTH_SHORT).show();
					}
		
					@Override
					public void onPageFinished(WebView view, String url) {
						// TODO Auto-generated method stub
						super.onPageFinished(view, url);
						switch (type) {
						case PIE:
							//pie
							mWebView.loadUrl("javascript:refreshView("+7+");");
							break;
						case BAR:
							//bar
							mWebView.loadUrl("javascript:refershView("+ data +");");
							break;
						default:
							break;
						}
					}
					
				});
		mWebView.loadUrl(url);
		
	}
	
}

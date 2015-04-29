package com.globalways.csacli.ui.store;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.store_statistics_activity);
		
		initView();
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			this.finish();
			break;
		case R.id.btnTurnover:
			resetWebView(BAR, "['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]");
			break;
		default:
			break;
		}

	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		mWebView.clearCache(true);
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
		
		//webview
		initWebView("file:///android_asset/www/store_statistics_bar.html",2);
		
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
	
	
	private void initWebView(String url,final int type)
	{
		mWebView = (WebView)findViewById(R.id.wv_statistics);
		mWebView.getSettings().setJavaScriptEnabled(true);
		//设置可以访问文件
		mWebView.getSettings().setAllowFileAccess(true);
		//设置可使用cookie
		CookieManager.getInstance().setAcceptCookie(true);
		//去a input标签边框
		mWebView.getSettings().setNeedInitialFocus(false);
		//加载js访问控件
		//Handler mHandler = new Handler();
		
		
		
//		mWebView.addJavascriptInterface(new Object(){
//			
//			@JavascriptInterface
//			public void clickOnAndroid() { 
//				Handler mHandler = new Handler();
//                mHandler.post(new Runnable() { 
//
//                    public void run() { 
//
//                        Toast.makeText(StoreStatisticsActivity.this, "测试调用java", Toast.LENGTH_LONG).show();
//
//                    } 
//
//                }); 
//
//            } 
//			
//		}, "Fun");
		
		
		
		//设置捕捉js事件
		//mWebView.setWebChromeClient(new WebChromeClientSelf(this));
		
		mWebView.setWebChromeClient(new WebChromeClient());
		//设置背景为透明
		//mWebView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		//加载wap页面
//		mWebView.loadUrl(url);
		
		
//		mWebView.setWebViewClient(new WebViewClient(){
//			
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//				//Toast.makeText(HongIdActivity.this, "start load", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// TODO Auto-generated method stub
//				super.onPageFinished(view, url);
//				switch (type) {
//				case 1:
//					//pie
//					mWebView.loadUrl("javascript:refreshView("+7+");");
//					break;
//				case 2:
//					//bar
//					mWebView.loadUrl("javascript:refershView(['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]);");
//					break;
//				default:
//					break;
//				}
//			}
//			
//		});
		
		resetWebView(BAR, "['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]");
	}

}

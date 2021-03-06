package com.globalways.csacli.ui.hongid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

/**
 * HongId使用情况界面，主要是图表以及管理入口
 * 
 * @author James
 *
 */
public class HongIdActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView textLeft, textCenter;
	private Button btnExpand, btnDetail ,btnToBar,btnToPie;
	private View layoutContainer;
    private WebView mWebView;
	
	private HongIdExpandFragment hongIdExpandFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hongid_activity);
		initView();
		initData();
	}

	/** 加载数据 */
	private void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textleft:
			HongIdActivity.this.finish();
			break;
		case R.id.btnExpand:
			if (hongIdExpandFragment == null) {
				hongIdExpandFragment = new HongIdExpandFragment();
				getSupportFragmentManager().beginTransaction().add(R.id.layoutContainer, hongIdExpandFragment)
						.show(hongIdExpandFragment).commit();
			}
			layoutContainer.setVisibility(View.VISIBLE);
			break;
		case R.id.btnDetail:
			startActivity(new Intent(this, HongIdManagerActivity.class));
			break;
		case R.id.btnToBar:
			setupWebView("file:///android_asset/www/hongidbar.html",2);
			btnToBar.setVisibility(View.GONE);
			btnToPie.setVisibility(View.VISIBLE);
			break;
		case R.id.btnToPie:
			setupWebView("file:///android_asset/www/survey_result.html",1);
			btnToBar.setVisibility(View.VISIBLE);
			btnToPie.setVisibility(View.GONE);
			break;
		}
	}

	/** 取消扩展HongId */
	public void hideHongIdExpand() {
		layoutContainer.setVisibility(View.GONE);
	}

	/** 扩展HongId成功，刷新UI */
	public void expandeFinish() {
		layoutContainer.setVisibility(View.GONE);
		initData();
	}

	/** 初始化UI、设置监听 */
	private void initView() {
		
		setupWebView("file:///android_asset/www/survey_result.html",1);
		
		
		textLeft = (TextView) findViewById(R.id.textleft);
		textLeft.setText("返回");
		textLeft.setVisibility(View.VISIBLE);
		textLeft.setOnClickListener(this);

		textCenter = (TextView) findViewById(R.id.textCenter);
		textCenter.setText("Hongid使用情况");
		textCenter.setVisibility(View.VISIBLE);

		btnExpand = (Button) findViewById(R.id.btnExpand);
		btnExpand.setOnClickListener(this);
		btnDetail = (Button) findViewById(R.id.btnDetail);
		btnDetail.setOnClickListener(this);
		
		btnToBar = (Button) findViewById(R.id.btnToBar);
		btnToBar.setOnClickListener(this);
		btnToPie = (Button) findViewById(R.id.btnToPie);
		btnToPie.setOnClickListener(this);
		

		layoutContainer = findViewById(R.id.layoutContainer);
		layoutContainer.setVisibility(View.GONE);

	}
	
	public void getCheckDetail(final int flowIndex)
	{
		Toast.makeText(this, "您选择的是饼图块坐标"+flowIndex,Toast.LENGTH_SHORT).show();
	}
	private void setupWebView(String url,final int type)
	{
		mWebView  = (WebView)findViewById(R.id.webView_hongidrate);
		mWebView.getSettings().setJavaScriptEnabled(true);
		//设置可以访问文件
		mWebView.getSettings().setAllowFileAccess(true);
		//设置可使用cookie
		CookieManager.getInstance().setAcceptCookie(true);
		//去a input标签边框
		mWebView.getSettings().setNeedInitialFocus(false);
		//加载js访问控件
		//Handler mHandler = new Handler();
		mWebView.addJavascriptInterface(new Object(){
			
			@JavascriptInterface
			public void clickOnAndroid() { 
				//Handler mHandler = new Handler();
               // mHandler.post(new Runnable() { 

                  //  public void run() { 

                        Toast.makeText(HongIdActivity.this, "测试调用java", Toast.LENGTH_LONG).show();

                   // } 

                ///}); 

            } 
			
		}, "SurveyUtil");
		//设置捕捉js事件
		//mWebView.setWebChromeClient(new WebChromeClientSelf(this));
		
		mWebView.setWebChromeClient(new WebChromeClient());
		//设置背景为透明
		//mWebView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		//加载wap页面
		mWebView.loadUrl(url);
		
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
				case 1:
					//pie
					mWebView.loadUrl("javascript:refreshView("+7+");");
					break;
				case 2:
					//bar
					mWebView.loadUrl("javascript:refershView(['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]);");
					break;
				default:
					break;
				}
			}
			
		});
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		//mWebView.loadUrl("about:blank");
		
	} 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mWebView.clearCache(true);
		//mWebView.loadUrl("about:blank");
	}

}

package com.globalways.csacli.ui.hongid;

import android.annotation.SuppressLint;
import android.content.Intent;
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

/**
 * HongId使用情况界面，主要是图表以及管理入口
 * 
 * @author James
 *
 */
public class HongIdBarActivity extends BaseFragmentActivity implements OnClickListener {

	private TextView textLeft, textCenter;
	private Button btnExpand, btnDetail;
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
			HongIdBarActivity.this.finish();
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

                        Toast.makeText(HongIdBarActivity.this, "测试调用java", Toast.LENGTH_LONG).show();

                   // } 

                ///}); 

            } 
			
		}, "SurveyUtil");
		//设置捕捉js事件
		//mWebView.setWebChromeClient(new WebChromeClientSelf(this));
		
		mWebView.setWebChromeClient(new WebChromeClient());
		//设置背景为透明
		mWebView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		//加载wap页面
		mWebView.loadUrl("file:///android_asset/www/hongidbar.html");
		
		mWebView.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				mWebView.loadUrl("javascript:refershView(['一月','二月','三月','四月'],[200,800,400,500],[100,900,20,300]);");
			}
			
		});
		
		
		
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

		layoutContainer = findViewById(R.id.layoutContainer);
		layoutContainer.setVisibility(View.GONE);

	}
	
	public void getCheckDetail(final int flowIndex)
	{
		Toast.makeText(this, "您选择的是饼图块坐标"+flowIndex,Toast.LENGTH_SHORT).show();
	} 

}

package com.globalways.csacli.ui.store;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.IndustryEntity;
import com.globalways.csacli.entity.StoreEntity;
import com.globalways.csacli.http.manager.ManagerCallBack;
import com.globalways.csacli.http.manager.StoreManager;
import com.globalways.csacli.http.manager.StoreManager.StoreStatus;
import com.globalways.csacli.http.manager.StoreManager.StoreType;
import com.globalways.csacli.tools.MD5;
import com.globalways.csacli.ui.BaseFragment;

public class StoreDetailFragment extends BaseFragment implements OnClickListener {

	private View storeDetailView;
	private TextView textStoreId, textStoreName;
	private Button btnUpdate,btnStatistics;
	
	private TextView tvPID;
	private EditText  editStoreName, editStoreSubName, editAddress, editProductHotLimit, editDesc,
	editStoreEmail, editStorePhone,editStorePassword;
	private Spinner spinnerStoreType, spinnerIndustry;
	private StoreIndustryAdapter industryAdapter;
	private CheckBox checkBoxLock;
	
	private StoreType storeType = StoreType.NORMAL;
	private String industry_name = null;
	private List<IndustryEntity> listIndustry;
	private StoreStatus storeStatus = StoreStatus.UNLOCK;
	
	private StoreEntity currentEntity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == storeDetailView) {
			storeDetailView = inflater.inflate(R.layout.store_detail_fragment, container, false);
		}
		return storeDetailView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		loadIndustryList();
	}

	public void setEntity(final StoreEntity entity) {
		//textStoreId.setText(entity.getId() + "");
		//textStoreName.setText(entity.getStore_name());
		this.currentEntity = entity;
		tvPID.setText(String.valueOf(entity.getPid()));
		editProductHotLimit.setText(String.valueOf(entity.getProduct_hot_limit()));
		editStoreName.setText(entity.getStore_name());
		editStoreSubName.setText(entity.getStore_sub());
		editAddress.setText(entity.getStore_address());
		editDesc.setText(entity.getStore_desc());
		editStoreEmail.setText(entity.getStore_email());
		editStorePhone.setText(entity.getStore_phone());
		if(entity.getStatus() == StoreStatus.UNLOCK.getType())
		{
			checkBoxLock.setChecked(true);
		}else{
			checkBoxLock.setChecked(false);
		}
		
		spinnerStoreType.setSelection(entity.getStore_type()-1);
		
		//init industry spinner
		if(listIndustry != null)
		{
			for(int i=0;i<listIndustry.size();i++)
			{
				if(listIndustry.get(i).compareName(currentEntity.getIndustry_name()))
				{
					
					spinnerIndustry.setSelection(i , true);
					break;
				}
				
			}
		}
		
	}

	private void initView() {
		//textStoreId = (TextView) storeDetailView.findViewById(R.id.textStoreId);
		//textStoreName = (TextView) storeDetailView.findViewById(R.id.textStoreName);
		
		btnUpdate = (Button)storeDetailView.findViewById(R.id.btnUpdate);
		btnUpdate.setOnClickListener(this);
		btnStatistics = (Button)storeDetailView.findViewById(R.id.btnStatistics);
		btnStatistics.setOnClickListener(this);
		
		tvPID = (TextView) storeDetailView.findViewById(R.id.tvPID);
		editProductHotLimit = (EditText) storeDetailView.findViewById(R.id.editProductHotLimit);
		editStoreName = (EditText) storeDetailView.findViewById(R.id.editStoreName);
		editStoreSubName = (EditText) storeDetailView.findViewById(R.id.editStoreSubName);
		editAddress = (EditText) storeDetailView.findViewById(R.id.editAddress);
		editDesc = (EditText) storeDetailView.findViewById(R.id.editDesc);
		editStoreEmail = (EditText) storeDetailView.findViewById(R.id.editStoreEmail);
		editStorePhone = (EditText) storeDetailView.findViewById(R.id.editStorePhone);
		
//		editStorePassword = (EditText) storeDetailView.findViewById(R.id.editStorePassword);

		spinnerStoreType = (Spinner) storeDetailView.findViewById(R.id.spinnerStoreType);
		
		spinnerStoreType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					storeType = StoreType.NORMAL;
					break;
				case 1:
					storeType = StoreType.CHAIN;
					break;
				case 2:
					storeType = StoreType.FREE_DUTY;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		spinnerIndustry = (Spinner) storeDetailView.findViewById(R.id.spinnerIndustry);
		industryAdapter = new StoreIndustryAdapter(getActivity());
		spinnerIndustry.setAdapter(industryAdapter);
		spinnerIndustry.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				industry_name = listIndustry.get(position).getIndustry_name();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		checkBoxLock = (CheckBox) storeDetailView.findViewById(R.id.checkBoxLock);
		checkBoxLock.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				storeStatus = isChecked ? StoreStatus.UNLOCK : StoreStatus.LOCK;
			}
		});
		
	}
	
	/** 加载行业分类数据 */
	private void loadIndustryList() {
		StoreManager.getInstance().getIndustryList(new ManagerCallBack<List<IndustryEntity>>() {
			@Override
			public void onSuccess(List<IndustryEntity> returnContent) {
				super.onSuccess(returnContent);
				listIndustry = returnContent;
				industryAdapter.setData(returnContent);
				
				if(currentEntity != null)
				{
					
					for(int i=0;i<listIndustry.size();i++)
					{
						if(listIndustry.get(i).compareName(currentEntity.getIndustry_name()))
						{
							
							spinnerIndustry.setSelection(i , true);
							break;
						}
						
					}
				}
				
			}

			@Override
			public void onFailure(int code, String msg) {
				super.onFailure(code, msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * get form data and update store
	 */
	private void updateStore()
	{
//		int pid = Integer.valueOf(editPID.getText().toString().trim());
		String store_name = editStoreName.getText().toString().trim();
		if (store_name.isEmpty()) {
			Toast.makeText(getActivity(), "请输入店铺名称", Toast.LENGTH_SHORT).show();
			return;
		}
		String store_sub = editStoreSubName.getText().toString().trim();
		if (store_sub.isEmpty()) {
			Toast.makeText(getActivity(), "请输入店铺副标题", Toast.LENGTH_SHORT).show();
			return;
		}
		String store_desc = editDesc.getText().toString().trim();
		String store_address = editAddress.getText().toString().trim();
		if (store_address.isEmpty()) {
			Toast.makeText(getActivity(), "请输入店铺地址", Toast.LENGTH_SHORT).show();
			return;
		}
		String store_email = editStoreEmail.getText().toString().trim();
		String store_phone = editStorePhone.getText().toString().trim();
		
		//disable password
//		String store_password = editStorePassword.getText().toString().trim();
//		if(store_password.isEmpty())
//		{
//			Toast.makeText(getActivity(), "请输入管理密码", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		store_password = new MD5().getMD5(store_password);
		
		if (store_phone.isEmpty()) {
			Toast.makeText(getActivity(), "请输入店铺联系方式", Toast.LENGTH_SHORT).show();
			return;
		}
		int product_hot_limit = Integer.valueOf(editProductHotLimit.getText().toString().trim());
		
		
		
		
		StoreManager.getInstance().updateStore(currentEntity.getStore_id(), store_name, store_sub, store_desc, industry_name,
				store_address, null, store_phone, store_email, product_hot_limit, 0, 0,
				2, 0, 2, null, storeStatus , storeType, 
				
				new ManagerCallBack<String>() {
					@Override
					public void onSuccess(String returnContent) {
//						progressDialog.cancleDialog();
						super.onSuccess(returnContent);
						AlertDialog.Builder builder = new Builder(getActivity());
						builder.setMessage("更新成功！");
						builder.setTitle("提示！");
						builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								((StoreActivity) getActivity()).hideAddStoreView();
//								resetView();
							}
						});
						builder.create().show();
					}

					@Override
					public void onFailure(int code, String msg) {
						super.onFailure(code, msg);
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//						progressDialog.cancleDialog();
					}
				});
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUpdate:
			
			updateStore();
			
			break;
		case R.id.btnStatistics:
			startActivity(new Intent(getActivity(), StoreStatisticsActivity.class));
			break;
		default:
			break;
		}
		
	}
	
	
	

}

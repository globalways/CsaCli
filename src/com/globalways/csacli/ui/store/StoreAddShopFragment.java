package com.globalways.csacli.ui.store;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.globalways.csacli.R;
import com.globalways.csacli.entity.IndustryEntity;
import com.globalways.csacli.http.manager.ManagerCallBack;
import com.globalways.csacli.http.manager.StoreManager;
import com.globalways.csacli.http.manager.StoreManager.StoreStatus;
import com.globalways.csacli.http.manager.StoreManager.StoreType;
import com.globalways.csacli.ui.BaseFragment;
import com.globalways.csacli.view.SimpleProgressDialog;

public class StoreAddShopFragment extends BaseFragment implements OnClickListener {

	private View layoutView;
	private EditText editPID, editStoreName, editStoreSubName, editAddress, editProductHotLimit, editDesc,
			editStoreEmail, editStorePhone;
	private Spinner spinnerStoreType, spinnerIndustry;
	private StoreIndustryAdapter industryAdapter;
	private CheckBox checkBoxLock;
	private Button btnDefine, btnCancel;

	private StoreStatus storeStatus = StoreStatus.UNLOCK;
	private StoreType storeType = StoreType.NORMAL;
	private String industry_name = null;
	private List<IndustryEntity> listIndustry;
	private SimpleProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == layoutView) {
			layoutView = inflater.inflate(R.layout.store_addshop_fragment, container, false);
		}
		return layoutView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		loadIndustryList();
	}

	/** 加载行业分类数据 */
	private void loadIndustryList() {
		progressDialog.showDialog();
		StoreManager.getInstance().getIndustryList(new ManagerCallBack<List<IndustryEntity>>() {
			@Override
			public void onSuccess(List<IndustryEntity> returnContent) {
				super.onSuccess(returnContent);
				listIndustry = returnContent;
				industryAdapter.setData(returnContent);
				progressDialog.cancleDialog();
			}

			@Override
			public void onFailure(int code, String msg) {
				super.onFailure(code, msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				progressDialog.cancleDialog();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDefine:
			toAddShop();
			break;
		case R.id.btnCancel:
			((StoreActivity) getActivity()).hideAddStoreView();
			break;
		}
	}

	/** 新建店铺 */
	private void toAddShop() {
		int pid = Integer.valueOf(editPID.getText().toString().trim());
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
		if (store_phone.isEmpty()) {
			Toast.makeText(getActivity(), "请输入店铺联系方式", Toast.LENGTH_SHORT).show();
			return;
		}
		int product_hot_limit = Integer.valueOf(editProductHotLimit.getText().toString().trim());

		progressDialog.showDialog();
		StoreManager.getInstance().addStore(pid, store_name, store_sub, store_desc, industry_name, store_address, null,
				store_phone, store_email, product_hot_limit, 0, 0, 2, 0, 2, null, storeStatus, storeType,
				new ManagerCallBack<String>() {
					@Override
					public void onSuccess(String returnContent) {
						progressDialog.cancleDialog();
						super.onSuccess(returnContent);
						AlertDialog.Builder builder = new Builder(getActivity());
						builder.setMessage("新建成功！");
						builder.setTitle("提示！");
						builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								((StoreActivity) getActivity()).hideAddStoreView();
								resetView();
							}
						});
						builder.create().show();
					}

					@Override
					public void onFailure(int code, String msg) {
						super.onFailure(code, msg);
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
						progressDialog.cancleDialog();
					}
				});
	}

	/** 重置View */
	private void resetView() {
	}

	/** 初始化UI、设置监听 */
	private void initView() {
		editPID = (EditText) layoutView.findViewById(R.id.editPID);
		editPID.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = editPID.getText().toString().trim();
				if (text != null && !text.isEmpty()) {
					if (text.startsWith("0") && text.length() > 1) {
						text = text.substring(1);
						editPID.setText(text);
					}
				} else {
					editPID.setText("0");
				}
				editPID.setSelection(editPID.getText().toString().trim().length());
			}
		});
		editProductHotLimit = (EditText) layoutView.findViewById(R.id.editProductHotLimit);
		editProductHotLimit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = editProductHotLimit.getText().toString().trim();
				if (text != null && !text.isEmpty()) {
					if (text.startsWith("0") && text.length() > 1) {
						text = text.substring(1);
						editProductHotLimit.setText(text);
					}
				} else {
					editProductHotLimit.setText("0");
				}
				editProductHotLimit.setSelection(editProductHotLimit.getText().toString().trim().length());
			}
		});

		editStoreName = (EditText) layoutView.findViewById(R.id.editStoreName);
		editStoreSubName = (EditText) layoutView.findViewById(R.id.editStoreSubName);
		editAddress = (EditText) layoutView.findViewById(R.id.editAddress);
		editDesc = (EditText) layoutView.findViewById(R.id.editDesc);
		editStoreEmail = (EditText) layoutView.findViewById(R.id.editStoreEmail);
		editStorePhone = (EditText) layoutView.findViewById(R.id.editStorePhone);

		spinnerStoreType = (Spinner) layoutView.findViewById(R.id.spinnerStoreType);
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
		spinnerIndustry = (Spinner) layoutView.findViewById(R.id.spinnerIndustry);
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
		checkBoxLock = (CheckBox) layoutView.findViewById(R.id.checkBoxLock);
		checkBoxLock.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				storeStatus = isChecked ? StoreStatus.UNLOCK : StoreStatus.LOCK;
			}
		});

		btnDefine = (Button) layoutView.findViewById(R.id.btnDefine);
		btnDefine.setOnClickListener(this);
		btnCancel = (Button) layoutView.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

		progressDialog = new SimpleProgressDialog(getActivity());
	}

	public void cancelDialog() {
		progressDialog.cancleDialog();
	}

	public boolean isProgressing() {
		if (progressDialog.isShowing()) {
			return true;
		}
		return false;
	}

}
package com.globalways.csacli.ui.hongid;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.globalways.csacli.R;
import com.globalways.csacli.http.manager.HongIdManager;
import com.globalways.csacli.http.manager.HongIdManager.MemberType;
import com.globalways.csacli.http.manager.ManagerCallBack;
import com.globalways.csacli.ui.BaseFragment;

/**
 * 扩充HongId的弹出层
 * 
 * @author James
 *
 */
public class HongIdExpandFragment extends BaseFragment implements OnClickListener {

	private View layoutView;
	private EditText editMinNum, editMaxNum, editNum;
	private Spinner spinnerType;
	private Button btnDefine, btnCancel;

	private MemberType member_type = MemberType.USER;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == layoutView) {
			layoutView = inflater.inflate(R.layout.hongid_expand_fragment, container, false);
		}
		return layoutView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDefine:
			toExpandHongId();
			break;
		case R.id.btnCancel:
			((HongIdActivity) getActivity()).hideHongIdExpand();
			break;
		}
	}

	/** 扩展HongId */
	private void toExpandHongId() {
		int min_id = Integer.valueOf(editMinNum.getText().toString().trim());
		int max_id = Integer.valueOf(editMaxNum.getText().toString().trim());
		int count = Integer.valueOf(editNum.getText().toString().trim());

		HongIdManager.getInstance().expandHongId(min_id, max_id, count, member_type, new ManagerCallBack<String>() {
			@Override
			public void onSuccess(String returnContent) {
				super.onSuccess(returnContent);
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage("扩展成功！");
				builder.setTitle("提示！");
				builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((HongIdActivity) getActivity()).expandeFinish();
					}
				});
				builder.create().show();
			}

			@Override
			public void onFailure(int code, String msg) {
				super.onFailure(code, msg);
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/** 在最大最小ID输入框焦点变化后执行该方法 */
	private void afterMinMaxEditFocusChange() {
		if (member_type == MemberType.USER) {
			// 会员帐号必须是8位的
			if (editMinNum.getText().toString().trim().length() != 8) {
				editMinNum.setText("10000000");
			}
			if (editMaxNum.getText().toString().trim().length() != 8) {
				editMaxNum.setText("99999999");
			}
		} else {
			// 机构会员必须是5位的
			if (editMinNum.getText().toString().trim().length() != 8) {
				editMinNum.setText("10000");
			}
			if (editMaxNum.getText().toString().trim().length() != 8) {
				editMaxNum.setText("99999");
			}
		}
		int minNum = Integer.valueOf(editMinNum.getText().toString().trim());
		int maxNum = Integer.valueOf(editMaxNum.getText().toString().trim());
		if (minNum > maxNum) {
			editMaxNum.setText(minNum + "");
		}
	}

	/** 初始化UI、设置监听 */
	private void initView() {
		editMinNum = (EditText) layoutView.findViewById(R.id.editMinNum);
		editMinNum.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = editMinNum.getText().toString().trim();
				if (text != null && !text.isEmpty()) {
					if (text.startsWith("0") && text.length() > 1) {
						text = text.substring(1);
						editMinNum.setText(text);
					}
				} else {
					editMinNum.setText("0");
				}
				editMinNum.setSelection(editMinNum.getText().toString().trim().length());
			}
		});
		editMinNum.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				afterMinMaxEditFocusChange();
			}
		});
		editMaxNum = (EditText) layoutView.findViewById(R.id.editMaxNum);
		editMaxNum.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = editMaxNum.getText().toString().trim();
				if (text != null && !text.isEmpty()) {
					if (text.startsWith("0") && text.length() > 1) {
						text = text.substring(1);
						editMaxNum.setText(text);
					}
				} else {
					editMaxNum.setText("0");
				}
				editMaxNum.setSelection(editMaxNum.getText().toString().trim().length());
			}
		});
		editMaxNum.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				afterMinMaxEditFocusChange();
			}
		});
		editNum = (EditText) layoutView.findViewById(R.id.editNum);
		editNum.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = editNum.getText().toString().trim();
				if (text != null && !text.isEmpty()) {
					if (text.startsWith("0") && text.length() > 1) {
						text = text.substring(1);
						editNum.setText(text);
					}
				} else {
					editNum.setText("0");
				}
				editNum.setSelection(editNum.getText().toString().trim().length());
			}
		});
		spinnerType = (Spinner) layoutView.findViewById(R.id.spinnerType);
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					member_type = MemberType.USER;
					editMinNum.setText("10000000");
					editMaxNum.setText("99999999");
					break;
				case 1:
					member_type = MemberType.COMPANY;
					editMinNum.setText("10000");
					editMaxNum.setText("99999");
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		btnDefine = (Button) layoutView.findViewById(R.id.btnDefine);
		btnDefine.setOnClickListener(this);
		btnCancel = (Button) layoutView.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
	}
}

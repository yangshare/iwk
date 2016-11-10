package com.iwk.yang.volley;

import com.android.volley.Response;

/**
 * 返回成功监听(自定义处理逻辑)
 * 
 * @author 石岩
 * 
 */
public abstract class MyReponseListener implements Response.Listener<BaseVO> {
	@Override
	public void onResponse(BaseVO arg0) {
		onMyResponse(arg0);
	}

	public boolean onMyResponse(BaseVO t) {
		//		DialogMaker.closeProgressDialog();
		// 自定义处理逻辑

		//		if((!(arg0.status).equals("40007"))&&(!(arg0.status).equals("40011"))){
		//			ToastMaker.showToast(AppData.getContext(), arg0.msg, 0);
		//			return false;
		//		}
		return true;
	}
}

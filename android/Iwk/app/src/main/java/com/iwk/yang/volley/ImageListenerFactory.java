package com.iwk.yang.volley;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 * 图片加载状态监听
 * @author Yan
 *
 */
public class ImageListenerFactory {
	private static final String TAG="ImageListenerFactory";

	public static ImageListener getImageListener(final ImageView view,
			final int defaultImageResId, final int errorImageResId){
		return new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(errorImageResId!=0){
					view.setImageResource(errorImageResId);
				}
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if(response.getBitmap()!=null){
					if(view.getTag().toString().equals(response.getRequestUrl())){
						view.setImageBitmap(response.getBitmap());
					}
				}
				else if(defaultImageResId!=0){
					view.setImageResource(defaultImageResId);
				}
			}
		};
	}
}

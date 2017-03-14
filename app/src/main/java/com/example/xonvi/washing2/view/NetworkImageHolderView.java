package com.example.xonvi.washing2.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by xonvi on 2016/12/14.
 */
public class NetworkImageHolderView implements Holder<String> {
    private SimpleDraweeView draweeView;
    @Override
    public View createView(Context context) {
        draweeView = new SimpleDraweeView(context);
        draweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return draweeView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        //这里使用fresco
      draweeView.setImageURI(data);

    }
}
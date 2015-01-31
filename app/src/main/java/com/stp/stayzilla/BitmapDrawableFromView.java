package com.stp.stayzilla;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * Created by santhosh on 12/22/14.
 */
public class BitmapDrawableFromView {

    private static BitmapDrawableFromView instance = null;
    private Context mContext = null;
    private WindowManager windowManager = null;

    public BitmapDrawableFromView(Context context) {
        this.mContext = context;
        this.windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public static BitmapDrawableFromView getInstance(Context context) {
        if(instance == null) instance = new BitmapDrawableFromView(context);
        return instance;
    }
    // Convert a view to bitmap
    public Bitmap createDrawableFromView(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }
}

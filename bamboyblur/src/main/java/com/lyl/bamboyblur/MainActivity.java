package com.lyl.bamboyblur;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyl.bamboyblur.util.UtilAnim;
import com.lyl.bamboyblur.util.UtilBitmap;
import com.lyl.bamboyblur.util.UtilScreenCapture;

public class MainActivity extends Activity implements View.OnClickListener {

    /**
     * 按钮【模糊图片】
     */
    private TextView tv_blur_img;
    /**
     * 图片
     */
    private ImageView iv_head_portrait;
    /**
     * 按钮【显示弹窗】
     */
    private TextView tv_blur_popup_window;

    /**
     * 弹窗背景
     */
    private ImageView iv_popup_window_back;
    /**
     * 弹窗容器
     */
    private RelativeLayout rl_popup_window;
    /**
     * 按钮【关闭弹窗】
     */
    private TextView tv_close_popup_window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        tv_blur_img = (TextView) findViewById(R.id.tv_blur_img);
        iv_head_portrait = (ImageView) findViewById(R.id.iv_head_portrait);
        tv_blur_popup_window = (TextView) findViewById(R.id.tv_blur_popup_window);

        iv_popup_window_back = (ImageView) findViewById(R.id.iv_popup_window_back);
        tv_close_popup_window = (TextView) findViewById(R.id.tv_close_popup_window);
        rl_popup_window = (RelativeLayout) findViewById(R.id.rl_popup_window);

        tv_blur_img.setOnClickListener(this);
        tv_blur_popup_window.setOnClickListener(this);
        tv_close_popup_window.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_blur_img:          // 点击模糊图片
                clickBlurImg();
                break;
            case R.id.tv_blur_popup_window: // 点击显示弹窗
                clickPopupWindow();
                break;
            case R.id.tv_close_popup_window: // 关闭弹窗
                clickClosePopupWindow();
                break;
        }
    }

    /**
     * 模糊图片
     */
    private void clickBlurImg() {
        // 将图片进行高斯模糊，
        // 最后一个参数是模糊等级，值为 0~25
        UtilBitmap.blurImageView(this, iv_head_portrait, 5);
    }

    /**
     * 显示弹窗
     */
    private void clickPopupWindow() {
        // 获取截图的Bitmap
        Bitmap bitmap = UtilScreenCapture.getDrawing(this);

        if (bitmap != null) {
            // 将截屏Bitma放入ImageView
            iv_popup_window_back.setImageBitmap(bitmap);
            // 将ImageView进行高斯模糊【25是最高模糊等级】【0x77000000是蒙上一层颜色，此参数可不填】
            UtilBitmap.blurImageView(this, iv_popup_window_back, 25, 0x77000000);
        } else {
            // 获取的Bitmap为null时，用半透明代替
            iv_popup_window_back.setBackgroundColor(0x77000000);
        }

        // 打开弹窗
        UtilAnim.showToUp(rl_popup_window, iv_popup_window_back);

    }

    /**
     * 关闭弹窗
     */
    private void clickClosePopupWindow() {
        UtilAnim.hideToDown(rl_popup_window, iv_popup_window_back);
    }

}

package com.chenliang.baselibrary.exception;
/**
 * Created by chenliangj2ee on 2016/6/19.
 */

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.chenliang.baselibrary.R;
import com.chenliang.baselibrary.utils.MyFunctionKt;

/**
 * 使用实例：
 * AsExceptionDialog dialog = new AsExceptionDialog(this);
 * dialog.setTitle("提示").setMessage("确定要退出登录吗？").setCanceledOnTouchOutside(false);
 * dialog.setYesListener("确定", new DialogInterface.OnClickListener() {
 * public void onClick(DialogInterface dialog, int which) {
 * dialog.dismiss();
 * //退出登录
 * }
 * });
 * dialog.show();
 */
public class AsExceptionDialog {
    View layout;
    private Context context;
    private TextView title;
    private TextView message;
    private Button yes;
    private Button no;
    private Dialog dialog;
    private DialogInterface.OnClickListener positiveButtonClickListener;
    private DialogInterface.OnClickListener negativeButtonClickListener;

    public AsExceptionDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog = new Dialog(context, R.style.ExceptionDialog);
        layout = inflater.inflate(R.layout.exception_dialog_as, null);
        dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        title = ((TextView) layout.findViewById(R.id.title));
        message = ((TextView) layout.findViewById(R.id.message));
        yes = ((Button) layout.findViewById(R.id.yes));
        no = ((Button) layout.findViewById(R.id.no));
        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (positiveButtonClickListener != null) {
                    positiveButtonClickListener.onClick(dialog,
                            DialogInterface.BUTTON_POSITIVE);
                }
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (negativeButtonClickListener != null) {
                    negativeButtonClickListener.onClick(dialog,
                            DialogInterface.BUTTON_NEGATIVE);
                }
                dialog.dismiss();
            }
        });
        dialog.setContentView(layout);

    }
    public void show() {
        dialog.show();

    }
    public AsExceptionDialog setCanceledOnTouchOutside(boolean boo) {
        dialog.setCanceledOnTouchOutside(boo);
        return this;
    }
    public AsExceptionDialog setMessage(String message) {
        this.message.setText(message);
        return this;
    }
    public Bitmap getMessageBitmap(){
        int w=layout.getWidth();
        int h=layout.getHeight();
        Bitmap bmp=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c=new Canvas(bmp);
        c.drawColor(Color.WHITE);
        layout.layout(0, 0, w, h);
        layout.draw(c);
        return bmp;
    }
    public AsExceptionDialog setMessage(int message) {
        this.message.setText(message);
        return this;
    }
    public AsExceptionDialog setTitle(int title) {
        this.title.setText(title);
        return this;
    }
    public AsExceptionDialog dismiss() {
         dialog.dismiss();
        return this;
    }
    public AsExceptionDialog setTitle(String title) {
        this.title.setText(title);
        return this;
    }
    public AsExceptionDialog setYesListener(int text,
                                            DialogInterface.OnClickListener listener) {
        yes.setText(text);
        this.positiveButtonClickListener = listener;
        return this;
    }
    public AsExceptionDialog setYesListener(String text,
                                            DialogInterface.OnClickListener listener) {
        yes.setText(text);
        this.positiveButtonClickListener = listener;
        return this;
    }
    public AsExceptionDialog setNoListener(int text,
                                           DialogInterface.OnClickListener listener) {
        no.setText(text);
        this.negativeButtonClickListener = listener;
        return this;
    }
    public AsExceptionDialog setNoListener(String text,
                                           DialogInterface.OnClickListener listener) {
        no.setText(text);
        this.negativeButtonClickListener = listener;
        return this;
    }
    private void alphaShow() {
        ObjectAnimator an = ObjectAnimator.ofFloat(layout, "alpha", 0, 1);
        an.setDuration(1000);
        an.start();
    }


}
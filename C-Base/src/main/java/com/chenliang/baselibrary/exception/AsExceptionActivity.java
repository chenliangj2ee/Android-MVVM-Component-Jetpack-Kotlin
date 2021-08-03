package com.chenliang.baselibrary.exception;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.chenliang.baselibrary.utils.MyFunctionKt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AsExceptionActivity extends Activity {
    String message;
    AsExceptionDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        message = getIntent().getStringExtra("message");
        dialog = new AsExceptionDialog(AsExceptionActivity.this);
        dialog.setTitle("尼玛，又出错了").setMessage(message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setYesListener("保存图库", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        saveMyBitmap(dialog.getMessageBitmap(), System.currentTimeMillis() + "");
                    }
                },500);
            }
        });
        dialog.setNoListener("重启", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int which) {
                restartApp();
            }
        });
        dialog.show();
        super.onResume();
    }
    public String saveMyBitmap(Bitmap mBitmap, String bitName) {
        MyFunctionKt.log(this,"bitmap:"+mBitmap);
        String path = getSdcardPath() + "/AstException";
        File f = new File(path);
        if (!f.exists() && !f.isDirectory()) {
            f.mkdir();
        }
        path = getSdcardPath() + "/" + bitName + ".jpg";
        f = new File(path);
        if (f.exists())
            return path;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MyFunctionKt.log(this,"bitmap:"+e.getMessage());
        }

        try {

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
        AsExceptionActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        Toast.makeText(AsExceptionActivity.this, "正在保存，请稍后", Toast.LENGTH_SHORT).show();
        Toast.makeText(AsExceptionActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
        restartApp();
        return path;
    }
    public static String getSdcardPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return "";
        }

    }
    public void restartApp()   {

        Intent intent = new Intent(this, AstException.getInstance().getMainActClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP    | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);


    }
}

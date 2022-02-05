package com.usermanagement.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class ProgressDialogManager {
    private ProgressDialog progressDialog;
    private static final int SHOW_PROG_DIALOG = 0, HIDE_PROG_DIALOG = 1, LOAD_QUESTION_SUCCESS = 2;
    private String PROG_DIALOG_MSG = "Loading...";
    private static Handler mHandler;
    private static ProgressDialogManager dialogManager = null;
    private Context context;

    private ProgressDialogManager(Context context) {
        this.context = context;
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case SHOW_PROG_DIALOG:
                    showProgressDialog();
                    break;
                    case HIDE_PROG_DIALOG:
                    hideProgressDialog();
                    break;
                    case LOAD_QUESTION_SUCCESS:
                    break;
                }
                return false;
            }
        });
    }

    public static synchronized ProgressDialogManager getInstance(Context context){
        if (dialogManager == null){
            dialogManager = new ProgressDialogManager(context);
        }
        return dialogManager;
    }

    public void showProgressDialog() {
        progressDialog = null;
        try {
            if (Build.VERSION.SDK_INT >= 11){
                progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
            }else {
                progressDialog = new ProgressDialog(context);
            }
                progressDialog.setMessage(PROG_DIALOG_MSG);
                progressDialog.setCancelable(false);
                progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
        }catch (Exception e){

        }
    }

}

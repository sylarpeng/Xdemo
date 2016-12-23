package com.pzj.vplibrary.utils;

import android.support.v4.app.FragmentActivity;

import com.pzj.vplibrary.dialog.VpProgressDialog;

/**
 * Created by pzj on 2016/12/21.
 */

public class LoadingProgressDialog {
    private static VpProgressDialog dialog;
    public static void  showProgressDialog(FragmentActivity context){
        if(dialog==null){
            dialog=new VpProgressDialog();
        }
        dialog.show(context.getSupportFragmentManager(),"show");
    }

    public static void closeProgressDialog(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}

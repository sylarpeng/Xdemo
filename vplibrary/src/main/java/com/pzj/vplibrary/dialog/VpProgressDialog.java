package com.pzj.vplibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pzj.vplibrary.R;

/**
 * Created by pzj on 2016/12/21.
 */

public class VpProgressDialog extends DialogFragment{
    private static Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.progressdialog,container);
        return v;
    }
}

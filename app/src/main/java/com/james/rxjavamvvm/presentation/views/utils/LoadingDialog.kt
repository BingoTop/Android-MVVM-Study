package com.james.rxjavamvvm.presentation.views.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.james.rxjavamvvm.R
import javax.inject.Inject

class LoadingDialog @Inject constructor(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun show() {
        if(!this.isShowing)super.show()
    }
}


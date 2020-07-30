package com.asuravan.smartsupply.activity.commons;

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class CommonActivity : AppCompatActivity() {
    private val sharedPrefFile = "appsharedpreference"
    var sharedPreferences: SharedPreferences? =null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences= this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }
    private var mProgressBar: ProgressBar? = null
    open fun activateProgress(context:Context, isenable: Boolean) {
        val viewGroup =
            (context as Activity).findViewById<View>(R.id.content)
                .rootView as ViewGroup
        mProgressBar = ProgressBar(context, null, R.attr.progressBarStyle)
        mProgressBar!!.setIndeterminate(true)
        mProgressBar!!.getIndeterminateDrawable()
            .setColorFilter(-0x10000, PorterDuff.Mode.MULTIPLY)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        val rl = RelativeLayout(context)
        rl.gravity = Gravity.CENTER
        rl.addView(mProgressBar)
        viewGroup.addView(rl, params)
        progressHide()
    }

    open fun progressHide() {
        if (mProgressBar != null) mProgressBar!!.visibility = View.INVISIBLE
    }

    open fun progressShow() {
        if (mProgressBar != null) mProgressBar!!.visibility = View.VISIBLE
    }

    @SuppressLint("ResourceAsColor")
    open fun toggleEnaDisable(view: View,state:Boolean) {
       view.isEnabled = state;
    }

    @SuppressLint("ResourceAsColor")
    open fun toggleEnaDisable(view: View) {
        if(view.isEnabled)
        view.isEnabled = false;
        else
            view.isEnabled = true
    }

    open fun makeToast(msg:String,duration:Int){
        Toast.makeText(getApplicationContext(),msg,duration).show();
    }

    open fun makeToast(msg:String){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}

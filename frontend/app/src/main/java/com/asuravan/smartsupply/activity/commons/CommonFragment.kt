package com.asuravan.smartsupply.activity.commons

import android.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.utils.BluebeeUtils
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


open class CommonFragment: Fragment() {
    private val sharedPrefFile = "appsharedpreference"
    var sharedPreferences: SharedPreferences? = null;// this.activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           // sharedPreferences =
            //    this.activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences  = this.activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    open fun addItemToSharedPref(key:String,
                                 value: String
    ){
       val editor = sharedPreferences?.edit();
        if (editor != null) {
            editor.putString(key,value)
            editor.apply();
        }

    }

    open fun removeItemToSharedPref( key:String){
        val editor = sharedPreferences?.edit();
        if (editor != null) {
            editor.remove(key)
            editor.apply();
        }

    }

    open fun getStringToItemListSharedPref( key:String): Any? {
       val cart = sharedPreferences?.getString(key,null);
        if(cart!=null){
            val listType: Type =
                object : TypeToken<ArrayList<ItemInfo?>?>() {}.type
            return BluebeeUtils.GetInstance().convertToObject(cart,listType)
        }

        return ArrayList<ItemInfo>();
    }

    private var mProgressBar: ProgressBar? = null
     fun init(context:Context){
       //  activateProgress(context,true);
    }

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

    open fun makeToast(msg:String,duration:Int){
        Toast.makeText(this.context,msg,duration).show();
    }

    open fun makeToast(msg:String){
        Toast.makeText(this.context,msg, Toast.LENGTH_SHORT).show();
    }

    open fun toggleEnaDisable(view: View) {
        if(view.isEnabled)
            view.isEnabled = false;
        else
            view.isEnabled = true
    }
}
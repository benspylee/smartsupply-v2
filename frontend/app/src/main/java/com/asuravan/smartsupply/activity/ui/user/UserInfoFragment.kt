package com.asuravan.smartsupply.activity.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment


class UserInfoFragment : CommonFragment() {

   lateinit var txtusername: TextView
    lateinit var txtemail: TextView;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        var root= inflater.inflate(R.layout.fragment_user_info, container, false)
        txtusername = root.findViewById(R.id.txtusername);
        txtemail = root.findViewById(R.id.txtemail);
        txtusername.setText(sharedPreferences?.getString("username",""))
        txtemail.setText(sharedPreferences?.getString("emailid",""))


        return root;
    }


}
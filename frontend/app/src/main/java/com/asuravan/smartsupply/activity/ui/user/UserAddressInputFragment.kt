package com.asuravan.smartsupply.activity.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.AppUserAddress
import com.asuravan.smartsupply.pojo.Shop
import com.asuravan.smartsupply.service.AppUserAddressService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserAddressInputFragment : CommonFragment() {

    private var appUserservice: AppUserAddressService = RetroRestClient<AppUserAddressService>().create(AppUserAddressService::class.java);

    lateinit var address_1: EditText;
    lateinit var address_2: EditText;
    lateinit var address_3: EditText;
    lateinit var zipcode: EditText;
    lateinit var mobileno: EditText;
    lateinit var email: EditText;
    lateinit var addAddress:Button;


    var appuseraddress: AppUserAddress? = null;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        context?.let { super.activateProgress(it,false) }
       val root  = inflater.inflate(R.layout.fragment_address_input, container, false)

        address_1 = root.findViewById(R.id.address_1);
        address_2 = root.findViewById(R.id.address_2);
        address_3 = root.findViewById(R.id.address_3);
        zipcode = root.findViewById(R.id.zipcode);

        addAddress =root.findViewById(R.id.addAddress)
        addAddress.setOnClickListener {
            var appuseraddress: AppUserAddress? =null;
            if(appuseraddress!=null){
                appuseraddress = appuseraddress as AppUserAddress;
            }else{
                appuseraddress = AppUserAddress()
            }

            appuseraddress!!.address2 = address_2.text.toString();
            appuseraddress!!.addess1 = address_1.text.toString();
            appuseraddress!!.address3=address_3.text.toString();
            appuseraddress!!.zipcode=  zipcode.text.toString();
            appuseraddress!!.appusercd = sharedPreferences?.getInt("appusercd",0)
            appuseraddress!!.status =1;
            progressShow()
            try {
                if(appuseraddress!=null){
                    updateAppUserAddresssinfo(appuseraddress!!)
                }else{
                    addAppUserAddresssinfo(appuseraddress!!)
                }

            } catch (e: Exception) {
                Log.e("error","error")
            }
        }

        if(arguments!=null){
            appuseraddress=    arguments!!.get("updateitem") as AppUserAddress;
            if(appuseraddress!=null){
                address_1.setText( appuseraddress!!.addess1.toString().trim())
                address_2.setText( appuseraddress!!.address2.toString().trim())
                address_3.setText(appuseraddress!!.address3.toString())
                zipcode.setText(appuseraddress!!.zipcode.toString())

            }


            
        }
        return root;
    }


    override fun onStart() {
        super.onStart()
        println("hi")
    }

    private fun addAppUserAddresssinfo(item: AppUserAddress) {
        progressShow()
        toggleEnaDisable(addAddress)
        val call = appUserservice.addAppUserAddress(item);
        callMethod(call)
    }

    private fun updateAppUserAddresssinfo(item: AppUserAddress) {
        progressShow()
        toggleEnaDisable(addAddress)
        val call = appUserservice.addAppUserAddress(item);
        callMethod(call)
    }

    private fun callMethod(call: Call<AppUserAddress>) {
        call.enqueue(object : Callback<AppUserAddress> {
            override fun onFailure(call: Call<AppUserAddress>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(addAddress)
            }

            override fun onResponse(call: Call<AppUserAddress>, response: Response<AppUserAddress>) {
                if (response.isSuccessful) {
                    val info: AppUserAddress? = response.body();
                    if (info != null) {
                    makeToast("address add successfully")
                        address_1.setText("")
                        address_2.setText( "")
                        address_3.setText("")
                        zipcode.setText("")
                    }
                } else {
                    Log.e(
                        ItemMasterFragment::class.java.simpleName,
                        "Error: ${response.code()} ${response.message()}"
                    )
                }
                progressHide()
                toggleEnaDisable(addAddress)
            }
        })
    }

}
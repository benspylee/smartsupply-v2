package com.asuravan.smartsupply.activity.ui.shop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.Shop
import com.asuravan.smartsupply.service.ShopService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopInputFragment : CommonFragment() {

    private var shopservice: ShopService = RetroRestClient<ShopService>().create(ShopService::class.java);

    lateinit var shopname: EditText;
    lateinit var ownername: EditText;
    lateinit var address_1: EditText;
    lateinit var address_2: EditText;
    lateinit var address_3: EditText;
    lateinit var zipcode: EditText;
    lateinit var mobileno: EditText;
    lateinit var email: EditText;
    lateinit var addShop:Button;


    var shopedit: Shop? = null;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        context?.let { super.activateProgress(it,false) }
       val root  = inflater.inflate(R.layout.fragment_shop_input, container, false)
        shopname = root.findViewById(R.id.shopname);
        ownername = root.findViewById(R.id.ownername);
        address_1 = root.findViewById(R.id.address_1);
        address_2 = root.findViewById(R.id.address_2);
        address_3 = root.findViewById(R.id.address_3);
        zipcode = root.findViewById(R.id.zipcode);
        mobileno = root.findViewById(R.id.mobileno);
        email = root.findViewById(R.id.email);
        addShop =root.findViewById(R.id.addShopbtn)
        addShop.setOnClickListener {
            var shop:Shop;
            if(shopedit!=null){
                shop = shopedit as Shop;
            }else{
                shop = Shop()
            }
            shop.ownername= ownername.text.toString()
            shop.shopname = shopname.text.toString()
            shop.address2 = address_2.text.toString();
            shop.addess1 = address_1.text.toString();
            shop.address3=address_3.text.toString();
            shop.mobileno=  mobileno.text.toString();
            shop.emailid=  email.text.toString();
            shop.zipcode=  zipcode.text.toString();
            shop.appusercd = sharedPreferences?.getInt("appusercd",0)
            shop.status =1;
            progressShow()
            try {
                if(shopedit!=null){
                    updateshopsinfo(shop)
                }else{
                    addshopsinfo(shop)
                }

            } catch (e: Exception) {
                Log.e("error","error")
            }
        }

        if(arguments!=null){
            shopedit=    arguments!!.get("updateitem") as Shop;
            if(shopedit!=null){
                shopname.setText(shopedit!!.shopname.toString().trim());
                ownername.setText(shopedit!!.ownername.toString().trim());
                address_1.setText( shopedit!!.addess1.toString().trim())
                address_2.setText( shopedit!!.address2.toString().trim())
                address_3.setText(shopedit!!.address3.toString())
                zipcode.setText(shopedit!!.zipcode.toString())
                mobileno.setText(shopedit!!.mobileno.toString())
                email.setText(shopedit!!.emailid.toString())
            }


            
        }
        return root;
    }


    override fun onStart() {
        super.onStart()
        println("hi")
    }

    private fun addshopsinfo(item: Shop) {
        progressShow()
        toggleEnaDisable(addShop)
        val call = shopservice.addShop(item);
        callMethod(call)
    }

    private fun updateshopsinfo(item: Shop) {
        progressShow()
        toggleEnaDisable(addShop)
        val call = shopservice.updateShop(item);
        callMethod(call)
    }

    private fun callMethod(call: Call<Shop>) {
        call.enqueue(object : Callback<Shop> {
            override fun onFailure(call: Call<Shop>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(addShop)
            }

            override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
                if (response.isSuccessful) {
                    val info: Shop? = response.body();
                    if (info != null) {
                        //   val bundle = Bundle()
                        //   bundle.putParcelable("item", item);
                        val fragmentGet = ShopFragment()
                        //     fragmentGet.setArguments(bundle)
                        var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                        fr?.replace(R.id.nav_host_fragment, fragmentGet)
                        fr?.commit()
                    }
                } else {
                    Log.e(
                        ItemMasterFragment::class.java.simpleName,
                        "Error: ${response.code()} ${response.message()}"
                    )
                }
                progressHide()
                toggleEnaDisable(addShop)
            }
        })
    }

}
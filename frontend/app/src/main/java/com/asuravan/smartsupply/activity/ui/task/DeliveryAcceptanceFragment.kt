package com.asuravan.smartsupply.activity.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.order.DeilveryTaskFragment
import com.asuravan.smartsupply.activity.ui.order.OrderFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.*
import com.asuravan.smartsupply.service.OrderBundleService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeliveryAcceptanceFragment : CommonFragment() {

    private var orderBundleService: OrderBundleService = RetroRestClient<OrderBundleService>().create(
        OrderBundleService::class.java);

    private lateinit var order: Order
    lateinit var rejectdelivery: Button;
    lateinit var acceptDelivery: Button;
    lateinit var deliveryAddress: TextView;
    lateinit var pickupAddress: TextView;

    lateinit var tottiemprice: TextView;
    lateinit var txtdelverycharge: TextView;
    lateinit var totcartamt: TextView;
    lateinit var deliactions:LinearLayout;
    lateinit var deliverycomp:Button;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        context?.let { super.activateProgress(it,true) }

        var root= inflater.inflate(R.layout.fragment_delivery_acceptance, container, false)
        rejectdelivery=root.findViewById(R.id.rejectdeliv)
        acceptDelivery =  root.findViewById(R.id.acceptdeliv)
        deliveryAddress=root.findViewById(R.id.deliveryAddress)
        pickupAddress =  root.findViewById(R.id.pickupAddress)
        tottiemprice = root.findViewById(R.id.tottiemprice)
        txtdelverycharge = root.findViewById(R.id.txtdelverycharge)
        totcartamt = root.findViewById(R.id.totcartamt)
        deliactions = root.findViewById(R.id.deliactions)
        deliverycomp= root.findViewById(R.id.deliverycomp)
        if(arguments!=null) {
            order = arguments!!.get("item") as Order;
            tottiemprice.text=order.totOrderPrice
            txtdelverycharge.text=0.0.toString()
            totcartamt.text=order.totOrderPrice
            deliveryAddress.text=order.deliveryAddress.toString().replace("@@",",\n ");
            pickupAddress.text=order.shopAddress.toString().replace("@@",",\n ");

            if(order.status==2)
                deliactions.visibility=View.VISIBLE
            else
                deliactions.visibility=View.INVISIBLE
            if(order.status==4)
                deliverycomp.visibility=View.VISIBLE
            else
                deliverycomp.visibility=View.INVISIBLE


        }
        deliverycomp.setOnClickListener {
            val call = orderBundleService.accept( orderBundlePull(7))
            callMethod(call, deliverycomp)
        }

        rejectdelivery.setOnClickListener {
            val call = orderBundleService.rejectdelivery( orderBundlePull(5))
            callMethod(call, rejectdelivery)
        }

        acceptDelivery.setOnClickListener {
            val call = orderBundleService.acceptdelivery( orderBundlePull(4))
            callMethod(call, acceptDelivery)
        }

        return root;
    }

    private fun procesStatus(status: Int) {
        //1-order placed //2- accepted //3-rejected //4-delivery accepted
        //5- delivery rejected // 6 - inTransit //7-delivered //8-cancelled
       // orderBundlePull(status)



       // callMethod(call, acceptDelivery)
    }

    private fun orderBundlePull(status: Int) :OrderBundle {
        var orderBundle =
            OrderBundle();
        orderBundle.appUser = AppUser();
        val appuserid = sharedPreferences?.getInt("appusercd", 0);
        val appuserrolecd = sharedPreferences?.getInt("appuserrolecd", 0);
        orderBundle.appUser.appusercd = appuserid;
        orderBundle.appUser.appuserrolecd = appuserrolecd
        orderBundle.orderStatus = OrderStatus();
        orderBundle.orderStatus.orderid = order.orderid
        orderBundle.orderStatus.orderstatuscd = status
        orderBundle.orderStatus.updateuid = appuserid

        orderBundle.orderDelivery = OrderDelivery();
        orderBundle.orderDelivery.orderid = order.orderid
        return  orderBundle;
    }

    private fun callMethod(call: Call<List<Order>>,view:View) {
        progressShow()
        toggleEnaDisable(view)
        call.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(view)
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val info: List<Order>? = response.body();
                    if (info != null) {
                        removeItemToSharedPref("cart")
                        makeToast("Processed Successfully...")
                        val fragmentGet = DeilveryTaskFragment()
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
                toggleEnaDisable(view)
            }
        })
    }


}
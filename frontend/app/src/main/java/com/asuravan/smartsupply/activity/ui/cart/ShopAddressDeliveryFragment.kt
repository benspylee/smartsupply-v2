package com.asuravan.smartsupply.activity.ui.cart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.order.OrderFragment
import com.asuravan.smartsupply.activity.ui.shop.ShopFragment
import com.asuravan.smartsupply.activity.ui.user.UserAddressFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.*
import com.asuravan.smartsupply.service.AppUserAddressService
import com.asuravan.smartsupply.service.OrderBundleService
import com.asuravan.smartsupply.service.ShopService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShopAddressDeliveryFragment : CommonFragment() {
    private var appUserservice: AppUserAddressService = RetroRestClient<AppUserAddressService>().create(
        AppUserAddressService::class.java);
    private var orderBundleService: OrderBundleService = RetroRestClient<OrderBundleService>().create(OrderBundleService::class.java);

    lateinit  var info: List<AppUserAddress>;
    lateinit var deliveryprice:TextView;
    lateinit var txttotalorderprice:TextView;
lateinit var paymentradioGroup:RadioGroup;
    lateinit var radioGroup: RadioGroup
    lateinit var recyclerView: RecyclerView ;
    lateinit var placeOrder: Button;
    var totalOrderprice:Double=0.0;
    var deliverycharge:Double=0.0;
    var selectedPosition:Int? = -1;
    var selectedAddress:AppUserAddress? = null;
    var paytype:Int =1;
    var deliverytype:Int =1;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        val root= inflater.inflate(R.layout.fragment_shop_address_delivery, container, false)
        recyclerView = root.findViewById(R.id.recyclerView)
        radioGroup= root.findViewById(R.id.deliverytype)
        deliveryprice=root.findViewById(R.id.txtdelverycharge)
        txttotalorderprice=root.findViewById(R.id.txttotalorderprice)
        placeOrder=root.findViewById(R.id.placeOrder);
        paymentradioGroup=root.findViewById(R.id.paytype)
        radioGroup.setOnCheckedChangeListener{ view, b ->
            if(b==R.id.selfpick){
                deliveryprice.text = "Rs.0"
                totalOrderprice=totalOrderprice-deliverycharge;
                deliverycharge=0.0;
                deliverytype=1;
            }
                else if(b==R.id.doordelivery) {
                deliveryprice.text = "Rs.20"
                deliverycharge=20.0
                deliverytype=2
                totalOrderprice=totalOrderprice+deliverycharge;
            }
            txttotalorderprice.text="Rs."+totalOrderprice.toString();
        }
        paymentradioGroup.setOnCheckedChangeListener { radioGroup, i ->
            if(i==R.id.cash)
                paytype=2;
            else
                paytype=1;
        }
        if(arguments!=null) {
            var valid = arguments!!.get("valid_order") as Boolean;
            totalOrderprice =arguments!!.getDouble("totalvalue")
        }
        deliveryprice.text="Rs.0"
        txttotalorderprice.text="Rs."+totalOrderprice.toString();
        placeOrder.setOnClickListener {
            if(validateorder())
            {
                placeOrderToVendor()
            }
        }
        context?.let { super.activateProgress(it,true) }
        return root;
    }

    private fun placeOrderToVendor() {
       var orderBundle=
           OrderBundle();
        orderBundle.appUser= AppUser();
        val appuserid=  sharedPreferences?.getInt("appusercd",0);
        orderBundle.appUser.appusercd= appuserid;
        orderBundle.order=Order();
        orderBundle.orderItems = ArrayList();
        var  listitems = getStringToItemListSharedPref("cart") as java.util.ArrayList<ItemInfo?>?
        listitems!!.forEach { item ->
            var orderitem=OrderItem();
            orderitem.itemcd=item!!.itemcode
            orderitem.qnty=item!!.quantity
            orderitem.unitcode=item!!.unitcode
            orderitem.status=1
            orderitem.shopcode=item!!.shopcode
            orderBundle.orderItems.add(orderitem);
         }
        orderBundle.orderPayment= OrderPayment();
        orderBundle.orderPayment.paymode=paytype
        orderBundle.orderDelivery= OrderDelivery()
        orderBundle.orderDelivery.deliverytype=deliverytype
        orderBundle.orderDelivery.deliveryaddr=selectedAddress?.appuseraddrcd
        orderBundle.orderDelivery.status=1
       val call= orderBundleService.placeOrder(orderBundle)
        callMethod(call)
    }

    private fun callMethod(call: Call<List<Order>>) {
        progressShow()
        toggleEnaDisable(placeOrder)
        call.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(placeOrder)
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val info: List<Order>? = response.body();
                    if (info != null) {
                        removeItemToSharedPref("cart")
                        makeToast("Order placed sucess..!")
                        val fragmentGet = OrderFragment()
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
                toggleEnaDisable(placeOrder)
            }
        })
    }

    private fun validateorder(): Boolean {

        if(selectedPosition==-1) {
            makeToast("select address...before continue..")
            return false
        }
       /* if(!radioGroup.isSelected)
        {
            makeToast("select delivery type...before continue..")
            return false
        }*/
        return true;
    }

    fun loadItems(reload:Boolean = true) {
        progressShow()
        val call = appUserservice.getAllAppUserAddress()
        call.enqueue(object: Callback<List<AppUserAddress>> {
            override fun onFailure(call: Call<List<AppUserAddress>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<AppUserAddress>>, response: Response<List<AppUserAddress>>) {
                if (response.isSuccessful) {
                    progressHide()
                     info = response.body()!!
                    if(info!=null){
                        if(!reload)
                            loadRecyleView(info)
                        else{
                            recyclerView.loadAdapter(info)
                        }
                    }else
                        makeToast("No address found addd uer address and continue")
                } else {
                    Log.e(ItemMasterFragment::class.java.simpleName, "Error: ${response.code()} ${response.message()}")
                }
            }
        })
    }

    private fun loadRecyleView(info: List<AppUserAddress>?) {
        if(recyclerView!=null){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                loadAdapter(info)
            }
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun RecyclerView?.loadAdapter(info: List<AppUserAddress>?) {
        if (info != null) {
            var listitems: ArrayList<AppUserAddress?> = info as ArrayList<AppUserAddress?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<AppUserAddress?>): GenericAdapter<AppUserAddress?> {
        return object : GenericAdapter<AppUserAddress?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.useraddres_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: AppUserAddress?) {
                var appUserAddress = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.appUserAddress = appUserAddress as AppUserAddress;
                itemholder.address.text = appUserAddress?.addess1 +"\n" + appUserAddress?.address2 +"\n" + appUserAddress?.address3 +"\n"+appUserAddress?.zipcode

            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                if (selectedPosition === position) {holder.itemView.setBackgroundColor(
                    Color.parseColor(
                        "#D3CFCF"
                    )
                )} else holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    selectedAddress = info.get(position);
                    notifyDataSetChanged()
                }
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView;
                var address: TextView

                lateinit var appUserAddress: AppUserAddress;

                init {
                    imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView

                    address=
                        itemView.findViewById<View>(R.id.address) as TextView


                    itemView.optionsBtn.setOnClickListener {
                     /*   val popup = CustomPopUp(context,itemView.optionsBtn) //view?.let { it1 -> activity?.let { it2 -> CustomPopUp(it2, it1) } }
                        if (popup != null) {
                            popup.setOnMenuItemClickListener(this@ItemViewHolder)
                            popup.setItem(imageView)
                            popup.inflate(R.menu.popup_menu_deliv)
                            popup.menu.removeItem(R.id.totalcartprice)
                            popup.show()
                        }*/
                    }

                }

                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.select_addres){
                            selectedPosition=itemCount;
                            return true;
                        }else {
                            return false;
                        }
                    }
                    return false;
                }

                private fun navigateToupdate(bundle: Bundle) {
                    val fragmentGet = UserAddressFragment()
                    fragmentGet.setArguments(bundle)
                    var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                    fr?.replace(R.id.nav_host_fragment, fragmentGet)
                    fr?.commit()
                }

                private fun deleteAppUserAddress(item: Int) {
                    val call = appUserservice.deleteAppUserAddressById(item)
                    if (call != null) {
                        callMethod(call)
                    }
                }

                private fun callMethod(call: Call<AppUserAddress>) {
                    call.enqueue(object : Callback<AppUserAddress> {
                        override fun onFailure(call: Call<AppUserAddress>, t: Throwable) {
                            Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                            progressHide()
                        }

                        override fun onResponse(call: Call<AppUserAddress>, response: Response<AppUserAddress>) {
                            if (response.isSuccessful) {
                                val info: AppUserAddress? = response.body();
                                if (info != null) {
                                    loadItems();
                                }
                            } else {
                                Log.e(
                                    ItemMasterFragment::class.java.simpleName,
                                    "Error: ${response.code()} ${response.message()}"
                                )
                            }
                            progressHide()
                        }
                    })
                }
            }

        }
    }
}
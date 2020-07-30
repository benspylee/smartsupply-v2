package com.asuravan.smartsupply.activity.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.*
import com.asuravan.smartsupply.service.OrderBundleService
import com.asuravan.smartsupply.service.OrderItemService
import com.asuravan.smartsupply.service.OrderService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetailsFragment : CommonFragment() {

    private lateinit var order: Order
    private var orderItemService: OrderItemService = RetroRestClient<OrderItemService>().create(OrderItemService::class.java);
    private var orderBundleService: OrderBundleService = RetroRestClient<OrderBundleService>().create(OrderBundleService::class.java);
   lateinit var mrecyclerView: RecyclerView ;

    lateinit var tottiemprice: TextView;
    lateinit var txtdelverycharge: TextView;
    lateinit var totcartamt: TextView;
    lateinit var rejectorder:Button;
    lateinit var proccedtoTra:Button;
    lateinit var detail: LinearLayout;
    lateinit var action:LinearLayout;
    lateinit var recycleinfo:LinearLayout;
    lateinit var deliveryAddress:TextView;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
       val root=inflater.inflate(R.layout.fragment_order_details, container, false)
            mrecyclerView = root.findViewById(R.id.recyclerView)
            tottiemprice = root.findViewById(R.id.tottiemprice)
        txtdelverycharge = root.findViewById(R.id.txtdelverycharge)
        totcartamt = root.findViewById(R.id.totcartamt)
        proccedtoTra=root.findViewById(R.id.proccedtoTra)
        detail=root.findViewById(R.id.detail)
        action=root.findViewById(R.id.action)
        deliveryAddress=root.findViewById(R.id.deliveryAddress)
        recycleinfo=root.findViewById(R.id.orderItem)
        rejectorder =  root.findViewById(R.id.rejectorder)
        context?.let { super.activateProgress(it,true) }
        progressShow()

            if(arguments!=null) {
                order = arguments!!.get("item") as Order;
                tottiemprice.text="Rs."+order.totOrderPrice
                txtdelverycharge.text="Rs."+0.0.toString()
                totcartamt.text="Rs."+order.totOrderPrice
                deliveryAddress.text=order.deliveryAddress.toString().replace("@@",",\n ");

                //if(order.acceptstatcd==4)
                //    action.visibility=View.INVISIBLE
            }

        proccedtoTra.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("item", order);
            val fragmentGet = OrderDeliveryFragment()
            fragmentGet.setArguments(bundle)
            var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
            fr?.replace(R.id.nav_host_fragment, fragmentGet)
            fr?.commit()
        }
        rejectorder.setOnClickListener {
            reject()
        }
                return root
    }

    private fun reject() {
        //1-order placed //2- accepted //3-rejected //4-delivery accepted
        //5- delivery rejected // 6 - inTransit //7-delivered //8-cancelled
        var orderBundle =
            OrderBundle();
        orderBundle.appUser = AppUser();
        val appuserid = sharedPreferences?.getInt("appusercd", 0);
        val appuserrolecd = sharedPreferences?.getInt("appuserrolecd", 0);
        orderBundle.appUser.appusercd = appuserid;
        orderBundle.appUser.appuserrolecd = appuserrolecd
        orderBundle.orderStatus = OrderStatus();
        orderBundle.orderStatus.orderid =order.orderid
        orderBundle.orderStatus.orderstatuscd=3
        orderBundle.orderStatus.updateuid=appuserid
        val call = orderBundleService.reject(orderBundle)
        callMethod(call)
    }

    private fun callMethod(call: Call<List<Order>>) {
        progressShow()
        toggleEnaDisable(rejectorder)
        call.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(rejectorder)
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val info: List<Order>? = response.body();
                    if (info != null) {
                        removeItemToSharedPref("cart")
                        makeToast("Action Processed sucess..!")
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
                toggleEnaDisable(rejectorder)
            }
        })
    }

    fun loadItems(reload:Boolean = true) {
        val call = orderItemService.getOrderItemByOrderId(order.orderid!!)
        call.enqueue(object: Callback<List<OrderItem>> {
            override fun onFailure(call: Call<List<OrderItem>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                action.visibility=View.INVISIBLE
                recycleinfo.visibility=View.INVISIBLE
                makeToast("Please  try after sometime")
            }
            override fun onResponse(call: Call<List<OrderItem>>, response: Response<List<OrderItem>>) {
                if (response.isSuccessful) {
                    progressHide()
                    val info: ArrayList<OrderItem>? = response.body() as ArrayList<OrderItem>;
                    if(info!=null){
                        val itemheader= OrderItem();
                        itemheader.header=true;
                        info.add(0,itemheader);
                        if(!reload)
                            loadRecyleView(info)
                        else{
                            mrecyclerView.loadAdapter(info)
                        }
                    }
                } else {
                    Log.e(ItemMasterFragment::class.java.simpleName, "Error: ${response.code()} ${response.message()}")
                }
            }
        })
    }

    private fun loadRecyleView(info: List<OrderItem>?) {
        if(mrecyclerView!=null){
            mrecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                loadAdapter(info)
            }
            if(activity!=null){
                mrecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

        }
    }

    private fun RecyclerView?.loadAdapter(info: List<OrderItem>?) {
        if (info != null) {
            var listitems: ArrayList<OrderItem?> = info as ArrayList<OrderItem?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
            action.visibility=View.VISIBLE
            recycleinfo.visibility=View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<OrderItem?>): GenericAdapter<OrderItem?> {
        return object : GenericAdapter<OrderItem?>(context,listitems) {

            var seq=1;

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.orderdetails_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: OrderItem?) {
                var orderItem = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                if(orderItem?.header!!){
                    itemholder.qnty.text = "Quantity"
                    itemholder.orderItem = orderItem as OrderItem;
                    itemholder.itemname.text = "Item"
                    itemholder.seq.text = "SNo"
                    itemholder.price.text ="Price"
                }else {
                    itemholder.qnty.text = orderItem?.qnty.toString()
                    itemholder.orderItem = orderItem as OrderItem;
                    itemholder.itemname.text = orderItem?.itemname.toString()
                    itemholder.seq.text = (seq++).toString();
                    itemholder.price.text =
                        "Rs." + (orderItem.qnty?.times(orderItem.price)).toString()

                }
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
                // var imageView: ImageView;
                var seq: TextView
                var itemname: TextView
                var qnty: TextView
                var price: TextView
                // var orderstatus: TextView


                lateinit var orderItem: OrderItem;

                init {
                    /* imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView*/
                    seq =
                        itemView.findViewById<View>(R.id.seq) as TextView
                    itemname =
                        itemView.findViewById<View>(R.id.itemname) as TextView
                    qnty =
                        itemView.findViewById<View>(R.id.qnty) as TextView
                    price =
                        itemView.findViewById<View>(R.id.price) as TextView


                }
            }



        }
    }


}
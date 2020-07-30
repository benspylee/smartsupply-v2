package com.asuravan.smartsupply.activity.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.Order
import com.asuravan.smartsupply.service.OrderService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderFragment : CommonFragment() {

    private var orderService: OrderService = RetroRestClient<OrderService>().create(OrderService::class.java);
    lateinit var recyclerView: RecyclerView ;
    var appuserrolecd=0;
    var appusercd=0;
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       super.onCreateView(inflater, container, savedInstanceState)
        val root= inflater.inflate(R.layout.fragment_order, container, false)
            recyclerView= root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()
        return  root;
    }


    fun loadItems(reload:Boolean = true) {
      appusercd= sharedPreferences?.getInt("appusercd",0)!!
         appuserrolecd= sharedPreferences?.getInt("appuserrolecd",0)!!
        var call: Call<List<Order>>? = null;
        if(appuserrolecd==1)
         call = orderService.getOrderByCustomer(appusercd!!)
        if(appuserrolecd==2)
         call = orderService.getOrderByShopOwner(appusercd!!)
        call?.enqueue(object: Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    progressHide()
                    val info: List<Order>? = response.body()
                    if(info!=null){
                        if(!reload)
                            loadRecyleView(info)
                        else{
                            recyclerView.loadAdapter(info)
                        }
                    }
                } else {
                    Log.e(ItemMasterFragment::class.java.simpleName, "Error: ${response.code()} ${response.message()}")
                }
            }
        })
    }

    private fun loadRecyleView(info: List<Order>?) {
        if(recyclerView!=null){
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                loadAdapter(info)
            }
            if(activity!=null){
                recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

        }
    }

    private fun RecyclerView?.loadAdapter(info: List<Order>?) {
        if (info != null) {
            var listitems: ArrayList<Order?> = info as ArrayList<Order?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<Order?>): GenericAdapter<Order?> {
        return object : GenericAdapter<Order?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: Order?) {
                var order = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.orderid.text="ODR0000"+order?.orderid
                itemholder.shopname.text=order?.shopName
                itemholder.order = order as Order;
                itemholder.orderprice.text = order?.totOrderPrice.toString()
                itemholder.orderdate.text = order?.orderdt.toString()
                itemholder.orderstatus.text = order?.orderStatDesc

            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView;
                var orderid: TextView
                var shopname: TextView
                var orderprice: TextView
                var orderdate: TextView
                var orderstatus: TextView


                lateinit var order: Order;

                init {
                    imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView
                    shopname =
                        itemView.findViewById<View>(R.id.shopname) as TextView
                    orderid =
                        itemView.findViewById<View>(R.id.orderid) as TextView
                    orderprice=
                        itemView.findViewById<View>(R.id.orderprice) as TextView
                    orderdate=
                        itemView.findViewById<View>(R.id.orderdate) as TextView
                    orderstatus=
                        itemView.findViewById<View>(R.id.orderstatus) as TextView

                    itemView.optionsBtn.setOnClickListener {
                        val popup = CustomPopUp(context,itemView.optionsBtn) //view?.let { it1 -> activity?.let { it2 -> CustomPopUp(it2, it1) } }
                        if (popup != null) {
                            popup.setOnMenuItemClickListener(this@ItemViewHolder)
                            popup.setItem(imageView)
                            popup.inflate(R.menu.popup_menu)
                            popup.show()
                        }
                    }

                    itemView.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putParcelable("item", order);
                        var fragmentGet:Fragment? = null
                            if(appuserrolecd==1)
                         fragmentGet = OrderDetailCustomerFragment()
                        if(appuserrolecd==2)
                         fragmentGet = OrderDetailsFragment()
                        fragmentGet?.setArguments(bundle)
                        var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                        fr?.replace(R.id.nav_host_fragment, fragmentGet!!)
                        fr?.commit()

                    }
                }

                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.update_item){
                         /*   val bundle = Bundle()
                            bundle.putParcelable("updateitem", Order);
                            navigateToupdate(bundle)*/
                            return true;
                        }else if(item.itemId == R.id.delete_item){
//                            Order?.Ordercd?.let { deleteOrder(it) }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }

                private fun navigateToupdate(bundle: Bundle) {

                }

                private fun deleteOrder(item: Int) {

                }

                private fun callMethod(call: Call<Order>) {
                    call.enqueue(object : Callback<Order> {
                        override fun onFailure(call: Call<Order>, t: Throwable) {
                            Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                            progressHide()
                        }

                        override fun onResponse(call: Call<Order>, response: Response<Order>) {
                            if (response.isSuccessful) {
                                val info: Order? = response.body();
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
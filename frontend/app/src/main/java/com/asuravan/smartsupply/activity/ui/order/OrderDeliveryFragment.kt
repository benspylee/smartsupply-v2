package com.asuravan.smartsupply.activity.ui.order

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.asuravan.smartsupply.activity.ui.vechile.VechileInputFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.*
import com.asuravan.smartsupply.service.OrderBundleService
import com.asuravan.smartsupply.service.VechileService
import com.asuravan.smartsupply.utils.BluebeeUtils
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.util.*


class OrderDeliveryFragment : CommonFragment() {
    private var vechileService: VechileService = RetroRestClient<VechileService>().create(
        VechileService::class.java);
    private var orderBundleService: OrderBundleService = RetroRestClient<OrderBundleService>().create(
        OrderBundleService::class.java);
    lateinit var picimg:ImageView;
    lateinit var datePickup:EditText;
    var selectedPosition:Int? = -1;
    var selectedVechile: Vechile? = null;
    var datePicker=null;
    var info: List<Vechile>? =null;
    val myCalendar: Calendar = Calendar.getInstance()
    private lateinit var order: Order
    private lateinit var acceptOrder:Button;
    private lateinit var selfpick:RadioButton;
    private lateinit var doordelivery:RadioButton;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        context?.let { super.activateProgress(it,true) }

        val root= inflater.inflate(R.layout.fragment_order_delivery, container, false)
        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        picimg = root.findViewById(R.id.picimg)
        datePickup = root.findViewById(R.id.datePickup)
        acceptOrder = root.findViewById(R.id.acceptOrder)
        doordelivery = root.findViewById(R.id.doordelivery)
        selfpick = root.findViewById(R.id.selfpick)

        picimg.setOnClickListener {
            context?.let { it1 ->
                val date =
                    OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        myCalendar[Calendar.YEAR] = year
                        myCalendar[Calendar.MONTH] = monthOfYear
                        myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                        datePickup.setText(BluebeeUtils.sf.format(myCalendar.time))
                    }
             DatePickerDialog(
                    it1, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
        if(arguments!=null) {
            order = arguments!!.get("item") as Order;
            if(order.deliverytype==1){
                selfpick.setChecked(true)
                doordelivery.isChecked=false;
            }
            else{
                selfpick.setChecked(false)
                doordelivery.isChecked=true;
            }
        }
        acceptOrder.setOnClickListener {
            accept()
        }

        context?.let { super.activateProgress(it,true) }
        progressShow()
        return root;
    }

    private fun accept() {
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
        orderBundle.orderStatus.orderstatuscd=2
        orderBundle.orderStatus.updateuid=appuserid

        orderBundle.orderDelivery = OrderDelivery();
        orderBundle.orderDelivery.orderid = order.orderid
        orderBundle.orderDelivery.driveruserid=selectedVechile?.appusercd
        orderBundle.orderDelivery.vechilecd=selectedVechile?.vechilecd
       // orderBundle.orderDelivery.pickupts= //Timestamp(System.currentTimeMillis())

        val call = orderBundleService.accept(orderBundle)
        callMethod(call)
    }

    private fun callMethod(call: Call<List<Order>>) {
        progressShow()
        toggleEnaDisable(acceptOrder)
        call.enqueue(object : Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(acceptOrder)
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val info: List<Order>? = response.body();
                    if (info != null) {
                        removeItemToSharedPref("cart")
                        makeToast("Processed Successfully...")
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
                toggleEnaDisable(acceptOrder)
            }
        })
    }

    fun loadItems(reload:Boolean = true) {
        val call = vechileService.getAllVechile()
        call.enqueue(object: Callback<List<Vechile>> {
            override fun onFailure(call: Call<List<Vechile>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<Vechile>>, response: Response<List<Vechile>>) {
                if (response.isSuccessful) {
                    progressHide()
                    info = response.body()
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

    private fun loadRecyleView(info: List<Vechile>?) {
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

    private fun RecyclerView?.loadAdapter(info: List<Vechile>?) {
        if (info != null) {
            var listitems: ArrayList<Vechile?> = info as ArrayList<Vechile?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<Vechile?>): GenericAdapter<Vechile?> {
        return object : GenericAdapter<Vechile?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.vechile_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: Vechile?) {
                var vechile = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.mobileno.text=vechile?.mobileno
                itemholder.ownername.text=vechile?.ownername
                itemholder.vechile = vechile as Vechile;
                itemholder.vechileregno.text = vechile?.vechileregno
                //   itemholder.address.text = vechile?.addess1 +"\n" + vechile?.address2 +"\n" + vechile?.address3 +"\n"+vechile?.zipcode
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
                    selectedVechile= info?.get(position);
                    notifyDataSetChanged()
                }
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView;
                var mobileno: TextView
                var ownername: TextView
                var vechileregno: TextView

                lateinit var vechile: Vechile;

                init {
                    imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView
                    mobileno =
                        itemView.findViewById<View>(R.id.mobileno) as TextView
                    ownername =
                        itemView.findViewById<View>(R.id.ownername) as TextView
                    vechileregno=
                        itemView.findViewById<View>(R.id.vechileregno) as TextView

                    itemView.optionsBtn.visibility=View.INVISIBLE

                    itemView.optionsBtn.setOnClickListener {
                        val popup = CustomPopUp(context,itemView.optionsBtn) //view?.let { it1 -> activity?.let { it2 -> CustomPopUp(it2, it1) } }
                        if (popup != null) {
                            popup.setOnMenuItemClickListener(this@ItemViewHolder)
                            popup.setItem(imageView)
                            popup.inflate(R.menu.popup_menu)
                            popup.show()
                        }
                    }
                }

                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.update_item){
                            val bundle = Bundle()
                            bundle.putParcelable("updateitem", vechile);
                            navigateToupdate(bundle)
                            return true;
                        }else if(item.itemId == R.id.delete_item){
                            vechile?.vechilecd?.let { deletevechile(it) }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }

                private fun navigateToupdate(bundle: Bundle) {
                    val fragmentGet = VechileInputFragment()
                    fragmentGet.setArguments(bundle)
                    var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                    fr?.replace(R.id.nav_host_fragment, fragmentGet)
                    fr?.commit()
                }

                private fun deletevechile(item: Int) {
                    val call = vechileService.deleteVechileById(item)
                    if (call != null) {
                        callMethod(call)
                    }
                }

                private fun callMethod(call: Call<Vechile>) {
                    call.enqueue(object : Callback<Vechile> {
                        override fun onFailure(call: Call<Vechile>, t: Throwable) {
                            Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                            progressHide()
                        }

                        override fun onResponse(call: Call<Vechile>, response: Response<Vechile>) {
                            if (response.isSuccessful) {
                                val info: Vechile? = response.body();
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
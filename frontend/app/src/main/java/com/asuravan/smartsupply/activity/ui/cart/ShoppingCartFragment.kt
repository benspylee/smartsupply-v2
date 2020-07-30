package com.asuravan.smartsupply.activity.ui.cart

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.shop.ShopInputFragment
import com.asuravan.smartsupply.activity.ui.shop.ShopItemDetailFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterInputFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.service.ItemService
import com.asuravan.smartsupply.utils.BluebeeUtils
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import java.util.function.Predicate


class ShoppingCartFragment : CommonFragment() {
    private var itemservice: ItemService = RetroRestClient<ItemService>().create(ItemService::class.java)
    var listitems: java.util.ArrayList<ItemInfo?>? = null;
    lateinit var totalcartprice:TextView;
    lateinit var proccedtoaddresTransport:Button;
    var  totalcartpriceDouble:Double =0.0;

    lateinit var  recyclerViewCart: RecyclerView ;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        context?.let { super.activateProgress(it,false) }
        val root = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        recyclerViewCart = root.findViewById(R.id.recyclerViewCart)
        totalcartprice= root.findViewById(R.id.totalcartprice);
        proccedtoaddresTransport= root.findViewById(R.id.proccedtoaddresTransport);
        proccedtoaddresTransport.setOnClickListener {
            if(listitems!!.size>0) {
                val bundle = Bundle()
                bundle.putBoolean("valid_order",true)
                bundle.putDouble("totalvalue",totalcartpriceDouble)
                val fragmentGet = ShopAddressDeliveryFragment()
                fragmentGet.setArguments(bundle)
                var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                fr?.replace(R.id.nav_host_fragment, fragmentGet)
                fr?.commit()
            }else
                makeToast("add Items to cart before proceed")
        }
        progressShow()
        return root;
    }

    fun loadItems(reload:Boolean = true) {
        val appuserid=  sharedPreferences?.getInt("appusercd",0);
        var call: Call<List<ItemInfo>>;
        if(appuserid!! >0)
            call = itemservice.getItemsByUserId(appuserid)
        else
            call = itemservice.getAllItems()
        call.enqueue(object: Callback<List<ItemInfo>> {
            override fun onFailure(call: Call<List<ItemInfo>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<ItemInfo>>, response: Response<List<ItemInfo>>) {
                if (response.isSuccessful) {
                    progressHide()
                    listitems = response.body() as java.util.ArrayList<ItemInfo?>?
                    if(listitems!=null){
                        if(!reload)
                            loadRecyleView(listitems)
                        else{
                            recyclerViewCart.loadAdapter(listitems)
                        }
                    }
                } else {
                    Log.e(ItemMasterFragment::class.java.simpleName, "Error: ${response.code()} ${response.message()}")
                }
            }
        })
    }

    private fun loadRecyleView(info: ArrayList<ItemInfo?>?) {
        if(recyclerViewCart!=null){
            recyclerViewCart.apply {
                layoutManager = LinearLayoutManager(activity)
                loadAdapter(info)
            }
            recyclerViewCart.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun RecyclerView?.loadAdapter(info: ArrayList<ItemInfo?>?) {
        if (info != null) {
            listitems = info as java.util.ArrayList<ItemInfo?>?
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // loadItems(false)
        loadItemsFromSharePref()
    }

    private fun loadItemsFromSharePref() {
         listitems = getStringToItemListSharedPref("cart") as ArrayList<ItemInfo?>?
        loadRecyleView(listitems)
        totalcartpriceDouble = listitems!!.sumByDouble { it?.price?.times(it?.quantity!!)!! }
        totalcartprice.text = "Rs." + totalcartpriceDouble;
         progressHide()
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<ItemInfo?>): GenericAdapter<ItemInfo?> {
        return object : GenericAdapter<ItemInfo?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.cart_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: ItemInfo?) {
                var iteminfo = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.itemname.text=iteminfo?.itemname
                itemholder.procatdesc.text=iteminfo?.prodcatdesc
                itemholder.itemInfo = iteminfo as ItemInfo;
                itemholder.itemprice.text ="Rs."+ iteminfo.price.toString() +"/"+ ItemMasterInputFragment.list_of_units.get(
                    iteminfo.unitcode?.minus(1)!!
                )
                itemholder.display.text= if( iteminfo?.quantity != null)  iteminfo?.quantity!!.toString() else "1";
                itemholder.totalprice.text = if(iteminfo?.totalprice!=null)  (iteminfo?.totalprice) else ("Rs." + ((iteminfo?.price * 1).toString()))
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView
                var procatdesc: TextView
                var itemname: TextView
                var itemprice: TextView
                var display: TextView
                var totalprice: TextView
                var increment:Button;
                var decrement: Button;


                lateinit var itemInfo: ItemInfo;

                init {
                    imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView
                    procatdesc =
                        itemView.findViewById<View>(R.id.prodcatdesc) as TextView
                    itemname =
                        itemView.findViewById<View>(R.id.itemname) as TextView
                    itemprice=
                        itemView.findViewById<View>(R.id.itemprice) as TextView
                    display=
                        itemView.findViewById<View>(R.id.display) as TextView
                    totalprice=
                        itemView.findViewById<View>(R.id.totalprice) as TextView
                    increment=
                        itemView.findViewById<View>(R.id.increment) as Button
                    decrement=
                        itemView.findViewById<View>(R.id.decrement) as Button

                    increment.setOnClickListener {

                        totalcartpriceDouble=totalcartpriceDouble-(itemInfo.price * itemInfo.quantity!!)
                        var res=display.text.toString().toInt()+1;
                        itemInfo.quantity=res;
                        display.text= res.toString();
                        itemInfo.totalprice ="Rs." + ((itemInfo.price * res).toString());
                        totalprice.text =itemInfo.totalprice
                        totalcartpriceDouble=totalcartpriceDouble+(itemInfo.price * itemInfo.quantity!!)
                        totalcartprice.text = "Rs." + totalcartpriceDouble;
                    }

                    decrement.setOnClickListener {

                        if(display.text.toString().toInt()>1) {
                            totalcartpriceDouble=totalcartpriceDouble-(itemInfo.price * itemInfo.quantity!!)
                            var res = display.text.toString().toInt() - 1;
                            itemInfo.quantity=res;
                            display.text = res.toString();
                            itemInfo.totalprice ="Rs." + ((itemInfo.price * res).toString());
                            totalcartpriceDouble=totalcartpriceDouble+(itemInfo.price * itemInfo.quantity!!)
                            totalcartprice.text = "Rs." + totalcartpriceDouble;
                            totalprice.text =itemInfo.totalprice
                        }
                    }

                    itemView.optionsBtn.setOnClickListener {
                        val popup = CustomPopUp(context,itemView.optionsBtn) //view?.let { it1 -> activity?.let { it2 -> CustomPopUp(it2, it1) } }
                        if (popup != null) {
                            popup.setOnMenuItemClickListener(this@ItemViewHolder)
                            popup.setItem(imageView)
                            popup.inflate(R.menu.popup_menu_cart)
                            popup.menu.removeItem(R.id.add_to_cart)
                            popup.show()
                        }
                    }
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.remove_from_cart){
                            //listitems.remove(itemInfo)
                            var cartlist:java.util.ArrayList<ItemInfo?>? = getStringToItemListSharedPref("cart") as java.util.ArrayList<ItemInfo?>?;
                            if(cartlist==null)
                                cartlist= ArrayList();
                            //var predicate:Predicate<ItemInfo> = obj
                            cartlist.removeIf(Predicate { obj -> obj!!.itemcode ==itemInfo.itemcode   })
                            val incart=   BluebeeUtils.GetInstance().convertToString(cartlist);
                            addItemToSharedPref("cart",incart)

                            recyclerViewCart.loadAdapter(cartlist)
                            return true;
                        }else {
                            return false;
                        }
                    }
                    return false;
                }

                private fun navigateToupdate(bundle: Bundle) {
                    val fragmentGet = ItemMasterInputFragment()
                    fragmentGet.setArguments(bundle)
                    var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                    fr?.replace(R.id.nav_host_fragment, fragmentGet)
                    fr?.commit()
                }

                private fun deleteItemsinfo(item: Int) {
                    val call = itemservice.deleteItemById(item)
                    if (call != null) {
                        callMethod(call)
                    }
                }

                private fun callMethod(call: Call<ItemInfo>) {
                    call.enqueue(object : Callback<ItemInfo> {
                        override fun onFailure(call: Call<ItemInfo>, t: Throwable) {
                            Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                            progressHide()
                        }

                        override fun onResponse(call: Call<ItemInfo>, response: Response<ItemInfo>) {
                            if (response.isSuccessful) {
                                val info: ItemInfo? = response.body();
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
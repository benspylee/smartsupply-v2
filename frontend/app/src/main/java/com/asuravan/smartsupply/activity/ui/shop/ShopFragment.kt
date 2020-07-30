package com.asuravan.smartsupply.activity.ui.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterInputFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.Shop
import com.asuravan.smartsupply.service.ShopService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import com.asuravan.smartsupply.activity.R

class ShopFragment : CommonFragment() {


    private var shopservice: ShopService = RetroRestClient<ShopService>().create(ShopService::class.java);


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_shop, container, false)
        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            try {
                var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                fr?.replace(R.id.nav_host_fragment, ShopInputFragment())
                fr?.commit()
            } catch (e: Exception) {
            }
        }
        return root
    }


    fun loadItems(reload:Boolean = true) {
        val appusercd=   sharedPreferences?.getInt("appusercd",0)
        val call = shopservice.getShopByUserId(appusercd!!)
        call.enqueue(object: Callback<List<Shop>> {
            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                if (response.isSuccessful) {
                    progressHide()
                    val info: List<Shop>? = response.body()
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

    private fun loadRecyleView(info: List<Shop>?) {
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

    private fun RecyclerView?.loadAdapter(info: List<Shop>?) {
        if (info != null) {
            var listitems: ArrayList<Shop?> = info as ArrayList<Shop?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<Shop?>): GenericAdapter<Shop?> {
        return object : GenericAdapter<Shop?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.shop_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: Shop?) {
                var shop = `val`
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.phoneno.text=shop?.mobileno
                itemholder.ownername.text=shop?.ownername
                itemholder.shop = shop as Shop;
                itemholder.shopname.text = shop?.shopname
                itemholder.address.text = shop?.addess1 +"\n" + shop?.address2 +"\n" + shop?.address3 +"\n"+shop?.zipcode
                itemholder.txtemailid.text = shop?.emailid

            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView:ImageView;
                var phoneno: TextView
                var ownername: TextView
                var shopname: TextView
                var address: TextView
                var txtemailid: TextView
                var imageemail:ImageView;
                var imgmobileno:ImageView;


                lateinit var shop: Shop;

                init {
                    imageView =
                        itemView.findViewById<View>(R.id.imageView) as ImageView
                    imageemail =
                        itemView.findViewById<View>(R.id.imgemail) as ImageView
                    imgmobileno =
                        itemView.findViewById<View>(R.id.imgmobileno) as ImageView
                    phoneno =
                        itemView.findViewById<View>(R.id.phoneno) as TextView
                    ownername =
                        itemView.findViewById<View>(R.id.ownername) as TextView
                    shopname=
                        itemView.findViewById<View>(R.id.shopname) as TextView
                    address=
                        itemView.findViewById<View>(R.id.address) as TextView
                    txtemailid=
                        itemView.findViewById<View>(R.id.txtemailid) as TextView

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
                           bundle.putParcelable("item", shop);
                        val fragmentGet = ShopItemDetailFragment()
                             fragmentGet.setArguments(bundle)
                        var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                        fr?.replace(R.id.nav_host_fragment, fragmentGet)
                        fr?.commit()
                    }
                }

                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.update_item){
                            val bundle = Bundle()
                            bundle.putParcelable("updateitem", shop);
                            navigateToupdate(bundle)
                            return true;
                        }else if(item.itemId == R.id.delete_item){
                            shop?.shopcd?.let { deleteshop(it) }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }

                private fun navigateToupdate(bundle: Bundle) {
                    val fragmentGet = ShopInputFragment()
                    fragmentGet.setArguments(bundle)
                    var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                    fr?.replace(R.id.nav_host_fragment, fragmentGet)
                    fr?.commit()
                }

                private fun deleteshop(item: Int) {
                    val call = shopservice.deleteShopById(item)
                    if (call != null) {
                        callMethod(call)
                    }
                }

                private fun callMethod(call: Call<Shop>) {
                    call.enqueue(object : Callback<Shop> {
                        override fun onFailure(call: Call<Shop>, t: Throwable) {
                            Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                            progressHide()
                        }

                        override fun onResponse(call: Call<Shop>, response: Response<Shop>) {
                            if (response.isSuccessful) {
                                val info: Shop? = response.body();
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
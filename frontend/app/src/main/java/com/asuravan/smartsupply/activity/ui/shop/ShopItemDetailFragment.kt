package com.asuravan.smartsupply.activity.ui.shop

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterInputFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.pojo.Shop
import com.asuravan.smartsupply.service.ItemService
import com.asuravan.smartsupply.utils.BluebeeUtils
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Predicate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopItemDetailFragment : CommonFragment() {
    private var itemservice: ItemService = RetroRestClient<ItemService>().create(ItemService::class.java)
    lateinit var imageView:ImageView;
    lateinit var phoneno: TextView
    lateinit var ownername: TextView
    lateinit var shopname: TextView
    lateinit var address: TextView
    lateinit var txtemailid: TextView
    lateinit var imageemail:ImageView;
    lateinit var imgmobileno:ImageView;
    lateinit var shopedit:Shop;
    var shopusercd:Int = 0;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        val root= inflater.inflate(R.layout.fragment_shop_item_detail, container, false)
        phoneno =
            root.findViewById<View>(R.id.phoneno) as TextView
        ownername =
            root.findViewById<View>(R.id.ownername) as TextView
        shopname=
            root.findViewById<View>(R.id.shopname) as TextView
        address=
            root.findViewById<View>(R.id.address) as TextView
        txtemailid=
            root.findViewById<View>(R.id.txtemailid) as TextView

        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()
        if(arguments!=null){
            shopedit=    arguments!!.get("item") as Shop;
            if(shopedit!=null){
                shopname.setText(shopedit!!.shopname.toString().trim());
                ownername.setText(shopedit!!.ownername.toString().trim());
                address.setText(  shopedit?.addess1 +"\n" + shopedit?.address2 +"\n" + shopedit?.address3 +"\n"+shopedit?.zipcode)
                phoneno.setText(shopedit!!.mobileno.toString())
                txtemailid.setText(shopedit!!.emailid.toString())
                shopusercd = shopedit!!.appusercd!!
            }
        }
        return root;
    }

    fun loadItems(reload:Boolean = true) {
        var call= shopusercd?.let { itemservice.getItemsByUserId(it) }
        if (call != null) {
            call.enqueue(object: Callback<List<ItemInfo>> {
                override fun onFailure(call: Call<List<ItemInfo>>, t: Throwable) {
                    Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                    progressHide()

                }
                override fun onResponse(call: Call<List<ItemInfo>>, response: Response<List<ItemInfo>>) {
                    if (response.isSuccessful) {
                        progressHide()
                        val info: List<ItemInfo>? = response.body()
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
    }

    private fun loadRecyleView(info: List<ItemInfo>?) {
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

    private fun RecyclerView?.loadAdapter(info: List<ItemInfo>?) {
        if (info != null) {
            var listitems: ArrayList<ItemInfo?> = info as ArrayList<ItemInfo?>
            if (listitems != null)
                this?.adapter = createItemsAdapter(listitems)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadItems(false)
    }

    fun createItemsAdapter(listitems:java.util.ArrayList<ItemInfo?>): GenericAdapter<ItemInfo?> {
        return object : GenericAdapter<ItemInfo?>(context,listitems) {

            override fun setViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.iteminfo_list_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun onBindData(holder: RecyclerView.ViewHolder?, `val`: ItemInfo?) {
                var iteminfo = `val`
                iteminfo!!.shopcode=shopedit.shopcd
                var itemholder:ItemViewHolder = holder as ItemViewHolder
                itemholder.itemname.text=iteminfo?.itemname
                itemholder.procatdesc.text=iteminfo?.prodcatdesc
                itemholder.itemInfo = iteminfo as ItemInfo;
                itemholder.itemprice.text ="Rs."+ iteminfo.price.toString() +"/"+ ItemMasterInputFragment.list_of_units.get(
                    iteminfo.unitcode?.minus(1)!!
                )
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView
                var procatdesc: TextView
                var itemname: TextView
                var itemprice: TextView


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

                    itemView.optionsBtn.setOnClickListener {
                        val popup = CustomPopUp(context,itemView.optionsBtn) //view?.let { it1 -> activity?.let { it2 -> CustomPopUp(it2, it1) } }
                        if (popup != null) {
                            popup.setOnMenuItemClickListener(this@ItemViewHolder)
                            popup.setItem(imageView)
                            popup.inflate(R.menu.popup_menu_cart)
                            popup.show()
                        }
                    }
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.add_to_cart){
                          var cartlist:java.util.ArrayList<ItemInfo> = getStringToItemListSharedPref("cart") as java.util.ArrayList<ItemInfo>;
                            if(cartlist!=null) {
                                if(!cartlist.contains(itemInfo)) {
                                    cartlist.add(itemInfo)
                                    val incart = BluebeeUtils.GetInstance().convertToString(cartlist);
                                    addItemToSharedPref("cart", incart)
                                }
                                else
                                    makeToast("Already added in cart")

                            }
                            return true;
                        }else if(item.itemId == R.id.remove_from_cart){

                            return true;
                        } else {
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
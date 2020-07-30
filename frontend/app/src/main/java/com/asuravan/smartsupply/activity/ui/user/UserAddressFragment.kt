package com.asuravan.smartsupply.activity.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterInputFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.AppUserAddress

import com.asuravan.smartsupply.service.AppUserAddressService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserAddressFragment : CommonFragment() {

    private var appUserservice: AppUserAddressService = RetroRestClient<AppUserAddressService>().create(
        AppUserAddressService::class.java);

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root= inflater.inflate(R.layout.fragment_user_address, container, false)
        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()
//        val fab: FloatingActionButton = root.findViewById(R.id.fab)
       /* fab.setOnClickListener { view ->
            try {
                var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                fr?.replace(R.id.nav_host_fragment, UserAddressInputFragment())
                fr?.commit()
            } catch (e: Exception) {
            }
        }*/
        return root;
    }

    fun loadItems(reload:Boolean = true) {
        val appusercd=   sharedPreferences?.getInt("appusercd",0)
        val call = appUserservice.getAppUserAddressByUserId(appusercd!!)
        call.enqueue(object: Callback<List<AppUserAddress>> {
            override fun onFailure(call: Call<List<AppUserAddress>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<AppUserAddress>>, response: Response<List<AppUserAddress>>) {
                if (response.isSuccessful) {
                    progressHide()
                    val info: List<AppUserAddress>? = response.body()
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
                        bundle.putParcelable("item", appUserAddress);
                        val fragmentGet = UserAddressFragment()
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
                            bundle.putParcelable("updateitem", appUserAddress);
                            navigateToupdate(bundle)
                            return true;
                        }else if(item.itemId == R.id.delete_item){
                            appUserAddress?.appuseraddrcd?.let { deleteAppUserAddress(it) }
                            return true;
                        } else {
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
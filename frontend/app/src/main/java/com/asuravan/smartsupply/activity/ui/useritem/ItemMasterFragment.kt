package com.asuravan.smartsupply.activity.ui.useritem

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.service.ItemService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemMasterFragment : CommonFragment() {

    private lateinit var itemMasterModel: ItemMasterModel
    private var itemservice: ItemService = RetroRestClient<ItemService>().create(ItemService::class.java)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        context?.let { super.activateProgress(it,false) }
        itemMasterModel =
            ViewModelProviders.of(this).get(ItemMasterModel::class.java)
        val root = inflater.inflate(R.layout.fragment_item_master, container, false)
        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()
        itemMasterModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        })

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            try {
                var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                fr?.replace(R.id.nav_host_fragment, ItemMasterInputFragment())
                fr?.commit()
            } catch (e: Exception) {
            }
        }

        return root
    }

    fun loadItems(reload:Boolean = true) {
      val appuserid=  sharedPreferences?.getInt("appusercd",0);
        var call:Call<List<ItemInfo>>;
        if(appuserid!! >0)
         call = itemservice.getItemsByUserId(appuserid)
        else
         call = itemservice.getAllItems()
        call.enqueue(object: Callback<List<ItemInfo>> {
            override fun onFailure(call: Call<List<ItemInfo>>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

            }
            override fun onResponse(call: Call<List<ItemInfo>>,response: Response<List<ItemInfo>>) {
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
                var itemholder:ItemViewHolder = holder as ItemViewHolder
//                try {
//                    iteminfo?.imageid?.let { itemholder.imageView.setImageDrawable(resources.getDrawable(it)) }
//                } catch (e: Exception) {
//                }
                itemholder.itemname.text=iteminfo?.itemname
                itemholder.procatdesc.text=iteminfo?.prodcatdesc
                itemholder.itemInfo = iteminfo as ItemInfo;
                itemholder.itemprice.text ="Rs."+ iteminfo.price.toString() +"/"+ItemMasterInputFragment.list_of_units.get(
                    iteminfo.unitcode?.minus(1)!!
                )
            }

            inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                android.widget.PopupMenu.OnMenuItemClickListener {
                var imageView: ImageView
                var procatdesc: TextView
                var itemname: TextView
                var itemprice: TextView


                lateinit var itemInfo:ItemInfo;

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
                            popup.inflate(R.menu.popup_menu)
                            popup.show()
                        }
                    }
                }

                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    if (item != null) {
                        if(item.itemId == R.id.update_item){
                            val bundle = Bundle()
                            bundle.putParcelable("updateitem", itemInfo);
                            navigateToupdate(bundle)
                            return true;
                        }else if(item.itemId == R.id.delete_item){
                            deleteItemsinfo(itemInfo?.itemcode)
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

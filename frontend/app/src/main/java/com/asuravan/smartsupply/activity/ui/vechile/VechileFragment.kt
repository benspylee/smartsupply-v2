package com.asuravan.smartsupply.activity.ui.vechile

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
import com.asuravan.smartsupply.activity.ui.vechile.VechileInputFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.Vechile
import com.asuravan.smartsupply.service.VechileService
import com.asuravan.smartsupply.utils.CustomPopUp
import com.asuravan.smartsupply.utils.GenericAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_item_master.*
import kotlinx.android.synthetic.main.iteminfo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VechileFragment : CommonFragment() {

    private var vechileService: VechileService = RetroRestClient<VechileService>().create(
        VechileService::class.java);
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root= inflater.inflate(R.layout.fragment_vechile, container, false)
        val mRecyclerView: RecyclerView = root.findViewById(R.id.recyclerView)
        context?.let { super.activateProgress(it,true) }
        progressShow()

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            try {
                var fr = fragmentManager?.beginTransaction()?.addToBackStack(null)
                fr?.replace(R.id.nav_host_fragment, VechileInputFragment())
                fr?.commit()
            } catch (e: Exception) {
            }
        }
        return root
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
                    val info: List<Vechile>? = response.body()
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
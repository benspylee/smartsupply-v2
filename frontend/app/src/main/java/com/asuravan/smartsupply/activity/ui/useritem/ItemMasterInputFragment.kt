package com.asuravan.smartsupply.activity.ui.useritem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.ItemInfo
import com.asuravan.smartsupply.service.ItemService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemMasterInputFragment : CommonFragment() {

   // private lateinit var itemMasterModel: ItemMasterModel

    var list_of_items = arrayOf("Grocery","Vegtables");
    private var itemservice: ItemService = RetroRestClient<ItemService>().create(ItemService::class.java);

   // @BindView(R.id.prodcat)
    lateinit var prodcat: Spinner;
    lateinit var unitcodespin: Spinner;
    lateinit var itmSubmit:Button;
    lateinit var itemname:EditText;
    lateinit var itemdesc:EditText;
    lateinit var price:EditText;


    var itemedit: ItemInfo? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
      //  itemMasterModel =
       //     ViewModelProviders.of(this).get(ItemMasterModel::class.java)
        context?.let { super.activateProgress(it,false) }
        val root = inflater.inflate(R.layout.fragment_item_master_input, container, false)
       // prodcat!!.setOnItemSelectedListener(this)
        val aa = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, list_of_items) }
        val unitadap = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item,
            Companion.list_of_units
        ) }
        prodcat=root.findViewById(R.id.prodcat);
        itmSubmit=root.findViewById(R.id.btnSubmitItem);
        itemname =  root.findViewById(R.id.item_name)
        itemdesc =  root.findViewById(R.id.item_desc)
        unitcodespin = root.findViewById(R.id.unit)
        price  = root.findViewById(R.id.price)
       // prodcatdesc = root.findViewById(R.id.prodcatdesc)

        aa?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitadap?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prodcat!!.setAdapter(aa);
        unitcodespin!!.setAdapter(unitadap);

        if(arguments!=null){
            itemedit=    arguments!!.get("updateitem") as ItemInfo;
            if(itemedit!=null){
                prodcat.setSelection(itemedit!!.prodcat-1)
                itemname.setText(itemedit!!.itemname.toString().trim());
                itemdesc.setText( itemedit!!.itemdesc.toString().trim())
                itemedit!!.unitcode?.minus(1)?.let { unitcodespin.setSelection(it) }
                price.setText(itemedit!!.price.toString())
            }
        }
//        itemMasterModel.text.observe(viewLifecycleOwner, Observer  {
  //      })

        itmSubmit.setOnClickListener{
            var item:ItemInfo;
                if(itemedit!=null){
                    item = itemedit as ItemInfo;
            }else{
                    item = ItemInfo()
                    item.imageid = R.drawable.vegetable
                }
            item.prodcat= prodcat.selectedItemPosition+1;
            item.itemname = itemname.text.toString()
            item.itemdesc = itemdesc.text.toString();
            item.prodcatdesc = prodcat.selectedItem.toString()
            item.unitcode=unitcodespin.selectedItemPosition+1;
            item.price=  price.text.toString().toDouble()
            item.appusercd = sharedPreferences?.getInt("appusercd",0)
            progressShow()
            try {
                if(itemedit!=null){
                    updateItemsinfo(item)
                }else{
                    addItemsinfo(item)
                }

            } catch (e: Exception) {
                Log.e("error","error")
            }
        }


        return root
    }

    private fun addItemsinfo(item: ItemInfo) {
        val call = itemservice.addItem(item);
        callMethod(call)
    }

    private fun updateItemsinfo(item: ItemInfo) {
        val call = itemservice.updateItem(item);
        callMethod(call)
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
                        //   val bundle = Bundle()
                        //   bundle.putParcelable("item", item);
                        val fragmentGet = ItemMasterFragment()
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
            }
        })
    }

    companion object {
        val list_of_units = arrayOf("Kg","liter");
    }
}
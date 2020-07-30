package com.asuravan.smartsupply.activity.ui.vechile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonFragment
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.Vechile
import com.asuravan.smartsupply.service.VechileService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VechileInputFragment : CommonFragment() {

    private var vechileService: VechileService = RetroRestClient<VechileService>().create(VechileService::class.java);

    lateinit var vechileregno: EditText;
    lateinit var ownername: EditText;
    lateinit var address_1: EditText;
    lateinit var address_2: EditText;
    lateinit var address_3: EditText;
    lateinit var zipcode: EditText;
    lateinit var mobileno: EditText;
    lateinit var email: EditText;
    lateinit var addVechile: Button;

    var vechile_edit: Vechile? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        context?.let { super.activateProgress(it,false) }
        val root = inflater.inflate(R.layout.fragment_vechile_input, container, false)
        vechileregno = root.findViewById(R.id.vechileregno);
        ownername = root.findViewById(R.id.ownername);
        address_1 = root.findViewById(R.id.address_1);
        address_2 = root.findViewById(R.id.address_2);
        address_3 = root.findViewById(R.id.address_3);
        zipcode = root.findViewById(R.id.zipcode);
        mobileno = root.findViewById(R.id.mobileno);
        addVechile =root.findViewById(R.id.addvechile)
        addVechile.setOnClickListener {
            var vechile: Vechile;
            if(vechile_edit!=null){
                vechile = vechile_edit as Vechile;
            }else{
                vechile = Vechile()
            }
            vechile.ownername= ownername.text.toString()
            vechile.vechileregno = vechileregno.text.toString()
            vechile.address2 = address_2.text.toString();
            vechile.addess1 = address_1.text.toString();
            vechile.address3=address_3.text.toString();
            vechile.mobileno=  mobileno.text.toString();
            vechile.zipcode=  zipcode.text.toString();
            vechile.appusercd = sharedPreferences?.getInt("appusercd",0)
            vechile.status =1;
            progressShow()
            try {
                if(vechile_edit!=null){
                    updatevechilesinfo(vechile)
                }else{
                    addvechilesinfo(vechile)
                }
            } catch (e: Exception) {
                Log.e("error","error")
            }
        }

        if(arguments!=null){
            vechile_edit=    arguments!!.get("updateitem") as Vechile;
            if(vechile_edit!=null){
                vechileregno.setText(vechile_edit!!.vechileregno.toString().trim());
                ownername.setText(vechile_edit!!.ownername.toString().trim());
                address_1.setText( vechile_edit!!.addess1.toString().trim())
                address_2.setText( vechile_edit!!.address2.toString().trim())
                address_3.setText(vechile_edit!!.address3.toString())
                zipcode.setText(vechile_edit!!.zipcode.toString())
                mobileno.setText(vechile_edit!!.mobileno.toString())
            }
        }
        return root;
    }



    private fun addvechilesinfo(item: Vechile) {
        progressShow()
        toggleEnaDisable(addVechile)
        val call = vechileService.addVechile(item);
        callMethod(call)
    }

    private fun updatevechilesinfo(item: Vechile) {
        progressShow()
        toggleEnaDisable(addVechile)
        val call = vechileService.updateVechile(item);
        callMethod(call)
    }

    private fun callMethod(call: Call<Vechile>) {
        call.enqueue(object : Callback<Vechile> {
            override fun onFailure(call: Call<Vechile>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(addVechile)
            }

            override fun onResponse(call: Call<Vechile>, response: Response<Vechile>) {
                if (response.isSuccessful) {
                    val info: Vechile? = response.body();
                    if (info != null) {
                        //   val bundle = Bundle()
                        //   bundle.putParcelable("item", item);
                        val fragmentGet = VechileFragment()
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
                toggleEnaDisable(addVechile)
            }
        })
    }

}
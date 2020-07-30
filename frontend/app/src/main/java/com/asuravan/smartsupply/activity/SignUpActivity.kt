package com.asuravan.smartsupply.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonActivity
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.service.AppUserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : CommonActivity(), AdapterView.OnItemSelectedListener {
    private var appUserService: AppUserService = RetroRestClient<AppUserService>().create(
        AppUserService::class.java);

    private var invalid: Int = 0;
    var list_of_items = arrayOf("Customer", "Retail Vendor","WholeSale Vendor", "Transporter");
    var  list_of_control: Array<View>? = null;

    @BindView(R.id.usertype)
    lateinit var userType: Spinner;

    @BindView(R.id.btnLinkToLoginScreen)
    lateinit var logInbtn:Button;

    @BindView(R.id.btnRegister)
    lateinit var registerBtn:Button;

    @BindView(R.id.firstname)
    lateinit var firstname: EditText;

    @BindView(R.id.lastname)
    lateinit var lastname:EditText;

    @BindView(R.id.mobileno)
    lateinit var mobileno:EditText;

    @BindView(R.id.email)
    lateinit var email:EditText;

    @BindView(R.id.password)
    lateinit var password:EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        ButterKnife.bind(this)
        supportActionBar?.hide();
       list_of_control = arrayOf(firstname,email,password,mobileno);
        userType!!.setOnItemSelectedListener(this)
        val aa = ArrayAdapter(this, R.layout.spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        userType!!.setAdapter(aa)

        logInbtn.setOnClickListener{
            val i = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        registerBtn.setOnClickListener{
            for (obj in list_of_control!!)
               if(validateMadatory(obj))
                   this.invalid++;

            if(invalid>0) {
                makeToast("enter all madatory feilds")
            }else{
                if(!validateEmail(email) || !validatePass(password))
                    makeToast("enter valid email/ password");
                else
                saveUserInfo()
            }


            }


    }

    private fun saveUserInfo() {
        var appUser=AppUser();
        appUser.firstname = firstname.text.toString()
        appUser.lastname = lastname.text.toString()
        appUser.emailid = email.text.toString()
        appUser.mobileno = mobileno.text.toString()
        appUser.password = password.text.toString()
        appUser.appuserrolecd= userType.selectedItemPosition+1
        appUser.status=1
        val call = appUserService.addAppUser(appUser);
        callMethod(call)
    }

    private fun callMethod(call: Call<AppUser>) {
        progressShow()
        call.enqueue(object : Callback<AppUser> {
            override fun onFailure(call: Call<AppUser>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()
                toggleEnaDisable(registerBtn,true);
            }

            override fun onResponse(call: Call<AppUser>, response: Response<AppUser>) {
                if (response.isSuccessful) {
                    val info: AppUser? = response.body();
                    if (info != null) {
                        makeToast("Registered Successfully..goto login to continue",Toast.LENGTH_LONG)
                        resetAll()
                    }
                } else {
                    Log.e(
                        ItemMasterFragment::class.java.simpleName,
                        "Error: ${response.code()} ${response.message()}"

                    )
                    Toast.makeText(applicationContext,"Error occured",Toast.LENGTH_LONG).show()
                }
                progressHide()
                toggleEnaDisable(registerBtn,true);
            }
        })
    }

    fun  resetAll() {
        for (view in this!!.list_of_control!!)
          {
              if(view is EditText ){
                  view.setText("")
              }
              if(view is Spinner ){
                  view.setSelection(0)
              }
          }
        lastname.setText("")

    }

    fun  validateMadatory( view: View): Boolean {
         if(view is EditText ){
          if(!view.text.isEmpty())
              return false;
         }
         return true
    }

    fun  validateEmail( view: View): Boolean {
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if(view is EditText ){
            if(view.text.toString().trim().matches(emailPattern.toRegex()))
                return true;
        }
        return false
    }

    fun  validatePass( view: View): Boolean {
        if(view is EditText ){
            if(view.text.toString().trim().length>=4)
                return true;
        }
        return false
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // use position to know the selected item
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}
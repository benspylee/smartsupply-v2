package com.asuravan.smartsupply.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonActivity
import com.asuravan.smartsupply.activity.ui.useritem.ItemMasterFragment
import com.asuravan.smartsupply.client.RetroRestClient
import com.asuravan.smartsupply.pojo.AppUser
import com.asuravan.smartsupply.service.AppUserService
import com.asuravan.smartsupply.utils.BluebeeUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : CommonActivity() {

    private var appUserService: AppUserService = RetroRestClient<AppUserService>().create(AppUserService::class.java); 
    
    @BindView(R.id.btnLogin)
    lateinit var loginButton:Button;
    @BindView(R.id.email)
    lateinit var email:EditText;
    @BindView(R.id.password)
    lateinit var password:EditText;
    @BindView(R.id.btnLinkToRegisterScreen)
    lateinit var registerButton:Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        activateProgress(this@LoginActivity,true)
        ButterKnife.bind(this);
        registerButton.setOnClickListener{
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
            finish()
        }
        loginButton.setOnClickListener {
            var appuser=AppUser();
            appuser.emailid = email.text.toString()
            appuser.password= password.text.toString()
            logIn(appuser)
        }
    }

    private fun logIn(appuser: AppUser) {
        toggleEnaDisable(loginButton,false)
        progressShow()
        val call = appUserService.logIn(appuser);
        callMethod(call)
    }

    private fun callMethod(call: Call<AppUser>) {
        call.enqueue(object : Callback<AppUser> {
            override fun onFailure(call: Call<AppUser>, t: Throwable) {
                Log.e(ItemMasterFragment::class.java.simpleName, t.message, t)
                progressHide()

                toggleEnaDisable(loginButton,true);
            }

            override fun onResponse(call: Call<AppUser>, response: Response<AppUser>) {
                if (response.isSuccessful) {
                    val info: AppUser? = response.body();
                    if (info != null) {
                      //  toggleEnaDisable(loginButton,true);
                        val editor: SharedPreferences.Editor = sharedPreferences?.edit()!!
                        info.appuserrolecd?.let { editor.putInt("appuserrolecd", it) }
                        editor.putInt("appusercd", info.appusercd!!)
                        editor.putString("emailid",info.emailid.toString())
                        editor.putString("username",info.firstname!!.trim()+" " + info.lastname!!.trim())
                        editor.putString("usersession",BluebeeUtils.GetInstance().convertToString(info))
                        editor.apply();
                        editor.commit();
                        val i = Intent(this@LoginActivity, DashBoardactivity::class.java)
                        startActivity(i)
                        finish()
                    }
                } else {
                    Log.e(
                        ItemMasterFragment::class.java.simpleName,
                        "Error: ${response.code()} ${response.message()}"

                    )
                    if(response.code()==500)
                        makeToast("Check your credentials")
                        else
                        makeToast("Error occurred..")
                }
                progressHide()
                toggleEnaDisable(loginButton,true);
            }
        })
    }
}
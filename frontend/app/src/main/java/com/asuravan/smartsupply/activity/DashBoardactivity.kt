package com.asuravan.smartsupply.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.asuravan.smartsupply.R
import com.asuravan.smartsupply.activity.commons.CommonActivity
import com.asuravan.smartsupply.activity.ui.order.DeilveryTaskFragment
import com.asuravan.smartsupply.pojo.AppPushMessage
import com.asuravan.smartsupply.utils.BluebeeUtils
import com.google.android.material.navigation.NavigationView
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushException
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener
import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushResponseListener
import org.json.JSONException
import org.json.JSONObject

class DashBoardactivity : CommonActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var push // Push client
            : MFPPush? =null;
    private var notificationListener // Notification listener to handle a push sent to the phone
            : MFPPushNotificationListener? = null
    var appuserid =0;
    var appuserrolecd =0;
    lateinit var username:TextView;
    lateinit var useremail:TextView;
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_boardactivity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        BMSClient.getInstance().initialize(this, BMSClient.REGION_UK)

        // Grabs push client sdk instance
        push = MFPPush.getInstance()
        // You can find your App Guid and Client Secret by navigating to the Configure section of your Push dashboard, click Mobile Options (Upper Right Hand Corner)
        // TODO: Please replace <APP_GUID> and <CLIENT_SECRET> with a valid App GUID and Client Secret from the Push dashboard Mobile Options
        push?.initialize(this, "xxx", "xxx")

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
           var  view:View=      navView.getHeaderView(0);
              username=  view.findViewById(R.id.username)
           useremail=  view.findViewById(R.id.useremail)

        val adminRoleScreen =setOf(R.id.nav_delivtask,  R.id.nav_home, R.id.nav_item_master, R.id.nav_slideshow, R.id.nav_vechile,R.id.nav_vendor_search,R.id.nav_userproile,R.id.nav_order)
        val customerRoleScreen =setOf(R.id.nav_home,R.id.nav_vendor_search,R.id.nav_userproile,R.id.nav_order)
        val retailvendorRoleScreen =setOf( R.id.nav_home, R.id.nav_item_master, R.id.nav_slideshow,R.id.nav_userproile,R.id.nav_order)
        val wholesaleRoleScreen =setOf(R.id.nav_home, R.id.nav_item_master,R.id.nav_userproile,R.id.nav_order)
        val transporterRoleScreen =setOf(R.id.nav_delivtask,  R.id.nav_home, R.id.nav_vechile,R.id.nav_userproile)
        // arrayOf("Customer", "Retail Vendor","WholeSale Vendor", "Transporter");
        var switchScreen:Set<Int> = adminRoleScreen;
         appuserid = sharedPreferences?.getInt("appusercd", 0)!!;
         appuserrolecd = sharedPreferences?.getInt("appuserrolecd", 0)!!;

           username.setText(sharedPreferences?.getString("username",""))
            useremail.setText(sharedPreferences?.getString("emailid",""))


        if(appuserrolecd ==1)
            navView.inflateMenu(R.menu.customer_main_drawer)
        else if(appuserrolecd ==2)
            navView.inflateMenu(R.menu.retail_main_drawer)
        else if(appuserrolecd ==3)
            navView.inflateMenu(R.menu.wholesale_main_drawer)
        else if(appuserrolecd ==4)
            navView.inflateMenu(R.menu.transporter_main_drawer)


        appBarConfiguration = AppBarConfiguration(
            switchScreen, drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        notificationListener =
            MFPPushNotificationListener { message ->
                Log.i("FragmentActivity.TAG", "Received a Push Notification: $message")
                runOnUiThread {
                    //  message.payload;
                    showNotification("Job",message.alert);
                }
            }

        registerDevice(null)

        
    }

    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String?, body: String?) {
        val mBuilder =
            NotificationCompat.Builder(
                getApplicationContext(),
                "notify_001"
            )
        val ii = Intent(getApplicationContext(), DeilveryTaskFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, ii, 0)

       val  bodystr:String=BluebeeUtils.GetInstance().b64toString(body);
       val input:AppPushMessage=
           BluebeeUtils.GetInstance().convertToPushMessage(bodystr) as AppPushMessage;

        if(appuserid ==input.appusercd){

            val bigText =
                NotificationCompat.BigTextStyle()
            // bigText.bigText("New Delivery Job Assigned")
            bigText.setBigContentTitle("New Delivery Job Assigned")
            bigText.bigText("Delivery Address:"+input.deliveryaddress.replace(" ",""))
            bigText.setSummaryText("delivery")

            mBuilder.setContentIntent(pendingIntent)
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
            mBuilder.setContentTitle(title)
            mBuilder.setContentText(input.deliveryaddress.substring(0,input.deliveryaddress.length/2)+"...")
            mBuilder.priority = Notification.PRIORITY_MAX
            mBuilder.setStyle(bigText)

            notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "Your_channel_id"
                val channel = NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager!!.createNotificationChannel(channel)
                mBuilder.setChannelId(channelId)
            }

            notificationManager!!.notify(0, mBuilder.build())
        }

    }

    fun registerDevice(view: View?) {

        // Checks for null in case registration has failed previously
        if (push == null) {
            push = MFPPush.getInstance()
        }


        // Make register button unclickable during registration and show registering text
        Log.i("", "Registering for notifications")

        // Creates response listener to handle the response when a device is registered.
        val registrationResponselistener: MFPPushResponseListener<*> =
            object : MFPPushResponseListener<String> {
                override fun onSuccess(response: String) {
                    // Split response and convert to JSON object to display User ID confirmation from the backend
                    val resp =
                        response.split("Text: ".toRegex()).toTypedArray()
                    try {
                        val responseJSON = JSONObject(resp[1])
                        Log.i("", "Device Registered Successfully with USER ID " + responseJSON.getString(
                            "userId")
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    Log.i(
                        "FragmentActivity.TAG",
                        "Successfully registered for push notifications, $response"
                    )
                    // Start listening to notification listener now that registration has succeeded
                    push!!.listen(notificationListener)
                }

                override fun onFailure(exception: MFPPushException) {
                    var errLog = "Error registering for push notifications: "
                    val errMessage = exception.errorMessage
                    val statusCode = exception.statusCode

                    // Set error log based on response code and error message
                    if (statusCode == 401) {
                        errLog += "Cannot authenticate successfully with Bluemix Push instance, ensure your CLIENT SECRET was set correctly."
                    } else if (statusCode == 404 && errMessage.contains("Push GCM Configuration")) {
                        errLog += "Push GCM Configuration does not exist, ensure you have configured GCM Push credentials on your Bluemix Push dashboard correctly."
                    } else if (statusCode == 404 && errMessage.contains("PushApplication")) {
                        errLog += "Cannot find Bluemix Push instance, ensure your APPLICATION ID was set correctly and your phone can successfully connect to the internet."
                    } else if (statusCode >= 500) {
                        errLog += "Bluemix and/or your Push instance seem to be having problems, please try again later."
                    }
                    Log.e("FragmentActivity.TAG", errLog)
                    // make push null since registration failed
                    push = null
                }
            }


        // Attempt to register device using response listener created above
        // Include unique sample user Id instead of Sample UserId in order to send targeted push notifications to specific users
        //TODO : Registartion with UserId
        val userId ="BLUEBEE-USER-000"+sharedPreferences?.getInt("appusercd",0)
        push!!.registerDeviceWithUserId(userId,
            registrationResponselistener as MFPPushResponseListener<String>?
        )

        //TODO: Registartion without UserId
        //push.registerDevice(registrationResponselistener);
    }

    class MyResponseListener : ResponseListener {
        override fun onSuccess(response: Response?) {
            if (response != null) {
                Log.i("MyApp", "Response status: " + response.getStatus())
                Log.i("MyApp", "Response headers: " + response.getHeaders())
                Log.i("MyApp", "Response body: " + response.getResponseText())
            }
        }

        override fun onFailure(
            response: Response?,
            t: Throwable?,
            extendedInfo: JSONObject?
        ) {
            if (response != null) {
                Log.i("MyApp", "Response status: " + response.getStatus())
                Log.i("MyApp", "Response body: " + response.getResponseText())
            }
            if (t != null && t.message != null) {
                Log.i("MyApp", "Error: " + t.message)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dash_boardactivity, menu)
        if(appuserrolecd !=1)
        menu.findItem(R.id.cart).setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cart ->{
                navigateToFrag()
            }
            R.id.action_logout ->{
                val i = Intent(this@DashBoardactivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToFrag() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.actionhometocart)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

package com.lambdaschool.sprint2_challenge

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lambdaschool.sprint2_challenge.ItemsRepo.Companion.itemsList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ItemsRepo.createItemsList()

        list_view.setHasFixedSize(true)
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ItemsListAdapter(itemsList)
        list_view.layoutManager = manager
        list_view.adapter = adapter

        send_list_button.setOnClickListener {


            //getList fun needs to be here so it re-creates the string each time you click send list, otherwise it just keeps a running list
            fun getList(): String {
                var shoppingListString = ""
                for (items in ItemsRepo.itemsList) {
                    if (items.isPurchase == true) shoppingListString += "${items.name}, "
                }
                return shoppingListString
            }



            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Here is my shopping list: ${getList()}")
                type = "text/plain"
            }
            startActivity(intent)

            val channelId = "$packageName.simplechannel"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {                    //this is just to create a channel for versions of android above 28. not actually creating the notification
                val name = "Shopping List Notification Channel"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val description = "Channel to send shopping list"

                val channel = NotificationChannel(channelId, name, importance)
                channel.description = description

                notificationManager.createNotificationChannel(channel)
            }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)       //this is what is actually creating the notification
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setSmallIcon(android.R.drawable.ic_input_get)
                    .setContentTitle("Confirmation")
                    .setContentText("Your Shopping List Has Been Sent")
                    .setAutoCancel(true)
            notificationManager.notify(1, notificationBuilder.build())
        }
    }
}

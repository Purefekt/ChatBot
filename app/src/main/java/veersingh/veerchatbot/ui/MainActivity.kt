package veersingh.veerchatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import veersingh.veerchatbot.R
import veersingh.veerchatbot.data.Message
import veersingh.veerchatbot.utils.Constants.RECEIVE_ID
import veersingh.veerchatbot.utils.Constants.SEND_ID
import veersingh.veerchatbot.utils.BotResponse
import veersingh.veerchatbot.utils.Constants.OPEN_WEBPAGE
import veersingh.veerchatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import veersingh.veerchatbot.utils.Constants.OPEN_G
import veersingh.veerchatbot.utils.Constants.OPEN_PHD
import veersingh.veerchatbot.utils.Constants.OPEN_UG


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        customMessage("Hello! Today you're speaking with GenNextron-1000, please type one of the given options\n--Undergraduate programs\n--Graduate programs\n--PhD programs\n--Website")

    }
    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_WEBPAGE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.gennexteducation.com/")
                        startActivity(site)
                    }
                    OPEN_UG -> {
                        val open_ug = Intent(this@MainActivity,Undergraduate::class.java )
                        startActivity(open_ug)
                    }
                    OPEN_G -> {
                        val open_g = Intent(this@MainActivity,Graduate::class.java )
                        startActivity(open_g)
                    }
                    OPEN_PHD -> {
                        val open_phd = Intent(this@MainActivity,PhD::class.java )
                        startActivity(open_phd)
                    }
                }
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}


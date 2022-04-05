package com.example.newstelly

import android.content.Context
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.*

//singleton matlab 1 aisi class jiska bas ek hi instance ho sakta hai
class MySingleton constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: MySingleton? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MySingleton(context).also {
                    INSTANCE = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy { //making this private so no one else can call this
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>) { //we are calling requestQueue () fun using this function
        requestQueue.add(req)
    }
}


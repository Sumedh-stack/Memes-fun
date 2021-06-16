package com.example.android.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var currentimageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun loadmeme(){
        progress_bar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
       currentimageurl= "https://meme-api.herokuapp.com/gimme "

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, currentimageurl,null,
            Response.Listener<JSONObject> { response ->
val url=response.getString("url")
                Glide.with(this).load(url).listener(object :RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                      progress_bar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility=View.GONE
                        return false
                    }




                }).into(imageView)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Someethin went wrong",Toast.LENGTH_SHORT).show()
            })

        singletonclass.getInstance(this).addToRequestQueue(jsonObjectRequest)
        queue.add(jsonObjectRequest)
    }
    fun sharememe(view: View) {

        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout tjis meme i got from  $currentimageurl")
        val chooser =Intent.createChooser(intent,"Share this meme using")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmeme()
    }
}
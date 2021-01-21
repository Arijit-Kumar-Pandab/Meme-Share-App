package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.MySingleton.MySingleton.Companion.getInstance
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var memeUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
        next_button.setOnClickListener {
            loadMeme()
        }
        share_button.setOnClickListener {
            val intent=Intent(Intent(Intent.ACTION_SEND))
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"Check out this cool meme $memeUrl")
            val chooser=Intent.createChooser(intent,"Share this meme using...")
            startActivity(chooser)
        }
    }

    private fun loadMeme() {
        progressBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        //val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    memeUrl = response.getString("url")
                    Glide.with(this).load(memeUrl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            progressBar.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            progressBar.visibility=View.GONE
                            return false
                        }
                    }).into(meme_image)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                })

        // Add the request to the RequestQueue.
        getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
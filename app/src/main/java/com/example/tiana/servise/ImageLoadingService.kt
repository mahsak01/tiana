package com.example.tiana.servise
import android.widget.ImageView


interface ImageLoadingService {
    fun  load(imageView: ImageView, imageUrl:String)
}
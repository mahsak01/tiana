package com.example.tiana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiana.data.Model.Banner
import com.example.tiana.servise.ImageLoadingService
import com.example.tiana.view.TianaImageView
import org.koin.android.ext.android.inject


class BannerFragment: TianaFragment() {
    val imageLoadingService: ImageLoadingService by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView= inflater.inflate(R.layout.fragment_main_banner,container,false) as TianaImageView
        val banner= requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA)?:throw IllegalStateException("image not null")
        imageLoadingService.load(imageView,banner.image)
        return imageView
    }

    companion object{
        fun newInstance(banner: Banner):BannerFragment{
            val bannerFragment=BannerFragment()
            bannerFragment.arguments= Bundle().apply {
                putParcelable(EXTRA_KEY_DATA,banner)
            }
            return bannerFragment
        }
    }
}
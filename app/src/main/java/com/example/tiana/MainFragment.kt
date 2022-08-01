package com.example.tiana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiana.common.convertDpToPixel
import com.example.tiana.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : TianaFragment() {
    private lateinit var binding: FragmentMainBinding
    val viewModel: MainViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            if (it)
                this.binding.loadingView.visibility = View.VISIBLE
            else
                this.binding.loadingView.visibility = View.GONE
        }

        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            this.binding.activityMainBannerSliderVp.adapter = bannerSliderAdapter
            this.binding.activityMainBannerSliderVp.post {
                val height = (((this.binding.activityMainBannerSliderVp.width - convertDpToPixel(
                    32f,
                    requireContext()
                )) * 173) / 328).toInt()
                val layoutParams = this.binding.activityMainBannerSliderVp.layoutParams
                layoutParams.height = height
                this.binding.activityMainBannerSliderVp.layoutParams = layoutParams
                this.binding.activityMainBannerSliderDi.setViewPager2(this.binding.activityMainBannerSliderVp)
            }
        }


    }


}
package com.example.tianaserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.tianaserver.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var bottomSheetDialog = ServerBottomSheetFragment()

        this.binding.activityMainSettingBtn.setOnClickListener {

            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")

        }

    }
}
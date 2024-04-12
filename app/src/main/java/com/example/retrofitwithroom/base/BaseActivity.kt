package com.example.retrofitwithroom.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.retrofitwithroom.model.AppCompactImplMethod

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    AppCompactImplMethod {

    lateinit var binding: T



    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        binding = DataBindingUtil.setContentView(this, getContentView())
        initVariable()
        loadData()


    }

    override fun onStart() {
        super.onStart()
    }

    abstract fun getContentView(): Int

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Intent.start() {
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(this)
    }


}
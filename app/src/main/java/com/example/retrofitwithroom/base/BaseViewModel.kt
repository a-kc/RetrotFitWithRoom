package com.example.demoapplication.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.retrofitwithroom.utility.SingleLiveEvent

open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val messageLiveData = SingleLiveEvent<String>()

    fun showMessage(message: String) {
        messageLiveData.value = message
    }

}
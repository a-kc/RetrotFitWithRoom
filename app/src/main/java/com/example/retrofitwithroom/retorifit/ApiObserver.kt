package com.example.retrofitwithroom.retorifit

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.retrofitwithroom.model.ResponseData
import com.example.retrofitwithroom.model.Status

abstract class ApiObserver<T>(val context: Context) : Observer<ResponseData<T>> {

    override fun onChanged(responseData: ResponseData<T>) {

        when (responseData.resource) {
            Status.LOADING -> {
                onProgress(true)
            }

            Status.SUCCESS -> {
                onProgress(false)
                onSuccess(responseData)
            }

            Status.ERROR -> {
                onProgress(false)
                onError(responseData)
            }
            else ->{

            }
        }
    }

    abstract fun onSuccess(responseData: ResponseData<T>)

    open fun onProgress(isProgress: Boolean) {

    }

    open fun onError(data: ResponseData<T>) {
        Toast.makeText(context, data.message.toString(), Toast.LENGTH_SHORT).show()
    }
}
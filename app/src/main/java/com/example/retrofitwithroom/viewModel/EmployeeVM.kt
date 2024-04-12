package com.example.retrofitwithroom.viewModel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.demoapplication.base.BaseViewModel
import com.example.retrofitwithroom.model.Employee
import com.example.retrofitwithroom.model.EmployeeData
import com.example.retrofitwithroom.model.ResponseData
import com.example.retrofitwithroom.repo.EmployeeRepo
import com.example.retrofitwithroom.utility.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeVM @Inject constructor(val employeeRepo: EmployeeRepo, val app: Application) :
    BaseViewModel(app) {
    var employeeListFromApi: SingleLiveEvent<ResponseData<EmployeeData>> = SingleLiveEvent()

    fun insert(employee: Employee) =
        viewModelScope.launch(Dispatchers.Default) {
            employeeRepo.insert(employee)
        }

    suspend fun allEmployeeList() = employeeRepo.allEmployeeList()

    suspend fun deleteEmployeeData(id : Long) = employeeRepo.deleteEmployeeData(id)

    fun callCategoryApi() {
        viewModelScope.launch {
            employeeRepo.getEmployeeList().onStart {
                emit(ResponseData.loading(emptyList()))
            }.collect {
                employeeListFromApi.value = it
            }
        }
    }
}
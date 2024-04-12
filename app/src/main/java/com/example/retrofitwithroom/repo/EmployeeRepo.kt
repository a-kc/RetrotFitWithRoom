package com.example.retrofitwithroom.repo

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.retrofitwithroom.dao.EmployeeDao
import com.example.retrofitwithroom.model.Employee
import com.example.retrofitwithroom.model.EmployeeData
import com.example.retrofitwithroom.model.ResponseData
import com.example.retrofitwithroom.retorifit.ApiService
import com.example.retrofitwithroom.retorifit.ResponseManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ViewModelScoped
class EmployeeRepo @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: ApiService,
    private val employeeDao: EmployeeDao
) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun allEmployeeList() = employeeDao.getAllEmployeeData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteEmployeeData(id : Long) = employeeDao.deleteEmployeeData(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insert(employee: Employee): Long {
        return employeeDao.insertEmployee(employee)
    }



    suspend fun getEmployeeList(): Flow<ResponseData<EmployeeData>> {
        return flow {
            emit(ResponseManager.safeApiCall {
                apiService.getEmployeeList()
            })
        }.flowOn(Dispatchers.IO)
    }
}
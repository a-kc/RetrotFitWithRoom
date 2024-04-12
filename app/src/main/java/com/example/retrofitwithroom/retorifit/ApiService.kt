package com.example.retrofitwithroom.retorifit

import com.example.retrofitwithroom.model.EmployeeData
import com.example.retrofitwithroom.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET


const val GET_EMPLOYEE = "v1/employees"

interface ApiService {

    @GET(GET_EMPLOYEE)
    suspend fun getEmployeeList():Response<ResponseData<EmployeeData>>
}
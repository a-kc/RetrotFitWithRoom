package com.example.retrofitwithroom.model

import com.google.gson.annotations.SerializedName

data class EmployeeData(
    @SerializedName("id")
    val id : Int?,
    @SerializedName("employee_age")
    val employeeAge : Int?,
    @SerializedName("employee_name")
    val employeeName : String?,
    @SerializedName("employee_salary")
    val employeeSalary : Int?,
    @SerializedName("profile_image")
    val profileImage : String?

)

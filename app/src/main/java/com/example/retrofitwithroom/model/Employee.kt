package com.example.retrofitwithroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "empId")
    var empId: Long,
    @ColumnInfo(name = "employee_name")
    var employeeName: String?,
    @ColumnInfo(name = "employee_salary")
    var employeeSalary: Int?,
)

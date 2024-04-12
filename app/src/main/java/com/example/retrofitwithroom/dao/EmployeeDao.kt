package com.example.retrofitwithroom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitwithroom.model.Employee

@Dao
interface EmployeeDao {

    @Insert
    fun insertEmployee(employee: Employee) : Long

    @Query("SELECT * FROM employee")
    suspend fun getAllEmployeeData(): List<Employee>

    @Query("DELETE FROM employee WHERE empId = :id")
    suspend fun deleteEmployeeData(id : Long)
}
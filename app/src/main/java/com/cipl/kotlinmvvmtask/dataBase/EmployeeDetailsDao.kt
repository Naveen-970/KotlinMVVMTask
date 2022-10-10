package com.cipl.kotlinmvvmtask.dataBase

import androidx.room.*
import com.cipl.kotlinmvvmtask.model.EmployeeDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDetailsDao {
    @Insert
    suspend fun insertEmployeeDetails(employeeDetails: EmployeeDetails) : Long

    @Update
    suspend fun updateEmployeeDetails(employeeDetails: EmployeeDetails) : Int

    @Delete
    suspend fun deleteEmployeeDetails(employeeDetails: EmployeeDetails) : Int

    @Query("DELETE FROM employee_details_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM employee_details_table")
    fun getAllEmployees(): Flow<List<EmployeeDetails>>

}
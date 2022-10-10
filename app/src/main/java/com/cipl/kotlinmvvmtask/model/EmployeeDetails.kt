package com.cipl.kotlinmvvmtask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_details_table")
data class EmployeeDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "emp_id")
    var empId : Int,
    @ColumnInfo(name = "emp_name")
    var empName : String,
    @ColumnInfo(name = "emp_email")
    var empEmail : String
)

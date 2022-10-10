package com.cipl.kotlinmvvmtask.dataBase

import com.cipl.kotlinmvvmtask.model.EmployeeDetails

class EmployeeRepository(private val employeeDetailsDao: EmployeeDetailsDao) {
    val employees = employeeDetailsDao.getAllEmployees()

    suspend fun insertEmployees(employeeDetails: EmployeeDetails):Long{
        return employeeDetailsDao.insertEmployeeDetails(employeeDetails)
    }

    suspend fun updateEmployees(employeeDetails: EmployeeDetails):Int{
        return employeeDetailsDao.updateEmployeeDetails(employeeDetails)
    }

    suspend fun deleteEmployees(employeeDetails: EmployeeDetails):Int{
        return employeeDetailsDao.deleteEmployeeDetails(employeeDetails)
    }

    suspend fun deleteAll():Int{
        return employeeDetailsDao.deleteAll()
    }
}
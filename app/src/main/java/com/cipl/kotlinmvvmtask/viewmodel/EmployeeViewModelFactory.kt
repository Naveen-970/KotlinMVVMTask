package com.cipl.kotlinmvvmtask.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.cipl.kotlinmvvmtask.dataBase.EmployeeRepository

class EmployeeViewModelFactory(private val employeeRepository: EmployeeRepository,
                                private val context: Context): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EmployeeViewModel::class.java)){
            return EmployeeViewModel(employeeRepository,context) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}
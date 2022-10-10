package com.cipl.kotlinmvvmtask.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.cipl.kotlinmvvmtask.R
import com.cipl.kotlinmvvmtask.dataBase.EmployeeRepository
import com.cipl.kotlinmvvmtask.model.EmployeeDetails
import com.cipl.kotlinmvvmtask.utils.Event
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepository: EmployeeRepository,
                        private val context: Context) : ViewModel() {

    private val TAG = "EmployeeViewModel"
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()
    val getSavedEmployeesData = MutableLiveData<String>()
    private lateinit var employeeToUpdateOrDelete: EmployeeDetails
    private var isUpdateOrDelete = false


    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = context.getString(R.string.txt_save)
        clearAllOrDeleteButtonText.value = context.getString(R.string.txt_clear_all)
        getSavedEmployeesData.value = context.getString(R.string.txt_get_data)
    }

    fun saveOrUpdate() {
        if (inputName.value == null || inputName.value == "") {
            statusMessage.value = Event("Please enter Employee name")
        } else if (inputEmail.value == null || inputEmail.value == "") {
            statusMessage.value = Event("Please enter Employee email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            if (isUpdateOrDelete){
                employeeToUpdateOrDelete.empName = inputName.value!!
                employeeToUpdateOrDelete.empEmail = inputEmail.value!!
                updateEmployee(employeeToUpdateOrDelete)
            }else{
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertEmployee(EmployeeDetails(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }

    private fun insertEmployee(employeeDetails: EmployeeDetails) = viewModelScope.launch {
        val result = employeeRepository.insertEmployees(employeeDetails)
        if (result > -1){
            statusMessage.value = Event("Successfully Inserted")
        }else{
            statusMessage.value = Event("Error Occurred")
        }
        Log.e(TAG,"Result of Insert: $result")
    }

    private fun updateEmployee(employeeDetails: EmployeeDetails) = viewModelScope.launch {
        val noOfRows = employeeRepository.updateEmployees(employeeDetails)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedEmployees() = liveData {
        employeeRepository.employees.collect {
            emit(it)
        }
    }

    private fun deleteEmployee(employeeDetails: EmployeeDetails) = viewModelScope.launch {
        val noOfRowsDeleted = employeeRepository.deleteEmployees(employeeDetails)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = context.getString(R.string.txt_save)
            clearAllOrDeleteButtonText.value = context.getString(R.string.txt_clear_all)
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }


    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = employeeRepository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Employees Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteEmployee(employeeToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun initUpdateAndDelete(employeeDetails: EmployeeDetails) {
        inputName.value = employeeDetails.empName
        inputEmail.value = employeeDetails.empEmail
        isUpdateOrDelete = true
        employeeToUpdateOrDelete = employeeDetails
        saveOrUpdateButtonText.value = context.getString(R.string.txt_update)
        clearAllOrDeleteButtonText.value = context.getString(R.string.txt_delete)
    }

}
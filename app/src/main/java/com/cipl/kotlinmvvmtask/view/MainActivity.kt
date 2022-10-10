package com.cipl.kotlinmvvmtask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cipl.kotlinmvvmtask.R
import com.cipl.kotlinmvvmtask.adapters.EmployeeAdapter
import com.cipl.kotlinmvvmtask.dataBase.EmployeeRepository
import com.cipl.kotlinmvvmtask.dataBase.EmployeesDataBase
import com.cipl.kotlinmvvmtask.databinding.ActivityMainBinding
import com.cipl.kotlinmvvmtask.model.EmployeeDetails
import com.cipl.kotlinmvvmtask.utils.CommonUtils
import com.cipl.kotlinmvvmtask.viewmodel.EmployeeViewModel
import com.cipl.kotlinmvvmtask.viewmodel.EmployeeViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = CommonUtils.getTagName(this)
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EmployeeViewModel
    private lateinit var adapter: EmployeeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = EmployeesDataBase.getDbInstance(this).employeeDetailsDao
        val repository = EmployeeRepository(dao)
        val factory = EmployeeViewModelFactory(repository,this)
        viewModel = ViewModelProvider(this,factory).get(EmployeeViewModel::class.java)
        binding.employeeViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let { CommonUtils.displayToastMessage(this,it) }
            Log.e(TAG,"Status Message $it")
        })
        initRecyclerView()
    }


    private fun initRecyclerView(){
        binding.rvEmployees.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter { selectedItem: EmployeeDetails -> listItemClicked(selectedItem) }
        binding.rvEmployees.adapter = adapter
        displayEmployeesList()
    }

    private fun displayEmployeesList(){
        viewModel.getSavedEmployees().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            Log.e(TAG,"Recycler List Refreshed: $it")
        })
    }

    private fun listItemClicked(employeeDetails: EmployeeDetails){
        //Toast.makeText(this,"selected name is ${employeeDetails.empName}",Toast.LENGTH_LONG).show()
        viewModel.initUpdateAndDelete(employeeDetails)
    }

}
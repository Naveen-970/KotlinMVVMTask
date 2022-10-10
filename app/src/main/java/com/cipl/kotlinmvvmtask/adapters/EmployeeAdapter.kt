package com.cipl.kotlinmvvmtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cipl.kotlinmvvmtask.R
import com.cipl.kotlinmvvmtask.databinding.ActivityMainBinding
import com.cipl.kotlinmvvmtask.databinding.LayoutAdapterBinding
import com.cipl.kotlinmvvmtask.generated.callback.OnClickListener
import com.cipl.kotlinmvvmtask.model.EmployeeDetails

class EmployeeAdapter(private val clickListener: (EmployeeDetails) -> Unit):
    RecyclerView.Adapter<EmployeeAdapter.MyViewHolder>(){

    private val employeesList = ArrayList<EmployeeDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //val binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_adapter, parent, false)
        //val binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_adapter,parent,false)
        val binding = LayoutAdapterBinding.inflate(layoutInflater,parent,false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(employeesList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return employeesList.size
    }

    fun setList(employeeDetails: List<EmployeeDetails>) {
        employeesList.clear()
        employeesList.addAll(employeeDetails)
    }

    class MyViewHolder(private val binding:LayoutAdapterBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(employeeDetails: EmployeeDetails, clickListener: (EmployeeDetails) -> Unit) {
            binding.txtEmpId.text = "Employee Id: "+employeeDetails.empId.toString()
            binding.txtEmpName.text = "Name: "+employeeDetails.empName
            binding.txtEmpEmail.text = "Email: "+employeeDetails.empEmail
            binding.cvEmployeeCard.setOnClickListener {
                clickListener(employeeDetails)
            }
        }
    }
}
package com.cipl.kotlinmvvmtask.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cipl.kotlinmvvmtask.model.EmployeeDetails


@Database(entities = [EmployeeDetails::class], version = 1)
abstract class EmployeesDataBase : RoomDatabase(){
    abstract val employeeDetailsDao : EmployeeDetailsDao

    /*companion object{
        @Volatile
        private var INSTANCE : EmployeesDataBase ?= null
        fun getDbInstance(context: Context):EmployeesDataBase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeesDataBase::class.java,
                        "employee_details_table.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return instance
            }
        }
    }*/



    //The below method is nothing but a singleton object for room data base
    companion object {
        @Volatile
        private var instance: EmployeesDataBase? = null
        fun getDbInstance(context: Context): EmployeesDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,EmployeesDataBase::class.java,
                    "kotlin_training.db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // <-- For avoid migrating to new version just need to add this
                    .build()
            }
            return instance as EmployeesDataBase
        }
    }

}
package com.cipl.kotlinmvvmtask.utils

import android.content.Context
import android.widget.Toast

object CommonUtils {
    fun displayToastMessage(context: Context, message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    fun getTagName(context: Context):String{
        val tag = context.javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }
}
package com.cipl.kotlinmvvmtask.utils

open class Event<out T>(private val content:T) {
    var hasHandled = false
    private set

    fun getContentIfNotHandled():T?{
        return if (hasHandled){
            null
        }else{
            hasHandled = true
            content
        }
    }

    fun peekContent():T = content

}
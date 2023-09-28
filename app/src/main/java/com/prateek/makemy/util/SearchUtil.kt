package com.prateek.makemy.util

import com.prateek.makemy.models.ServiceItem

object SearchUtil {

    fun searchServices(text: String, list: List<ServiceItem>) : List<ServiceItem>{
        val lowercaseQuery = text.toLowerCase()
        return list.filter { serviceItem ->
            serviceItem.serviceName.toLowerCase().contains(lowercaseQuery)
        }
    }


}
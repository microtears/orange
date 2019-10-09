package com.microtears.orange.livedata_transformer_example.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microtears.orange.livedata.transformer.Transform
import com.microtears.orange.livedata.transformer.collect
import com.microtears.orange.livedata.transformer.flatMap
import com.microtears.orange.livedata.transformer.map
import com.microtears.orange.livedata_transformer_example.fetchRepositoryInfo
import com.microtears.orange.livedata_transformer_example.fetchUserInfo
import com.microtears.orange.livedata_transformer_example.graphql.UserQuery


class HomeViewModel : ViewModel() {
    private val userName = MutableLiveData<String>()
    val userInfo = userName.flatMap {
        fetchUserInfo(it)
    }.map {
        it.user!!
    }
    val userRepositories = userInfo.map {
        if (it.repositories.nodes != null)
            it.repositories.nodes.filterNotNull().map { it.name }
        else
            emptyList()
    }.flatMap {
        Transform.from(it).flatMap { repoName ->
            fetchRepositoryInfo(userName.value!!, repoName)
        }
    }.collect()

    fun updateInfo(userName: String) {
        this.userName.value = userName
    }
}

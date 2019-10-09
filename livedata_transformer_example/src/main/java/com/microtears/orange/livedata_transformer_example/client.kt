package com.microtears.orange.livedata_transformer_example

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.microtears.orange.livedata_transformer_example.graphql.RepositoryQuery
import com.microtears.orange.livedata_transformer_example.graphql.UserQuery
import okhttp3.OkHttpClient
import type.CustomType

private val okHttpClient = OkHttpClient.Builder()
    .authenticator { _, response ->
        response.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer 3f039204bbd220b091043239b94506c9c42ab318")
            .build()
    }
    .build()

val stringTypeAdapter = object : CustomTypeAdapter<String> {
    override fun encode(value: String): CustomTypeValue<*> {
        return CustomTypeValue.fromRawValue(value)
    }

    override fun decode(value: CustomTypeValue<*>): String {
        return value.value as String
    }
}

private val apolloClient = ApolloClient.builder()
    .okHttpClient(okHttpClient)
    .serverUrl("https://api.github.com/graphql")
    .addCustomTypeAdapter(CustomType.ID, stringTypeAdapter)
    .addCustomTypeAdapter(CustomType.URI, stringTypeAdapter)
    .addCustomTypeAdapter(CustomType.DATETIME, stringTypeAdapter)
    .build()

fun fetchUserInfo(username: String): LiveData<UserQuery.Data> {
    val watcher = apolloClient.query(UserQuery(username)).watcher()
    return LivedataApollo.from(watcher)
}

fun fetchRepositoryInfo(ownerId: String, repoName: String): LiveData<RepositoryQuery.Data> {
    val watcher = apolloClient.query(RepositoryQuery(ownerId, repoName)).watcher()
    return LivedataApollo.from(watcher)
}
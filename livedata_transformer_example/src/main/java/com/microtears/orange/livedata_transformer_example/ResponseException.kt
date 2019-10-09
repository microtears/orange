package com.microtears.orange.livedata_transformer_example

import com.apollographql.apollo.api.Error

class ResponseException(val errors: List<Error>) : Exception()
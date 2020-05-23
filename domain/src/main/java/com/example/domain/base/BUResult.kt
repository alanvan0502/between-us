/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.domain.base

import com.example.domain.base.BUResult.Success

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class BUResult<out R> {

    data class Success<out T>(val data: T? = null) : BUResult<T>()
    data class Error(val throwable: Throwable) : BUResult<Nothing>()
    object Loading : BUResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$throwable]"
            Loading -> "Loading"
        }
    }
}

/**
 * [Success.data] if [BUResult] is of type [Success]
 */
fun <T> BUResult<T>.successOr(fallback: T): T {
    return (this as? Success<T>)?.data ?: fallback
}

val <T> BUResult<T>.data: T?
    get() = (this as? Success)?.data

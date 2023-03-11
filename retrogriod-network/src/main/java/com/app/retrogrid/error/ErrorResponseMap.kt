package com.app.retrogrid.error

import kotlin.reflect.KClass

/**
 * Add this implementation on top of your repository to map the API error data
 * Eg:-
 *
 * interface RetrogridService {
 *   @GET(value = "url")
 *   @ErrorResponseMap(errorClass = NewsErrorResponse::class)
 *  suspend fun fetchNewsOverview():RetrogridResponse<NewsOverview>
 *   }
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ErrorResponseMap(val errorClass: KClass<*>)

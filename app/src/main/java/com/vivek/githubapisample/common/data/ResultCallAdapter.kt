package com.vivek.githubapisample.common.data

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.net.ssl.HttpsURLConnection

/**
 * Custom Retrofit CallAdapter to handle the API calls errors and success states, by this we can
 * get response in form of [Result]
 *
 * For more details about it check here
 * [Medium](https://proandroiddev.com/create-retrofit-calladapter-for-coroutines-to-handle-response-as-states-c102440de37a)
 */
class ResultCallAdapter<R>(
    private val responseType: Type,
    private val errorBodyConverter: Converter<ResponseBody, ErrorResponse>
) : CallAdapter<R, Call<Result<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Call<Result<R>> {
        return ResultCall(call, errorBodyConverter)
    }

    private class ResultCall<R>(
        private val delegate: Call<R>,
        private val errorConverter: Converter<ResponseBody, ErrorResponse>
    ) : Call<Result<R>> {

        override fun enqueue(callback: Callback<Result<R>>) {
            delegate.enqueue(object : Callback<R> {
                override fun onResponse(call: Call<R>, response: Response<R>) {

                    val body = response.body()
                    val code = response.code()
                    val error = response.errorBody()

                    val result = if (response.isSuccessful) {
                        body?.let { Result.success<R>(body) } ?: AppException.EmptyBody().toResult()
                    } else if (code == HttpsURLConnection.HTTP_NOT_FOUND) {
                        AppException.NotFound().toResult()
                    } else {
                        val errorBody = when {
                            error == null -> null
                            error.contentLength() == 0L -> null
                            else -> try {
                                errorConverter.convert(error)?.message
                            } catch (ex: Exception) {
                                ex.message
                            }
                        }
                        errorBody?.let {
                            AppException.ApiError(errorBody, code).toResult()
                        } ?: AppException.ApiError(null, code = code).toResult()
                    }

                    callback.onResponse(this@ResultCall, Response.success(result))
                }

                override fun onFailure(call: Call<R>, t: Throwable) {

                    val networkResponse = when (t) {
                        is IOException -> AppException.NetworkError(t)
                        else -> AppException.Error(t.message, t)
                    }

                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(networkResponse))
                    )
                }
            })
        }

        // Other methods delegate to the original Call
        override fun execute(): Response<Result<R>> {
            throw UnsupportedOperationException("execute not supported")
        }

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun cancel() = delegate.cancel()

        override fun isCanceled() = delegate.isCanceled

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()

        override fun clone(): Call<Result<R>> {
            return ResultCall(delegate.clone(), errorConverter)
        }
    }
}

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<Result<<Foo>> or Call<Result<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != Result::class.java) {
            return null
        }

        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as Result<Foo> or Result<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<ErrorResponse>(
                null,
                ErrorResponse::class.java,
                annotations
            )

        return ResultCallAdapter<Any>(successBodyType, errorBodyConverter)
    }
}

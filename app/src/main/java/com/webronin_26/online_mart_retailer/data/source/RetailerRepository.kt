package com.webronin_26.online_mart_retailer.data.source

import com.webronin_26.online_mart_retailer.data.remote.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.2.2:1323"

class RetailerRepository {

    private fun getRetrofitInstance(): RequestInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestInterface::class.java)
    }

    suspend fun login(account: String, password: String): Result<Response.LoginResponse> = withContext(Dispatchers.IO) {
            try {
                val loginRequestBody = RequestInterface.LoginRequestBody(account, password)
                val response = getRetrofitInstance().loginRequest(loginRequestBody)

                if (response.code == STATUS_SUCCESS) {
                    return@withContext Result.Success(response)
                } else {
                    return@withContext Result.Error(Exception("request error"))
                }
            } catch (e: Exception) {
                return@withContext Result.ConnectException(Exception(e.toString()))
            }
        }

    suspend fun logout(token: String): Result<Response.LogoutResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().logoutRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun addProduct(token: String, name: String, imageUrl: String, summary: String,
                           information: String, price: Float, number: Int, maxBuy: Int) :
            Result<Response.AddProductResponse> = withContext(Dispatchers.IO) {
        try {

            val addProductRequestBody = RequestInterface.
                AddProductRequestBody(name, imageUrl, summary, information, price, number, maxBuy)
            val response = getRetrofitInstance().addProductRequest(token, addProductRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else if (response.code == STATUS_CREATE_POST_FAILED_CREATE_RECORD_NAME) {
                return@withContext Result.CreateRecordNameError(Exception("create product name error"))
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun productList(token: String): Result<Response.ProductListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().productListRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun productQuery(token: String, productId: Int): Result<Response.ProductResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().productRequest(token, productId)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun orderList(token: String): Result<Response.OrderListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().orderListRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun orderQuery(token: String, orderId: Int): Result<Response.OrderResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().orderRequest(token, orderId)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun updateProduct(token: String, productId: Int, name: String, imageUrl: String, summary: String,
                              information: String, price: Float, number: Int, maxBuy: Int):
            Result<Response.UpdateProductResponse> = withContext(Dispatchers.IO) {
        try {

            val updateProductRequestBody =
                RequestInterface.UpdateProductRequestBody(name, imageUrl, summary, information, price, number, maxBuy)
            val response = getRetrofitInstance().updateProductRequest(token, productId, updateProductRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun profileQuery(token: String, retailerId: Int): Result<Response.ProfileResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().profileRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    suspend fun updateProfile(token: String,name: String, responsiblePerson: String, invoice: String,
                              remittanceAccount: String, officePhone: String, personalPhone: String,
                              officeAddress: String, correspondenceAddress: String, deliveryFee: Float):
            Result<Response.UpdateProfileResponse> = withContext(Dispatchers.IO) {
        try {

            val updateProfileRequestBody = RequestInterface.UpdateProfileRequestBody(
                    name, responsiblePerson, invoice, remittanceAccount, officePhone, personalPhone,
                    officeAddress, correspondenceAddress, deliveryFee)
            val response = getRetrofitInstance().updateProfileRequest(token, updateProfileRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }
}
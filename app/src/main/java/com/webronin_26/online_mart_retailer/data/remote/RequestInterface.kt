package com.webronin_26.online_mart_retailer.data.remote

import retrofit2.http.*

interface RequestInterface {

    @Headers(
        "Cache-Control: max-age=0",
        "Upgrade-Insecure-Requests: 1",
        "user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding: gzip, deflate, br",
        "Accept-Language: zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,zh-CN;q=0.5,lb;q=0.4")

    @POST("/login/retailer")
    suspend fun loginRequest(@Body loginRequestBody: LoginRequestBody): Response.LoginResponse

    data class LoginRequestBody (var account: String, val password: String)

    @DELETE("/retailer/logout")
    suspend fun logoutRequest(@Header("Authorization") token: String): Response.LogoutResponse

    @POST("/retailer/product")
    suspend fun addProductRequest(@Header("Authorization") token: String,
                                  @Body addProductRequestBody: AddProductRequestBody): Response.AddProductResponse

    data class AddProductRequestBody (var name: String,
                                      val image_url: String,
                                      var summary: String,
                                      val information: String,
                                      var price: Float,
                                      val number: Int,
                                      var max_buy: Int)

    @GET("/retailer/product")
    suspend fun productListRequest(@Header("Authorization") token: String): Response.ProductListResponse

    @GET("/retailer/product/{product_id}")
    suspend fun productRequest(@Header("Authorization") token: String,
                               @Path("product_id") productId: Int): Response.ProductResponse

    @PATCH("/retailer/product/{product_id}")
    suspend fun updateProductRequest(@Header("Authorization") token: String,
                                     @Path("product_id") productId: Int,
                                     @Body updateProductRequestBody: UpdateProductRequestBody): Response.UpdateProductResponse

    data class UpdateProductRequestBody (var name: String,
                                         val image_url: String,
                                         var summary: String,
                                         val information: String,
                                         var price: Float,
                                         val number: Int,
                                         var max_buy: Int)

    @GET("/retailer/order")
    suspend fun orderListRequest(@Header("Authorization") token: String): Response.OrderListResponse

    @GET("/retailer/order/{order_id}")
    suspend fun orderRequest(@Header("Authorization") token: String,
                             @Path("order_id") orderId: Int): Response.OrderResponse

    @GET("/retailer/company/{company_id}")
    suspend fun profileRequest(@Header("Authorization") token: String): Response.ProfileResponse

    @PATCH("/retailer/company")
    suspend fun updateProfileRequest(@Header("Authorization") token: String,
                                     @Body updateProfileRequestBody: UpdateProfileRequestBody): Response.UpdateProfileResponse

    data class UpdateProfileRequestBody (var company_name: String,
                                         val responsible_person: String,
                                         var invoice_number: String,
                                         val remittance_account: String,
                                         var office_phone_number: String,
                                         val personal_phone_number: String,
                                         var office_address: String,
                                         val correspondence_address: String,
                                         var delivery_fee: Float)
}
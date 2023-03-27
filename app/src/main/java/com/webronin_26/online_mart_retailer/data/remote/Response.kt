package com.webronin_26.online_mart_retailer.data.remote

import com.google.gson.annotations.SerializedName

class Response {

    /**
     *  Login
     */
    data class LoginResponse (val count: Int, val code: Int, val data: LoginResponseData)

    data class LoginResponseData (val token: String, val id: Int, val name: String)

    /**
     *  Logout
     */
    data class LogoutResponse (val count: Int, val code: Int)

    /**
     *  Add product
     */
    data class AddProductResponse (val count: Int, val code: Int)

    /**
     *  Product list
     */
    data class ProductListResponse (val count: Int, val code: Int, val data: Array<Products>)

    data class Products (val id: Int,
                         @SerializedName("name") val productName: String,
                         @SerializedName("price") val productPrice: Float,
                         @SerializedName("image_url") val imageUrl: String)

    /**
     *  Product
     */
    data class ProductResponse (val count: Int, val code: Int, val data: Product)

    data class Product (@SerializedName("id") val id: Int,
                        @SerializedName("name") val name: String,
                        @SerializedName("image_url") val imageUrl: String,
                        @SerializedName("summary") val summary: String,
                        @SerializedName("information") val information: String,
                        @SerializedName("price") val price: Float,
                        @SerializedName("inventory_number") val inventoryNumber: Int,
                        @SerializedName("max_buy") val maxBuy: Int,

                        @SerializedName("company_id") val companyId: Int,
                        @SerializedName("company_name") val companyName:String,
                        @SerializedName("company_address") val companyAddress: String)

    /**
     *  Update product
     */
    data class UpdateProductResponse (val count: Int, val code: Int)

    /**
     *  Order list
     */
    data class OrderListResponse (val count: Int, val code: Int, val data: Array<Orders>)

    data class Orders (val id: Int,
                       @SerializedName("order_number") val orderNumber: String,
                       @SerializedName("product_list") val productList: String,
                       @SerializedName("total_price") val totalPrice: String)

    /**
     *  Order
     */
    data class OrderResponse (val count: Int, val code: Int, val data: Order)

    data class Order (val id: Int,
                      @SerializedName("order_number") val orderNumber: String,
                      @SerializedName("order_address") val orderAddress: String,
                      @SerializedName("order_product_list") val orderProductList: String,
                      @SerializedName("total_price") val totalPrice: Float,
                      @SerializedName("paid_time") val paidTime: String)

    /**
     *  Profile
     */
    data class ProfileResponse (val count: Int, val code: Int, val data: Profile)

    data class Profile (val id: Int,
                        @SerializedName("name") val name: String,
                        @SerializedName("responsible_person") val responsiblePerson: String,
                        @SerializedName("invoice") val invoice: String,
                        @SerializedName("remittance_account") val remittanceAccount: String,
                        @SerializedName("office_phone") val officePhone: String,
                        @SerializedName("personal_phone") val personalPhone: String,
                        @SerializedName("office_address") val officeAddress: String,
                        @SerializedName("correspondence_address") val correspondenceAddress: String,
                        @SerializedName("delivery_fee") val deliveryFee: Float,)

    /**
     *  Update profile
     */
    data class UpdateProfileResponse (val count: Int, val code: Int)
}

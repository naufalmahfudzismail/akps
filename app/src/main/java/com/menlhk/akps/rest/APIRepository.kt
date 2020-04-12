package com.menlhk.akps.rest

import android.preference.MultiSelectListPreference
import com.menlhk.akps.model.*
import io.reactivex.Flowable
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface APIRepository {

    @GET("{id}/usulan")
    fun getUsulan(@Path("id") id: String): Deferred<Response<List<ListUsulanResponse>>>

    @FormUrlEncoded
    @POST("login")
    @Headers("No-Authentication: true")
    fun login(
        @Field("username")
        username: String, @Field("password")
        password: String
    ): Deferred<Response<User>>

    @POST("{id}/addusulan")
    @Multipart
    @Headers("Accept: application/json")
    fun insertUsulan(
        @Path("id") id: String,
        @PartMap data: HashMap<String, RequestBody?>,
        @Part files: List<MultipartBody.Part?>
    ): Deferred<Response<List<InserUsulanResponse>>>

    @Headers("Accept: application/json")
    @GET("provinsi")
    fun getProvinsi(): Deferred<Response<List<ProvinsiResponse>>>

    @Headers("Accept: application/json")
    @GET("skema")
    fun getSkema(): Deferred<Response<List<SkemaResponse>>>

    @Headers("Accept: application/json")
    @GET("kawasan")
    fun getKawasan(): Deferred<Response<List<KawasanResponse>>>

    @Headers("Accept: application/json")
    @GET("all/kabupaten ")
    fun getAllKabupaten() : Deferred<Response<List<KabupatenResponse>>>

    @Headers("Accept: application/json")
    @GET("kabupaten/{proid}")
    fun getKabupaten(@Path("proid") proid: String)
            : Deferred<Response<List<KabupatenResponse>>>


}
package kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork

import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropCategoriesResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrFarmWorkByCropResponse
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.dto.NsrCropsByCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NsrFarmWorkApi {
    @GET("workScheduleGrpList")
    suspend fun getCropCategories(): NsrCropCategoriesResponse
    @GET("workScheduleList")
    suspend fun getCropsByCategory(
        @Query("idofcomdtySeCode") categoryCode:String
    ): NsrCropsByCategoryResponse

    @GET("")
    suspend fun getFarmWorkByCropJson(
        @Query("cntntsCode") cropCode:String
    ): NsrFarmWorkByCropResponse
}
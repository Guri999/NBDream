package kr.co.remote.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.calendar.HolidayData
import kr.co.remote.model.response.calendar.HolidayListResponse

internal object HolidayRemoteMapper
    : Mapper<HolidayListResponse, HolidayData> {
    override fun convert(param: HolidayListResponse): HolidayData =
        with(param) {
            HolidayData(
                date = date,
                isHoliday = isHoliday,
                type = type,
                name = name
            )
        }
}

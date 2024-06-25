package kr.co.main.calendar.screen.calendarScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.GetDiariesUseCase
import kr.co.domain.usecase.calendar.GetFarmWorksUseCase
import kr.co.domain.usecase.calendar.GetHolidaysUseCase
import kr.co.domain.usecase.calendar.GetSchedulesUseCase
import kr.co.domain.usecase.calendar.GetUserCropsUseCase
import kr.co.main.mapper.calendar.CropModelMapper
import kr.co.main.mapper.calendar.HolidayModelMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.DiaryModel
import kr.co.main.model.calendar.FarmWorkModel
import kr.co.main.model.calendar.HolidayModel
import kr.co.main.model.calendar.ScheduleModel
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

internal interface CalendarScreenEvent {
    fun onSelectYear(year: Int)
    fun onSelectMonth(month: Int)
    fun onSelectCrop(crop: CropModel)

    fun onSelectDate(date: LocalDate)
}

@HiltViewModel
internal class CalendarScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getUserCrops: GetUserCropsUseCase,
    private val getFarmWork: GetFarmWorksUseCase,
    private val getHolidays: GetHolidaysUseCase,
    private val getSchedules: GetSchedulesUseCase,
    private val getDiaries: GetDiariesUseCase
) : BaseViewModel<CalendarScreenViewModel.CalendarScreenState>(savedStateHandle),
    CalendarScreenEvent {
    val event: CalendarScreenEvent = this@CalendarScreenViewModel

    private val _crop = MutableStateFlow<CropModel?>(null)
    private val _year = MutableStateFlow(LocalDate.now().year)
    private val _month = MutableStateFlow(LocalDate.now().monthValue)

    private val _selectedDate = MutableStateFlow(LocalDate.now())

    data class CalendarScreenState(
        val userCrops: List<CropModel> = emptyList(),
        val crop: CropModel? = if (userCrops.isNotEmpty()) userCrops.first() else null,
        val year: Int = LocalDate.now().year,
        val month: Int = LocalDate.now().monthValue,

        val selectedDate: LocalDate = LocalDate.now(),

        val farmWorks: List<FarmWorkModel> = emptyList(),
        val holidays: List<HolidayModel> = emptyList(),
        val allSchedules: List<ScheduleModel> = emptyList(),
        val cropSchedules: List<ScheduleModel> = emptyList(),
        val diaries: List<DiaryModel> = emptyList()
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO("serialize")
            return null
        }
    }

    override fun createInitialState(savedState: Parcelable?): CalendarScreenState =
        savedState?.let {
            // TODO("deserialize")
            CalendarScreenState()
        } ?: CalendarScreenState()

    init {
        viewModelScopeEH.launch {
            getUserCrops().collect { userCrops ->
                updateState {
                    copy(userCrops = userCrops.map { CropModelMapper.toRight(it) })
                }
            }
        }
        viewModelScopeEH.launch {
            getHolidays(
                GetHolidaysUseCase.Params(
                    year = currentState.year,
                    month = currentState.month
                )
            ).let { holidays ->
                updateState {
                    copy(holidays = holidays.map { HolidayModelMapper.toRight(it) })
                }
            }
            viewModelScopeEH.launch {
                //TODO '전체' 카테고리 일정 api 요청
            }

            with(state) {
                select { it.crop }.bindState(_crop)
                select { it.year }.bindState(_year)
                select { it.month }.bindState(_month)
                select { it.selectedDate }.bindState(_selectedDate)
            }

            combine(_crop, _year, _month) { crop, year, month ->
                //TODO 농작업 일정, 작물 카테고리 일정, 영농일지 api 요청
            }.launchIn(viewModelScopeEH)
        }
    }

    override fun onSelectYear(year: Int) {
        updateState { copy(year = year) }
    }

    override fun onSelectMonth(month: Int) {
        updateState { copy(month = month) }
    }

    override fun onSelectCrop(crop: CropModel) {
        updateState { copy(crop = crop) }
    }

    override fun onSelectDate(date: LocalDate) {
        updateState { copy(selectedDate = date) }
    }
}
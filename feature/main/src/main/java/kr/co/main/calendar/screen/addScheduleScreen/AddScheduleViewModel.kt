package kr.co.main.calendar.screen.addScheduleScreen

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.usecase.calendar.CreateScheduleUseCase
import kr.co.domain.usecase.calendar.GetScheduleDetailUseCase
import kr.co.domain.usecase.calendar.UpdateScheduleUseCase
import kr.co.main.mapper.calendar.ScheduleModelTypeMapper
import kr.co.main.model.calendar.CropModel
import kr.co.main.model.calendar.type.CropModelType
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.main.model.calendar.type.ScreenModeType
import kr.co.main.navigation.CalendarNavGraph
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

internal interface AddScheduleScreenEvent {
    fun onPostClick()
    fun onEditClick()

    fun onTypeSelect(type: ScheduleModelType)
    fun onTitleInput(title: String)
    fun onStartDateSelect(startDate: LocalDate)
    fun onEndDateSelect(endDate: LocalDate)
    fun onMemoInput(memo: String)

    fun onAlarmOnSelect(alarmOn: Boolean)
    fun onAlarmDateTimeSelect(alarmDateTime: LocalDateTime)
}

@HiltViewModel
internal class AddScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSchedule: GetScheduleDetailUseCase,
    private val createSchedule: CreateScheduleUseCase,
    private val updateSchedule: UpdateScheduleUseCase
) : BaseViewModel<AddScheduleViewModel.AddScheduleScreenState>(savedStateHandle),
    AddScheduleScreenEvent {
    val event: AddScheduleScreenEvent = this@AddScheduleViewModel

    data class AddScheduleScreenState(
        val screenMode: ScreenModeType = ScreenModeType.POST_MODE,
        val calendarCrop: CropModel? = null,

        val scheduleId: Long? = null,
        val scheduleType: ScheduleModelType = ScheduleModelType.All,
        val title: String = "",
        val startDate: LocalDate = LocalDate.now(),
        val endDate: LocalDate = LocalDate.now(),
        val memo: String = "",
        val alarmOn: Boolean = false,
        val alarmDateTime: LocalDateTime = LocalDateTime.of(startDate, LocalTime.of(7, 0))
    ) : State {
        override fun toParcelable(): Parcelable? {
            // TODO ("serialize")
            return null
        }
    }

    override fun createInitialState(savedState: Parcelable?): AddScheduleScreenState =
        savedState?.let {
            // TODO ("deserialize")
            AddScheduleScreenState()
        } ?: AddScheduleScreenState()

    init {
        with(savedStateHandle) {
            get<String>(CalendarNavGraph.ARG_CROP_NAME_ID)?.toIntOrNull()?.let { cropNameId ->
                updateState {
                    copy(calendarCrop = CropModel.create(CropModelType.ofValue(cropNameId)))
                }
                Timber.d("init) calendarCrop: ${currentState.calendarCrop?.type}")
            }

            get<Int>(CalendarNavGraph.ARG_SCREEN_MODE_ID)?.let { screenModeId ->
                updateState {
                    copy(screenMode = ScreenModeType.ofValue(screenModeId))
                }
                Timber.d("init) screenMode: ${currentState.screenMode}")
            } ?: throw IllegalArgumentException("screen mode id is null")

            get<String>(CalendarNavGraph.ARG_SCHEDULE_ID)?.toLongOrNull()?.let { scheduleId ->
                updateState {
                    copy(scheduleId = scheduleId)
                }
                Timber.d("init) scheduleId: ${currentState.scheduleId}")
                viewModelScopeEH.launch {
                    getSchedule(GetScheduleDetailUseCase.Params(scheduleId)).let {
                        Timber.d("init) schedule: $it")
                        updateState {
                            copy(
                                scheduleType = ScheduleModelTypeMapper.toRight(it.type),
                                title = it.title,
                                startDate = it.startDate,
                                endDate = it.endDate,
                                memo = it.memo,
                                alarmOn = it.isAlarmOn,
                                alarmDateTime = it.alarmDateTime
                            )
                        }
                    }
                }
            } ?: Timber.d("init) scheduleId: null")
        }
    }

    override fun onPostClick() {
        if (currentState.screenMode != ScreenModeType.POST_MODE)
            throw IllegalStateException("screen mode is not post mode")

        viewModelScopeEH.launch {
            Timber.d("onPostClick) start date: ${currentState.startDate}, end date: ${currentState.endDate}")
            createSchedule(
                CreateScheduleUseCase.Params(
                    category = ScheduleModelTypeMapper.toLeft(currentState.scheduleType),
                    title = currentState.title,
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    memo = currentState.memo,
                    alarmOn = currentState.alarmOn,
                    alarmDateTime = currentState.alarmDateTime
                )
            )
        }

    }

    override fun onEditClick() {
        if (currentState.screenMode != ScreenModeType.EDIT_MODE)
            throw IllegalStateException("screen mode is not edit mode")
        checkNotNull(currentState.scheduleId)

        viewModelScopeEH.launch {
            Timber.d("onEditClick) start date: ${currentState.startDate}, end date: ${currentState.endDate}")
            updateSchedule(
                UpdateScheduleUseCase.Params(
                    id = currentState.scheduleId!!,
                    category = ScheduleModelTypeMapper.toLeft(currentState.scheduleType),
                    title = currentState.title,
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    memo = currentState.memo,
                    alarmOn = currentState.alarmOn,
                    alarmDateTime = currentState.alarmDateTime
                )
            )
        }
    }

    override fun onTypeSelect(type: ScheduleModelType) {
        updateState { copy(scheduleType = type) }
    }

    override fun onTitleInput(title: String) {
        Timber.d("onTitleInput) title: $title")
        updateState { copy(title = title) }
    }

    override fun onStartDateSelect(startDate: LocalDate) {
        Timber.d("onStartDateSelect) startDate: $startDate")
        updateState {
            copy(
                startDate = startDate,
                endDate = startDate,
                alarmDateTime = LocalDateTime.of(startDate, LocalTime.of(7, 0))
            )
        }
    }

    override fun onEndDateSelect(endDate: LocalDate) {
        Timber.d("onEndDateSelect) endDate: $endDate")
        updateState { copy(endDate = endDate) }
    }

    override fun onMemoInput(memo: String) {
        updateState { copy(memo = memo) }
    }

    override fun onAlarmOnSelect(alarmOn: Boolean) {
        updateState { copy(alarmOn = alarmOn) }
        if (alarmOn)
            updateState { copy(alarmDateTime = LocalDateTime.of(startDate, LocalTime.of(7, 0))) }
    }

    override fun onAlarmDateTimeSelect(alarmDateTime: LocalDateTime) {
        updateState { copy(alarmDateTime = alarmDateTime) }
    }
}
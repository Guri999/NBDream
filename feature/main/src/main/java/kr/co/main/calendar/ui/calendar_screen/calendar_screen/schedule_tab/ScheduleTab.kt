package kr.co.main.calendar.ui.calendar_screen.calendar_screen.schedule_tab

import FarmWorkCalendar
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import kr.co.main.R
import kr.co.main.calendar.model.CropModel
import kr.co.main.calendar.model.FarmWorkModel
import kr.co.main.calendar.model.HolidayModel
import kr.co.main.calendar.model.ScheduleModel
import kr.co.main.calendar.model.filterAndSortHolidays
import kr.co.main.calendar.ui.common.CalendarCategoryIndicator
import kr.co.main.calendar.ui.common.inner_calendar.InnerCalendar
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.LocalDate

internal class ScheduleTabStateHolder(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarCrop: CropModel,
    val farmWorks: List<FarmWorkModel>,
    val holidays: List<HolidayModel>,
    val schedules: List<ScheduleModel>
)

@Composable
internal fun ScheduleTab(
    modifier: Modifier = Modifier,
    state: State<ScheduleTabStateHolder>
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.gray9
    ) {
        Column {
            FarmWorkCalendarCard(
                modifier = Modifier.padding(Paddings.large),
                farmWorks = state.value.farmWorks
            )
            InnerCalendarCard(
                modifier = Modifier.padding(Paddings.large),
                calendarCrop = CropModel.POTATO //TODO use state
            )

            ScheduleCard(
                modifier = Modifier.padding(Paddings.large),
                selectedDate = state.value.selectedDate,
                holidays = filterAndSortHolidays(
                    holidays = state.value.holidays,
                    date = state.value.selectedDate
                ),
                schedules = state.value.schedules
            )
        }
    }
}

@Composable
private fun FarmWorkCalendarCard(
    farmWorks: List<FarmWorkModel>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        FarmWorkCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.large),
            farmWorks = farmWorks
        )
    }
}


@Composable
private fun InnerCalendarCard(
    calendarCrop: CropModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.xlarge)
        ) {
            CategoryIndicatorList(
                modifier = Modifier
                    .padding(horizontal = Paddings.large)
                    .padding(bottom = Paddings.large),
                crop = calendarCrop
            )
            InnerCalendar(
                calendarYear = 2024,
                calendarMonth = 6
            )
        }
    }
}

@Composable
private fun CategoryIndicatorList(
    crop: CropModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIndicatorListItem(
            cropNameId = crop.nameId,
            cropColor = crop.color
        )
        CategoryIndicatorListItem(
            cropNameId = R.string.feature_main_calendar_category_all,
            cropColor = Color.Gray.toArgb()
        )
    }
}

@Composable
private fun CategoryIndicatorListItem(
    @StringRes cropNameId: Int,
    @ColorInt cropColor: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.small),
            categoryColor = cropColor
        )
        Text(
            text = stringResource(id = cropNameId),
            style = MaterialTheme.typo.labelM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun ScheduleCard(
    selectedDate: LocalDate,
    holidays: List<HolidayModel>,
    schedules: List<ScheduleModel>,
    modifier: Modifier = Modifier
) {

}
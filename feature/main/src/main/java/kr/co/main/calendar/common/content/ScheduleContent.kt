package kr.co.main.calendar.common.content

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import kr.co.main.calendar.common.CalendarCategoryIndicator
import kr.co.main.calendar.common.CalendarContentDropDownMenuItem
import kr.co.main.model.calendar.ScheduleModel
import kr.co.main.model.calendar.type.ScheduleModelType
import kr.co.main.providers.calendar.FakeScheduleModelProvider
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Edit
import kr.co.ui.icon.dreamicon.MoreVertical
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import java.time.format.DateTimeFormatter

@Composable
internal fun ScheduleContent(
    schedule: ScheduleModel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandDropDown by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                ScheduleTitle(
                    modifier = Modifier.padding(end = Paddings.xsmall),
                    type = schedule.type,
                    title = schedule.title
                )
                ScheduleDate(
                    startDate = schedule.startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    endDate = schedule.endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                )
            }
            ScheduleMemo(
                modifier = Modifier.padding(start = Paddings.xlarge),
                memo = schedule.memo
            )
        }
        Column {
            Icon(
                modifier = Modifier
                    .scale(0.8f)
                    .clickable {
                        expandDropDown = true
                    },
                imageVector = DreamIcon.MoreVertical,
                tint = MaterialTheme.colors.gray5,
                contentDescription = ""
            )
            DropdownMenu(
                expanded = expandDropDown,
                onDismissRequest = { expandDropDown = false },
            ) {
                CalendarContentDropDownMenuItem(
                    menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_edit,
                    onClick = onEditClick
                )
                CalendarContentDropDownMenuItem(
                    menuNameId = kr.co.nbdream.core.ui.R.string.core_ui_dropdown_menu_delete,
                    onClick = onDeleteClick
                )
            }
        }
    }
}

@Composable
private fun ScheduleTitle(
    type: ScheduleModelType,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CalendarCategoryIndicator(
            modifier = Modifier.padding(end = Paddings.medium),
            categoryColor = type.color
        )
        Text(
            text = title,
            style = MaterialTheme.typo.bodyM,
            color = MaterialTheme.colors.text1
        )
    }
}

@Composable
private fun ScheduleDate(
    startDate: String,
    modifier: Modifier = Modifier,
    endDate: String? = null
) {
    val scheduleDate = if (startDate != endDate) " $startDate~$endDate" else " $startDate"
    Text(
        modifier = modifier,
        text = scheduleDate,
        style = MaterialTheme.typo.labelL,
        color = MaterialTheme.colors.text2
    )
}

@Composable
private fun ScheduleMemo(
    memo: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = memo,
        style = MaterialTheme.typo.labelL,
        color = MaterialTheme.colors.text2
    )
}

@Preview(showBackground = true)
@Composable
private fun ScheduleContentPreview(
    @PreviewParameter(FakeScheduleModelProvider::class) schedule: ScheduleModel
) {
    ScheduleContent(
        schedule = schedule,
        onEditClick = {},
        onDeleteClick = {}
    )
}
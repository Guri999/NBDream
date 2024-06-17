package kr.co.main.calendar.ui.calendartab.diarytab

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.main.calendar.model.DiaryModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryTabViewModel @Inject constructor(
    stateHandle: SavedStateHandle
): BaseViewModel<DiaryTabViewModel.State>(stateHandle) {
    data class State(
        val diaries: List<DiaryModel>
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?): State {
        TODO("Not yet implemented")
    }
}
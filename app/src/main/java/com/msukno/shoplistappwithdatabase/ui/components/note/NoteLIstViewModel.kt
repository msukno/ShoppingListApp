package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msukno.shoplistappwithdatabase.data.note.Note
import com.msukno.shoplistappwithdatabase.data.note.NotesRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to fetch and manage the list of notes from the database.
 */
class NoteListViewModel(private val notesRepository: NotesRepo) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /**
     * Holds the current list of notes in the UI state.
     * It fetches all notes from the database and maps them to a NoteListUiState.
     */
    val noteListUiState: StateFlow<NoteListUiState> =
        notesRepository.getAllNotesStream().map { NoteListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = NoteListUiState()
            )

    /**
     * Updates a [Note] in the databse.
     */
    suspend fun updateNote(noteData: NoteData){
        notesRepository.updateNote(noteData.toNote())
    }
}

/**
 * Represents UI State for a list of Notes.
 */
data class NoteListUiState(val noteList: List<Note> = listOf())


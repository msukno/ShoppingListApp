package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msukno.shoplistappwithdatabase.data.note.NotesRepo
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to validate, update, and delete notes in the database.
 */
class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private var cache = CacheData()
    }

    /**
     * Holds current note UI state.
     */
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    /**
     * Fetch the ID of the note being edited.
     */
    private val noteId: Int = checkNotNull(savedStateHandle[NoteEditDestination.noteIdArg])

    /**
     * Initializes the note UI state with the note being edited and caches the initial note data.
     */
    init{
        viewModelScope.launch {
            noteUiState = notesRepository.getNoteStream(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState()

            cache = noteUiState.noteData.toCacheData()
        }
    }

    /**
     * Updates the [noteUiState] with the value provided in the argument.
     */
    fun updateUiState(noteData: NoteData) {
        noteUiState = NoteUiState(noteData = noteData)
    }

    /**
     * Deletes a [Note] from the databse.
     */
    suspend fun deleteNote(){
        notesRepository.deleteNote(noteUiState.noteData.toNote())
    }

    /**
     * Updates a [Note] in the databse if the input is valid.
     */
    suspend fun updateNote(){
        if (validateInput()) {
            notesRepository.updateNote(noteUiState.noteData.toNote())
        }
    }

    /**
     * Validates the input values. Returns true if the title and text are not blank.
     */
    fun validateInput(inputData: NoteData = noteUiState.noteData): Boolean {
        return inputData.title.isNotBlank()
    }

    /**
     * Checks if any changes have been made to the note data.
     */
    fun changesMade(): Boolean = cache != noteUiState.noteData.toCacheData()
}

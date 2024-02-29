package com.msukno.shoplistappwithdatabase.ui.components.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.msukno.shoplistappwithdatabase.data.item.Item
import com.msukno.shoplistappwithdatabase.data.note.Note
import com.msukno.shoplistappwithdatabase.data.note.NotesRepo
import com.msukno.shoplistappwithdatabase.ui.components.item.ItemUiState
import com.msukno.shoplistappwithdatabase.ui.components.item.toItemDetails
import com.msukno.shoplistappwithdatabase.ui.components.item.toItemUiState
import java.time.Instant
import java.time.LocalDateTime


/**
 * ViewModel to validate and insert notes in the database.
 */
class NoteAddViewModel(private val notesRepository: NotesRepo): ViewModel(){


    var noteUiState by mutableStateOf(NoteUiState())

    /**
     * Validates the input values. Returns true if the title and text are not blank.
     */
    fun validateInput(inputData: NoteData = noteUiState.noteData): Boolean {
        return inputData.title.isNotBlank()
    }

    /**
     * Updates the [noteUiState] with the value provided in the argument.
     */
    fun updateUiState(noteData: NoteData){
        noteUiState = NoteUiState(noteData = noteData)
    }

    /**
     * Inserts a [Note] in the database if the input is valid.
     */
    suspend fun saveNote() {
        if (validateInput()){
            notesRepository.insertNote(noteUiState.noteData.toNote())
        }
    }
}

/**
 * Represents UI State for a Note.
 */
data class NoteUiState(
    val noteData: NoteData = NoteData()
)

/**
 * Extension function to convert [Note] to [NoteUiState].
 */
fun Note.toNoteUiState(): NoteUiState = NoteUiState(
    noteData = this.toNoteData()
)

/**
 * Represents the details of a Note.
 */
data class NoteData(
    val id: Int = 0,
    val dateTime: Instant = Instant.now(),
    val title: String = "",
    val text: String = "",
    val linkedItems: Map<Int, Boolean> = mapOf()
)

/**
 * Extension function to convert [NoteData] to [Note]. If the value of [NoteData.linkedItems] is
 * not a valid [Boolean], then the linkedItems will be set to false.
 */
fun NoteData.toNote(): Note = Note(
    id = id,
    dateTime = dateTime,
    title = title,
    text = text,
    linkedItems = linkedItems.keys.filter {
        linkedItems[it] ?: false
    }
)

/**
 * Extension function to convert [Note] to [NoteData].
 */
fun Note.toNoteData(): NoteData = NoteData(
    id = id,
    dateTime = dateTime,
    title = title,
    text = text,
    linkedItems = linkedItems.map { it to true }.toMap()
)

/**
 * Represents a cache of a Note.
 */
data class CacheData(
    val title: String = "",
    val text: String = "",
    val linkedItems: Set<Int> = setOf()
)

/**
 * Extension function to convert [NoteData] to [CacheData].
 */
fun NoteData.toCacheData(): CacheData = CacheData(
    title = title,
    text = text,
    linkedItems = linkedItems.keys.filter {
        linkedItems[it] ?: false }.toSet()
)




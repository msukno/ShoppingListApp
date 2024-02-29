package com.msukno.shoplistappwithdatabase.data.note

import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    /**
     * Retrieve all the Notes from the given data source.
     */
    fun getAllNotesStream(): Flow<List<Note>>

    /**
     * Retrieve an Note from the given data source that matches with the [id].
     */
    fun getNoteStream(id: Int): Flow<Note?>

    /**
     * Insert Note in the data source
     */
    suspend fun insertNote(note: Note)

    /**
     * Delete Note from the data source
     */
    suspend fun deleteNote(note: Note)

    /**
     * Update Note in the data source
     */
    suspend fun updateNote(note: Note)
}
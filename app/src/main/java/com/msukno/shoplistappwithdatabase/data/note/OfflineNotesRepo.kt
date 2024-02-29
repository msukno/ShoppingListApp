package com.msukno.shoplistappwithdatabase.data.note

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepo(private val noteDao: NoteDao): NotesRepo {

    override fun getAllNotesStream(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNoteStream(id: Int): Flow<Note?> = noteDao.getNote(id)

    override suspend fun insertNote(note: Note) = noteDao.insert(note)

    override suspend fun deleteNote(note: Note) = noteDao.delete(note)

    override suspend fun updateNote(note: Note) = noteDao.update(note)
}
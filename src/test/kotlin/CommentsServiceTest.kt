import org.junit.Test

import org.junit.Assert.*

class CommentsServiceTest {

    @Test
    fun add_passed() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        assertEquals(1, idComment1)
    }

    @Test
    fun editExisting() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "утром уже +23")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.edit(Comments(id = idComment2, text = "утром уже +25"))
        assertTrue(result)
    }

    @Test
    fun tryEditNoExisting() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.edit(Comments(id = 3, text = "вечером всего +15"))
        assertFalse(result)
    }

    @Test
    fun tryEditDel() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20", isDeleted = true)
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.edit(Comments(idComment2, text = "вечером всего +15"))
        assertFalse(result)
    }

    @Test
    fun restorePassed() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20", isDeleted = true)
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.restore(idComment2)
        assertTrue(result)
    }

    @Test
    fun tryRestoreNoDel() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.restore(idComment2)
        assertFalse(result)
    }

    @Test
    fun tryRestoreNoExisting() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.restore(3)
        assertFalse(result)
    }

    @Test
    fun deletePassed() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.delete(idComment2)
        assertTrue(result)
    }

    @Test
    fun tryDeleteAlsoDel() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20", isDeleted = true)
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.delete(idComment2)
        assertFalse(result)
    }

    @Test
    fun tryDeleteNoExisting() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.delete(3)
        assertFalse(result)
    }

    @Test
    fun deleteByNotePassed() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        commentsService.deleteByNote(idNote1)
        val countComments = commentsService.get(idNote1).count()
        assertEquals(0, countComments)
    }


    @Test
    fun get() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val countComments = commentsService.get(idNote1).count()
        assertEquals(2, countComments)
    }

    @Test
    fun getById() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.getById(idComment2)
        assertEquals("вечером тоже +20", result.text)
    }

    @Test(expected = EntityNotFoundException::class)
    fun getByIdNoExisting() {
        val noteService = NoteService()
        val note1 = Notes(text = "Сегодня хорошая погода")
        val idNote1 = noteService.add(note1, 0)
        val commentsService = CommentsService()
        val comment1 = Comments(text = "утром уже +20")
        val comment2 = Comments(text = "вечером тоже +20")
        val idComment1 = commentsService.add(comment1, idNote1)
        val idComment2 = commentsService.add(comment2, idNote1)
        val result = commentsService.getById(3)

    }
}

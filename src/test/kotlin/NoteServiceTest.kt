import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add_passed() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди")
        val idNote2 = noteService.add(Note2, 0)
        assertEquals(2, idNote2)
    }

    @Test
    fun deletePassed() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди")
        val idNote2 = noteService.add(Note2, 0)
        val result =  noteService.delete(idNote1)
        assertTrue(result)
    }

    @Test
    fun tryDeleteAlsoDel() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", isDeleted = true)
        val idNote2 = noteService.add(Note2, 0)
        val result =  noteService.delete(idNote2)
        assertFalse(result)
    }

    @Test
    fun tryDeleteNoExisting() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", isDeleted = true)
        val idNote2 = noteService.add(Note2, 0)
        val result =  noteService.delete(3)
        assertFalse(result)
    }

    @Test
    fun editExisting() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди")
        val idNote2 = noteService.add(Note2, 0)
        val result = noteService.edit(Notes(id = idNote2, text = "Завтра тоже cолнечно"))
        assertTrue(result)
    }

    @Test
    fun tryEditNoExisting() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожжди")
        val idNote2 = noteService.add(Note2, 0)
        val result = noteService.edit(Notes(id = 3, text = "Завтра тоже cолнечно"))
        assertFalse(result)
    }

    @Test
    fun tryEditDel() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!")
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", isDeleted = true)
        val idNote2 = noteService.add(Note2, 0)
        val result = noteService.edit(Notes(idNote2, text = "Завтра тоже cолнечно"))
        assertFalse(result)
    }

    @Test
    fun get() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!", ownerId = 222)
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", ownerId = 555)
        val idNote2 = noteService.add(Note2, 0)
        val Note3 = Notes(text = "Послезавтра снова солнечно", ownerId = 555)
        val idNote3 = noteService.add(Note3, 0)
        val countNotes = noteService.get(555).count()
        assertEquals(2, countNotes)
    }

    @Test
    fun getById() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!", ownerId = 222)
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", ownerId = 555)
        val idNote2 = noteService.add(Note2, 0)
        val result = noteService.getById(idNote2)
        assertEquals("Завтра уже дожди", result.text)
    }

    @Test(expected = EntityNotFoundException::class)
    fun getByIdNoExisting() {
        val noteService = NoteService()
        val Note1 = Notes(text = "Сегодня хорошая погода!", ownerId = 222)
        val idNote1 = noteService.add(Note1, 0)
        val Note2 = Notes(text = "Завтра уже дожди", ownerId = 555)
        val idNote2 = noteService.add(Note2, 0)
        val result = noteService.getById(3)
    }

}
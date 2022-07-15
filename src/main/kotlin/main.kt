fun main() {

}

data class Notes(
    val id: Long = 0,
    val ownerId: Long = 0,
    val title: String = "", //Заголовок заметки
    var text: String ="", //Текст заметки
    val date: Int = 0,
    val comments: Int = 0, // Количество комментариев
    val readComments: Int = 0, //Количество прочитанных комментариев
    val view_url: String = "", //URL страницы для отображения заметки.
    val privacyView: String = "", // Настройки приватности комментирования заметки
    val canComment: Boolean = true, //Есть ли возможность оставлять комментарии
    val textWiki: String = "", // Тэги ссылок на wiki
    var isDeleted: Boolean = false
)
data class Comments( //
    val id: Long = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyToUser: Int = 0, // reply_to
    var isDeleted: Boolean = false,
    val noteId: Long = 0//идентификатор заметки, к которой относится комментарий
)

interface CrudService<E> {
    fun add(entity: E, parentId:Long): Long
    fun delete(id: Long):Boolean
    fun edit(entity: E):Boolean
    fun get(id: Long): List<E> //здесь id это идентификатор пользователя либо идентификатор заметки
    fun getById(id: Long): E
    fun restore(id: Long): Boolean
}

class NoteService() :CrudService<Notes> {
    private var notes = emptyArray<Notes>()
    private var lastId: Long = 0

    override fun add(entity: Notes, parentId: Long): Long {
        lastId += 1
        notes += entity.copy(id = lastId)
        return lastId
    }

    override fun delete(id: Long): Boolean {
        var find = false
        for ((index, currnNote) in notes.withIndex()) {
            if ((currnNote.id == id) && (!currnNote.isDeleted)) {
                notes[index] = notes[index].copy(isDeleted = true)
                CommentsService().deleteByNote(id)
                find = true
            }
        }
        return find
    }

    override fun edit(entity: Notes): Boolean {
        val searchId = entity.id
        var find = false
        for ((index, currnNote) in notes.withIndex()) {
            if ((currnNote.id == searchId) && (!currnNote.isDeleted)) {
                notes[index] = entity.copy(text = entity.text)
                find = true
            }
        }
        return find
    }

    override fun get(ownerId: Long): List<Notes> {
        return notes.filter { it.ownerId == ownerId }
    }

    override fun getById(id: Long): Notes {
        var find: Notes? = null
        for ((index, currnNote) in notes.withIndex()) {
            if (currnNote.id == id) {
                find = currnNote
            }
        }
        return find ?: throw EntityNotFoundException("Не найдена заметка с id: $id")
    }

    override fun restore(id: Long):Boolean {
        return false
    }
}

class CommentsService() :CrudService<Comments> {
    private var comments = emptyArray<Comments>()
    private var lastId: Long = 0

    override fun add(entity: Comments, parentId: Long): Long {
        lastId += 1
        comments += entity.copy(id = lastId, noteId = parentId)
        return lastId
    }

    override fun edit(entity: Comments): Boolean {
        var find = false
        val searchId = entity.id
            for ((index, currComment) in comments.withIndex()) {
                if ((currComment.id == searchId) && (!currComment.isDeleted)) {
                    comments[index] = entity.copy(text = entity.text)
                    find = true
                }
            }

        return find
    }

    override fun restore(id: Long): Boolean {
        var find = false
        for ((index, currComment) in comments.withIndex()) {
            if ((currComment.id == id) && (currComment.isDeleted)) {
                if (comments[index].isDeleted)
                    comments[index] = comments[index].copy(isDeleted = false)
                find = true
            }
        }

        return find
    }

    override fun delete(id: Long): Boolean {
        var find = false
        for ((index, currComment) in comments.withIndex()) {
            if ((currComment.id == id) && (!currComment.isDeleted)) {
                comments[index] = comments[index].copy(isDeleted = true)
                find = true
            }
        }
        return find
    }

    fun deleteByNote(noteId: Long) {
        val deletedComments = comments.filter { it.noteId == noteId }
        for ((index, currComment) in deletedComments.withIndex()){
            delete(currComment.id)
        }
    }

    override fun get(noteId: Long): List<Comments> {
        return comments.filter { it.noteId == noteId && !it.isDeleted }
    }

    override fun getById(id: Long): Comments {
        var find: Comments? = null
        val comment = Comments()
        for ((index, currComment) in comments.withIndex()) {
            if (currComment.id == id) {
                find = currComment
            }
        }
        //return find ?: comment
        return find ?: throw EntityNotFoundException("Не найден комментарий с id: $id")
    }

}

class EntityNotFoundException(massage:String): RuntimeException(massage)
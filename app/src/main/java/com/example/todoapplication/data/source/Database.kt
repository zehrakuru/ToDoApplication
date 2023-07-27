package com.example.todoapplication.data.source

import com.example.todoapplication.data.model.ToDo
import java.text.FieldPosition

object Database {
    private val toDoList = mutableListOf<ToDo>()
    private val doneList = mutableListOf<ToDo>()

    fun getToDoList() = toDoList
    fun getDoneList() = doneList

    fun addToDoList(title: String, desc: String, date: String, saveType: String, isDone: Boolean) {
        toDoList.add(
            ToDo(
                id = (toDoList.lastOrNull()?.id ?: 0) + 1,
                title = title,
                desc = desc,
                date = date,
                saveType = saveType,
                isDone = isDone
            )
        )
    }

    fun removeToDoList(toDo : ToDo) = toDoList.remove(toDo)
    fun removeDoneList(toDo : ToDo) = doneList.remove(toDo)

    fun updateToDoList(index: Int, toDo: ToDo) {
        toDoList[index] = toDo
    }

    fun addDoneList(title: String, desc: String, date: String, saveType: String, isDone: Boolean) {
        doneList.add(
            ToDo(
                id = (toDoList.lastOrNull()?.id ?: 0) + 1,
                title = title,
                desc = desc,
                date = date,
                saveType = saveType,
                isDone = isDone
            )
        )
    }
}
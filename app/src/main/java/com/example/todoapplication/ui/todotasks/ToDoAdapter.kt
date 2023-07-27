package com.example.todoapplication.ui.todotasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.data.model.ToDo
import com.example.todoapplication.data.source.Database
import com.example.todoapplication.databinding.ItemToDoTasksBinding

class ToDoAdapter(
    private val onToDoClick: (ToDo) -> Unit,
    var checkboxClick: (ToDo) -> Unit = {}
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private val toDoList = mutableListOf<ToDo>()

    inner class ToDoViewHolder(private val binding: ItemToDoTasksBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(toDo: ToDo) {
            with(binding) {
                txtViewTitle.text = toDo.title
                txtViewDate.text = toDo.date
                checkBtnDone.setOnCheckedChangeListener { _, _ ->
                    checkboxClick(toDo)
                }
                root.setOnClickListener{
                    onToDoClick(toDo)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ToDoViewHolder(
            ItemToDoTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        return holder.bind(toDoList[position])
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun updateList(list: List<ToDo>) {
        toDoList.clear()
        toDoList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }

}
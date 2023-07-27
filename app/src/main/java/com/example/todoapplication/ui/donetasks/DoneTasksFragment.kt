package com.example.todoapplication.ui.donetasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.model.ToDo
import com.example.todoapplication.data.source.Database
import com.example.todoapplication.databinding.FragmentDoneTasksBinding
import com.example.todoapplication.databinding.FragmentToDoTasksBinding
import com.example.todoapplication.ui.todotasks.ToDoAdapter
import com.example.todoapplication.ui.todotasks.ToDoTasksFragmentDirections

class DoneTasksFragment : Fragment() {

    private lateinit var binding : FragmentDoneTasksBinding
    private val toDoAdapter = ToDoAdapter(::onToDoClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoneTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getList = Database.getDoneList()
        binding.rvDoneTasks.adapter = toDoAdapter
        toDoAdapter.updateList(getList)
        checkBoxClick()
    }

    private fun onToDoClick(toDo: ToDo) {
        val action = DoneTasksFragmentDirections.actionDoneTasksFragmentToDetailScreenFragment(toDo)
        findNavController().navigate(action)
    }

    private fun checkBoxClick() {
        toDoAdapter.checkboxClick = { toDoTask ->
            Database.removeDoneList(toDoTask)
            toDoTask.desc?.let {
                Database.addToDoList(
                    toDoTask.title,
                    it,
                    toDoTask.date,
                    toDoTask.saveType,
                    toDoTask.isDone
                )
            }
            toDoTask.isDone = false
            val getList = Database.getDoneList()
            binding.rvDoneTasks.adapter = toDoAdapter
            toDoAdapter.updateList(getList)
        }
    }
}
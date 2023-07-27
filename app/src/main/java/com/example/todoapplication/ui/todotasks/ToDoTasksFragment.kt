package com.example.todoapplication.ui.todotasks

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.common.showDatePicker
import com.example.todoapplication.common.showTimePicker
import com.example.todoapplication.data.model.ToDo
import com.example.todoapplication.data.source.Database
import com.example.todoapplication.databinding.AddAlertDialogDesignBinding
import com.example.todoapplication.databinding.FragmentToDoTasksBinding
import com.example.todoapplication.ui.detailscreen.DetailScreenFragment
import java.util.*

class ToDoTasksFragment : Fragment(R.layout.fragment_to_do_tasks) {

    private lateinit var binding : FragmentToDoTasksBinding
    private val toDoAdapter = ToDoAdapter(::onToDoClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setData(Database.getToDoList())

            fabBtn.setOnClickListener {
                showAddDialog()
            }
            checkBoxClick()
        }
    }

    private fun setData(list: List<ToDo>) {
        binding.rvToDoTasks.adapter = toDoAdapter
        toDoAdapter.updateList(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onToDoClick(toDo: ToDo) {
        val action = ToDoTasksFragmentDirections.actionToDoTasksFragmentToDetailScreenFragment(toDo)
        findNavController().navigate(action)
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = AddAlertDialogDesignBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()

        val saveTypeList = listOf("To Do", "Done")

        var selectedSaveType = ""
        var selectedDate = ""

        val saveTypeAdapter = ArrayAdapter(
            requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, saveTypeList
        )

        with(binding) {

            val calendar = Calendar.getInstance()
            actSaveType.setAdapter(saveTypeAdapter)

            actSaveType.setOnItemClickListener { _, _, position, _ ->
                selectedSaveType = saveTypeList[position]
            }

            switchDate.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    showDatePicker(calendar) { year, month, day ->
                        showTimePicker(calendar) { hour, minute ->
                            selectedDate = "$day.$month.$year - $hour:$minute"
                            tvDate.text = "$day.$month.$year - $hour:$minute"
                            tvDate.visibility = View.VISIBLE
                        }
                    }
                }
            }

            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()
                if (title.isNotEmpty() && desc.isNotEmpty() && selectedSaveType.isNotEmpty()) {
                    Database.addToDoList(title, desc, selectedDate, selectedSaveType, false)
                    setData(Database.getToDoList())
                    dialog.dismiss()
                }
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun checkBoxClick() {
        toDoAdapter.checkboxClick = { toDoTask ->
            Database.removeToDoList(toDoTask)
            toDoTask.desc?.let {
                Database.addDoneList(
                    toDoTask.title,
                    it,
                    toDoTask.date,
                    toDoTask.saveType,
                    toDoTask.isDone
                )
            }
            toDoTask.isDone = false
            val getList = Database.getToDoList()
            binding.rvToDoTasks.adapter = toDoAdapter
            toDoAdapter.updateList(getList)
        }
    }
}
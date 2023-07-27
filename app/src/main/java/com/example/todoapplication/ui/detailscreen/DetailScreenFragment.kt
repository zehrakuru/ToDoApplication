package com.example.todoapplication.ui.detailscreen

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.todoapplication.R
import com.example.todoapplication.data.source.Database
import com.example.todoapplication.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment() {

    private lateinit var binding : FragmentDetailScreenBinding
    private val args: DetailScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            txtDetailTitle.text = args.toDo.title
            txtDescDetail.text = args.toDo.desc
            txtDate.text = args.toDo.date

            btnDelete.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete This Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete") {_,_ ->
                        if(args.toDo.isDone) {
                            Database.removeDoneList(args.toDo)
                        } else {
                            Database.removeToDoList(args.toDo)
                        }
                    }.setNegativeButton("Cancel", null).show()
            }
        }
    }
}
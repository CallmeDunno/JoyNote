package com.example.joynote.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.joynote.R
import com.example.joynote.adapter.NoteAdapter
import com.example.joynote.data.Notes
import com.example.joynote.databinding.FragmentHomeBinding
import com.example.joynote.interfaces.IHomeItemClick
import com.example.joynote.viewModel.NotesViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private val adapter = NoteAdapter(Notes.NoteDiffUtil)

    private var listOldNotes: List<Notes> = ArrayList()
    private var listImportantNotes: ArrayList<Notes> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAction()
    }

    private fun initView() {
        binding.rcvHome.adapter = adapter
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        viewModel.getAllNotes().observe(requireActivity()) {
            if (it != null) {
                setNotificationEmpty(it.size)
                adapter.submitList(it)

                setDataForListImportantNote(it)
            }
        }
    }

    private fun initAction() {
        binding.btnAddHome.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(null)
            requireView().findNavController().navigate(action)
        }

        binding.btnImportantNoteHome.setOnCheckedChangeListener { _, b ->
            if (b) adapter.submitList(listImportantNotes)
            else adapter.submitList(listOldNotes)
        }

        adapter.setOnClickItem(object : IHomeItemClick {
            override fun onClickItem(note: Notes) {
                val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(note)
                requireView().findNavController().navigate(action)
            }

            override fun onLongClickItem(id: Int) {
                showDeleteDialog(id)
            }
        })
    }

    private fun setDataForListImportantNote(it: List<Notes>) {
        listOldNotes = it
        val listTerm = ArrayList<Notes>()
        for (i in it) {
            if (i.important) {
                listTerm.add(i)
            }
        }
        listImportantNotes.clear()
        listImportantNotes = listTerm
    }

    private fun showDeleteDialog(id: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_delete)
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window ?: return
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<Button>(R.id.btn_yes_dialog).setOnClickListener {
            viewModel.deleteNote(id)
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_no_dialog).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setNotificationEmpty(size: Int) {
        if (size == 0) binding.tvWListEmpty.visibility = View.VISIBLE
        else binding.tvWListEmpty.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        binding.btnImportantNoteHome.isChecked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
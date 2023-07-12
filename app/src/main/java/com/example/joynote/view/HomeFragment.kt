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

    private fun initAction() {
        binding.btnAddHome.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(null)
            requireView().findNavController().navigate(action)
        }
    }

    private fun initView() {
        binding.rcvHome.adapter = adapter
        adapter.setOnClickItem(object : IHomeItemClick {
            override fun onClickItem(note: Notes) {
                val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(note)
                requireView().findNavController().navigate(action)
            }

            override fun onLongClickItem(id: Int) {
                showDialog(id)
            }
        })
        initViewModel()
    }

    private fun showDialog(id: Int) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_delete)
        dialog.setCanceledOnTouchOutside(false)
        val window = dialog.window ?: return
        window.setGravity(Gravity.CENTER)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        );
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        viewModel.getAllNotes().observe(requireActivity()) {
            getNotificationEmpty(it.size)
            adapter.submitList(it)
        }
    }

    private fun getNotificationEmpty(size: Int) {
        if (size == 0) binding.tvWListEmpty.visibility = View.VISIBLE
        else binding.tvWListEmpty.visibility = View.GONE
    }

}
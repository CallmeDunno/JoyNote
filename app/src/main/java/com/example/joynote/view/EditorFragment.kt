package com.example.joynote.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.joynote.data.Notes
import com.example.joynote.databinding.FragmentEditorBinding
import com.example.joynote.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        initView(arguments)
        initAction()
    }

    private fun initView(arguments: Bundle?) {
        binding.tvDateTimeEditor.text = getCurrentTime()
        if (arguments != null) {
            val action = EditorFragmentArgs.fromBundle(arguments).note
            action?.let {
                this.id = action.id
                binding.apply {
                    edtTitleEditor.setText(action.title)
                    edtContentEditor.setText(action.content)
                    tvDateTimeEditor.text = action.date
                    btnImportantNoteEditor.isChecked = action.important
                }
            }
        }
    }

    private fun initAction() {
        binding.apply {
            btnBackEditor.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            btnDoneEditor.setOnClickListener {

                val content = binding.edtContentEditor.text.toString()
                val title = binding.edtTitleEditor.text.toString()
                val important = binding.btnImportantNoteEditor.isChecked

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(requireContext(), "The content is blank", Toast.LENGTH_LONG).show()
                } else {
                    if (TextUtils.isEmpty(title)){
                        viewModel.insertNote(Notes(id, "No title", content, getCurrentTime(), important))
                    } else {
                        viewModel.insertNote(Notes(id, title, content, getCurrentTime(), important))
                    }
                    requireView().findNavController().popBackStack()
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val now = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        return formatter.format(now)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
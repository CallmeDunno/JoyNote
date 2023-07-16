package com.example.joynote.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.joynote.data.Notes
import com.example.joynote.databinding.ListNoteBinding
import com.example.joynote.interfaces.IHomeItemClick

class NoteAdapter(diffUtil: DiffUtil.ItemCallback<Notes>) :
    ListAdapter<Notes, NoteAdapter.NoteVH>(diffUtil) {

    private lateinit var iHomeItemClick: IHomeItemClick

    fun setOnClickItem(iHomeItemClick: IHomeItemClick) {
        this.iHomeItemClick = iHomeItemClick
    }

    inner class NoteVH(itemBinding: ListNoteBinding, private val iHomeItemClick: IHomeItemClick) :
        ViewHolder(itemBinding.root) {

        private var itemBinding: ListNoteBinding? = null

        init {
            this.itemBinding = itemBinding
        }

        @SuppressLint("SetTextI18n")
        fun bindData(note: Notes) {
            itemBinding!!.apply {
                itemView.setOnClickListener {
                    iHomeItemClick.onClickItem(note)
                }
                itemView.setOnLongClickListener {
                    note.id?.let { id -> iHomeItemClick.onLongClickItem(id) }
                    return@setOnLongClickListener true
                }
                tvTitle.text = note.title
                if (note.content.length > 200) tvContent.text = "${note.content.subSequence(0, 200)}..."
                else tvContent.text = note.content
                tvDateTime.text = note.date
                if (note.important) imgImportantNoteHome.visibility = View.VISIBLE
                else imgImportantNoteHome.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val itemBinding =
            ListNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteVH(itemBinding, iHomeItemClick)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        getItem(position)?.let {
            holder.bindData(it)
        }
    }

}
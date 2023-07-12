package com.example.joynote.interfaces

import com.example.joynote.data.Notes

interface IHomeItemClick {
    fun onClickItem(note: Notes)
    fun onLongClickItem(id: Int)
}
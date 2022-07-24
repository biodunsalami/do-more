package com.example.domore.adapter

interface TaskInfoInterface {
    fun onCardClicked(position: Int)

    fun onFavouriteClicked(position: Int, isFavourite: Boolean)

    fun onDoneClicked(position: Int, isDone: Boolean)


}
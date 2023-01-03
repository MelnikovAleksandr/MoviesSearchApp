package ru.asmelnikov.android.moviesearchapp.utils

class Events<out T>(private val content: T) {

    private var hasBeenHandler = false

    fun getContentIfNotHandled(): T? {
        return if (!hasBeenHandler) {
            hasBeenHandler = true
            content
        } else {
            null
        }
    }

    fun peekContent(): T = content

}
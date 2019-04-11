package com.twitter.callback

interface OnError<ERROR> {
    fun onError(error: ERROR)
}
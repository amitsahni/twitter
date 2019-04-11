package com.twitter.callback

interface OnSuccess<RESULT> {
    fun onSuccess(result: RESULT)
}
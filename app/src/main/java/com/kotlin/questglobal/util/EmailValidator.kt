package com.kotlin.questglobal.util

import java.util.regex.Pattern

class EmailValidator  {
    fun checkIsValid(email:String, pwd:String):Boolean{
        return isValidEmail(email) && isValidPassword(pwd)
    }

    companion object {
        val EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        fun isValidEmail(email: CharSequence?): Boolean {
            return email != null && EMAIL_PATTERN.matcher(email).matches()
        }

        fun isValidPassword(pass: CharSequence?): Boolean {
            return pass != null &&  pass.length > 5
        }
    }
}
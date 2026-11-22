package com.jsbv99.gymmate.data.auth

fun filterName(value: String): String = value.filter { it.isLetter() || it == ' ' }
fun isValidName(value: String): Boolean = value == filterName(value) && value.count { it.isLetter() } >= 3

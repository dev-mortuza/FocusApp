package com.example.focusapp.domain.entity

enum class FocusMode(val durationMinutes: Int) {
    FOCUS(25),
    SHORT_BREAK(5),
    LONG_BREAK(15);

    val durationSeconds: Long = durationMinutes * 60L
}


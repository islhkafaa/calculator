package app.calculator.domain

import java.util.UUID

data class HistoryEntry(
    val expression: String,
    val result: String,
    val id: String = UUID.randomUUID().toString()
)

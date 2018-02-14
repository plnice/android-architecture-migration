package com.github.plnice.archmigration.utils

import android.database.Cursor
import kotlin.coroutines.experimental.buildSequence

fun <T> Cursor.toSequence(transform: (Cursor) -> T) = buildSequence {
    if (moveToFirst()) {
        do {
            yield(transform(this@toSequence))
        } while (moveToNext())
    }
}

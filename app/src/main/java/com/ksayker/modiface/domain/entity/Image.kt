package com.ksayker.modiface.domain.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val IMAGE_TABLE_NAME = "image"

const val URI_COLUMN = "uri"
const val DATE_COLUMN = "date"

@Parcelize
@Entity(tableName = IMAGE_TABLE_NAME)
data class Image constructor(
    @PrimaryKey
    @ColumnInfo(name = URI_COLUMN)
    val uri: String,

    @ColumnInfo(name = DATE_COLUMN)
    val date: Long = System.currentTimeMillis()
) : Parcelable
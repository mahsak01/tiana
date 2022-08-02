package com.example.tianaserver.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "servers")
@Parcelize
data class Server(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val address: String,
    val reporting: String,
    @ColumnInfo(name = "is_default")
    var isDefault: Boolean
) : Parcelable {


}

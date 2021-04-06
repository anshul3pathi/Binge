package com.example.binge.model.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.binge.randomNumberGenerator
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Entity(tableName = "movies")
data class Movies(
    @Json(name = "actors") val actors: List<String>,
    @Json(name = "desc") val desc: String,
    @Json(name = "directors") val directors: List<String>,
    @Json(name = "genre") val genre: List<String>,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "thumb_url") val thumbNailUrl: String,
    @Json(name = "imdb_url") val imdbUrl: String,
    @Json(name = "name") val movieName: String,
    @Json(name = "rating") val rating: Double,
    @Json(name = "year") val year: Long,
) : Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = randomNumberGenerator()
}

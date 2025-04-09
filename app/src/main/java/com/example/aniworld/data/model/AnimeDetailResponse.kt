package com.example.aniworld.data.model

import com.google.gson.annotations.SerializedName

data class AnimeDetailResponse(
    @SerializedName("data")
    val anime: AnimeDetail
)

data class AnimeDetail(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("score")
    val score: Double?,
    @SerializedName("images")
    val images: AnimeImages,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("trailer")
    val trailer: Trailer?,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("status")
    val status: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("rating")
    val rating: String?
)

data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("embed_url")
    val embedUrl: String?,
    @SerializedName("images")
    val images: TrailerImages?
)

data class TrailerImages(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("medium_image_url")
    val mediumImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?,
    @SerializedName("maximum_image_url")
    val maximumImageUrl: String?
)

data class Genre(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
) 
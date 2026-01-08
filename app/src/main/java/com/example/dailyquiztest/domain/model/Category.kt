package com.example.dailyquiztest.domain.model

import androidx.annotation.StringRes
import com.example.dailyquiztest.R

enum class Category(val apiId: Int, @StringRes val textId: Int) {
    GENERAL_KNOWLEDGE(9, R.string.general_knowledge),
    BOOKS(10, R.string.entertainment_books),
    FILM(11, R.string.entertainment_film),
    MUSIC(12, R.string.entertainment_music),
    MUSICALS_AND_THEATRES(13, R.string.entertainment_musicals_theatres),
    TELEVISION(14, R.string.entertainment_television),
    VIDEO_GAMES(15, R.string.entertainment_video_games),
    BOARD_GAMES(16, R.string.entertainment_board_games),
    SCIENCE_AND_NATURE(17, R.string.science_nature),
    SCIENCE_AND_COMPUTERS(18, R.string.science_computers),
    SCIENCE_AND_MATHEMATICS(19, R.string.science_mathematics),
    MYTHOLOGY(20, R.string.mythology),
    SPORTS(21, R.string.sports),
    GEOGRAPHY(22, R.string.geography),
    HISTORY(23, R.string.history),
    POLITICS(24, R.string.politics),
    ART(25, R.string.art),
    CELEBRITIES(26, R.string.celebrities),
    ANIMALS(27, R.string.animals),
    VEHICLES(28, R.string.vehicles),
    COMICS(29, R.string.entertainment_comics),
    SCIENCE_AND_GADGETS(30, R.string.science_gadgets),
    JAPANESE(31, R.string.entertainment_japanese_anime_manga),
    CARTOON_AND_ANIMATIONS(32, R.string.entertainment_cartoon_animations)
}
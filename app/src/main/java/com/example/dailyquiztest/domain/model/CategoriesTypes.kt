package com.example.dailyquiztest.domain.model

enum class CategoriesTypes(val apiId: Int, val categoryName: String) {
    GENERAL_KNOWLEDGE(9, "General Knowledge"),
    BOOKS(10, "Entertainment: Books"),
    FILM(11, "Entertainment: Film"),
    MUSIC(12, "Entertainment: Music"),
    MUSICALS_AND_THEATRES(13, "Entertainment: Musicals & Theatres"),
    TELEVISION(14, "Entertainment: Television"),
    VIDEO_GAMES(15, "Entertainment: Video Games"),
    BOARD_GAMES(16, "Entertainment: Board Games"),
    SCIENCE_AND_NATURE(17, "Science & Nature"),
    SCIENCE_AND_COMPUTERS(18, "Science: Computers"),
    SCIENCE_AND_MATHEMATICS(19, "Science: Mathematics"),
    MYTHOLOGY(20, "Mythology"),
    SPORTS(21, "Sports"),
    GEOGRAPHY(22, "Geography"),
    HISTORY(23, "History"),
    POLITICS(24, "Politics"),
    ART(25, "Art"),
    CELEBRITIES(26, "Celebrities"),
    ANIMALS(27, "Animals"),
    VEHICLES(28, "Vehicles"),
    COMICS(29, "Entertainment: Comics"),
    SCIENCE_AND_GADGETS(30, "Science: Gadgets"),
    JAPANESE(31, "Entertainment: Japanese Anime & Manga"),
    CARTOON_AND_ANIMATIONS(32, "Entertainment: Cartoon & Animations");

    companion object {
        fun from(categoryName: String): CategoriesTypes =
            requireNotNull(CategoriesTypes.entries.find { it.categoryName == categoryName }) { "No found Categories with type $categoryName" }
    }
}
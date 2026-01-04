package com.example.dailyquiztest.data.model.network.model

import com.google.gson.annotations.SerializedName

//{
//    "type":"multiple",
//    "difficulty":"easy",
//    "category":"General Knowledge",
//    "question":"In &quot;Katamari Damacy&quot;, you control a character known as:",
//    "correct_answer":"The Prince",
//    "incorrect_answers":["Fujio", "Ichigo ", "Foomin"]
//}
data class NetworkQuizQuestion(
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("question")
    val question: String? = null,
    @SerializedName("correct_answer")
    val correctAnswer: String? = null,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>? = null,
) {

    fun <T> map(mapper: Mapper<T>) : T {
        return mapper.mappedValue(
            question = this.question ?: "",
            incorrectAnswers = this.incorrectAnswers ?: emptyList(),
            correctAnswer = this.correctAnswer ?: "",
            type = this.type ?: ""
        )
    }

    interface Mapper<T> {
        fun mappedValue(
            question: String,
            incorrectAnswers: List<String>,
            correctAnswer: String,
            type: String
        ): T
    }
}

data class NetworkQuizQuestionsList(
    @SerializedName("response_code")
    val responseCode: Int = -1,
    @SerializedName("results")
    val result: List<NetworkQuizQuestion>? = null
)
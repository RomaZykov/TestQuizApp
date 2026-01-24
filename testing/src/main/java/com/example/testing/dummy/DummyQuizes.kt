package com.example.testing.dummy

import com.example.dailyquiztest.domain.model.QuestionTypes
import com.example.dailyquiztest.domain.model.QuizQuestion

val stubQuizes = listOf(
    QuizQuestion(
        question = "Test question 1",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 2",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        type = QuestionTypes.BOOLEAN.typeApi
    ),
    QuizQuestion(
        question = "Test question 3",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuestionTypes.BOOLEAN.typeApi
    ),
    QuizQuestion(
        question = "Test question 4",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 5",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 6",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 7",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 8",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 9",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 10",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 11",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 12",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        type = QuestionTypes.BOOLEAN.typeApi
    ),
    QuizQuestion(
        question = "Test question 13",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    ),
    QuizQuestion(
        question = "Test question 14",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuestionTypes.BOOLEAN.typeApi
    ),
    QuizQuestion(
        question = "Test question 15",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuestionTypes.MULTIPLE.typeApi
    )
)
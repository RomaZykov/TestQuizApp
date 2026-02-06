package com.example.testing.stub

import com.example.dailyquiztest.domain.model.QuizTypeDomain
import com.example.dailyquiztest.domain.model.QuizDomain

val stubQuizes = listOf(
    QuizDomain(
        question = "Test question 1",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 2",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        type = QuizTypeDomain.BOOLEAN
    ),
    QuizDomain(
        question = "Test question 3",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuizTypeDomain.BOOLEAN
    ),
    QuizDomain(
        question = "Test question 4",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 5",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 6",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 7",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 8",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 9",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 10",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 11",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 12",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        type = QuizTypeDomain.BOOLEAN
    ),
    QuizDomain(
        question = "Test question 13",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    ),
    QuizDomain(
        question = "Test question 14",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuizTypeDomain.BOOLEAN
    ),
    QuizDomain(
        question = "Test question 15",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE
    )
)
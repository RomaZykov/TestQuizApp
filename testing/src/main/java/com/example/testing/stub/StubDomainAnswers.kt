package com.example.testing.stub

import com.example.dailyquiztest.domain.model.QuizDomain
import com.example.dailyquiztest.domain.model.QuizTypeDomain

/**
 * Size of answers = [15]
 */
val stubDomainAnswers = listOf(
    QuizDomain.Quiz(
        question = "Test question 1",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 2",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuizTypeDomain.BOOLEAN,
        userAnswer = "false",
        isAnsweredCorrect = false
    ),
    QuizDomain.Quiz(
        question = "Test question 3",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "inCorrect 1",
        isAnsweredCorrect = false
    ),
    QuizDomain.Quiz(
        question = "Test question 4",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 5",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        type = QuizTypeDomain.BOOLEAN,
        userAnswer = "true",
        isAnsweredCorrect = false
    ),
    QuizDomain.Quiz(
        question = "Test question 6",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuizTypeDomain.BOOLEAN,
        userAnswer = "false",
        isAnsweredCorrect = false
    ),
    QuizDomain.Quiz(
        question = "Test question 7",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 8",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 9",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        type = QuizTypeDomain.BOOLEAN,
        userAnswer = "false",
        isAnsweredCorrect = false
    ),
    QuizDomain.Quiz(
        question = "Test question 10",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 11",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 12",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 13",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 14",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "correct",
        isAnsweredCorrect = true
    ),
    QuizDomain.Quiz(
        question = "Test question 15",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        type = QuizTypeDomain.MULTIPLE,
        userAnswer = "inCorrect 3",
        isAnsweredCorrect = false
    ),
)
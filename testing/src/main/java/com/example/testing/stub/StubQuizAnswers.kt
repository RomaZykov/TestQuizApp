package com.example.testing.stub

import com.example.dailyquiztest.domain.model.CategoryDomain
import com.example.dailyquiztest.domain.model.DifficultyDomain
import com.example.dailyquiztest.domain.model.QuestionTypeDomain
import com.example.dailyquiztest.presentation.feature.quiz.model.QuizUi

/**
 * Size of quiz answers = [15]
 */
val stubQuizAnswers = listOf(
    QuizUi(
        number = 1,
        question = "Test question 1",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 2,
        question = "Test question 2",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionTypeDomain = QuestionTypeDomain.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 3,
        question = "Test question 3",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("inCorrect 1"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 4,
        question = "Test question 4",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 5,
        question = "Test question 5",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        questionTypeDomain = QuestionTypeDomain.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("true"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 6,
        question = "Test question 6",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionTypeDomain = QuestionTypeDomain.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 7,
        question = "Test question 7",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 8,
        question = "Test question 8",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 9,
        question = "Test question 9",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionTypeDomain = QuestionTypeDomain.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 10,
        question = "Test question 10",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 11,
        question = "Test question 11",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 12,
        question = "Test question 12",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 13,
        question = "Test question 13",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 14,
        question = "Test question 14",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        number = 15,
        question = "Test question 15",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionTypeDomain = QuestionTypeDomain.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("inCorrect 3", "inCorrect 2"),
        isAnsweredCorrect = false,
        categoryDomain = CategoryDomain.FILM,
        difficultyDomain = DifficultyDomain.HARD,
//        timerDialogUi = DialogUiState.NoDialog
    )
)
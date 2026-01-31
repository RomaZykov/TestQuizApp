package com.example.testing.dummy

import com.example.dailyquiztest.domain.model.Category
import com.example.dailyquiztest.domain.model.Difficulty
import com.example.dailyquiztest.domain.model.QuestionType
import com.example.dailyquiztest.presentation.features.quiz.model.QuizUi
import com.example.dailyquiztest.presentation.features.quiz.model.small_screen.DialogUiState

/**
 * Size of quiz answers = [15]
 */
val stubQuizAnswers = listOf(
    QuizUi(
        currentNumberQuestion = 1,
        question = "Test question 1",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 2,
        question = "Test question 2",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionType = QuestionType.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 3,
        question = "Test question 3",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("inCorrect 1"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 4,
        question = "Test question 4",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 5,
        question = "Test question 5",
        incorrectAnswers = listOf("true"),
        correctAnswer = "false",
        questionType = QuestionType.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("true"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 6,
        question = "Test question 6",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionType = QuestionType.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 7,
        question = "Test question 7",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 8,
        question = "Test question 8",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 9,
        question = "Test question 9",
        incorrectAnswers = listOf("false"),
        correctAnswer = "true",
        questionType = QuestionType.BOOLEAN,
        totalQuestions = 15,
        userAnswers = listOf("false"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 10,
        question = "Test question 10",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 11,
        question = "Test question 11",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 12,
        question = "Test question 12",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 13,
        question = "Test question 13",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 14,
        question = "Test question 14",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("correct"),
        isAnsweredCorrect = true,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    ),
    QuizUi(
        currentNumberQuestion = 15,
        question = "Test question 15",
        incorrectAnswers = listOf("inCorrect 1", "inCorrect 2", "inCorrect 3"),
        correctAnswer = "correct",
        questionType = QuestionType.MULTIPLE,
        totalQuestions = 15,
        userAnswers = listOf("inCorrect 3", "inCorrect 2"),
        isAnsweredCorrect = false,
        category = Category.FILM,
        difficulty = Difficulty.HARD,
        timerDialogUi = DialogUiState.NoDialog
    )
)
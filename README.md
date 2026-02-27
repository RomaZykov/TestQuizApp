<p align="center">
  <a href="https://codecov.io/gh/username/repo">
    <img src="https://github.com/RomaZykov/TestQuizApp/actions/workflows/android_ui_tests.yml/badge.svg">
  </a>
  <a href="https://codecov.io/gh/username/repo">
    <img src="https://github.com/RomaZykov/TestQuizApp/actions/workflows/android_unit_tests.yml/badge.svg">
  </a>
</p>

<p align="center">
  <a href="https://github.com/RomaZykov/TestQuizApp/blob/master/README.md">
    <img src="https://img.shields.io/badge/lang-en-blue" />
  </a>
  <a href="https://github.com/RomaZykov/TestQuizApp/blob/master/README.ru.md">
    <img src="https://img.shields.io/badge/%D1%8F%D0%B7%D1%8B%D0%BA-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9-purple" />
  </a>
</p>
<p align="center">

<h1 align="center">TestQuizApp <br /></h1>

## Description
  TestQuizApp — an application for passing quizzes. The user can start the quiz at any time, complete 5 (depends on difficulty) questions and save the result. A history of previous attempts is also available with the ability to view details.

## Screenshots

## Stack
  - Kotlin (KTS), Kotlin coroutines, Flow
  - MVVM, Clean Architecture
  - Single-Activity
  - Jetpack Compose
  - Hilt
  - Room

## Features (client)
  - Quiz by category with 3 types of difficulties
  - Timer - time gradation depending on the difficulty
  - View quiz results with a brief description and date
  - State saving during orientation/configuration change (okak)

## Features (dev)
  - 35 UI tests of various levels of complexity in the OOP style:
      1. Simple texts/screens checks;
      2. Timer tests with different time intervals;
      3. End-to-end navigation tests;
      4. Configuration change tests;
      5. Tests of the horizontal position of the screen with the transition to verticality;
      6. Testing the appearance of dialogs and snack bars;
      7. Resource tests.

  - 19 UNIT tests which include:
      1. viewModel`s tests;
      2. navigation invokation tests;
      3. repository tests;
      4. mapper tests.

  - CI/CD for UNIT and UI tests
  - OOP approach in design (not all), patterns used:
      1. prototype;
      2. singleton;
      3. visitor;
      4. strategy;
      5. decorator.

## UI tests highlights
  - [End-to-end tests here](https://github.com/RomaZykov/TestQuizApp/blob/master/app/src/androidTest/java/com/example/dailyquiztest/ScenarioTest.kt)

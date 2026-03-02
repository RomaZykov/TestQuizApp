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

<a href="https://drive.google.com/drive/folders/12di8CXu-fQpZOwDtYse1PMIF_siAOQMY?ths=true">
    <strong>Download APK</strong>
</a>

## Description
  TestQuizApp — an application for passing quizzes. The user can start the quiz at any time, complete 5 (depends on difficulty) questions and save the result. A history of previous attempts is also available with the ability to view details.

## Screenshots
<p align="center">
<img src="https://github.com/RomaZykov/TestQuizApp/blob/master/demo/DailyQuiz%201.png">
</p>
<p align="center">
<img src="https://github.com/RomaZykov/TestQuizApp/blob/master/demo/DailyQuiz%202.png">
</p>

## Video

https://github.com/user-attachments/assets/a080b1a3-a863-4fe5-804d-cafe713157db

## Stack
  - Kotlin (KTS), Kotlin coroutines, Flow
  - MVVM, Clean Architecture
  - Single-Activity
  - Jetpack Compose
  - Hilt
  - Room
  - Retrofit

## Features (client)
  - Quiz by category with 3 types of difficulties (with time gradation depending on the difficulty)
  - History with a brief description and date
  - Instant verification of the response
  - Getting questions from the Open Trivia API

## Features (dev)
  - State saving during orientation/configuration change (okak)
  - CI/CD for UNIT and UI tests
  - 35 UI tests of various levels of complexity in the OOP style:
      - :one: Simple texts/screens checks;
      - :two: Timer tests with different time intervals;
      - 3️⃣ End-to-end navigation tests;
      - 4️⃣ Configuration change tests;
      - 5️⃣ Tests of the horizontal position of the screen with the transition to verticality;
      - :six: Testing the appearance of dialogs and snack bars;
      - :seven: Resource tests.

  - 19 UNIT tests which include:
      - :one: viewModel`s tests;
      - :two: navigation invokation tests;
      - :three: repository tests;
      - :four: mapper tests.

  - OOP approach in design (not all), patterns used:
      - :one: prototype;
      - :two: singleton;
      - 3️⃣ visitor;
      - 4️⃣ strategy;
      - 5️⃣ decorator.

## UI tests
  - [End-to-end tests here](https://github.com/RomaZykov/TestQuizApp/blob/master/app/src/androidTest/java/com/example/dailyquiztest/ScenarioTest.kt)

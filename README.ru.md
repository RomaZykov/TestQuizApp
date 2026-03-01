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
    <img src="https://img.shields.io/badge/lang-en-purple" />
  </a>
  <a href="https://github.com/RomaZykov/TestQuizApp/blob/master/README.ru.md">
    <img src="https://img.shields.io/badge/%D1%8F%D0%B7%D1%8B%D0%BA-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9-blue" />
  </a>
</p>
<p align="center">

<h1 align="center">TestQuizApp <br /></h1>

## Описание
  TestQuizApp — приложение для прохождения коротких викторин. Пользователь может запустить викторину в любое время, пройти 5 вопросов и сохранить результат. Также доступна история предыдущих попыток с возможностью просмотра деталей.

## Скриншоты
<p align="center">
<img src="https://github.com/RomaZykov/TestQuizApp/blob/master/demo/DailyQuiz%201.png">
</p>
<p align="center">
<img src="https://github.com/RomaZykov/TestQuizApp/blob/master/demo/DailyQuiz%202.png">
</p>

## Видео

https://github.com/user-attachments/assets/8abbf8d7-1759-4775-be6b-34503a1749b4

## Технологии
  - Kotlin (KTS), Kotlin coroutines, Flow
  - MVVM, Clean Architecture
  - Single-Activity
  - Jetpack Compose
  - Hilt
  - Room

## Особенности (клиент)
  - Квиз по категориям с 3 видами сложностями
  - Таймер - градация времени в зависимости от сложности
  - Просмотр результатов квизов с кратким описанием и датой
  - Перевороты экрана с сохранением состояния (окак)

## Особенности (разработка)
  - CI/CD для прохождения UNIT и UI тестов
  - 35 UI тестов различного уровня сложности в ООП стиле:
      - :one: обычные проверки текста/нужных экранов;
      - :two: тесты таймера с различными временными отрезками;
      - 3️⃣: end-to-end тесты навигации;
      - 4️⃣: тесты при смене конфигурации;
      - 5️⃣: тесты горизонтального положения экрана с переходом в вертикальность;
      - 6️⃣: тесты появления диалогов и снэкбаров;
      - 7️⃣: тесты ресурсов.

  - 19 UNIT тестов, куда входит:
      - :one: тесты viewModel`ей;
      - :two: тесты вызова навигации;
      - 3️⃣ тесты репозиториев;
      - 4️⃣ тесты data мапперов.

  - OOP подход в проектировании (не всё), используемые паттерны:
      - :one: прототип;
      - :two: одиночка;
      - 3️⃣ посетитель;
      - 4️⃣ стратегия;
      - 5️⃣ декоратор.

## UI тесты
  - [End-to-end тесты находятся здесь](https://github.com/RomaZykov/TestQuizApp/blob/master/app/src/androidTest/java/com/example/dailyquiztest/ScenarioTest.kt)


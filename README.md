# Diplom_2: API-тесты для Stellar Burgers

[![Java](https://img.shields.io/badge/Java-11-blue?logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-orange?logo=apache-maven)](https://maven.apache.org/)
[![Allure](https://img.shields.io/badge/Allure-2.15.0-ff69b4)](https://docs.qameta.io/allure/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.5.0-green)](https://rest-assured.io/)

Тестовый проект для проверки API сервиса Stellar Burgers. Включает тесты создания пользователя, авторизации, иземенения данных, а также тесты с заказами пользователя.

## Технологический стек

| Компонент               | Версия   | Назначение                          |
|-------------------------|----------|-------------------------------------|
| Java                    | 11       | Базовый язык разработки             |
| JUnit 4                | 4.13.2   | Фреймворк для unit-тестирования     |
| RestAssured            | 5.5.0    | Библиотека для API-тестирования     |
| Allure Framework       | 2.15.0   | Генератор отчётов                   |
| Lombok                | 1.18.34  | Автогенерация boilerplate-кода      |
| JavaFaker             | 1.0.2    | Генерация тестовых данных           |

## Настройка окружения

1. **Установите зависимости**:
    - JDK 11+
    - Maven 3.9.0+
    - Git (опционально)

2. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/your-username/Diplom_2.git
   cd Diplom_2
3. **Соберите проект**:
    ```bash
    mvn clean install

4. **Запуск тестов**
```bash
Стандартный запуск всех тестов:
    mvn clean test

Запуск с генерацией Allure-отчёта:
    mvn clean test allure:report
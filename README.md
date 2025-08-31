# 📝 ToDo List — веб-приложение на Spring MVC

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?logo=java)](https://www.oracle.com/java/)
[![Spring](https://img.shields.io/badge/Spring-6.2.10-6DB33F?logo=spring)](https://spring.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15%2B-336791?logo=postgresql)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6.6.25-59666C?logo=hibernate)](https://hibernate.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![Tests](https://img.shields.io/badge/tests-85%20passed-success)](#-тестирование)

**ToDo List** — веб-приложение для управления списком задач с современным интерфейсом и полным покрытием тестами.  
Построено на **Spring MVC 6.2.10** с **Hibernate ORM** и **PostgreSQL**, использует **JSP + CSS** для фронтенда.

> 🎯 **Видение**: простое, но функциональное приложение для управления задачами с возможностью фильтрации, статистики и полным покрытием тестами. Готово к развертыванию на **Apache Tomcat** или **Docker**.

---

## 🧭 Зачем это нужно

- **Управление задачами**: создание, выполнение, удаление задач с интуитивным интерфейсом
- **Фильтрация и статистика**: просмотр активных/выполненных задач с счетчиками
- **Полное тестирование**: 85 тестов обеспечивают надежность и качество кода
- **Готовность к продакшену**: чистая архитектура, конфигурация через properties

---

## 🛠️ Используемые технологии (кратко)

- **Java 17+**: современный язык с поддержкой records, pattern matching
- **Spring MVC 6.2.10**: веб-фреймворк с аннотациями, DI, транзакциями
- **Hibernate 6.6.25**: ORM с JPA, автоматическая генерация схемы
- **PostgreSQL**: основная БД, H2 для тестов
- **JSP + JSTL**: серверный рендеринг с Jakarta Tags
- **Maven**: сборка, управление зависимостями, тестирование
- **JUnit 5 + Mockito**: юнит и интеграционные тесты
- **JaCoCo**: покрытие кода тестами

---

## ⚙️ Установка и запуск

### 1) Клонирование
```bash
git clone <repository-url>
cd ToDoListWebAppliacation
```

### 2) Конфигурация базы данных
Создайте базу данных PostgreSQL:
```sql
CREATE DATABASE ToDoList;
```

Настройте подключение в `src/main/resources/db.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/ToDoList
db.username=your_username
db.password=your_password
```

### 3) Сборка и запуск
```bash
# Сборка проекта
mvn clean package

# Развертывание на Tomcat
cp target/to-do-list-web-application.war $TOMCAT_HOME/webapps/

# Или запуск через Maven Tomcat Plugin
mvn tomcat7:run
```

Приложение будет доступно по адресу `http://localhost:8080/to-do-list-web-application/`

---

## 📡 Функциональность (Web Interface)

### Главная страница (`/home`)
- **Отображение задач**: список всех задач с возможностью фильтрации
- **Статистика**: счетчики активных и выполненных задач
- **Фильтры**: All, Active, Done с сохранением состояния

### Операции с задачами

#### Создание задачи
- **Форма**: текстовое поле для ввода названия
- **Валидация**: пустые задачи не сохраняются
- **Статус**: новые задачи создаются со статусом `ACTIVE`

#### Выполнение задачи
- **Кнопка**: зеленая галочка для активных задач
- **Действие**: изменение статуса на `DONE`
- **Визуал**: зачеркивание текста выполненных задач

#### Удаление задачи
- **Кнопка**: красный крестик для всех задач
- **Действие**: полное удаление из базы данных
- **Подтверждение**: без дополнительных диалогов

---

## 🧪 Тестирование

### Запуск тестов
```bash
# Все тесты
mvn clean test

# Без JaCoCo (рекомендуется для Java 17)
mvn clean test -Djacoco.skip=true

# Конкретный тест
mvn test -Dtest=RecordServiceTest
```

### Структура тестов (85 тестов)
```
src/test/java/ru/Artem/
├── controller/CommonControllerTest.java      # 19 тестов
├── service/RecordServiceTest.java            # 16 тестов  
├── dao/RecordDaoTest.java                    # 14 тестов
├── entity/RecordTest.java                    # 13 тестов
├── entity/RecordStatusTest.java              # 12 тестов
└── entity/dto/RecordsContainerDtoTest.java   # 11 тестов
```

### Типы тестирования
- **Юнит тесты**: с моками через Mockito
- **Интеграционные тесты**: с реальной H2 базой данных
- **Покрытие кода**: JaCoCo отчеты в `target/site/jacoco/`

---

## 🗂️ Структура проекта
```text
ToDoListWebAppliacation/
├── src/
│   ├── main/
│   │   ├── java/ru/Artem/
│   │   │   ├── controller/
│   │   │   │   └── CommonController.java          # Обработка HTTP запросов
│   │   │   ├── service/
│   │   │   │   └── RecordService.java             # Бизнес-логика
│   │   │   ├── dao/
│   │   │   │   └── RecordDao.java                 # Доступ к данным
│   │   │   ├── entity/
│   │   │   │   ├── Record.java                    # Сущность задачи
│   │   │   │   ├── RecordStatus.java              # Enum статусов
│   │   │   │   └── dto/
│   │   │   │       └── RecordsContainerDto.java   # DTO для передачи данных
│   │   │   └── config/
│   │   │       ├── WebConfig.java                 # Конфигурация Spring MVC
│   │   │       ├── PersistenceConfig.java         # Конфигурация JPA/Hibernate
│   │   │       └── ApplicationInitializer.java    # Инициализация приложения
│   │   ├── resources/
│   │   │   └── db.properties                      # Конфигурация БД
│   │   └── webapp/
│   │       ├── WEB-INF/views/
│   │       │   └── main-page.jsp                  # Главная страница
│   │       └── resources/css/
│   │           └── main-page.css                  # Стили
│   └── test/java/ru/Artem/                        # 85 тестов
├── pom.xml                                        # Maven конфигурация
└── README.md                                      # Документация
```

---

## 🧱 Схема БД (Hibernate Auto-Generation)

**records**
- `id BIGINT IDENTITY PRIMARY KEY` (автоинкремент)
- `title VARCHAR(255) NOT NULL` (название задачи)
- `status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'` (ACTIVE/DONE)
- Индексы: автоматически по первичному ключу

**Конфигурация Hibernate**:
- `hibernate.hbm2ddl.auto=update` (автообновление схемы)
- `hibernate.dialect=PostgreSQLDialect`
- `hibernate.show_sql=true` (логирование SQL)

---

## 🎨 Интерфейс

### Дизайн
- **Современный UI**: чистый дизайн с CSS Grid/Flexbox
- **Адаптивность**: корректное отображение на разных устройствах
- **Интерактивность**: hover-эффекты, анимации кнопок
- **Иконки**: SVG иконки для действий (галочка, крестик)

### Цветовая схема
- **Основной**: белый фон с серыми акцентами
- **Активные задачи**: черный текст
- **Выполненные задачи**: зачеркнутый серый текст
- **Кнопки**: зеленая галочка, красный крестик

---

## 🔧 Конфигурация

### Переменные окружения
```properties
# База данных
db.url=jdbc:postgresql://localhost:5432/ToDoList
db.username=postgres
db.password=your_password

# Hibernate
hibernate.hbm2ddl.auto=update
hibernate.show_sql=true
hibernate.format_sql=true
```

### Maven профили
```bash
# Разработка
mvn clean package

# Тестирование
mvn clean test

# Пропуск тестов
mvn clean package -DskipTests
```


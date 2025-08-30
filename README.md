# ToDo List Web Application

## Стек технологий 🧰

* Java, Maven, WAR
* Spring Web MVC, Spring ORM, Spring JDBC, Spring Transaction
* Jakarta Servlet API, JSP, JSTL
* Jakarta Persistence (JPA), Hibernate (ORM)
* PostgreSQL (JDBC-драйвер)
* Apache Tomcat (сервер приложений)
* Lombok, Apache Commons Lang

## Зачем нужен ToDo List? 🧭

* Помогает планировать задачи и ничего не забывать.
* Держит список дел перед глазами, снижая «ментальный шум».
* Подходит для личного использования, учебных проектов и быстрых прототипов.

## Чем отличается ✨

В отличие от многих pet-проектов, здесь есть **интерактивная фронт-часть (JSP/JSTL)**:

* можно добавлять задачи, отмечать/снимать выполнение и удалять их прямо в браузере;
* изменения мгновенно отражаются и сохраняются в **PostgreSQL** через **Hibernate/JPA**;
* развёртывание выполняется стандартным **WAR** на **Tomcat** (совместимо с Jakarta).

## Основные возможности ✅

* Создание задач с заголовком.
* Просмотр всех задач с сортировкой по ID.
* Фильтрация по статусам: `ACTIVE` / `DONE`.
* Переключение статуса задачи.
* Удаление задач.
* Персистентное хранение в базе данных.

## Установка ⚙️

### Требования

* JDK 17+
* Maven
* PostgreSQL
* Apache Tomcat (совместимый с Jakarta Servlet)

### Шаги

1. Клонируйте репозиторий:

   ```bash
   git clone <repo-url>
   cd to-do-list-web-application
   ```
2. Создайте базу данных:

   ```sql
   CREATE DATABASE "ToDoList";
   ```
3. Добавьте файл настроек `src/main/resources/db.properties`:

   ```
   db.url=jdbc:postgresql://localhost:5432/ToDoList
   db.username=postgres
   db.password=CHANGE_ME
   ```

   > Файл `db.properties` включён в `.gitignore`. Храните в репозитории только шаблон `db.properties.example`.
4. Соберите проект:

   ```bash
   mvn clean package
   ```

## Инструкция по запуску 🚀

### Для разработчика (IntelliJ IDEA)

1. Настройте локальный Tomcat (Run/Debug Configurations → Tomcat Server).
2. Выберите артефакт: `to-do-list-web-application:war exploded`.
3. Убедитесь, что PostgreSQL запущен и `db.properties` заполнен.
4. Запустите и откройте:

   ```
   http://localhost:8080/to-do-list-web-application/
   ```

### Для обычного пользователя (Tomcat вручную)

1. Разместите `target/to-do-list-web-application.war` в `${CATALINA_BASE}/webapps/`.
2. Запустите Tomcat.
3. Перейдите по адресу выше.

### Быстрый старт (user-инструкция)

* Введите заголовок и добавьте задачу.
* Кнопкой/переключателем меняйте статус `DONE` / `ACTIVE`.
* Фильтруйте задачи по статусу.
* Удаляйте ненужные — запись исчезает из списка и из БД.

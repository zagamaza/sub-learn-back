# Сервис ms-sublearn-back

## Установка, настройка и запуск

### Зависимости

* Java OpenJDK 11
* SpringBoot 2.1.6.RELEASE
* Gradle 5.4.1
* PostgreSQL 11.2
* Lombok 1.18.6


### Параметры приложения

Все конфигурации проекта лежат в файле
```
application.yaml
```

### Сборка приложения

Проект собирается gradle’ом из корневой папки при помощи скрипта:

```
build.sh  - Linux
build.bat - Windows
```

### Запуск приложения

Запустить приложение можно выполнив скрипт из корневой папки:
```
start.sh  - Linux 
start.bat - Windows
```
### Остановка приложения

Остановить приложение можно последовательно выполнив команды:
```
cd docker
docker-compose -f docker-compose-test.yaml down 
```

Для локального запуска БД можно последовательно выполнить команды:
```
cd docker
docker-compose -f docker-compose-dev.yaml up -d
```

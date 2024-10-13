# Дано:

### Описание задачи:
- Необходимо реализовать REST-API средствами языка программирования Java и фреймворка Spring.
- Выбор СУБД для хранения данных остаётся за Вами.
- Пункты помеченные звёздочкой, являются дополнительными.

### Обязательные условия:
1. *Реализовать возможность авторизации по логину и паролю (admin:admin). Тип авторизации разрешается выбрать с
   обоснованием: Base Auth, OAuth, JWT;
2. Реализовать CRUD модель для работы с пользователями (контактная информация);
3. Реализовать CRUD модель для работы с детальной информацией о
   пользователе;
4. *Реализовать CRUD модель для возможности работы с фотографией пользователя;

### Хранимая информации о пользователе:

1. Фамилия
2. Имя
3. Отчество
4. Дата рождения
5. Электронная почта
6. Номер телефона
7. *Фотография

#### Будет плюсом, если:

1. Выбранная Вами СУБД - PostgreSQL;
2. Проект будет покрыт автотестами;
3. В проекте будут присутствовать комментарии;
4. Для работы с базой данных будет использован JDBC / Hibernate;
5. *Хотя бы одно из дополнительных заданий в разделе обязательных будет выполнено;

#### Ожидаемые данные:

1. Исходный код готового проекта;
2. Экспортированный JSON из Postman для тестирования REST-API;

# Стек технологий
- Java 17
- Spring Boot
- Spring Security с защитой при помощи Jwt
- Junit 5
- Mockito
- Docker compose
- Lombok
- PostgreSQL
- FlyWay

# Схема базы данных

![Схема](/files/dataBaseChema1.png)

- База данных состоит из 2х схем
  - `users` - содержит чувствительную информацию, как: userName: имя пользователя для входа в систему и password: пароль
  - `user_credentials` - содержит личную информация о пользователе, имя, фамилия, отчество, дата рождения, email и номер телефона 

# Дополнительно выполнено:

#### Spring Security с защитой при помощи Jwt

- Была выбрана аутентификация по JWT. В связи с его распространённостью и безопасностью передачи данных.
- Основная причина достаточная надёжность шифрования. JSON Web Token – это открытый стандарт (RFC 7519), 
- который определяет компактный и автономный способ безопасной передачи информации между сторонами в виде объекта JSON.

#### Тестирование

- В проекте применено Moc тестирования пользовательских сервисов.

#### PostgreSQL

- В качестве базы данных была выбрана PostGreSql 12.3

#### FlyWay

- В качестве миграции применён FlyWay.

# Эндпоинты
1. Для регистрации на сервере:

## [POST] http://localhost:8080/auth/register

- Для регистрации на сервере используется запроса тело в виде json:

###

```
{
"firstName" : "Vasya",
"patronym" : "Petrovich",
"lastName" : "Sydorov",
"email" : "aaa11@mail.com",
"phoneNumber" : "+7(952)9815521",
"userName" : "admin",
"password" : "admin",
"birthDate" : "2001-01-11",
"roles": ["ROLE_USER", "ROLE_ADMIN"]
}

```

###

- Ответ сервера в виде json:

```

{
"userName": "admin",
"email": "aaa11@mail.com",
"firstname": "Vasya",
"patronym": "Petrovich",
"lastName": "Sydorov",
"phoneNumber": "+7(952)9815521",
"birthDat": "2001-01-11"
}

```

2. Для авторизации:

## [POST] http://localhost:8080/auth/signing

- Для авторизации на сервере используется запроса тело в виде json

## Запрос в виде json:

```
{
    "userName" : "admin",
    "password" : "admin"
}

```
##

- Ответ сервера в виде json:

```
 
{
    "id": 1,
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyODgzMjQ2MSwiZXhwIjoxNzI4ODMzMDYxfQ.RGmhDa4gfEFJ1T_UQMx6QBTTYBpLi2nzYe4aqEMG1bRGluLg6ojloQbK931nCS_qbTsk_SlJRs3qjS4W0idBrg",
    "refreshToken": "2444996b-a92b-4f32-a995-b030f3241d89",
    "username": "admin",
    "roles": [
    "ROLE_USER",
    "ROLE_ADMIN"
    ]
}

```
##

3. Для получения списка пользователей, содержащих только userName.

##

## [GET] http://localhost:8080/user/details/short

```
[
    {
    "firstName": "Vasya",
    "patronym": "Petrovich",
    "lastName": "Sydorov",
    "birthDate": "2001-01-11"
    },
    {
    "firstName": "Petr",
    "patronym": "Petrovich",
    "lastName": "Petrov",
    "birthDate": "2001-01-11"
    }
]

```

##

4. Для получения списка пользователей, содержащих все поля кроме password и username

##

## [GET] http://localhost:8080/user/details/full

##

```
[
{
"firstName": "Vasya",
"patronym": "Petrovich",
"lastName": "Sydorov",
"email": "aaa11@mail.com",
"phoneNumber": "+7(952)9815521",
"birthDate": "2001-01-11"
},
{
"firstName": "Petya",
"patronym": "Petrovich",
"lastName": "Petrov",
"email": "a1a11@mail.com",
"phoneNumber": "+7(952)9815531",
"birthDate": "2001-01-11"
}
]

```
##

5. Для получения данных пользователя по его id.

##

## [GET] http://localhost:8080/user/details/user/1

```

{
"firstName": "Vasya",
"patronym": "Petrovich",
"lastName": "Sydorov",
"email": "aaa11@mail.com",
"phoneNumber": "+7(952)9815521",
"birthDate": "2001-01-11"
}

```
##

6. Для обновления учётной записи в таблицах `users_credentials` и `users`. 

##

## [PUT]http://localhost:8080/user/details/user/1

## Тело запроса в виде json (пример):

```

{
"userId" : "1",
"username" : "vasya",
"firstName" : "Vasyliy"
}

```
- Ответ сервера в виде json:

```
{
"firstName": "Vasyliy",
"patronym": "Petrovich",
"lastName": "Sydorov",
"email": "aaa11@mail.com",
"phoneNumber": "+7(952)9815521",
"birthDate": "2001-01-11"
}

```

## [DELETE] http://localhost:8080/user/details/user/1

- Удаление пользователя по id. Удаляются все его записи в таблицах `users_credentials` и `users`.

# Запуск приложения:

- Для запуска Вам необходимо клонировать на свой компьютер код мз репозитория или скачать .zip архив с проектом.
- После этого выполнить команду:

```
docker compose up -d
```
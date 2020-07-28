запрос get с неверным id - 500 Internal Server Error
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100000'
```

запрос get с некорректным значением id - 400 Bad Request
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/hfgtng'
```

корректный запрос get - 200 OK
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100002'
```

корректный запрос getAll - 200 OK
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/'
```

запрос delete с неверным id - 500 Internal Server Error
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100000'
```

запрос delete с некорректным значением id - 400 Bad Request
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/оывп'
```

корректный запрос delete - 200 OK
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100002'
```

запрос delete без параметров - 405 Method Not Allowed
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/'
```

корректный запрос createWithLocation - 201 Created
```console
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T14:00:00",
    "description": "Перекус",
    "calories": 200
}'
```

запрос createWithLocation с неполными данными - 500 Internal Server Error
```console
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T14:00:00",
    "description": "Перекус"
}'
```

запрос createWithLocation с некорретными данными - 400 Bad Request
```console
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30 14:00:00",
    "description": "Перекус",
    "calories": 200
}'
```

корректный запрос update - 204 No Content
```console
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T12:30:00",
    "description": "Обед",
    "calories": 1250
}'
```

запрос update с некорректным id - 500 Internal Server Error
```console
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100002' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T12:30:00",
    "description": "Обед",
    "calories": 1250
}'
```

запрос update с некорректными данными - 400 Bad Request
```console
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30 12:30:00",
    "description": "Обед",
    "calories": 1250
}'
```

корректные запросы getBetween - 200 OK
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31&startTime=13:00:00&endTime=20:00:00'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=&endDate=2020-01-30&startTime=13:00:00&endTime=20:00:00'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&endDate=&startTime=13:00:00&endTime=20:00:00'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31&startTime=13:00:00&endTime='
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31&startTime=&endTime=13:00:00'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31&endTime=13:00:00'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-31'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?endDate=2020-01-31'
```
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter'
```

запрос getBetween с некорректными данными - 400 Bad Request
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=&endDate=2020-0130&startTime=13:00:00&endTime=20:00:00'
```
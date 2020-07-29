get request with invalid value id - 400 Bad Request
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/hfgtng'
```

correct request get - 200 OK
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100002'
```

correct request getAll - 200 OK
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/'
```

delete request with invalid value id - 400 Bad Request
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/оывп'
```

correct request delete - 200 OK
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100002'
```

delete request without parameters - 405 Method Not Allowed
```console
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/'
```

correct request createWithLocation - 201 Created
```console
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T14:00:00",
    "description": "Перекус",
    "calories": 200
}'
```

createWithLocation request with invalid values - 400 Bad Request
```console
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30 14:00:00",
    "description": "Перекус",
    "calories": 200
}'
```

correct request update - 204 No Content
```console
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30T12:30:00",
    "description": "Обед",
    "calories": 1250
}'
```

update request with invalid values - 400 Bad Request
```console
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dateTime": "2020-01-30 12:30:00",
    "description": "Обед",
    "calories": 1250
}'
```

correct requests getBetween - 200 OK
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

getBetween request with invalid values - 400 Bad Request
```console
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=&endDate=2020-0130&startTime=13:00:00&endTime=20:00:00'
```
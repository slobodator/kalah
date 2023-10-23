# Kalah Game

## Intro

The project provides the necessary REST API for playing [Kalah](https://en.wikipedia.org/wiki/Kalah) game.

## Frameworks

Implemented with Java 17 and Spring Boot 3.x

## Technical decisions made

For simplification

- the app uses Basic Auth instead of JWT
- and in-memory H2 database (the placeholder of the `application.yml` is overwritten with the `application-local.yml`
- there is no fine-tuned error handling of `@ControllerAdvice`
- instead, the spring built-in `/error` endpoint is allowed to provide an error message
- test are written just for the business logic; controllers & services are not covered

## How to run?

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

## API

The very same may be found at `/httpclient` directory and run by IDEA

## Users management

First we need to create at least two players

1. Adam

```
curl -X POST http://localhost:8080/players \
  -H "Content-Type: application/json" \
  -d '{"email": "adam@bol.com", "nickname": "Adam", "password": "1234"}'
```

2. and Bob

```
curl -X POST http://localhost:8080/players \
  -H "Content-Type: application/json" \
  -d '{"email": "bob@bol.com", "nickname": "Bob", "password": "4321"}'
```

## Games management

Then some of them creates a game (the initiator will have the first turn too)

```
curl -X POST http://localhost:8080/kalah/games \
  -H "Content-Type: application/json" \
  --user "adam@bol.com:1234" \
  -d '{"playerIds": [1, 2]}'
```

The response is **a player specific**.

I.e. after the first Adam move

```
curl -X POST http://localhost:8080/kalah/games/1/moves \
  -H "Content-Type: application/json" \
  --user "adam@bol.com:1234" \
  -d '{"pitIndex": 0}'
```

he will see

| opponent | 6 | 6 | 6 | 6 | 6 | 6 | Store: 0 |
|----------|---|---|---|---|---|---|----------|
| me       | 0 | 7 | 7 | 7 | 7 | 7 | Store: 1 |

The turn is still Adam's at he ended with sowing the last stone to his kalah/store.

At the same time Bob sees the opposite

```
curl -X GET http://localhost:8080/kalah/games/1 \
  -H "Content-Type: application/json" \
  --user "bob@bol.com:4321" 
```

| opponent | 0 | 7 | 7 | 7 | 7 | 7 | Store: 1 |
|----------|---|---|---|---|---|---|----------|
| me       | 6 | 6 | 6 | 6 | 6 | 6 | Store: 0 |

Please, check the entiry response info
via [Swagger](http://localhost:8080/swagger-ui/index.html#/kalah-game-controller/move)

Enjoy!

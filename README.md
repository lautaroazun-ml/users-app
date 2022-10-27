# users-app
App to authenticate users and calculate a new amount based on one external service

## Usage
For using this app you should follow the next steps:

1. Download the [collection](https://www.getpostman.com/collections/94919faf38f8a10d9a7f).
2. Deploy the container on your docker environment
3. Run the application

## Security
The application provides an signUp and Login that response an Bearer Token with 1 day of expiration. This token must 
be used on each API request.

## Restrictions
The next endpoint:
```
http://localhost:8080/fee/1/2?mock_error=false
```
Only will be able to 50 requests per day due to de mock external dependency restriction.

## Usage
Docker:
1. Pull the docker image
```bash
docker pull lazun/users:latest
```

2. Up the docker compose

```bash
docker-compose up
```






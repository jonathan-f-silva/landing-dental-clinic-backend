# API

## POST /login

### Request

```json
{
  "email": "user@email.com",
  "password": "user"
}
```

### Responses

- When the user or password is incorrect
    - Status: 401
- When the user is found and the password is correct
    - Status: 200
    - Body: JWT token

## POST /register

### Request

```json
{
  "name": "user",
  "email": "user@email.com",
  "password": "user"
}
```

- Admin JWT token in the header

### Responses

- When an admin token is not sent
    - Status: 401
- When an user token is sent
    - Status: 403
- When the email is already registered
    - Status: 409
    - Body: "Email already registered"
- When the email is not registered
    - Status: 201
    - Body: JWT token

## GET /user/:email

### Request

- Admin JWT token in the header

### Responses

- When an admin token is not sent
    - Status: 401
- When the user is not found
    - Status: 401
- When the user is found
    - Status: 200
    - Body: User

## POST /page

### Request

```json
{
  "url": "https://www.google.com",
  "config": "{}"
}
```

- JWT token in the header

### Responses

- When the request is invalid
    - Status: 400
    - Body: "Invalid request"
- When an token is not sent
    - Status: 401
- When URL is already registered in an active page
    - Status: 409
    - Body: "URL already registered"
- When the request is valid
    - Status: 201
    - Body: Page

## GET /page/:id

### Responses

- When the page is not found
    - Status: 401
- When the page is found
    - Status: 200
    - Body: Page

## PUT /page/:id

### Request

```json
{
  "url": "https://www.google.com",
  "config": "{}"
}
```

- Admin JWT token in the header

### Responses

- When the request is invalid
    - Status: 400
    - Body: "Invalid request"
- When an admin token is not sent
    - Status: 401
- When the page is not found
    - Status: 401
- When the user is not the owner of the found page
    - Status: 401
- When the URL is already registered in another page
    - Status: 409
    - Body: "URL already registered"
- When the user is the owner of the found page
    - Status: 200
    - Body: Page

## PATCH /page/:id/status

### Request

```json
{
  "status": "active"
}
```

- Admin JWT token in the header

### Responses

- When the request is invalid
    - Status: 400
    - Body: "Invalid request"
- When an admin token is not sent
    - Status: 401
- When the page is not found
    - Status: 401
- When the page exists
    - Status: 200
    - Body: Page

# ProjectMobile API Documentation

## Overview
The ProjectMobile API provides a set of endpoints to manage user authentication, topics, vocabulary, user points, and questions for a mobile application. It supports operations such as user registration, login, password recovery, topic and vocabulary management, user point creation and retrieval, and question creation, retrieval, update, and deletion. All API responses are in JSON format, and certain endpoints require Bearer Token authentication.

## Base URL
```
http://localhost:8989/v1/api
```

## Authentication
- **Bearer Token**: Required for endpoints that involve topic, vocabulary, user point, and question management. The token is obtained via the `/auth/login` endpoint and must be included in the `Authorization` header as `Bearer <token>` for authenticated requests.

## Endpoints

### 1. User Login
#### **POST** `/auth/login`
Authenticates a user and returns a Bearer Token for subsequent requests.

- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Example**:
  ```json
  {
    "username": "haodo",
    "password": "Hao1234@"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": {
      "result": true,
      "token": "string"
    },
    "message": "Login successful"
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": {
      "result": true,
      "token": "eyJhbGciOiJIUzUxMiJ9..."
    },
    "message": "Login successful"
  }
  ```

### 2. Verify Account
#### **POST** `/auth/verify`
Verifies a user account using an email and OTP (One-Time Password).

- **Request Body**:
  ```json
  {
    "email": "string",
    "otp": "string"
  }
  ```
- **Example**:
  ```json
  {
    "email": "dohao11129003@gmail.com",
    "otp": "683854"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Verify successful"
  }
  ```

### 3. Register Account
#### **POST** `/auth/register`
Registers a new user account and sends an OTP to the provided email.

- **Request Body**:
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Example**:
  ```json
  {
    "username": "haodo",
    "email": "dohao1129003@gmail.com",
    "password": "Hao1234@"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Registration successful. An OTP has been sent to your email"
  }
  ```

### 4. Forget Password
#### **POST** `/auth/forget-password`
Sends a password reset email to the provided email address.

- **Request Body**:
  ```json
  {
    "email": "string"
  }
  ```
- **Example**:
  ```json
  {
    "email": "haotinh2003@gmail.com"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Send email successfully"
  }
  ```

### 5. Reset Password
#### **POST** `/auth/reset-password`
Resets the password for the specified email address.

- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Example**:
  ```json
  {
    "email": "haotinh2003@gmail.com",
    "password": "Hao1234@"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Reset password successfully"
  }
  ```

### 6. Get User
#### **GET** `/user/get`
Retrieves user details for the authenticated user.

- **Authorization**: Bearer Token required.
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": {
      "account": {
        "username": "haodo",
        "email": "dohao11129003@gmail.com"
      }
    },
    "message": "get user successfully"
  }
  ```

### 7. Create Topic
#### **POST** `/topic/create`
Creates a new topic. Requires authentication.

- **Request Body**:
  ```json
  {
    "imageView": "string",
    "topic": "string"
  }
  ```
- **Example**:
  ```json
  {
    "imageView": "imageView",
    "topic": "Sport"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Create topic successfully"
  }
  ```

### 8. Get List of Topics
#### **GET** `/topic/list`
Retrieves a list of all topics. Requires authentication.

- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": [
      {
        "id": "string",
        "imageView": "string",
        "topic": "string"
      }
    ],
    "message": "Get list topic successfully"
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": [
      {
        "id": "UTETopicModel00000001",
        "imageView": "imageView",
        "topic": "Career"
      },
      {
        "id": "UTETopicModel00000002",
        "imageView": "imageView",
        "topic": "Sport"
      }
    ],
    "message": "Get list topic successfully"
  }
  ```

### 9. Update Topic
#### **PUT** `/topic/update`
Updates an existing topic by its ID. Requires authentication.

- **Request Body**:
  ```json
  {
    "id": "string",
    "imageView": "string",
    "topic": "string"
  }
  ```
- **Example**:
  ```json
  {
    "id": "UTETopicModel00000002",
    "imageView": "view",
    "topic": "Entertainment"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Update topic successfully"
  }
  ```

### 10. Delete Topic
#### **DELETE** `/topic/delete/{topicId}`
Deletes a topic by its ID. Requires authentication.

- **Path Parameter**:
  - `topicId`: The ID of the topic to delete (e.g., `UTETopicModel00000002`).
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Delete topic successfully"
  }
  ```

### 11. Create Vocabulary
#### **POST** `/vocabulary/create`
Creates a new vocabulary entry within a specified topic. Requires authentication.

- **Request Body**:
  ```json
  {
    "image": "string",
    "word": "string",
    "answer": "string",
    "topicId": "string"
  }
  ```
- **Example**:
  ```json
  {
    "image": "image",
    "word": "Engineer",
    "answer": "Kỹ sư",
    "topicId": "UTETopicModel00000001"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Create vocabulary successfully"
  }
  ```

### 12. Get List of Vocabulary
#### **GET** `/vocabulary/list/{topicId}`
Retrieves a list of vocabulary entries for a specific topic. Requires authentication.

- **Path Parameter**:
  - `topicId`: The ID of the topic (e.g., `UTETopicModel00000001`).
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": {
      "content": [
        {
          "id": "string",
          "image": "string",
          "word": "string",
          "answer": "string"
        }
      ],
      "totalElements": integer,
      "totalPages": integer
    }
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": {
      "content": [
        {
          "id": "UTEVocabularyModel00000001",
          "image": "image",
          "word": "Doctor",
          "answer": "Bác sĩ"
        },
        {
          "id": "UTEVocabularyModel00000002",
          "image": "image",
          "word": "Engineer",
          "answer": "Kỹ sư"
        }
      ],
      "totalElements": 2,
      "totalPages": 1
    }
  }
  ```

### 13. Update Vocabulary
#### **PUT** `/vocabulary/update`
Updates an existing vocabulary entry by its ID. Requires authentication.

- **Request Body**:
  ```json
  {
    "id": "string",
    "image": "string",
    "word": "string",
    "answer": "string",
    "topicId": "string"
  }
  ```
- **Example**:
  ```json
  {
    "id": "UTEVocabularyModel00000001",
    "image": "image",
    "word": "Chef",
    "answer": "Đầu bếp",
    "topicId": "UTETopicModel00000001"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Update vocabulary successfully"
  }
  ```

### 14. Delete Vocabulary
#### **DELETE** `/vocabulary/delete/{vocabularyId}`
Deletes a vocabulary entry by its ID. Requires authentication.

- **Path Parameter**:
  - `vocabularyId`: The ID of the vocabulary entry to delete (e.g., `UTEVocabularyModel00000002`).
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Delete vocabulary successfully"
  }
  ```

### 15. Create User Point
#### **POST** `/user-point/create`
Creates a new user point entry. Requires authentication.

- **Request Body**:
  ```json
  {
    "point": integer
  }
  ```
- **Example**:
  ```json
  {
    "point": 80
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Create user point successfully"
  }
  ```

### 16. Get List of User Points
#### **GET** `/user-point/list`
Retrieves a list of user points for authenticated users. Requires authentication.

- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": [
      {
        "id": "string",
        "user": {
          "account": {
            "username": "string",
            "email": "string"
          }
        },
        "point": integer,
        "time": "string"
      }
    ]
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": [
      {
        "id": "UTEUserPoint00000001",
        "user": {
          "account": {
            "username": "trinhhao",
            "email": "trinhthunghao2003@gmail.com"
          }
        },
        "point": 70,
        "time": "09/05/2025 11:20:01"
      },
      {
        "id": "UTEUserPoint00000002",
        "user": {
          "account": {
            "username": "haodo",
            "email": "dohao1129003@gmail.com"
          }
        },
        "point": 50,
        "time": "10/05/2025 09:15:30"
      }
    ]
  }
  ```

### 17. Create Question
#### **POST** `/question/create`
Creates a new question. Requires authentication.

- **Request Body**:
  ```json
  {
    "question": "string",
    "answer": "string",
    "allChoice": "string"
  }
  ```
- **Example**:
  ```json
  {
    "question": "She ___ her homework every day.",
    "answer": "does",
    "allChoice": "does do did doing"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Create question successfully"
  }
  ```

### 18. Get Question
#### **GET** `/question/get/{questionId}`
Retrieves details of a specific question by its ID. Requires authentication.

- **Path Parameter**:
  - `questionId`: The ID of the question (e.g., `UTEQuestion00000001`).
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": {
      "id": "string",
      "question": "string",
      "answer": "string",
      "allChoice": "string"
    },
    "message": "Get question successfully"
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": {
      "id": "UTEQuestion00000001",
      "question": "She ___ her homework every day.",
      "answer": "does",
      "allChoice": "does do did doing"
    },
    "message": "Get question successfully"
  }
  ```

### 19. Get List of Questions
#### **GET** `/question/list`
Retrieves a list of all questions. Requires authentication.

- **Response** (200 OK):
  ```json
  {
    "result": true,
    "data": {
      "content": [
        {
          "id": "string",
          "question": "string",
          "answer": "string",
          "allChoice": "string"
        }
      ],
      "totalElements": integer,
      "totalPages": integer
    }
  }
  ```
- **Example Response**:
  ```json
  {
    "result": true,
    "data": {
      "content": [
        {
          "id": "UTEQuestion00000001",
          "question": "She ___ her homework every day.",
          "answer": "does",
          "allChoice": "does do did doing"
        },
        {
          "id": "UTEQuestion00000002",
          "question": "He ___ to the store yesterday.",
          "answer": "went",
          "allChoice": "went goes go gone"
        }
      ],
      "totalElements": 2,
      "totalPages": 1
    }
  }
  ```

### 20. Update Question
#### **PUT** `/question/update`
Updates an existing question by its ID. Requires authentication.

- **Request Body**:
  ```json
  {
    "id": "string",
    "question": "string",
    "answer": "string",
    "allChoice": "string"
  }
  ```
- **Example**:
  ```json
  {
    "id": "UTEQuestion00000001",
    "question": "She ___ a book last night.",
    "answer": "read",
    "allChoice": "read reads reading readed"
  }
  ```
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Update question successfully"
  }
  ```

### 21. Delete Question
#### **DELETE** `/question/delete/{questionId}`
Deletes a question by its ID. Requires authentication.

- **Path Parameter**:
  - `questionId`: The ID of the question to delete (e.g., `UTEQuestion00000001`).
- **Response** (200 OK):
  ```json
  {
    "result": true,
    "message": "Delete question successfully"
  }
  ```

## Error Handling
All endpoints return a `result` field in the response. If `result` is `false`, an error message will be provided in the `message` field.

- **Example Error Response**:
  ```json
  {
    "result": false,
    "message": "Invalid credentials"
  }
  ```

## Notes
- Ensure the Bearer Token is included in the `Authorization` header for all endpoints requiring authentication.
- All requests with a body should be sent as raw JSON.
- The API runs on `localhost:8989` by default for development purposes.
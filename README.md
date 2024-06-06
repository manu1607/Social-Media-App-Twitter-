# Social-Media-App-Twitter-
Social-Media-App -OOPS-Backend-Project-SpringBoot-H2DataBase

# Social Media Platform (Twitter) API Documentation

This project implements a basic social media platform similar to Twitter, featuring user authentication, post creation, comment functionality, and user feed retrieval. The platform is built with RESTful API endpoints using Spring Boot, JPA for database interactions, and H2 as an in-memory database.

## Languages and Tools Used
- Java
- Spring Boot (for application framework)
- Spring Data JPA (for database interactions)
- H2 Database (for in-memory database)
- Postman (for API testing and documentation)

## Aim Of the Project

To create a social media platform with functionalities for user authentication, posting content, commenting on posts, and retrieving user and post data. This project provides a foundation for building more complex social media applications.

## API Endpoints

### Authentication

#### Login
Endpoint to login an existing user. If the user does not exist or the credentials are incorrect, a relevant error is thrown.
- **URL**: `/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
- **Response**:
  - `Login Successful`
  - `Username/Password Incorrect`
  - `User does not exist`

#### Signup
Endpoint to register a new user. If the account already exists, a relevant error should be returned.
- **URL**: `/signup`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "email": "string",
    "name": "string",
    "password": "string"
  }
  ```
- **Response**:
  - `Account Creation Successful`
  - `Forbidden, Account already exists`

### User Information

#### User Detail
Endpoint to retrieve details about a user. Relevant error is returned if the user does not exist.
- **URL**: `/user`
- **Method**: `GET`
- **Query Parameter**: `userID`
- **Response Body**:
  ```json
  {
    "name": "string",
    "userID": "int",
    "email": "string"
  }
  ```
  - `User does not exist`

#### All Users
Endpoint to show details about all the existing users.
- **URL**: `/users`
- **Method**: `GET`
- **Response**:
  ```json
  [
    {
      "name": "string",
      "userID": "int",
      "email": "string",
      "posts": [
        {
          "postID": "int",
          "postBody": "string",
          "date": "date"
        }
      ]
    }
  ]
  ```

### Feed

#### User Feed
Endpoint to retrieve all posts by all users in reverse chronological order of creation.
- **URL**: `/feed`
- **Method**: `GET`
- **Response Body**:
  ```json
  [
    {
      "postID": "int",
      "postBody": "string",
      "date": "date",
      "comments": [
        {
          "commentID": "int",
          "commentBody": "string",
          "commentCreator": {
            "userID": "int",
            "name": "string"
          }
        }
      ]
    }
  ]
  ```

### Post Operations

#### Create Post
Endpoint to create a new post. Returns relevant error if the user does not exist.
- **URL**: `/post`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "postBody": "string",
    "userID": "int"
  }
  ```
- **Response**:
  - `Post created successfully`
  - `User does not exist`

#### Retrieve Post
Endpoint to retrieve an existing post. Relevant error is returned if the post does not exist.
- **URL**: `/post`
- **Method**: `GET`
- **Query Parameter**: `postID`
- **Response Body**:
  ```json
  {
    "postID": "int",
    "postBody": "string",
    "date": "date",
    "comments": [
      {
        "commentID": "int",
        "commentBody": "string",
        "commentCreator": {
          "userID": "int",
          "name": "string"
        }
      }
    ]
  }
  ```
  - `Post does not exist`

#### Edit Post
Endpoint to edit an existing post. Relevant error is returned if the post does not exist.
- **URL**: `/post`
- **Method**: `PATCH`
- **Request Body**:
  ```json
  {
    "postBody": "string",
    "postID": "int"
  }
  ```
- **Response**:
  - `Post edited successfully`
  - `Post does not exist`

#### Delete Post
Endpoint to delete a post. Relevant error is returned if the post does not exist.
- **URL**: `/post`
- **Method**: `DELETE`
- **Query Parameter**: `postID`
- **Response**:
  - `Post deleted`
  - `Post does not exist`

### Comment Operations

#### Create Comment
Endpoint to create a new comment. Relevant error is returned if the post does not exist.
- **URL**: `/comment`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "commentBody": "string",
    "postID": "int",
    "userID": "int"
  }
  ```
- **Response**:
  - `Comment created successfully`
  - `User does not exist`
  - `Post does not exist`

#### Retrieve Comment
Endpoint to retrieve an existing comment. Relevant error is returned if the comment does not exist.
- **URL**: `/comment`
- **Method**: `GET`
- **Request Param**: `commentID`
- **Response Body**:
  ```json
  {
    "commentID": "int",
    "commentBody": "string",
    "commentCreator": {
      "userID": "int",
      "name": "string"
    }
  }
  ```
  - `Comment does not exist`

#### Edit Comment
Endpoint to edit an existing comment. Relevant error is returned if the comment does not exist.
- **URL**: `/comment`
- **Method**: `PATCH`
- **Request Body**:
  ```json
  {
    "commentBody": "string",
    "commentID": "int"
  }
  ```
- **Response**:
  - `Comment edited successfully`
  - `Comment does not exist`

#### Delete Comment
Endpoint to delete an existing comment. Relevant error is returned if the comment does not exist.
- **URL**: `/comment`
- **Method**: `DELETE`
- **Query Parameter**: `commentID`
- **Response**:
  - `Comment deleted`
  - `Comment does not exist`

## Testing the API

A Postman collection is available to test the API endpoints. You can import the collection into Postman using the following link: [Postman Collection](https://bold-robot-635964.postman.co/workspace/My-Workspace~fdcf320c-d95e-4a0f-8014-7f96e0ac11bd/collection/24752303-7dc37d1a-2404-4a0a-8ecf-b33facb1a9b9?action=share&creator=24752303).

## Directions to Run the Project

### Step 1: Clone the Repository
Clone the project repository to your local machine.
```bash
git clone <repository-url>
```

### Step 2: Install Dependencies
Navigate to the project directory and install the necessary dependencies using Maven.
```bash
mvn install
```

### Step 3: Run the Spring Boot Application
Run the main Spring Boot application class in your IDE or use the following Maven command:
```bash
mvn spring-boot:run
```

### Step 4: Use Postman to Test Endpoints
Import the provided Postman collection and use it to test the API endpoints.

## Additional Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)

For detailed code implementations and usage examples, refer to the respective API files in the project repository.

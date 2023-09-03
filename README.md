# Cognixus Todo Server

## Instruction for running the app
### 1. Running App using Maven command
Navigate to directory that contains pom.xml file and run the following command to start the app:
>**mvn spring-boot:run**

![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/7a67a5d0-9242-414a-bf2e-8ca29b3a30a7)
<br>**Note:** Please ensure that your machine is running MySQL server with configuration that same as screenshot above, else the application is unable to start.<br/>
Alternatively, you can replace the configuration from [application.properties](src/main/resources/application.properties)

### 2. Running App using Docker command
Navigate to directory that contains docker-compose.yml file and run the below command to create docker container and start the app afterwards:

>**docker compose up**

<br>The datasource credentials that set in docker-compose.yml is obtained from [.env](.env)

***

## Instruction for testing the app
### 1. Login Gmail Account and Generate Bearer Token
**Note:** Currently, the expiration time of bearer token is 1 hour<br>
First, open below url via browser and login your Gmail account
>**http://localhost:8080/api/oauth/google/login**

<br>Screenshot of Google login page:<br>
![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/9fa07303-663c-4294-ab0e-04e67a3d4913)


After login Gmail account, it will output result that includes bearer token. Extract the value of **id_token** from the result and keep it for further action. 

![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/08b4646c-fb5f-46c3-8960-2f254f50df94)

### 2. API Call with curl
Need to replace **$token** with bearer token that retrieved from above action before call any API.<br>
Example:
>**curl -X POST http://localhost:8080/api/v1/todo -H "Content-Type: application/json" -H "Authorization: Bearer $token" -d "{\"name\": \"Todo name\",\"description\": \"Todo description\"}"**

***

## Instruction for building the app
### 1. Build app using Maven command
Navigate to directory that contains pom.xml and run command below to generate java jar file of application.

>**mvn clean install**

### 2. Build docker image of app using Docker command
Navigate to directory that contains Dockerfile and run command below to generate docker image of application.<br>
**Note**: Docker image is generated based on existing java jar file of application in target folder.

>**docker build -t $imageName:$tagName .**

***

## Interface documentation
| API | HTTP Method | Authorization Required | Params | Description |
|-------------- | ----------- | ----------- | ----------- | ----------- |
| /api/oauth/{providerCode}/login | GET | NO | **Path Variable**<br> providerCode: oauth provider - google | Redirect to Oauth login page |
| /api/oauth/{providerCode}/callback | GET | NO | **Path Variable**<br> providerCode: oauth provider - google<br> **Request Param**<br> code: code that returned back from oauth provider | Used by Oauth Provider to callback and generate bearer token after login successfully |
| /api/v1/todo | POST | YES | **Request Body**<br> - name **(Required)**: name of the todo item.<br> - description **(Required)**: Description of the todo item. The length of it cannot longer than 255 characters | Add a todo item |
| /api/v1/todo/{id} | DELETE | YES | **Path Variable**<br> id: id of the todo item record | Soft delete a todo item based on given id |
| /api/v1/todo | GET | YES | N/A | Get all todo items based on logged user |
| /api/v1/todo/{id}/completed | PATCH | YES |  **Path Variable**<br> id: id of the todo item record | Update the status of a todo item to completed based on given id |

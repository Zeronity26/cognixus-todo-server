# Cognixus Todo Server
## Introduction

## 1. Instruction for running the app
### 1.1 Running App with Maven command
Navigate to directory that contains pom.xml file and run the following command to start the app:

>mvn spring-boot:run

<br>**Note:** The command above unable to start the application if the database is not ready. Therefore, please ensure that your machine is running a MySQL server which lies on port 3306 and contains a database  named as "Cognixus".<br/>
Alternatively, you can replace the value of properties from [application.properties](src/main/resources/application.properties)

![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/7a67a5d0-9242-414a-bf2e-8ca29b3a30a7)

<br>

### 1.2 Running App with Docker container
Navigate to directory that contains docker-compose.yml file and run the following command to start the app:

>docker compose up

<br>Docker will download the images that defined in docker-compose.yml file initially if your machine doesn't exists it. Then, the container will start after those images are ready or start directly if those images are already existsed. 
The datasource credentials that set in docker-compoese.yml is obtained from [.env](.env)

***

## 2. Instruction for testing the app
All API end points of the application requires user to be authenticated except Oauth API end points, so user needs to include the bearer token in authorization header to trigger those API end points successfully.<br>
Currently, the application is only available for Gmail user to access.

### 2.1 Login Gmail Account and Generate Bearer Token
**Note:** generated bearer token will expire after an hour, so need to generate it again after it is expired.<br>
First, open below url via browser and login your Gmail account
>http://localhost:8080/api/oauth/google/login

![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/9fa07303-663c-4294-ab0e-04e67a3d4913)


After login Gmail account, it will output result that includes bearer token. Extract the value of **id_token** from the result and keep it for further action.
![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/08b4646c-fb5f-46c3-8960-2f254f50df94)

### 2.2 Send Request
#### 2.2.1 Send Request with cURL
Below is an example of cURL request to add todo item. Before execute the request, need to replace **$token** with bearer token that retrieved from above action 
>curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $token" -d "{\"name\": \"Todo name\",\"description\": \"Todo description\"}" http://localhost:8080/api/v1/todo

#### 2.2.2 Send Request with Postman
Below is an example of using postman to send request to add todo item. Before execute the request, need to set bearer token to the token field which is under the authorization tab.
![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/e0eda820-eaf5-414d-bc6f-6b421896d7c1)
![image](https://github.com/Zeronity26/cognixus-todo-server/assets/77224053/e24c30df-d895-4745-ac18-e7f6b5d67082)

***

## 3. Instruction for building the app
### 3.1 Build app with Maven command
Navigate to directory that exists pom.xml and run following maven command to clean and build the application.<br>
After run the command, it will generate a java jar file of the application in target directory.
>mvn clean install

### 3.2 Build app with Docker
After executes maven command to build the application, run below docker command to build docker image of the application as the docker image is generated based on the jar file.<br>
Run below command under directory that contains Dockerfile.
>docker build -t $imageName:$tagName .

***

## 4. Interface documentation
| API | HTTP Method | Authorization Required | Params | Description |
|-------------- | ----------- | ----------- | ----------- | ----------- |
| /api/oauth/{providerCode}/login | GET | NO | **Path Variable**<br> providerCode: oauth provider - google | Redirect to Oauth login page |
| /api/oauth/{providerCode}/callback | GET | NO | **Path Variable**<br> providerCode: oauth provider - google<br> **Request Param**<br> code: code that returned back from oauth provider | Used by Oauth Provider to callback and generate bearer token after login successfully |
| /api/v1/todo | POST | YES | **Request Body**<br> - name **(Required)**: name of the todo item.<br> - description **(Required)**: Description of the todo item. The length of it cannot longer than 255 characters | Add a todo item |
| /api/v1/todo/{id} | DELETE | YES | **Path Variable**<br> id: id of the todo item record | Soft delete a todo item based on given id |
| /api/v1/todo | GET | YES | N/A | Get all todo items based on logged user |
| /api/v1/todo/{id}/completed | PATCH | YES |  **Path Variable**<br> id: id of the todo item record | Update the status of a todo item to completed based on given id |

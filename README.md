# rest_api_assignment
REST API + MySQL Interview Assignment


 ## Requirements:

  1. MySQL 8 running server.
  2. Java JDK 8+ installed.

 ## Setup steps:

  1. Edit the following files in the project file 'rest_api_assignment\src\main\resources\application.properties'
  
   > replace localhost:3306 with the url for your mysql 8 server;
   > add the username and password for login on the mysql server
   
    spring.datasource.url=jdbc:mysql://localhost:3306/rest_api_assignment?createDatabaseIfNotExist=true
    spring.datasource.username=root
    spring.datasource.password=
    
  2. Run ApplicationRunner.java
  3. REST API can be accessed on the url path `localhost:8080` (list of available commands can also be seen here)
  
  ## Details about the API
  
  The REST API was created Spring Boot 2.1.4 framework with the Spring Data JPA plugin, and it features:
  - two @Entity classes, `Buyer` and `Transaction`, which hold all the information for the two tables: `buyers` and `transactions`;
  - two @RestController clases, `BuyerController` and `TransactionController`, which map all the api commands to their specific paths (a list of available actions be found by accessing the main API path `localhost:8080:`;
  - two @Repository classes, `BuyersRepository` and `TransactionsRepository`, which handle the storage, retrieval and search of data from the tables;
  - few custom created Exceptions.
  
  ## Notes
  - new Buyer can be created by a post request; body sent must contain the following mandatory fields:
    - `name`: valid full name - must be unique;
    - `identification`: valid 13 digits string (or 9 digits string of buyer is `corporate`) - must be unique;
    - `email`: valid email address;
  - before creation of new Buyer, it will check if another Buyer with the same name or identification is present in the databse;
  - if not specified, new Buyer is automatically created as `Individual`;
    - to overwrite this, add the `type` field in the body, with the following possible values
    
    > individual | corporate - case not sensitive
    
  - Buyer info can be changed by a put request: available options include: `name, identification, email, phone, address`;
  
  - new Transactions can be created bu a post request; body sent must contain the following mandatory fields:
    - `name` : name of Buyer to which the transaction while be associated; 
    - following restrictions apply, transactions cannot be created if:
      - the Buyer with the name provided in the transaction post request, must exist in the database;
      - the Buyer must have a valid address and phone number assigned to it.
    - `value` : value of transaction: max 8 digits long number, with a max of 2 decimals
    - `description` : transaction description
    
  - changing the name of a Buyer, will also change the name associated to all its transactions (if present)
  - deleting a Buyer will delete all of its associated tranasactions
  - deleting a single transaction, will update the `transactions count` and `total sum of transaction` on the Buyer
  - if changing the name of identification of a Buyer, it will first check if the new values aren't already assigned
      
      
## Known bugs
  

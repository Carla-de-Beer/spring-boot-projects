# Pets API

This is a Spring Boot API project that displays data related to a list of pets. This data is manipulated by means of the standard set of CRUD calls. The project allows for the option of loading either locally stored in-memory data or from a MongoDB database (see notes below). 

The project is built with Java 11 and Maven. 

## Getting started

To swap the option of locally stored mock data out for the MongoDB database access, carry out the following steps:

* Inside the PetService class, change the `@Qualifier()` property from `"mockDao"` to `"mongoDbDao"`.
* Create a MongoDB database, activate its server, and populate it with data via the following commands:

`use pet_store;`

`db.createCollection("pet");`

```
db.pet.insertMany([
  {
    "name" : "Fluffy",
    "species" : "cat",
    "breed" : "siamese"
  },
  {
    "name" : "Henry",
    "species" : "tortoise",
    "breed" : "leopard tortoise"
  }
])
```

Download or clone the project. Add a database username and password to the `src/main/resources/application.properties` file. 
Build the project with the command `mvn clean install` and start the project server by running the command `mvn spring-boot:run`. The API can be called with any of the following cURL CRUD-based requests:

* GET/READ:
  * ```curl -i http://localhost:8080/api/v1/pets/```
  * ```curl -i http://localhost:8080/api/v1/pets/<ObjectId>```


* UPDATE/EDIT:
  * ```curl -i -H "Content-Type: application/json" -X PUT -d '{  "name" : "Spot","species" : "dog","breed" : "terrier"}' http://localhost:8080/api/v1/pets/<ObjectId>```


* CREATE/ADD:
  * ```curl -i -H "Content-Type: application/json" -X POST -d '{  "name" : "Cocky","species" : "bird","breed" : "cockatoo"}' http://localhost:8080/api/v1/pets/```


* DELETE:
  * ```curl -i -X DELETE http://localhost:8080/api/v1/pets/<ObjectId>```
  
  Alternatively, import and run the Postman test collection. These can be found under `src/test/resources/Pets\ API.postman_collection.json`.

<p align="center">
  <img src="images/screenShot-01.png"/>
</p>

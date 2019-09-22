# Pets API

This is a Spring Boot API project that allows for the option of loading data either from a locally stored CSV file or from a MySQL database.

Based the online tutorial "REST API: Java Spring Boot and MongoDB": https://www.codementor.io/gtommee97/rest-api-java-spring-boot-and-mongodb-j7nluip8d

The project makes use of Java 8 and Maven.

## Instructions

Execute the following commands in order to create the MongoDB database:

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

The API can be tested with the following CLI curl CRUD commands:

* GET/READ:
  * ```curl -i http://localhost:8080/api/v1/pets/```
  * ```curl -i http://localhost:8080/api/v1/pets/<ObjectId>```


* UPDATE/EDIT:
  * ```curl -i -H "Content-Type: application/json" -X PUT -d '{  "name" : "Spot","species" : "dog","breed" : "terrier"}' http://localhost:8080/api/v1/pets/<ObjectId>```


* CREATE/ADD:
  * ```curl -i -H "Content-Type: application/json" -X POST -d '{  "name" : "Cocky","species" : "bird","breed" : "cockatoo"}' http://localhost:8080/api/v1/pets/```


* DELETE:
  * ```curl -i -X DELETE http://localhost:8080/api/v1/pets/<ObjectId>```

</br>
<p align="center">
  <img src="images/screenShot-01.png"/>
</p>

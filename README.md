
# Ebazzar - Ecommerce Shopping app



## Requirements

* [JDK 11+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.6.0+](https://maven.apache.org/download.cgi)
* [MySql 8.0](https://www.mysql.com/downloads/)
* [Elastic Search 7.13.4](https://www.elastic.co/downloads/elasticsearch)

## Running


1. From the command line with *Maven*:

   `mvn spring-boot:run` 

1. Access the deployed web application at:

   http://localhost:8080

1. Log in with existing accounts 
   a) Seller
      {
        "email": "rahul@gmail.com",
        "password": "123"
      }
   b) Customer
     {
       "email": "teena@gmail.com",
       "password": "123"
      }

### Obtain Token

curl -X POST "http://localhost:8080/users/login" -H "accept: */*" -H "Content-Type: application/json" -d "{\"email\":\"rahul@gmail.com\",\"password\":\"123\"}"


Response will have the Bearer Token. Use this with every request like
 authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWh1bEBnbWFpbC5jb20iLCJleHAiOjE2Mjc5MDM4OTB9.xQW3LIEXUGXt3ae4Jpm4wnW1OD9UWtqk3RV7gL5OBWRKMoTme5lz3stBwAkAa25gwISnfyh0oY2qpc80aUDRlw 
 cache-control: no-cacheno-storemax-age=0must-revalidate 
 connection: keep-alive 
 content-length: 0 
 date: Mon02 Aug 2021 08:31:30 GMT 
 email: rahul@gmail.com 
 expires: 0 
 keep-alive: timeout=60 
 pragma: no-cache 
 x-content-type-options: nosniff 
 x-frame-options: DENY 
 x-xss-protection: 1; mode=block


### Swagger API Documentation 

http://localhost:8080/swagger-ui/index.html

# CheapsharkAPI-Project

## Description

This project contains two applications that interact with the CheapShark public API. Cheapshark being a price comparision website for digital PC games sold across multiple stores such as Steam. The main application utilizes JavaFX and makes use of the different endpoints available by the CheapShark API. Game deals are all loaded into a table based on the given parameters set inside of the application. Different parameters can be adjusted within the JavaFX application to filter out certain deals. Such as only showing games that cost less than $15. The second application uses Spring Boot and Hibernate to allow the deals to be saved in a local database to view later. Store activity is also stored in the database and is updated whenever the JavaFX application is first launched.

# Treasure Finder Multiplayer Game

Client-Server Architecture individual project. 

The project consists in a client and a server, which I have implemented during [Software Engineering 1][1] class at the University of Vienna. The network communication was already implemented by the [lecturer][2], and my task was to develop the client and the server. <br>

Full tasks and phases of the projects are available at the end of this readme file.


#### Highlights of what I have done:

-	Written requirements analyses. Documented typical scenario, alternative scenario and possible error cases of two of the functional requirements. (all of them can be found inside the Dokumentation/Teilaufgabe 1 - Planung folder)
-	Modeled class and sequential diagrams for client and server.
-	Implemented MVC pattern to separate client’s components.
-	Used SLF4J to log the behavior and errors of the client.
-	Used JUnit and Mockito to write 22 Unit Tests and achieve 77,5% coverage. (4895 of 6317 client’s total instructions)
-	Used Test Driven Development for the server implementation.
-	Server can run 999 concurrent games.
-	My AI implementation reached the semi-finals of Software Engineering 1 tournament at the University of Vienna.



#### Short description and my design decisions:




#### Our full task:

We had to follow these steps during the class:

##### Phase 1: Planning & Designing
* Read about the project, game rules and do a requirements' analysis.
* Read how the already implemented network protocol works and how the XML bodies look like.
* Write requirements documentation.
* Design the architecture of the client and the server. For that, class and sequential diagrams were required for the client and for the server. (all of them can be found inside the Dokumentation/Teilaufgabe 1 - Planung folder)
  <br>

##### Phase 2: Implement the client
* Start implementing the client, an online server was provided by the lecturer so that we can test our clients in a game against AI, against our own client or against the client of another student.
* Implement the network communication protocol using REST.
* Implement a CLI so that we can see the behavior of our AI (how it moves or how the generated map looks like).
* Implement the MVC pattern.
* Design an algorithm to generate a valid map before sending it to the server. It has to be random generated, contain no islands and at least 5 mountain fields, 24 grass fields, 7 water fields and a castle placed on a grass field.
* Implement an AI which is able to send correct moves to the server. The AI should find a treasure, pick the treasure up and go with the treasure to the enemy's castle to win the game.
* Use logging with the proper log levels.
* Write meaningful unit testing. Those should include parametrized tests, negative tests and Mockito. The test coverage should be at least 65%.
* Write checked and unchecked exceptions.
<br>

##### Phase 3: Implement the server
* Start implementing the server, which should be able to proof at least 8 business rules and run 999 concurrent games.
* Optional task for bonus points which I have done: Test Driven Development.


[1]: https://ufind.univie.ac.at/en/course.html?lv=051040&semester=2022W
[2]: https://ufind.univie.ac.at/en/person.html?id=54217

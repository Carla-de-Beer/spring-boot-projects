# GitHub Reader

A web-based reader to display GitHub repository information for a specific GitHub user given a valid GitHub account name. Note, all the information displayed in the Reader is information that has been made publicly accessible. No data regarding private repositories will be displayed.

The project was built with Spring Boot and ThymeLeaf and makes use of Java 8 and Maven.

## Getting started

Download the project, install the dependencies and start the server with the command `mvn spring-boot:run`. The landing page can then be viewed in a browser via the link: `http://localhost:8080/`. Enter a valid GitHub account name, when prompted, in order to display the relevant user repository information. Information displayed includes the following:
* The user name and short profile synopsis.
* A hyperlink to the user's repository.
* An associated avatar (if one is available).
* A confirmation of the total number of repositories found for this user.
* A summary of the repositories found. This list also includes subscriptions, where applicable.
* A brief analysis of the programming languages used in these repositories.

This project makes use of the [GitHub Developer API](https://developer.github.com/v3/).

</br>
<p align="center">
  <img src="images/screenShot-01.png"/>
  <img src="images/screenShot-02.png"/>
</p>

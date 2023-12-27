# Mikey's Blogging Web Application

***

### Project Description

Mikey's Blogging Web Application is a feature-rich web platform designed for blogging and content management. It empowers users to create, share, and interact with blog posts in a seamless and secure environment. The application includes an admin panel for operational management, user authentication, and various post interactions.

> Mikey's Blogging Web Application offers a robust blogging experience with a focus on user engagement, security, and efficient content management.
> 
***

### Table of contents

* [Features](#features)
  * [Technologies used](#technologies-used)
* [How to Install and Run](#how-to-install-and-run)
* [How to Use](#how-to-use)
* [Launched App Link](#launched-app-link)
* [Tests](#tests)
* [Author](#author)
* [Credits](#credits)

***

### Features

* **Admin Panel**: An operational management interface for administrative tasks.
* **Blog Content Page**: Presents blog posts with working pagination for easy navigation.
* **User Authentication**: Secure login functionality for user access.
* **Posts Interactions**:
  * Comments
  * Delete
  * Views
  * Update
  * Read
  * Create
* **User Profiles**:
  * Update
  * Delete
  * Read
  * Create
* **Index Page**: Central hub for users to explore and engage with content.

***

### Technologies used

* **AOP - Aspect Oriented Programming**:
  * Logging functionality integrated.
* **Testing**: using `Mockito` and `JUnit`
  * Services
  * Controllers
  * Utils
* **Validations**:
  * `@FieldsValueMatchValidator` - This checks two specified fields if they match.
  * `@ImageSizeValidator` - This checks and validates a specified image size.
  * `@ImageValidator` - This checks and validates an uploaded file to be an image.
  * `@PasswordValidator` - This checks the weakness of a password.
  * `@UniqueCategoryValidator` - This checks if category already exists in database to prevent double insertion.
  * `@UniqueEmailValidator` - This checks if email already exists in database to prevent double insertion.
  * `@UniqueUsernameValidator` - This checks if email already exists in database to prevent double insertion.
* **Spring Technologies**:
  * Spring Data JPA
  * Thymeleaf
  * Spring Security
  * Spring Boot
  * Spring Test
  * Spring Actuator
  * Devtools
  * Lombok
  * JUnit
  * Mockito
  * Validation
* **Database**:
  * MySQL
* **Microservices**
  * Docker

***

### How to Install and Run

* Download zip folder and import to your preferred IDE (Integrated development environment).
* Modify properties in `application.properties`, `application_uat.properties` and `application_uat.properties` to match your configurations from docker or any external mysql connection.
* Avoid hard-coding your database credentials in your properties file, make use of environment variables instead.
* This is a Maven project, hit `Run` in your preferred IDE whenever ready or in your terminal, run `mvn spring-boot:run`
* You can also generate a `.jar` file by running `mvn clean install` and after it generates a `.jar` file in your root directory `/target`, run `java -jar target/your-application-name.jar`

***

### How to Use

After you have got your app up and running, you will need to create data tables needed for the application.

* We are using Spring Data JPA which happens to be really amazing and has the power to create tables from our existing entities in `src/main/java/com/iykescode/blog/mikeybloggingwebapp/model`.
  * In your `application_uat.properties` or `application_prod.properties` depending on your active profile set in `application.properties`, uncomment `spring.jpa.hibernate.ddl-auto=update` and comment `spring.jpa.hibernate.ddl-auto=validate`, then run the application.
    * This way, Spring Data JPA builds our table schema and indexes in database automatically.
  * After that, go back to your active profile and comment `spring.jpa.hibernate.ddl-auto=update` and uncomment `spring.jpa.hibernate.ddl-auto=validate`
    * This way, in production environment, Spring Data JPA doesn't keep creating tables on application start-up.

Once that is done, we need to insert data into our database.

* In your root directory, go into `src/main/resources` where you will see a file named `data.sql`.
  * If you use, IntelliJ like me which already supports MySQL database and syntax, select all the query and `Execute` to insert to the tables.
  * If you use any other external MySQL resource, simply copy the query in the file and run it.

* For the user login credentials, we have a password encoder that checks user password when user tries to sign-in
  * In `data.sql` all the users passwords are encrypted for best practices and unencrypted password is `admin`
  * You can change to your preferred encrypted password, please make sure to use BCRYPT as that's what is recognized by the application.

Once insertion is completed, go into your preferred browser and hit `localhost:8080/`
* Port `8080` will be the default port, if not, please check your terminal for assigned port on your PC.

For Admin panel, hit `localhost:8080/user`

---

### Tests

* After importing the project to your preferred IDE (Integrated development environment).
  * In your Terminal, run `mvn test` : This will run all tests which will be successful.
  * You can also run them as single in your IDE by simply opening the Test class and clicking `Run`.

---

### Launched App Link

* [mikey-blogging-webapp](https://mikey-blogging-webapp-production.up.railway.app)

### Author

* Twitter - [@michael_chukss](https://twitter.com/michael_chukss)

### Credits
* Theme by [Themefisher](https://themefisher.com)

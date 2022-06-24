Vizsgaremek

Leírás

Az alkalmazás tudományos konferenciák szervezőinek nyújt segítséget abban, hogy előadások, résztvevők és előadók
adatait hatékonyan és kényelmesen lehessen adminisztrálni. Az előadóknak lehetőségük van előadást meghirdetni, 
a különböző szervezetek, egyetemek és intézetek által delegált résztvevők pedig jelentkezhetnek ezekre az előadásokra.

Entitások

Lecturer

A Lecturer entitás a következő attribútumokkal rendelkezik:
-id (Az előadó egyedi azonosítója)
-name (Az előadó neve)
-dateOfBirth (Az előadó születési ideje)
-institution (Az előadót foglalkoztató intézmény neve)
-adacemicRank (Az előadó tudományos fokozata, titulusa)
-email (Az előadó E-mail címe)
-presentation (Az előadó által tartott előadás)

A következő végpontokon érjük el az entitást:

GET	    "/api/lecturers"	          lekérdezi az összes előadót
GET	    "/api/lecturers/{id}"	      lekérdez egy előadót id alapján
GET     "/api/lecturers/findByName"   lekérdez egy előadót név alapján
POST    "/api/lecturers"	          létrehoz egy előadót
PUT	    "/api/lecturers/{id}"	      hozzárendel az előadóhoz egy előadást
DELETE	"/api/lecturers/{id}"	      id alapján töröl egy passzív előadót

A Lecturer és a Presentation entitás között egyirányú @OneToOne kapcsolat van.


Presentation

A Presentation entitás a következő attribútumokkal rendelkezik:
-id (Az előadás egyedi azonosítója)
-lecturer (Az előadás előadója)
-title (Az előadás címe)
-startTime (Az előadás kezdésének időpontja)
-participations (Az előadás jelentkezései)

A következő végpontokon érjük el az entitást:

GET	    "/api/presentations"	           lekérdezi az összes előadást
GET	    "/api/presentations/{id}"	       lekérdez egy előadást id alapján
GET     "/api/presentations/findByTitle"   lekérdez egy előadást cím alapján
POST    "/api/presentations"	           létrehoz egy előadást
PUT	    "/api/presentations/{id}"	       módosítja az előadás kezdetét
DELETE	"/api/presentations/{id}"	       id alapján törli az előadáshoz tartozó jelentkezéseket

A Presentation és a Participation entitás között egyirányú @OneToMany-@ManyToOne kapcsolat van.


Participant

A Participant entitás a következő attribútumokkal rendelkezik:
-id (A résztvevő egyedi azonosítója)
-name (A résztvevő neve)
-dateOfBirth (A résztvevő születési ideje)
-adacemicRank (A résztvevő tudományos fokozata, titulusa)
-institution (Az résztvevőt foglalkoztató intézmény neve)
-email (A résztvevő E-mail címe)
-participations (A résztvevő jelentkezései)

A következő végpontokon érjük el az entitást:

GET	    "/api/participants"	                   lekérdezi az összes résztvevőt
GET	    "/api/participants/{id}"	           lekérdez egy résztvevőt id alapján
GET     "/api/participants/findByAllByName"    lekérdez egy résztvevőt cím alapján
POST    "/api/participants"	                   létrehoz egy résztvevőt
PUT	    "/api/participants/{id}"	           módosítja egy résztvevő intézményét
DELETE	"/api/participants/{id}"	           id alapján törli a résztvevő jelentkezéseit

A Participant és a Participation entitás között egyirányú @OneToMany-@ManyToOne kapcsolat van.

Participation

A Participation entitás a következő attribútumokkal rendelkezik:
-id (A jelentkezés egyedi azonosítója)
-registration (A jelentkezés leadásának időpontja)
-participant (A jelentkezés alanya)
-presentation (A jelentkezéshez tartozó előadás)

A következő végpontokon érjük el az entitást:

GET	    "/api/participations"	                        lekérdezi az összes jelentkezést
GET	    "/api/participations/{id}"	                    lekérdez egy jelentkezést id alapján
GET     "/api/participations/findByAllByParticipant"    lekérdezi egy résztvevő jelentkezéseit
POST    "/api/participations"	                        létrehoz egy jelentkezést
PUT	    "/api/participations/{id}"	                    átjelentkezés másik előadásra id alapján
DELETE	"/api/participations/{id}"	                    id alapján törli jelentkezést

A Participation entitás adatai az adatbázisban a participation táblában tárolódnak, mely két külső kulcsot tartalmaz:
az adott jelentkezéshez kapcsolódó résztvevő id-jára, valamint 
az adott jelentkezéshez kapcsolódó előadás id-jára.


Technológiai részletek

Ez egy klasszikus háromrétegű webes alkalmazás, controller, service és repository réteggel, entitásonként a rétegeknek 
megfelelően elnevezett osztályokkal. A megvalósítás Java programnyelven, Spring Boot használatával történt. 
Az alkalmazás HTTP kéréseket képes fogadni, ezt a RESTful webszolgáltatások segítségével valósítja meg. 
A dokumentáláshoz Open API interfészt használ, ahol a végpontok fix értékekkel feliratozva láthatóak. 
Az azonos nevű és típusú adattagok egyik oldalról a másikra való leképezéséhez Modelmappert alkalmaz.
Adattárolásra SQL alapú MySQL adatbázist használ, melyben a táblákat Flyway hozza létre. Az adatbáziskezelés 
Spring Data JPA technológiával történik. A beérkező adatok validálását a Spring Boot spring-boot-starter-validation 
modulja végzi. Az alkalmazás tesztelésére integrációs és egység tesztek állnak rendelkezésre, melyek az src/test/java 
mappában találhatók. A mellékelt Dockerfile segítségével az alkalmazásból layerelt Docker image készíthető.
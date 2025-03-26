# Versicherungsprämien-Berechnungsdienst

## **Über das Projekt**  
Dieser Dienst berechnet Versicherungsprämien basierend auf der jährlichen Fahrleistung, dem Fahrzeugtyp und der Registrierungsregion. Dabei wird eine CSV-Datei zur regionalen Klassifizierung verwendet und spezifische Faktoren für Fahrleistung, Fahrzeugtyp und Region angewendet, um die Prämie zu bestimmen. Zusätzlich bietet der Dienst eine HTTP-API zur Integration mit Drittanbietern, um Prämienberechnungen anzufordern.  

Dieser Dienst berechnet eine Versicherungsprämie basierend auf:  

- Jährlicher Fahrleistung  
- Fahrzeugtyp  
- Registrierungsregion  

Eine CSV-Datei (*postcodes.csv*) wird für die regionale Klassifikation genutzt. Sie enthält wichtige Felder wie Bundesland, Land, Stadt, Postleitzahl und Bezirk.  

Nutzer können ihre geschätzte Fahrleistung, den Fahrzeugtyp und die Postleitzahl eingeben, um die Prämie zu berechnen. Die berechnete Prämie und die Benutzereingaben werden dauerhaft gespeichert.  

Formel zur Prämienberechnung:
### **Fahrleistungsfaktor** × **Fahrzeugtyp-Faktor** × **Regionen-Faktor** 

## **Funktionen**  
- Webanwendung für Antragsteller zur Berechnung von Prämien und Speicherung ihrer Eingaben.  
- HTTP-API für Drittanbieter zur Anforderung von Prämienberechnungen.  
- Mikroservice-Architektur mit BFF (*Backend for Frontend*)-Pattern  

## **Mikroservice-Architektur mit BFF-Pattern**  
Das System basiert auf einer *Mono-Repo*-Mikroservice-Architektur. Dabei werden alle Services in einem einzigen Repository verwaltet, um Konsistenz, eine vereinfachte Abhängigkeitsverwaltung und eine leichtere Zusammenarbeit zu ermöglichen. Jeder Mikroservice ist flexibel, skalierbar und unabhängig einsetzbar, wobei eine effiziente interne Kommunikation sichergestellt wird.  

Das *BFF*-Pattern erleichtert die Frontend-Entwicklung und optimiert zukünftige Integrationen mit Webanwendungen, indem es eine vereinfachte und optimierte Backend-Schnittstelle bereitstellt.  

### **Architekturprinzipien:**  
- **Mono-Repo-Mikroservices**: Jeder Dienst ist modular, aber in demselben Repository, was die Wartbarkeit und Entwicklungseffizienz verbessert.  
- **Spezialisierte Mikroservices**: Jeder Service übernimmt eine bestimmte Aufgabe, z. B. Datenverarbeitung, Prämienberechnung oder Kommunikation mit externen Systemen.  
- **BFF (Backend for Frontend)**: Der *Public API Microservice* fungiert als einzige Schnittstelle für Frontend-Anwendungen, um die Kommunikation zu optimieren und die Komplexität zu reduzieren.  

### **Mikroservices in diesem System**  

#### **Public API Microservice**  
- Verwaltet die öffentliche API  
- Bietet eine RESTful API für Drittanbieter und Benutzer  
- Bearbeitet Anfragen zur Prämienberechnung  
- Validiert eingehende Anfragen und leitet sie an den *Premium Microservice* weiter  

#### **Premium Microservice**  
- Führt die Berechnung der Versicherungsprämien durch  
- Interagiert mit dem *Data Microservice*, um externe Daten abzurufen  
- Berechnet die Versicherungsprämien basierend auf Fahrleistung, Fahrzeugtyp und Region  
- Verwaltet die zentrale Geschäftslogik zur Berechnung von Versicherungsprämien  

#### **Data Microservice**  
- Verarbeitet und verwaltet externe Datenquellen  
- Ruft Daten ab und verarbeitet sie zur Berechnung von Fahrzeug- und Regionalfaktoren  
- Stellt die berechneten Faktoren anderen Diensten zur Verfügung (*z. B. Premium Microservice*)  

---

### ⚠️ **Wichtiger Hinweis: Interne Kommunikation**  
Andere Mikroservices (*Premium und Data*) stellen ebenfalls RESTful-APIs bereit, jedoch ausschließlich für die interne Kommunikation innerhalb des Systems. Um Sicherheit und Zugriffskontrolle zu gewährleisten, muss in zukünftigen Implementierungen eine Authentifizierung für diese APIs hinzugefügt werden.  

---

### **Verwendete Technologien und Bibliotheken**  
- **Java 21**  
- **Maven**  
- **Spring Boot**  
- **Lombok**  
- **Spring Data JPA**  
- **Spring Cloud OpenFeign**  
- **PostgreSQL**  
- **JUnit5 & Mockito**  
- **Docker & Docker Compose**  
- **Postman**  

---

## **Installation**  

### **Voraussetzungen**  
- **JDK 21**
- **Docker Desktop**  

### **Schritte**  

1. **Repository klonen:**  
```bash
git clone https://github.com/neslisezen/insurance-premium-calculation-system.git
```  

2. **Projekt bauen:**  
```bash
mvn clean install
```  

3. **Docker-Compose-Datei ausführen:**  
```bash
docker-compose up --build
```  

---

## ⚠️ **Konfiguration**  
Falls Sie die *docker-compose*-Datei nicht verwenden, fügen Sie bitte die Umgebungsvariablen hinzu oder bearbeiten Sie die Datei *application.properties*, um die Datenbank und andere Details einzurichten:  

### **Für Public API Microservice:**  
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name_for_public_api
spring.datasource.username=your_username
spring.datasource.password=your_password
```  

### **Für Data Microservice:**  
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name_for_data_service
spring.datasource.username=your_username
spring.datasource.password=your_password
```  

---

## **API-Tests mit Postman**  
Zur einfacheren API-Testung wurden **Postman Collections** erstellt. Diese befinden sich unter folgendem Pfad:  

`postman-collection/ipcs - Insurance Premium Calculation Service.postman_collection.json`  

---

## **Beispiel-Anfragen**  

### **Versicherungsprämie berechnen**  
**POST** `http://localhost:8080/api/v1/public`  
**Header:**  
```json
Content-Type: application/json
X-Source: internalApp
```  
**Body:**  
```json
{
    "vehicle": "BUS",
    "mileage": "10000",
    "postcode": "24539"
}
```  
**Antwort:**  
```json
{
    "data": {
        "id": 1,
        "insurancePremium": 12.0
    },
    "message": "Calculation successful",
    "success": true
}
```  

---

### **Alle Benutzereingaben abrufen**  
**GET** `http://localhost:8080/api/v1/public/all`  
**Antwort:**  
```json
[
    {
        "id": 1,
        "vehicle": "pkw",
        "mileage": 10000,
        "postcode": "56075",
        "insurancePremium": 6.5,
        "source": "internalApp",
        "date": "2025-02-14T17:00:37.536489"
    },
    {
        "id": 2,
        "vehicle": "SUV",
        "mileage": 1200,
        "postcode": "53227",
        "insurancePremium": 3.90625,
        "source": "externalApp",
        "date": "2025-02-14T17:03:18.722667"
    }
]
```  

---

## **Tests ausführen**  
Führen Sie die Tests mit folgendem Befehl aus:  
```bash
mvn test
```  

---

## **Kontakt**  
**E-Mail:** neslihansezen9@gmail.com  
**GitHub:** [neslisezen](https://github.com/neslisezen)  
**LinkedIn:** [Neslihan Sezen](https://linkedin.com/in/neslihansezen)  

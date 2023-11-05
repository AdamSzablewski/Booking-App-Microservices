# Bokningsapp Mikrotjänster

## Översikt

Bokningsapplikationen är ett projekt som visar upp min erfarenhet inom Java-utveckling, Spring-ramverket och mikrotjänstarkitektur. Den ger möjlighet för företagare att erbjuda sina tjänster, och kunder att boka tider för dessa tjänster. Detta projekt erbjuder även möjligheten att lägga upp bilder till tjänsternas portfolion, skicka meddelanden mellan användare och även bilder.

## Nyckelfunktioner

- **Användarregistrering och autentisering**: Säker användarregistrering och autentisering för att skydda användardata och kontroll över åtkomst. Applikationen använder JWT för autentisering.

- **Tjänstpublicering och portföljbilder**: Tjänsteleverantörer kan enkelt publicera sina tjänster och förbättra sin portfolio genom att lägga till bilder för varje tjänst.

- **Bokning**: Användare kan enkelt bläddra bland tillgängliga tjänster som rekommenderas i ett givet område, sina favoriter, eller genom att söka.

- **Kolla upp lediga tider**: Användare kan enkelt kolla upp tillgängligheten av lediga tider för specifika tjänster under valfri dag. De tillgängliga tiderna kan även filtreras så att enbart de visas som är tillgängliga för en eller flera valda anställda.

- **Meddelanden med RabbitMQ**: Applikationen använder RabbitMQ för effektiv meddelandehantering mellan mikrotjänsterna.

- **Händelsestyrd arkitektur**: Applikationen använder RabbitMQ för händelsestyrd arkitektur, vilket används i bland annat radering av data bland tjänsterna.

- **Meddelanden**: Användare kan skicka både text och bild meddelanden mellan varandra.

## Använda teknologier

- **Java**: Det primära programmeringsspråket.

- **Spring-ramverket**: Projektet utnyttjar Spring-ekosystemet, inklusive Spring Cloud och Spring Data JPA för att bygga skalbara mikrotjänster.

- **Mikrotjänstarkitektur**: Applikationen använder effektivt mikrotjänstarkitektur för att bryta ner komplexa funktioner i hanterbara komponenter och säkerställa skalbarhet.

- **PostgreSQL**: Säker hantering av data, inklusive bilder, uppnås med PostgreSQL som databashanteringssystem.

- **RabbitMQ**: Meddelandetjänster möjliggörs av RabbitMQ och stöder realtidskommunikation mellan mikrotjänster samt möjliggör händelsestyrd datahantering.

- **Resilience4J**: Applikationen använder Resilience4J som en kretsbrytare för att säkerställa robusthet och felhantering i kommunikationen mellan mikrotjänsterna.

## Boknings Applikatiionens Arkitektur

### Api Gateway
Tar hand om routing och vägledning: API Gateway agerar som en inkommande trafikdirigent och kan skicka inkommande förfrågningar till rätt mikrotjänst baserat på URI, HTTP-metod eller andra kriterier. Det gör det möjligt att hantera flera tjänster med olika API-endpoints under en gemensam URI.

### Security-Service
- Autentisering och auktorisering för inkommande förfrågningar.
- Vid inloggning svarar med en JWT, för senare autentisering och auktorisering.
- Verifikation av JWT.
- Hashning av lösenord med SHA-256

### User-Service
- Tar hand om all data som har att göra med användare som t ex kontaktinformation, namn och lösenord.
- Använder sig av "Event driven architecture" genom RabbitMq, för att meddela de andra tjänsterna om evenemang som radering av en användare, för att ta bort all annan associerad data från andra databaser som används av de andra tjänsterna

### Booking-Service
- Vid förfrågningar om tillgängliga tider för en tjänst, returnerar med generade Timeslot objekt för de anställda som är lediga under en given dag och utför tjänsten.
- Returnerar företag-tjänster baserat på namn, och även ett antal baserat på deras popularitet i sin tjänst kategori.
- Vid bokning av en tid, skickar meddelanden med informsation till både den utförande samt klienten genom RabbitMq.

### Image-Service
- Komprimerar bilder och sparar de till PostrgreSQL databasen.
- När den returnerar bilder från databasen så dekomprimeras dem.
- För bilder avsedda för meddelanden och portföljbilder sparas bilden och mikrotjänsten svarar med ett unikt bild-id till de andra tjänsterna, som används för att hämta bilden vid senare tillfälle.

### Messaging-Service
- Ger möjlighet för användare att skicka meddelanden mellan varandra
- Text och bild meddelanden
- Tar hand om system-meddelanden om kommande bokningar och annan information från appen

### Eureka
- Tjänstregistrering
- Lastbalansering
- Tjänstupptäckt

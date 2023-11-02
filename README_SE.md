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

## Boknings Applikationens Arkitektur



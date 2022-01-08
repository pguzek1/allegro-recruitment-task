# Allegro - zadanie rekrutacyjne nr 3

## Wybrany język
* Java

## Wykorzystane biblioteki
Do zbudowania aplikacji wykorzystano następujące biblioteki:

* [Spring Boot](https://spring.io)
  * validation
  * webflux
  * test
* [GraphQL Java Kickstart WebClient](https://www.graphql-java-kickstart.com/web-client/)
* [Lombok](https://projectlombok.org/)
* [Reactor Test](https://projectreactor.io/)

## Wersja demonstracyjna aplikacji
Wersja demonstracyjna aplikacji została uruchomina w usłudze Heroku, można ją znaleźć pod adresem:

* [https://pguzek1-allegro-rt.herokuapp.com/](https://pguzek1-allegro-rt.herokuapp.com/)



## Uruchomienie aplikacji na własnym urządzeniu
Do poprawnego działania aplikacji wymagane jest wygenerowanie osobistego tokenu dostępowego w serwisie GitHub.
Osobisty token dostępowy można wygenrować pod adresem [https://github.com/settings/tokens](https://github.com/settings/tokens).

Oficjalną instrukcję dotyczącą wygenerowania żetonu można znaleźć pod [tym adresem](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token).

Podczas generowania tokenu nie jest wymagane przyznanie scope dla tokenu, natomiast ograniczy to dostęp tylko do publicznych repozytoriów użytkowników.
W przypadku przyznania dostępu do zakresu repo, można odczytywać również dane z prywatnych repozytoriów użytkownika generującego dany token.

<b>Do uruchomienia aplikacji wymagane jest JDK w wersji co najmniej 14, natomiast aplikację uruchamiałem w środowisku `Linux + OpenJDK 17`, dlatego ta wersja JDK została ustalona w pliku `build.gradle`. 
W przypadku braku spełnienia tych wymagań zalecam skorzystanie z dockerowej metody uruchomieniowej lub wersji demonstracyjnej dostępnej na platformie Heroku.</b>


### docker
W celu uruchomienia aplikacji przy użyciu tej metody, z repozytorium docker należy pobrać zasób poleceniem:
* `docker pull pguzek1/allegro-recruitment-task`

Następnie aplikację można uruchomić poleceniem:
* `docker run -p 8080:8080 -e GITHUB_TOKEN='TU_WKLEIC_TOKEN' pguzek1/allegro-recruitment-task`


### IntelliJ IDEA
W celu uruchomienia aplikacji, należy w nim otworzyć projekt pobrany z tego repozytorium, a następnie w konfiguracji uruchomieniowej dodać zmienną środowiskową
```
GITHUB_TOKEN=TU_WKLEIC_TOKEN
```


### gradlew
W celu uruchomienia aplikacji przy użyciu tej metody, w swojej ulubionej powłoce przejdź do katalogu głownego aplikacji.

W zależności od używanego systemu operacyjnego należy wykorzystać inny plik uruchomieniowy:
* `Linux` -> `gradlew`
* `Windows` -> `gradlew.bat`

W powłoce należy w pierwszej kolejności zbudować aplikację poleceniem:
* `./gradlew build`

Następnie aby uruchomić aplikację należy wykonać polecenie:
* `./gradlew bootRun --args='--GITHUB_TOKEN=TU_WKLEIC_TOKEN'`

Oneline z pominięciem testów:
* `./gradlew build bootRun -x test --args='--GITHUB_TOKEN=TU_WKLEIC_TOKEN'`



## Endpointy
Aplikacja umożliwia komunikację z klientem za pomocą RESTAPI.
* Parametry poza `user` muszą być przekazywane jako `query`.


### Listowanie repozytoriów
* GET `/api/users/{user}/repositories`

Endpoint wspiera następujące parametry:

| parametr           | typ                | wartość domyślna | dozwolone wartości (case-sensitive)                 | opis                                          |
|--------------------|--------------------|------------------|-----------------------------------------------------|-----------------------------------------------|
| user               | String             |                  |                                                     | Nazwa użytkownika GitHub                      |
| privacy            | String             | PUBLIC           | PUBLIC, PRIVATE, ALL                                | Typ repozytoriów do prezentacji               |
| ownerAffiliations  | List&lt;String&gt; | OWNER            | OWNER, COLLABORATOR, ORGANIZATION_MEMBER            | Przynależność użytkownika do repozytorium     |
| perPage            | Integer            | 30               | &lt;1; 100&gt;                                      | Ilość rekordów prezentowanych na stronie      |
| after              | String             |                  |                                                     | Kursor, miejsce od którego wyświetlać rekordy |
| direction          | String             | ASC              | ASC, DESC                                           | Kolejność sortowania wyników                  |
| orderBy            | String             | CREATED_AT       | CREATED_AT, UPDATED_AT, PUSHED_AT, NAME, STARGAZERS | Pole po którym sortować wyniki                |

Przykładowe zapytania:
* GET `/api/users/pguzek1/repositories?ownerAffiliations=OWNER,COLLABORATOR&perPage=5`
* GET `/api/users/allegro/repositories?perPage=3&direction=DESC&orderBy=STARGAZERS`
* GET `/api/users/allegro/repositories?perPage=3&direction=DESC&orderBy=STARGAZERS&after=Y3Vyc29yOnYyOpLNArrOAiAo2w`

Przykładowa odpowiedź:
```json
{
  "pageInfo": {
    "startCursor": "Y3Vyc29yOnYyOpLNFPfOA0AzNA==",
    "hasPreviousPage": false,
    "hasNextPage": true,
    "endCursor": "Y3Vyc29yOnYyOpLNBCfOAWOv-Q=="
  },
  "repositories": [
    {
      "cursor": "Y3Vyc29yOnYyOpLNFPfOA0AzNA==",
      "name": "bigcache",
      "stargazers": 5367
    },
    {
      "cursor": "Y3Vyc29yOnYyOpLNBrPOAEKDfg==",
      "name": "ralph",
      "stargazers": 1715
    },
    {
      "cursor": "Y3Vyc29yOnYyOpLNBCfOAWOv-Q==",
      "name": "tipboard",
      "stargazers": 1063
    }
  ]
}
```


### Sumowanie gwiazdek w repozytoriach
* GET `/api/users/{user}/stargazers`

Endpoint wspiera następujące parametry:

| parametr          | typ                | wartość domyślna | dozwolone wartości (case-sensitive)                 | opis                                          |
|-------------------|--------------------|------------------|-----------------------------------------------------|-----------------------------------------------|
| user              | String             |                  |                                                     | Nazwa użytkownika GitHub                      |
| privacy           | String             | PUBLIC           | PUBLIC, PRIVATE, ALL                                | Typ repozytoriów do prezentacji               |
| ownerAffiliations | List&lt;String&gt; | OWNER            | OWNER, COLLABORATOR, ORGANIZATION_MEMBER            | Przynależność użytkownika do repozytorium     |

Przykładowe zapytania:
* GET `/api/users/pguzek1/stargazers?ownerAffiliations=OWNER,COLLABORATOR,ORGANIZATION_MEMBER`
* GET `/api/users/allegro/stargazers`

Przykładowa odpowiedź:
```json
{
  "totalStargazer": 14467
}
```


### Listowanie najpopularniejszych języków programowania
* GET `/api/users/{user}/languages`

Endpoint wspiera następujące parametry:

| parametr          | typ                | wartość domyślna | dozwolone wartości (case-sensitive)        | opis                                          |
|-------------------|--------------------|------------------|--------------------------------------------|-----------------------------------------------|
| user              | String             |                  |                                            | Nazwa użytkownika GitHub                      |
| privacy           | String             | PUBLIC           | PUBLIC, PRIVATE, ALL                       | Typ repozytoriów do prezentacji               |
| ownerAffiliations | List&lt;String&gt; | OWNER            | OWNER, COLLABORATOR, ORGANIZATION_MEMBER   | Przynależność użytkownika do repozytorium     |
| direction         | String             | DESC             | ASC, DESC                                  | Kolejność sortowania wyników                  |
| orderBy           | String             | BYTES            | BYTES, LANGUAGE                            | Pole po którym sortować wyniki                |

Przykładowe zapytania:
* GET `/api/users/pguzek1/languages?ownerAffiliations=OWNER,COLLABORATOR`
* GET `/api/users/allegro/languages?orderBy=LANGUAGE&direction=ASC`

Przykładowa odpowiedź:
```json
[
  {
    "languageName": "Java",
    "totalBytes": 1408772
  },
  {
    "languageName": "JavaScript",
    "totalBytes": 432914
  },
  {
    "languageName": "TypeScript",
    "totalBytes": 266738
  },
  {
    "languageName": "C#",
    "totalBytes": 239540
  },
  {
    "languageName": "HTML",
    "totalBytes": 173391
  },
  {
    "languageName": "C++",
    "totalBytes": 88992
  },
  {
    "languageName": "TeX",
    "totalBytes": 19112
  },
  {
    "languageName": "Python",
    "totalBytes": 18266
  },
  {
    "languageName": "SCSS",
    "totalBytes": 12745
  },
  {
    "languageName": "CSS",
    "totalBytes": 10706
  },
  {
    "languageName": "CMake",
    "totalBytes": 4610
  },
  {
    "languageName": "Shell",
    "totalBytes": 2111
  },
  {
    "languageName": "XSLT",
    "totalBytes": 1040
  },
  {
    "languageName": "Makefile",
    "totalBytes": 482
  }
]
```


## Propozycje rozwoju aplikacji
* [ ] Wprowadzenie obsługi błędów w celu ponawiania niektórych akcji np. błąd klienta GitHubAPI;
* [ ] Poprawa prezentacji danych danych dla użytkowników np. poprzez wprowadzenie Swaggera;
* [ ] Uzupełnienie brakujących testów sprawdzających logikę aplikacji;
* [ ] Rejestrowanie wywołań postępów podczas przetwarzania metod lub sekwencji w strumieniu reaktywnym;
* [ ] Dodanie cache po stronie użytkownika aplikacji np. w formie ETag, pozwoli to uniknąć przesyłania użytkownikowi informacji o obiekcie, który nie uległ zmianie;
* [ ] Dodanie cache po stronie użytkownika GitHub GraphQL API;
* [ ] Jeżeli pobieramy dane o liczbie gwiazdek dot. repozytoriów, warto robić to po posortowaniu w kolejności malejącej względem ich liczby. Jeżeli ostatnie z repozytoriów na danej stronie będzie miało zero gwiazdek, wtedy nie ma potrzeby pobierania kolejnych stron;
* [ ] Użytkownicy uwierzytelnieni mogą pobrać maksymalnie 100 elementów na listach. W aktualnej implementacji nie uwzględniono pobierania więcej niż 100 języków programowania z pojedyńczego repozytorium, optymalnym rozwiązaniem jest sprawdzenie, czy pojedyńcze repozytorium ma więcej niż 100 unikalnych języków programowania oraz pobranie brakujących danych;


## Kilka słów
Dziękuję za możliwość stworzenia tej aplikacji oraz czekam na feedback.
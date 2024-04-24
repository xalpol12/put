```
Recipe zawiera images; kiedy usunięty jest recipe, powiązane images są również usuwane

Recipe list zawiera recipes; kiedy usunięta jest recipes list, recipes nie są usuwane
```

| URI                  |                   GET                    |                       POST                       |           PUT           |                    DELETE                     |
|----------------------|:----------------------------------------:|:------------------------------------------------:|:-----------------------:|:---------------------------------------------:|
| /recipes             |     lista przepisów /  wyszukiwanie      |                   dodaj przepis                  |            X            |                       X                       |
| /recipes/{rid}       |            info o  przepisie             |                         X                        |      aktualizacja       | usunięcie przepisu  (z powiązanymi zdjęciami) |
| /images              |       lista zdjęć /  wyszukiwanie        |                   dodaj zdjęcie                  |            X            |                       X                       |
| /images/{iid}        |        wyświetl metadane zdjęcia         |                         X                        | aktualizacja metadanych |              usunięcie  zdjęcia               |
| /images/{iid}/raw    |             wyświetl zdjęcie             |                         X                        |  aktualizacja zdjęcia   |                       X                       |
| /recipe-lists        | lista kolekcji przepisów /  wyszukiwanie |             dodaj kolekcję przepisów             |            X            |                       X                       |
| /recipe-lists/{riid} |             info o kolekcji              |                         X                        |      aktualizacja       |              usunięcie  kolekcji              |
| /recipe-lists-merges |                    X                     | złącz dwie listy  o podanych riid usuwając drugą |            X            |                       X                       |
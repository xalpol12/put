```
Recipe contains images; when deleting recipe, images are also deleted

Recipe list contains recipes; when deleting list, recipes are not deleted
```

| URI                  |                    GET                   |                       POST                       |      PUT     |          PATCH          |                     DELETE                    |
|----------------------|:----------------------------------------:|:------------------------------------------------:|:------------:|:-----------------------:|:---------------------------------------------:|
| /recipes             |      lista przepisów /  wyszukiwanie     |                   dodaj przepis                  |       X      |            X            |                       X                       |
| /recipes/{rid}       |             info o  przepisie            |                         X                        | aktualizacja | aktualizacja  częściowa | usunięcie przepisu  (z powiązanymi zdjęciami) |
| /images              |        lista zdjęć /  wyszukiwanie       |                   dodaj zdjęcie                  |       X      |            X            |                       X                       |
| /images/{iid}        |             wyświetl zdjęcie             |                         X                        | aktualizacja |  aktualizacja częściowa |               usunięcie  zdjęcia              |
| /recipe-lists        | lista kolekcji przepisów /  wyszukiwanie |             dodaj kolekcję przepisów             |       X      |            X            |                       X                       |
| /recipe-lists/{riid} |              info o kolekcji             |                         X                        | aktualizacja |  aktualizacja częściowa |              usunięcie  kolekcji              |
| /recipe-lists/merges |                     X                    | złącz dwie listy  o podanych riid usuwając drugą |       X      |            X            |                       X                       |
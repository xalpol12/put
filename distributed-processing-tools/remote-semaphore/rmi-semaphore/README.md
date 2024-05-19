## Opis
Implementacja semafora zliczającego z wykorzystaniem JavaRMI. Serwer kontroluje dostęp do pewnego zasobu, do którego dostęp może otrzymać tylko określona liczba klientów. Klient próbuje wywołać metodę `acquire` semafora, przedostając się do sekcji krytycznej, czeka na pozwolenie. Po poprawnym wywołaniu metody `acquire`, symulowana jest praca (usypiając wątek na 4 sekundy), po czym klient zwraca zabrane claimy.

## Uruchomienie
W celu uruchomienia projektu należy skompilować `Server.java` oraz `Client.java` za pomocą kompilatora Javy:
```
javac ./Server.java && javac ./Client.java
```

Następnie uruchomić serwer, jako argument podać pożądaną ilością zezwoleń:
```
java Server <liczba_pozwoleń_semafora>
```

Oraz klienta, który spróbuje użyć semafora, przekazując podaną jako drugi argument liczbę zezwoleń:
```
java Client localhost <liczba_pozwoleń_do_zajęcia>
```

W celu zaobserwowania blokowania kolejnych klientów, należy uruchomić kolejnych klientów lub podać liczbę zezwoleń większą niż maksymalna ilość zezwoleń w semaforze
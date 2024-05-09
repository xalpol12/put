Zadanie polega na zrealizowaniu zdalnej usługi semafora uogólnionego, czyli semafora zliczającego, który można zwiększać i zmniejszać o wartość całkowitą dodatnią podaną jako parametr operacji semaforowej. Interfejs operacji semaforowych można oprzeć (nie koniecznie w całości zrealizować) na rozwiązaniu w Javie (java.util.concurrent.Semaphore).

Usługę należy zaimplementować w oparciu o jedno z podejść:
- ONC RPC (czyli Sun RPC),
- gRPC,
- Java RMI,

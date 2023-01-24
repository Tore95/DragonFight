# DragonFight
This is a project for "Graphical Interfaces and Event Programming" college course. It's a DragonBall fighting game, written in Java with the JavaFX graphics library and DLV2.0 for the IA module integration

![Immagine di copertina del gioco](https://firebasestorage.googleapis.com/v0/b/personal-ee8a6.appspot.com/o/copertina_dragonfight.png?alt=media&token=14de4393-71a4-4f0c-9a6d-330119d2afc8)

### Autore:
Salvatore Spagnuolo

## Introduzione Al Gioco
DragonFight è un Picchia duro ispirato a Dragonball, ha due modalità di gioco:
- Singleplayer
- Multiplayer
La modalità single player consente di giocare contro un’intelligenza artificiale, mentre la multiplayer (solo offline) consente di giocare contro un altro giocatore condividendo la tastiera del pc.

## Comandi:
Per quanto riguarda il single player i comandi sono: W, A, S, D rispettivamente per muoversi su, sinistra, giù e destra, mentre Q ed E sono gli attacchi base calcio e pungo, poi ci sono 3 attacchi di aura R (onda piccola), F (onda energetica), C (attacco finale).
Nella modalità multiplayer il giocatore 2 userà Y, G, H, J per i movimenti, T, U per gli attacchi base e I, K, M per gli attacchi d’aura.

## Tecnologie Utilizzate:
Il gioco è stato programmato in **Java versione 1.8**, utilizzando le librerie grafiche di **JavaFX**.
Per l’intelligenza artificiale è stato utilizzata la tecnologia **DLV**, sviluppata presso **L’Università della Calabria**.

## Metodi Di Sviluppo:
Durante lo sviluppo del gioco sono stati utilizzati diversi pattern ed accortezze, una tra queste che merita attenzione è stata la volontà di creare delle **hitbox** più precise utilizzando delle **forme ellittiche**, operazione non facile quella di verificare in tempo reale la collisione tra due ellissi senza per forza mettere a sistema le rispettive equazioni. In pratica la questione si è risolta **definendo lungo la circonferenza dell’ellisse 8 punti equidistanti**, a questo punto è facile sapere se un punto si trova dentro un’ellisse o meno. In sostanza **due ellissi collidono se uno dei punti presenti sulla circonferenza entra dentro un’altra ellisse**.

## Chi aggiorna gli oggetti di gioco:
Altra accortezza è che ogni oggetto di gioco deriva dalla classe astratta **GameObject**, la quale contiene due metodi molto importanti, **move()** e **draw()**. Ogni GameObject viene listato all’interno di una Singleton Class **GlobalManager**, il quale implementa **AnimationTimer**. Nel suo Handler GlobalManager, scorre la lista di oggetti GameObject e ne chiama i metodi move() a 60FPS ed il metodo draw() (che disegna le sprite) a 16FPS. **GlobalManager esegue anche l’IA DLV a 2FPS** nel caso la partita sia single player.

## Come vengono creati gli oggetti nel Main:
Il main (**GameLauncher**) contiene un riferimento ad un'altra Singleton Class, la **GameFactory**, essa si occupa di creare le giuste istanze di **Player** a seconda del tipo di partita scelto, è una classica applicazione dei pattern **AbstractFactory** e **FactoryMethod**.
Inoltre, crea anche parte della UI di gioco contenuta in oggetti, come i backgrounds e le barre della vita e dell’aura.

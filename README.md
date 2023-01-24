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
![Formule dell'ellisse delle hitbox](https://firebasestorage.googleapis.com/v0/b/personal-ee8a6.appspot.com/o/HitboxEllipse_pages-to-jpg-0001.jpg?alt=media&token=63210eae-64ed-4796-a790-fe3fd676824d)

## Chi aggiorna gli oggetti di gioco:
Altra accortezza è che ogni oggetto di gioco deriva dalla classe astratta **GameObject**, la quale contiene due metodi molto importanti, **move()** e **draw()**. Ogni GameObject viene listato all’interno di una Singleton Class **GlobalManager**, il quale implementa **AnimationTimer**. Nel suo Handler GlobalManager, scorre la lista di oggetti GameObject e ne chiama i metodi move() a 60FPS ed il metodo draw() (che disegna le sprite) a 16FPS. **GlobalManager esegue anche l’IA DLV a 2FPS** nel caso la partita sia single player.

## Come vengono creati gli oggetti nel Main:
Il main (**GameLauncher**) contiene un riferimento ad un'altra Singleton Class, la **GameFactory**, essa si occupa di creare le giuste istanze di **Player** a seconda del tipo di partita scelto, è una classica applicazione dei pattern **AbstractFactory** e **FactoryMethod**.
Inoltre, crea anche parte della UI di gioco contenuta in oggetti, come i backgrounds e le barre della vita e dell’aura.

## Come DLV conosce lo stato del gioco
Per passare tutto lo stato del gioco a DLV è stata utilizzata una particolare implementazione del pattern **Observer**, ovvero il giocatore comandato dall’IA è observer di diversi subject (l’altro player e le varie onde) i quali ad ogni aggiornamento del loro stato mandano una **notify()** contentente un **InformationPack**, una piccola classe wrapper che contiene informazioni base sulla posizione, direzione ed azione. Questo pacchetto integra il sistema **EmbASP** e quindi una volta ricevuto dal player IA, viene inviato al sistema DLV, il quale elabora una soluzione che può restituire due tipi di risposte: **Go(Direction)** nel caso si è in pericolo di essere attaccati, o non si è allineati al soggetto per attaccare, oppure **Do(Attack)** nel caso in cui non si è minacciati e si è allineati con il soggetto per sferrare un attacco.

## Aggiunta Personagi:
Aggiungere un nuovo personaggio è molto facile, la classe Player prende le informazioni riguardanti le varie sprite da un file **properties**. Basta quindi specificare il nuovo personaggio nel file properties inserendo l’url dell’immagine che contiene le sprite nuove, e la mappatura delle varie azioni su essa. Tutto questo senza toccare ne la classe player ne la factory.

## Osservazione
L’utilizzo della factory ha permesso una divisione netta tra le due modalità di gioco, infatti nella modalità single player tutta la parte di intelligenza artificiale, observer, invio pacchetti informazione ecc. non viene proprio insaziata.

### Di seguito viene riportato uno schema generico delle classi in UML.
![UML delle classi](https://firebasestorage.googleapis.com/v0/b/personal-ee8a6.appspot.com/o/DragonFightUML.jpg?alt=media&token=cb9120c2-b987-4064-97e2-10c6147b22c4)

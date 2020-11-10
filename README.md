# hanasu
VER. #0.1
Adattata la chat a 2 utenti in modo che accettasse un numero indeterminato di client

VER. #0.1.2
I ServerThread sono salvati in un array

VER. #0.1.3
I nickname vengono salvati nel proprio thread e chiesti all'ingresso, una volta selezionato il nick si è ufficialmente operativi (non sono presenti controlli)

VER. #0.2
I client sono stati riadattati per il numero di input ed output richiesti alla connessione, creata una classe apposita sul server per l'inizializzazione
(al momento il clinet non ha necessitato modifiche se non il numero di I/O allo start)

VER. #0.3.464
Il Grande Fratello ti osserva sempre

VER. #0.4
Ora e' possibile inviare i messaggi a tutti gli utenti connessi

VER. #0.5
Ora tutti riceveranno un avviso quando qualcuno si connette e si disconnette

VER. #0.6
Ora si riceverà una lista di tutti i nickname già connessi

VER. #0.6.2
Per problemi con la lista dei nick ora i thread sono inseriti nell'array solo dopo aver inserito il nickname

VER. #0.6.3
Ora nella lista dei nickname non c'e' la virgola finale

VER. #0.7
Aggiunta la rimozione del thread alla chiusura della connessione col client

VER. #0.8
Aggiunto private message

VER. #0.8.2
Creato formato comando /dm nome^

VER. #0.8.3
Aggiunto rilevazione mancato nome, nome non presente

VER. #0.8.4
Creati i messagi di sistema

VER. #0.8.5
Creato formato dei dm

VER. #0.8.6
Fix chat one to one

VER. #0.8.7
Sistemato nickname one to one, veniva mostrato il destinatario invece del mittente

VER. #0.9
Create gui

VER. #0.9.2
Fatti input tramite gui

VER. #1.0
Riadattato clientGui perchè usasse i metodi già creati nella classe Client 

VER. #1.0.2
Trasmetti is no more a thread

VER. #1.0.3
Output spostato nella gui

VER. #1.0.4
Riadattato serverGui perchè usasse i metodi già creati nella classe MultiServer

VER. #1.0.5
Porta presa tramite input

VER. #1.1
Gestita chiusura inaspettata del client

VER. #1.1.3
Controllo nickname, non possono esistere 2 nickname uguali

VER. #1.1.5
Adesso è possibile cambiare nickname se è già esistente

TO DO
Comandi server,
Gruppi

DONE
chat one-all,
chat one-one,
GUI,
Chiusura forzata,
Controlli sul nickname,
Commentato

Project Manager
C.Stefano
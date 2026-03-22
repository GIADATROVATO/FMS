Food API primo approccio di autenticazione JWT

Questo progetto rappresenta una prima implementazione di una REST API per la gestione di entità Food, con introduzione di un sistema di autenticazione basato su JWT.
L’obiettivo era comprendere il funzionamento dell’autenticazione token-based e l’integrazione di un filtro di sicurezza.
Obiettivo Il progetto è stato sviluppato come fase sperimentale per comprendere:

	-	Il flusso Request → Filtro → Controller
	-	Il meccanismo di autenticazione tramite token
	-	L’uso di DTO per separare il livello API dal dominio
	-	L’integrazione della sicurezza
	-	Il funzionamento delle richieste HTTP (GET, POST, PUT, DELETE) in un’API REST
	-	L’uso di strumenti come Postman per testare endpoint con e senza autenticazione
	
Nota L’implementazione JWT in questo progetto era parziale e non completa dal punto di vista del flusso di autenticazione (mancavano componenti strutturati come AuthRequest e AuthResponse ed altre classi).
Per questo motivo, successivamente verrà sviluppato un secondo progetto con un’implementazione più strutturata e completa del processo di autenticazione.

Ho poi implementato una prima integrazione con un sistema ERP utilizzando un servizio dedicato (ErpService).
Il flusso prevede la gestione dello stato di sincronizzazione tramite valori come PENDING, SENT E FAILED, per garantire tracciabilità e possibilità di retry in caso di errore. 

Le chiamate verso ERP, garantite dalla classe Spring -> WebClient, sono gestite separatamente dalla logica principale, con un approccio asincrono per evitare blocchi della request.
Inoltre, ho introdotto un sistema di eccezioni personalizzate e un GlobalExceptionHandler per una gestione centralizzata degli errori.
Questa implementazione rappresenta una prima versione orientata alla resilienza e alla seprazione delle responsabilità tra logica applicativa e integrazione esterna.


Food API primo appriccio di autenticazione JWT 

Questo progetto rappresenta una prima implementazione di una REST API per la gestione di entità Food, con introduzione di un sistema di autenticazione basato su JWT.

L’obiettivo era comprendere il funzionamento dell’autenticazione token-based e l’integrazione di un filtro di sicurezza.

Obiettivo 
Il progetto è stato sviluppato come fase sperimentale per comprendere:
- Il flusso Request → Filtro → Controller
- Il meccanismo di autenticazione tramite token
- L’uso di DTO per separare il livello API dal dominio
- L’integrazione della sicurezza
- Il funzionamento delle richieste HTTP (GET, POST, PUT, DELETE) in un’API REST
- L’uso di strumenti come Postman per testare endpoint con e senza autenticazione

Nota
L’implementazione JWT in questo progetto era parziale e non completa dal punto di vista del flusso di autenticazione (mancavano componenti strutturati come AuthRequest e AuthResponse ed altre classi).

Per questo motivo, successivamente verrà sviluppato un secondo progetto con un’implementazione più strutturata e completa del processo di autenticazione.

# carretti
gestione del Carrello ad Aspetti

il file Carrello ad Aspetti.xmind contiene la mappa mentale del progetto


Project Flow

Step1)
Inizializzazione dello Shop attraverso il metodo Shop.InitShop();
L'aspetto provvede ad eseguire il metodo 	public List<Prodotto> getProdotti() che resistuisce la lista dei prodotti presenti nello shop. 
Step2)
L'utente effettua la procedura di login()
L'aspetto, nel caso di avvenuta autenticazione, provvede a 
1. controllare se per l'utente esiste un sessionId, ovvero l'utente ha già visitato lo shop. In caso contrario, viene generato un nuovo identificativo di Sessione. 
2. Controlla se vi è un carrello associato all'identificativo sessionId dell'utente e ne ritorna la relativa istanza In caso contrario, viene istanziato un nuovo carrello


ToDo: 
1. Inserire logica per il controllo della validità del timestamp della Sessione utente. 



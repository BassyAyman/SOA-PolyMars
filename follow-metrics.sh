#!/bin/bash

# Fonction pour envoyer la requête GET et extraire les valeurs JSON
send_get_request() {
    response=$(curl -s http://localhost:8082/rocketMetrics)  # Effectuer la requête GET et stocker la réponse JSON
    altitude=$(echo "$response" | sed -n 's/.*"altitude":\([^,]*\).*/\1/p')  # Extraire la valeur de l'altitude
    velocity=$(echo "$response" | sed -n 's/.*"velocity":\([^,]*\).*/\1/p')  # Extraire la valeur de la vitesse
    fuelVolume=$(echo "$response" | sed -n 's/.*"fuelVolume":\([^,]*\).*/\1/p')  # Extraire la valeur du volume de carburant
    elapsedTime=$(echo "$response" | sed -n 's/.*"elapsedTime":\([^,]*\)".*/\1/p')  # Extraire la valeur du temps écoulé

    # Afficher les valeurs sur une seule ligne
    printf "\rAltitude: %.2f m | Vitesse: %.2f m/s | Carburant: %.2f m^3 | Temps Écoulé: %s" "$altitude" "$velocity" "$fuelVolume" "$elapsedTime"
}

# Boucle pour envoyer la requête GET toutes les 100 ms
while true; do
    send_get_request
    sleep 0.1  # Attendre 100 ms
done

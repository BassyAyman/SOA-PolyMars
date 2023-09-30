SERVICE_NAME="$1"

docker-compose stop "$SERVICE_NAME"

docker-compose up --build "$SERVICE_NAME"
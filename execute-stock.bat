docker-compose -f docker-compose-infra.yml up -d
cd stock
CALL gradlew build
cd ..
docker-compose up --build

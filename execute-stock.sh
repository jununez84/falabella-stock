#!/bin/bash
docker-compose -f docker-compose-infra.yml up -d
cd stock
./gradlew build
cd ..
docker-compose up --build

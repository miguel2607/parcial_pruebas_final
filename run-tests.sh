#!/bin/bash

echo "======================================"
echo "TicketFast - Ejecutar Pruebas"
echo "======================================"

echo ""
echo "1. Compilando proyecto..."
mvn clean package -DskipTests

echo ""
echo "2. Levantando contenedores de prueba..."
docker-compose -f docker-compose.test.yml up -d

echo ""
echo "3. Esperando que los servicios esten listos..."
sleep 10

echo ""
echo "4. Ejecutando pruebas de integracion..."
mvn test -Dtest=ApiIntegrationTest

echo ""
echo "5. Ejecutando pruebas de sistema..."
mvn test -Dtest=SystemE2ETest

echo ""
echo "6. Deteniendo contenedores..."
docker-compose -f docker-compose.test.yml down

echo ""
echo "======================================"
echo "Pruebas completadas"
echo "======================================"

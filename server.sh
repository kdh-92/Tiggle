#!/bin/bash

set -e  # 하나라도 에러나면 스크립트 즉시 종료

 # 실행 중인 컨테이너 전체 제거
echo "Stopping and removing existing containers..."
docker compose -f docker-compose.yml -f docker-compose.kafka.yml down

# 백그라운드로 컨테이너 빌드 및 실행
echo "Building and starting containers..."
docker compose -f docker-compose.yml -f docker-compose.kafka.yml up -d --build

# 사용하지 않는 이미지 정리
echo "Cleaning up unused images..."
docker image prune -f

echo "✅ Deployment completed successfully!"

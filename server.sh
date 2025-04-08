#!/bin/bash

# 실행 중인 컨테이너 전체 제거
docker-compose -f docker-compose.yml -f docker-compose.kafka.yml down

# tiggle & mysql 백그라운드 실행
docker-compose -f docker-compose.yml -f docker-compose.kafka.yml up -d --build

# 이미지를 새로 생성하며 기존 unused (dangling) 이미지 삭제 처리
docker image prune --force

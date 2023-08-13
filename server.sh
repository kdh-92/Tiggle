#!/bin/bash

# backend/tiggle 디렉토리로 이동
cd backend/tiggle-root/tiggle

# 실행 중인 tiggle & mysql 컨테이너 제거
docker-compose down

# tiggle & mysql 백그라운드 실행
docker-compose up -d --build

# 이미지를 새로 생성하며 기존 unused (dangling) 이미지 삭제 처리
docker image prune --force

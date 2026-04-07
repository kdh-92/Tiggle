# 티글 아이템 & 의상 시스템 기획
> 날짜: 2026-04-07 | 회차: 3 | 상태: 기획

## 컨셉

캐릭터가 레벨업하면서 **아이템 슬롯이 해금**된다.
아이템은 의상, 악세서리, 배경, 이펙트 등으로 구분되며,
특정 업적 달성이나 레벨 도달 시 **보상으로 획득**한다.
착용한 아이템은 프로필과 거래 피드에서 다른 유저에게도 보인다.

---

## 1. 아이템 슬롯 시스템

### 슬롯 해금 조건
| 슬롯 | 해금 조건 | 설명 |
|------|----------|------|
| **모자 (Hat)** | 레벨 2 | 머리 위 장식 |
| **의상 (Outfit)** | 레벨 4 | 몸체 의상 |
| **악세서리 (Accessory)** | 레벨 6 | 목걸이, 안경 등 |
| **배경 (Background)** | 레벨 3 | 캐릭터 뒤 배경 |
| **이펙트 (Effect)** | 레벨 8 | 반짝임, 오라 등 |
| **칭호 (Title)** | 레벨 1 | 닉네임 아래 표시 텍스트 |

※ 알 상태에서는 **배경**, **이펙트**만 장착 가능 (알 위에 모자는 어색)
※ 부화 후 캐릭터 형태에 따라 슬롯 위치가 달라짐 (먼지에 의상 → 뭉치에 의상 등)

---

## 2. 아이템 획득 경로

### 업적 보상 (Achievement Reward)
| 업적 | 보상 아이템 | 등급 |
|------|-----------|------|
| 첫 거래 기록 | "초보 기록자" 칭호 | Common |
| 7일 연속 기록 | 무지개 배경 | Rare |
| 30일 연속 기록 | 불꽃 이펙트 | Epic |
| 100건 기록 달성 | "백전노장" 칭호 | Rare |
| 500건 기록 달성 | 금빛 왕관 모자 | Legendary |
| 첫 챌린지 완료 | 리본 악세서리 | Common |
| 챌린지 5회 완료 | 트로피 배경 | Epic |
| 전주 대비 소비 50% 감소 | 검소한 망토 의상 | Rare |
| 이상 소비 0회 (4주 연속) | 현명한 안경 악세서리 | Epic |
| 모든 카테고리 기록 | "만능 기록자" 칭호 | Rare |
| 무소비 30일 달성 | 수행자 의상 | Legendary |
| Legendary 색상 보유 | 홀로그램 오라 이펙트 | Legendary |
| Unique 캐릭터 부화 | 용의 날개 악세서리 | Unique |

### 레벨 보상 (Level Reward)
| 레벨 | 보상 | 비고 |
|------|------|------|
| 2 | 기본 모자 3종 택 1 | 슬롯 해금 기념 |
| 3 | 기본 배경 3종 택 1 | |
| 4 | 기본 의상 3종 택 1 | |
| 5 | 티어별 특수 아이템 1개 | 캐릭터 경로에 따라 다름 |
| 6 | 기본 악세서리 3종 택 1 | |
| 8 | 기본 이펙트 3종 택 1 | |
| MAX | 티어별 최종 의상 세트 | 풀세트 보상 |

### 티어별 특수 아이템 (레벨 5 보상)
| 경로 | 아이템 | 설명 |
|------|--------|------|
| Common (재물) | 금빛 안경 | 금괴를 감정하는 안경 |
| Rare (자연) | 꽃 화관 | 머리 위 꽃 장식 |
| Epic (바다) | 산호 목걸이 | 바다의 보물 |
| Legendary (별) | 별 먼지 이펙트 | 반짝이는 별가루 |
| Unique (용) | 미니 날개 | 작은 날개 장식 |

---

## 3. 아이템 등급

| 등급 | 색상 | 획득 난이도 | 비고 |
|------|------|-----------|------|
| **Common** | 회색 | 기본 보상 | 누구나 쉽게 |
| **Rare** | 파랑 | 꾸준한 기록 | 1~2주 노력 |
| **Epic** | 보라 | 특별한 업적 | 1~2개월 노력 |
| **Legendary** | 금색 | 극히 어려운 조건 | 장기 플레이 |
| **Unique** | 무지개 | 캐릭터 조건 연동 | 최상위 |

---

## 4. DB 스키마

### item_catalog (정적 — 아이템 정의)
```sql
CREATE TABLE item_catalog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    name_en VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    slot VARCHAR(20) NOT NULL,           -- HAT, OUTFIT, ACCESSORY, BACKGROUND, EFFECT, TITLE
    tier VARCHAR(20) NOT NULL,           -- COMMON, RARE, EPIC, LEGENDARY, UNIQUE
    image_key VARCHAR(100) NOT NULL,     -- FE 에셋 키
    unlock_type VARCHAR(20) NOT NULL,    -- ACHIEVEMENT, LEVEL, SPECIAL
    unlock_condition VARCHAR(200),       -- 해금 조건 설명 (JSON or 텍스트)
    required_character_level INT DEFAULT 0,  -- 장착 가능 최소 레벨
    
    UNIQUE KEY uk_name_en (name_en),
    INDEX idx_slot (slot),
    INDEX idx_tier (tier)
);
```

### member_items (회원 보유 아이템)
```sql
CREATE TABLE member_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    acquired_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    acquire_reason VARCHAR(50),          -- "ACHIEVEMENT:first_record", "LEVEL:5" 등
    
    UNIQUE KEY uk_member_item (member_id, item_id),
    INDEX idx_member_id (member_id)
);
```

### member_equipment (현재 장착 상태)
```sql
CREATE TABLE member_equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    hat_item_id BIGINT,
    outfit_item_id BIGINT,
    accessory_item_id BIGINT,
    background_item_id BIGINT,
    effect_item_id BIGINT,
    title_item_id BIGINT,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_member_id (member_id)
);
```

### achievements (업적 정의)
```sql
CREATE TABLE achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,    -- "FIRST_RECORD", "STREAK_7" 등
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    condition_type VARCHAR(30) NOT NULL,  -- RECORD_COUNT, STREAK, CHALLENGE, CATEGORY 등
    condition_value INT NOT NULL,         -- 달성 기준값
    reward_item_id BIGINT,               -- 보상 아이템 FK
    reward_exp INT DEFAULT 0,            -- 추가 경험치 보상
    
    INDEX idx_condition_type (condition_type)
);
```

### member_achievements (회원 업적 달성)
```sql
CREATE TABLE member_achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    achievement_id BIGINT NOT NULL,
    achieved_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE KEY uk_member_achievement (member_id, achievement_id),
    INDEX idx_member_id (member_id)
);
```

---

## 5. API 설계

### 아이템 관련
```
GET  /api/v1/items/inventory           — 내 보유 아이템 목록
GET  /api/v1/items/catalog             — 전체 아이템 카탈로그 (잠금 상태 포함)
PUT  /api/v1/items/equip               — 아이템 장착/해제
GET  /api/v1/items/equipment           — 현재 장착 상태
GET  /api/v1/items/equipment/{memberId} — 다른 유저 장착 상태
```

### 업적 관련
```
GET  /api/v1/achievements              — 전체 업적 목록 (달성 여부 포함)
GET  /api/v1/achievements/recent       — 최근 달성 업적
```

### 장착 요청 예시
```json
PUT /api/v1/items/equip
{
  "slot": "HAT",
  "itemId": 15        // null이면 해제
}
```

---

## 6. FE 렌더링

### 캐릭터 + 아이템 레이어 구조
```
[Layer 5] Effect       — 이펙트 (반짝임, 오라)
[Layer 4] Hat          — 모자
[Layer 3] Accessory    — 악세서리  
[Layer 2] Character    — 캐릭터 본체 (고유 색상 적용)
[Layer 1] Outfit       — 의상
[Layer 0] Background   — 배경
```

각 레이어는 독립된 SVG/PNG로 관리하며, CSS position absolute로 겹침.
캐릭터 진화 단계마다 슬롯 위치(offset)가 다름 → character_catalog에 slot_offsets JSON 저장.

```tsx
<CharacterRenderer>
  <BackgroundLayer item={equipment.background} />
  <OutfitLayer item={equipment.outfit} />
  <CharacterBody 
    form={character.currentForm} 
    color={character.color} 
  />
  <AccessoryLayer item={equipment.accessory} />
  <HatLayer item={equipment.hat} />
  <EffectLayer item={equipment.effect} />
</CharacterRenderer>
```

---

## 7. 전체 Phase 3 작업 재정리

| # | 작업 | 난이도 | 의존성 |
|---|------|--------|--------|
| 3-1 | Member 색상 시스템 | M | Phase 1 |
| 3-2 | Character 엔티티 + 알 시스템 | M | 3-1 |
| 3-3 | character_catalog 시딩 | S | 3-2 |
| 3-4 | 부화 판정 알고리즘 | L | 3-2 |
| 3-5 | 경험치 + 레벨업 | M | 3-4 |
| 3-6 | 캐릭터 API | M | 3-5 |
| 3-7 | item_catalog + achievements 시딩 | M | 3-3 |
| 3-8 | 아이템 획득/장착 API | M | 3-7 |
| 3-9 | 업적 판정 시스템 (EventListener) | L | 3-7 |
| 3-10 | FE 캐릭터 렌더링 + 색상 | L | 3-6 |
| 3-11 | FE 아이템 장착 UI | L | 3-8, 3-10 |
| 3-12 | FE 도감/업적 UI | M | 3-8 |
| 3-13 | FE 부화 연출 | M | 3-10 |

---

## 사용자 승인
- [ ] 승인 / 수정 요청: ___

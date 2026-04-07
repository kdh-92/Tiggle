# 티글 캐릭터 시스템 상세 기획
> 날짜: 2026-04-07 | 회차: 2 | 상태: 기획

## 컨셉

모든 유저는 **알(Egg)**로 시작한다.
알은 계정 기반 **고유 색상**을 가지며, 이 색상은 캐릭터가 진화해도 계속 계승된다.
기록 습관(빈도, 카테고리, 꾸준함)에 따라 알에서 부화하는 **캐릭터 종류**가 결정된다.
캐릭터는 등급(Common ~ Legendary)이 있고, 레벨업하며 진화한다.

---

## 1. 고유 색상 시스템

### 색상 생성
- 회원 가입 시 `memberId` + `email`을 SHA-256 해시 → 하위 3바이트로 HSL 색상 생성
- **Hue**: 0~359 (해시 바이트1 % 360) → 기본 색조
- **Saturation**: 50~90% (해시 바이트2 % 41 + 50) → 채도
- **Lightness**: 45~65% (해시 바이트3 % 21 + 45) → 명도
- 결과: 약 **360 × 41 × 21 = 309,960가지** 조합 (사실상 무한에 가까운 고유색)

### 색상 등급 (Color Rarity)
해시의 특정 비트 패턴으로 희귀도 결정:

| 등급 | 확률 | 설명 | 렌더링 |
|------|------|------|--------|
| **Normal** | 90% | 단색 | 고유 HSL 단일 색상 |
| **Shine** | 7% | 광택 | 단색 + 미세 광택 효과 (밝은 하이라이트) |
| **Holographic** | 2.5% | 홀로그램 | 2색 그라데이션 (고유색 ↔ 보색 30도 회전) |
| **Legendary** | 0.5% | 레전드 | 3색 그라데이션 + 파티클 효과 |

```
색상 등급 판정 로직:
hash_value = SHA256(memberId + email)
rarity_byte = hash_value[4]  // 5번째 바이트

if (rarity_byte < 2)       → Legendary  (2/256 = 0.78%)
else if (rarity_byte < 8)  → Holographic (6/256 = 2.34%)
else if (rarity_byte < 26) → Shine       (18/256 = 7.03%)
else                        → Normal      (230/256 = 89.84%)
```

### 색상 저장
```kotlin
data class MemberColor(
    val hue: Int,           // 0~359
    val saturation: Int,    // 50~90
    val lightness: Int,     // 45~65
    val rarity: ColorRarity // NORMAL, SHINE, HOLOGRAPHIC, LEGENDARY
)
```
- 회원가입 시 1회 생성, 이후 변경 불가
- `members` 테이블에 `color_hue`, `color_saturation`, `color_lightness`, `color_rarity` 컬럼 추가

---

## 2. 알(Egg) 시스템

### 알 단계
모든 유저는 알로 시작한다. 알의 외형은 고유 색상이 적용된다.

| 단계 | 이름 | 필요 기록 | 설명 |
|------|------|----------|------|
| 0 | 알 (Egg) | 0건 | 가입 직후 |
| 1 | 흔들리는 알 | 5건 | 알이 살짝 움직임 |
| 2 | 금 간 알 | 15건 | 금이 가기 시작 |
| 3 | 부화 직전 | 30건 | 빛이 새어 나옴 → **부화 트리거** |

### 부화 판정
30건 기록 달성 시 부화. **부화하는 캐릭터 종류**는 지금까지의 기록 패턴으로 결정:

```
부화 판정 기준:
1. 기록 빈도 (얼마나 자주?) → 매일 vs 몰아서
2. 주요 카테고리 (무엇을?) → 식비 vs 교통 vs 문화 등
3. 기록 꾸준함 (연속 기록일) → streak 기반
4. 소비 패턴 (평균 금액대) → 소액 vs 고액
```

---

## 3. 캐릭터 종류 & 등급

### 등급 (Tier)
| 등급 | 확률 | 진화 단계 수 | 획득 조건 |
|------|------|-------------|----------|
| **Common** | 60% | 10단계 | 기본 부화 |
| **Rare** | 25% | 8단계 | 특정 패턴 충족 |
| **Epic** | 10% | 6단계 | 까다로운 조건 |
| **Legendary** | 4% | 5단계 | 매우 까다로운 조건 |
| **Unique** | 1% | 3단계 | 극한 조건 + 운 |

### Common: 재물의 길 (기본 경로)
> 조건: 특별한 패턴 없이 꾸준히 기록

| 레벨 | 이름 | 필요 경험치 | 설명 |
|------|------|-----------|------|
| 1 | 먼지 | 0 | 작은 시작 |
| 2 | 뭉치 | 100 | 모이기 시작 |
| 3 | 동전 | 250 | 가치가 보임 |
| 4 | 지폐 | 500 | 성장하는 소비력 |
| 5 | 금 부스러기 | 800 | 반짝이기 시작 |
| 6 | 금 조각 | 1,200 | 존재감 |
| 7 | 한 돈 | 1,800 | 제대로 된 가치 |
| 8 | 금괴 | 2,500 | 단단한 자산 |
| 9 | 금괴 모음 | 3,500 | 쌓이는 부 |
| 10 | 금괴 산 | 5,000 | 현명한 소비의 끝 |

### Rare: 자연의 길 (꾸준함 특화)
> 조건: 부화 전 기록 연속 7일 이상 + 일 평균 1.5건 이상

| 레벨 | 이름 | 필요 경험치 | 설명 |
|------|------|-----------|------|
| 1 | 씨앗 | 0 | 대지에 심어짐 |
| 2 | 새싹 | 120 | 땅을 뚫고 나옴 |
| 3 | 풀잎 | 300 | 바람에 흔들림 |
| 4 | 꽃봉오리 | 600 | 곧 피어날 준비 |
| 5 | 꽃 | 1,000 | 만개 |
| 6 | 열매 | 1,600 | 결실 |
| 7 | 나무 | 2,400 | 뿌리 깊은 습관 |
| 8 | 숲 | 3,500 | 현명한 소비의 생태계 |

### Epic: 바다의 길 (다양한 카테고리)
> 조건: 부화 전 5개 이상 카테고리 사용 + 카테고리당 3건 이상

| 레벨 | 이름 | 필요 경험치 | 설명 |
|------|------|-----------|------|
| 1 | 물방울 | 0 | 한 방울의 시작 |
| 2 | 시냇물 | 150 | 흐르기 시작 |
| 3 | 개울 | 400 | 방향이 생김 |
| 4 | 강 | 800 | 힘차게 흐름 |
| 5 | 호수 | 1,500 | 고요하지만 깊음 |
| 6 | 바다 | 2,500 | 끝없는 가능성 |

### Legendary: 별의 길 (고액 관리자)
> 조건: 부화 전 기록 건당 평균 금액 상위 10% + 연속 기록 14일 이상

| 레벨 | 이름 | 필요 경험치 | 설명 |
|------|------|-----------|------|
| 1 | 유성 | 0 | 떨어지는 빛 |
| 2 | 별 | 200 | 밤하늘에 빛남 |
| 3 | 별자리 | 600 | 이야기가 생김 |
| 4 | 은하수 | 1,500 | 장대한 규모 |
| 5 | 우주 | 3,000 | 무한한 가능성 |

### Unique: 용의 길 (극한 도전자)
> 조건: 부화 전 30건 기록을 연속 무소비 없이 30일 안에 달성 + 챌린지 1회 완료 + 색상 Legendary

| 레벨 | 이름 | 필요 경험치 | 설명 |
|------|------|-----------|------|
| 1 | 아기 용 | 0 | 전설의 시작 |
| 2 | 비룡 | 1,000 | 하늘을 날기 시작 |
| 3 | 신룡 | 5,000 | 전설이 되다 |

---

## 4. 부화 판정 알고리즘

```
function determineCharacter(member, records):
    stats = analyzeRecords(records)  // 30건 분석
    
    // 점수 계산
    streakScore    = stats.maxStreak / 30.0           // 0~1 (연속 기록 비율)
    frequencyScore = stats.totalDays / stats.daySpan  // 0~1 (기록 빈도)
    diversityScore = stats.categoryCount / 10.0       // 0~1 (카테고리 다양성)
    amountScore    = percentileRank(stats.avgAmount)  // 0~1 (금액 백분위)
    
    // Unique 판정 (가장 먼저 체크)
    if streakScore >= 0.9 AND frequencyScore >= 0.8 
       AND member.colorRarity == LEGENDARY
       AND hasCompletedChallenge:
        return UNIQUE_DRAGON  // 1%
    
    // Legendary 판정
    if amountScore >= 0.9 AND streakScore >= 0.7:
        return LEGENDARY_STAR  // 4%
    
    // Epic 판정
    if diversityScore >= 0.5 AND stats.categoryCount >= 5
       AND stats.minPerCategory >= 3:
        return EPIC_OCEAN  // 10%
    
    // Rare 판정
    if streakScore >= 0.5 AND frequencyScore >= 0.6:
        return RARE_NATURE  // 25%
    
    // Common (기본)
    return COMMON_GOLD  // 60%
```

---

## 5. DB 스키마

### members 테이블 확장
```sql
ALTER TABLE members ADD COLUMN color_hue SMALLINT;
ALTER TABLE members ADD COLUMN color_saturation SMALLINT;
ALTER TABLE members ADD COLUMN color_lightness SMALLINT;
ALTER TABLE members ADD COLUMN color_rarity VARCHAR(20) DEFAULT 'NORMAL';
-- color_rarity: NORMAL, SHINE, HOLOGRAPHIC, LEGENDARY
```

### characters (신규)
```sql
CREATE TABLE characters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL UNIQUE,
    
    -- 캐릭터 상태
    stage VARCHAR(20) NOT NULL DEFAULT 'EGG',       -- EGG, HATCHING, ACTIVE
    tier VARCHAR(20),                                 -- COMMON, RARE, EPIC, LEGENDARY, UNIQUE
    character_path VARCHAR(30),                       -- GOLD, NATURE, OCEAN, STAR, DRAGON
    
    -- 레벨링
    level INT NOT NULL DEFAULT 0,
    experience INT NOT NULL DEFAULT 0,
    
    -- 알 상태
    egg_records INT NOT NULL DEFAULT 0,               -- 부화 전 기록 건수 (0~30)
    egg_phase INT NOT NULL DEFAULT 0,                 -- 0: 알, 1: 흔들림, 2: 금, 3: 부화직전
    
    -- 부화 판정 데이터 (부화 시점 스냅샷)
    hatch_max_streak INT DEFAULT 0,
    hatch_category_count INT DEFAULT 0,
    hatch_avg_amount INT DEFAULT 0,
    hatch_frequency_score DECIMAL(3,2) DEFAULT 0,
    hatched_at DATETIME,
    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_member_id (member_id),
    INDEX idx_tier (tier)
);
```

### character_catalog (신규 — 정적 데이터)
```sql
CREATE TABLE character_catalog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tier VARCHAR(20) NOT NULL,           -- COMMON, RARE, EPIC, LEGENDARY, UNIQUE
    path VARCHAR(30) NOT NULL,           -- GOLD, NATURE, OCEAN, STAR, DRAGON
    level INT NOT NULL,
    name VARCHAR(50) NOT NULL,           -- "먼지", "뭉치", "동전" ...
    name_en VARCHAR(50) NOT NULL,        -- "dust", "clump", "coin" ...
    description VARCHAR(200),            -- 설명
    required_exp INT NOT NULL,           -- 필요 경험치
    image_key VARCHAR(100) NOT NULL,     -- 이미지 에셋 키 (FE에서 매핑)
    
    UNIQUE KEY uk_path_level (path, level)
);
```

---

## 6. API 설계

### 캐릭터 조회
```
GET /api/v1/character/me
Response: {
  stage: "ACTIVE",                      // EGG | HATCHING | ACTIVE
  tier: "RARE",
  path: "NATURE",
  level: 3,
  experience: 380,
  nextLevelExp: 600,
  currentForm: {
    name: "풀잎",
    nameEn: "grass",
    description: "바람에 흔들리는 풀잎",
    imageKey: "nature_3_grass"
  },
  color: {
    hue: 142,
    saturation: 72,
    lightness: 55,
    rarity: "HOLOGRAPHIC",
    cssValue: "hsl(142, 72%, 55%)",      // FE 렌더링용
    gradientCss: "linear-gradient(...)"   // Holo/Legend용
  },
  egg: null                              // 부화 후 null, 부화 전이면 아래
  // egg: { phase: 2, records: 22, nextPhaseAt: 30 }
}
```

### 다른 유저 캐릭터 조회
```
GET /api/v1/character/{memberId}
Response: (동일 구조, 단 experience/nextLevelExp 미노출)
```

### 캐릭터 카탈로그 (전체 도감)
```
GET /api/v1/character/catalog
Response: {
  paths: [
    {
      path: "GOLD", tier: "COMMON", 
      forms: [
        { level: 1, name: "먼지", imageKey: "gold_1_dust", unlocked: true },
        { level: 2, name: "뭉치", imageKey: "gold_2_clump", unlocked: true },
        { level: 3, name: "동전", imageKey: "gold_3_coin", unlocked: false },
        ...
      ]
    },
    ...
  ]
}
```

---

## 7. FE 렌더링 전략

### 색상 적용
```tsx
// 캐릭터 이미지는 흰색/회색 기본 에셋
// CSS filter + hue-rotate로 고유 색상 적용
// 이렇게 하면 1개 에셋으로 모든 색상 커버

.character-image {
  filter: hue-rotate(${hue}deg) saturate(${saturation}%);
}

// Holographic: CSS animation으로 색상 시프트
@keyframes holo {
  0%   { filter: hue-rotate(${hue}deg); }
  50%  { filter: hue-rotate(${hue + 30}deg); }
  100% { filter: hue-rotate(${hue}deg); }
}

// Legendary: 파티클 오버레이 + 3색 그라데이션
```

### 에셋 구조
```
/assets/characters/
  egg/
    egg_phase_0.svg      -- 알
    egg_phase_1.svg      -- 흔들리는 알
    egg_phase_2.svg      -- 금 간 알
    egg_phase_3.svg      -- 부화 직전
  gold/                  -- Common: 재물의 길
    gold_1_dust.svg
    gold_2_clump.svg
    gold_3_coin.svg
    ...
  nature/                -- Rare: 자연의 길
    nature_1_seed.svg
    ...
  ocean/                 -- Epic: 바다의 길
  star/                  -- Legendary: 별의 길
  dragon/                -- Unique: 용의 길
```

---

## 8. 경험치 시스템 (수정)

| 행동 | 경험치 | 조건 | 비고 |
|------|--------|------|------|
| 거래 기록 | +10 | 하루 최대 5건 (50 cap) | 기본 |
| 연속 기록 보너스 | +5 × 연속일 | 최대 +50 (10일) | 꾸준함 보상 |
| 무소비 달성 | +20 | 챌린지 진행 중 | 절약 보상 |
| 전주 대비 소비 감소 | +30 | 주간 정산 시 | 개선 보상 |
| 이상 소비 없음 | +15 | 주간 정산 시 | 안정 보상 |
| 챌린지 완료 | +100 | 목표 달성 시 | 큰 보상 |
| 신규 카테고리 첫 기록 | +25 | 1회성 | 다양성 보상 |
| 월간 개근 (매일 1건 이상) | +200 | 월 1회 | 극한 꾸준함 |

---

## 9. 구현 우선순위 (Phase 3 세분화)

| # | 작업 | 난이도 | 의존성 |
|---|------|--------|--------|
| 3-1 | Member 색상 시스템 (해시 생성, DB 컬럼, API) | M | Phase 1 |
| 3-2 | Character 엔티티 + Repository + 기본 CRUD | M | 3-1 |
| 3-3 | character_catalog 정적 데이터 시딩 | S | 3-2 |
| 3-4 | 알 단계 진행 로직 (거래 등록 시 egg_records++) | S | 3-2 |
| 3-5 | 부화 판정 알고리즘 구현 | L | 3-4 |
| 3-6 | 경험치 시스템 + 레벨업 로직 | M | 3-5 |
| 3-7 | 캐릭터 API (me, {id}, catalog) | M | 3-6 |
| 3-8 | FE 캐릭터 렌더링 (색상 적용, 에셋) | L | 3-7 |
| 3-9 | FE 도감/프로필 UI | L | 3-8 |
| 3-10 | FE 부화 연출 애니메이션 | M | 3-8 |

---

## 사용자 승인
- [ ] 승인 / 수정 요청: ___

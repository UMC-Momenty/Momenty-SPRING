<div align="center">
  <h1>Momenty</h1>
  <p>🐶🐱 <strong>Momenty — 반려동물과의 모든 순간을 기록하다</strong> 🐾</p>
</div>
<br/>

## ✍️ 프로젝트 개요
> Momenty는 반려동물과 보호자의 일상을 기록하고, 케어 정보를 관리하며 소중한 순간을 오래 간직할 수 있도록 
돕는 **반려 라이프 케어 플랫폼**입니다.
<br/>

- **프로젝트명:** Momenty
- **프로젝트 기간**: 2025.12.21 ~ 2026.02.xx
- **개발 언어 :** Java
- **프레임워크 :** Spring Boot

<br/>

## 👥 팀 협업 방식 
Momenty 프로젝트는 **Git Flow 기반 협업 방식**을 사용하며,  `develop` 브랜치를 중심으로 안정적인 개발과 배포를 지향합니다.
<br/>

### 🌱 브랜치 전략
| Branch     |             Description        |
|------------|--------------------------------|
| `main`     | 항상 배포 가능한 상태의 코드 유지 |
| `develop`  | 개발 중인 기능을 통합하는 브랜치  |
<br/>

### 🔄 브랜치 흐름
1. 초기 프로젝트 설정 시 `main`에서 `develop` 브랜치 생성
2. 모든 작업은 `develop`에서 기능/이슈 단위로 브랜치 생성 
3. 작업 완료 후 `develop`으로 Pull Request(PR) 생성
4. 코드 리뷰 및 테스트 완료 후 `develop → main` 병합
5. 긴급 버그 발생 시 `main → fix/*` 브랜치 생성 후 수정
6. 수정 완료 후 `main`과 `develop`에 모두 병합
<br/>

## 🗂️ 프로젝트 구조
Momenty는 **도메인 중심 구조(Domain Driven Structure)** 를 사용합니다.

```
src/main/java/com/umc/momenty
│
├── MomentyApplication.java # @SpringBootApplication 
│
├── global
│ ├── annotation # 사용자 정의 어노테이션 
│ ├── apiPayload # 공통 응답 및 에러 처리
│ ├── config # 전역 설정 (Swagger, WebMvc 등)
│ ├── entity # BaseEntity
│ ├── infra # 외부 연동 (S3 등)
│ ├── security # JWT, OAuth 보안 설정
│ ├── validator # 사용자 정의 어노테이션
│ └── util # 공통 유틸리티
│
└── domain
    ├── member
    │   ├── controller
    │   ├── converter
    │   ├── dto
    │   ├── entity
    │   ├── enums
    │   ├── exception
    │   ├── repository
    │   └── service
    │
    └── community
        ├── controller
        ├── converter
        ├── dto
        ├── entity
        ├── enums
        ├── exception
        ├── repository
        └── service
```
<br/>

## 📏 코드 컨벤션
### 📢 네이밍 규칙
- **Class / Interface**: PascalCase
  - 예: MemberService, CommunityRepository

- **Method**: camelCase, 동사 또는 동사 + 명사 조합 
  - 예 : getMember()
    
- **Variable**: camelCase, 명사 사용 
  - 예: petName, orderList 

- **Constant**: UPPER_SNAKE_CASE
  - 예: MAX_COUNT

- **Package**: 소문자 사용, 단어는 점으로 구분 
  - 예: com.umc.momenty.member

- **Spring Bean**: CamelCase
  - 예: userService, orderController 
<br/>

### 🎨 코드 스타일
- 들여쓰기: **4 spaces**
- 중괄호 스타일: K&R 스타일 (여는 중괄호를 같은 줄에 작성)
- 연산자 및 콤마 뒤 공백 사용
- 주석 : 복잡한 로직에 한글 또는 영어로 간결하게 주석 작성 
<br/>

## 🧾 이슈 & PR 규칙
### 📌 이슈 규칙  
- **제목**: [타입] 이슈 설명
  - 예: `[✨ Feat] 회원가입 기능 구현`

- **내용**: 기능 설명 및 작업 내용 명시

- **라벨 / Assignee**: 필수 지정
<br/>

### 🔀 Pull Request 규칙
- **제목**: `타입: PR 설명 (#이슈번호)`
  - 예: `Feat: 회원가입 기능 추가 (#12)`

- **설명**:
  - 해결한 이슈 번호
  - 작업 내용 요약

- **리뷰어**: 1명 이상 지정

- **Merge 조건**:
  - 코드 리뷰 승인
  - CI 테스트 통과 (설정된 경우)

- **이슈와 연동** : PR이 관련 이슈를 닫도록 연결하거나 직접 닫기 
 <br/>
 
## 💬 커밋 메시지 컨벤션
| Tag              | Description                                 |
|------------------|---------------------------------------------|
| `✨ feat: `      | 새로운 기능 추가                             |
| `🐛 fix: `       | 버그 수정                                    |
| `📝 docs: `      | 문서 수정                                    |
| `✅ test: `      | 테스트 코드 추가 또는 수정                    |
| `🎨 style: `     | 코드 포맷팅, 세미콜론 누락 등 (로직 변경 없음) |
| `♻️ refactor: `  | 코드 리팩토링                                |
| `⚡ perf: `      | 성능 개선                                    |
| `🏗️ build: `     | 빌드 시스템 또는 의존성 변경                  |
| `⚙️ ci: `        | CI 관련 설정 수정                            |
| `🔧 chore: `     | 설정 파일, 기타 잡일                         |
<br/>

### 커밋 메시지 형식
- 제목과 본문은 **한 줄을 띄워 구분**
- 제목에는 **타입, 변경 내용, 이슈 번호** 를 포함하여 작성 
<br/>

### 커밋 메시지 예시

```
🔧 chore: 프로젝트 초기 세팅 (#1)

- 프로젝트 기본 구조 초기화
- 공통 ApiResponse 및 에러 코드 추가
- 전역 예외 처리 설정
- Swagger 및 Gradle 설정 구성
```
<br/>

### ✏️ 커밋 메시지 작성 규칙
**1. Subject (제목)**
- 한글로 명확하고 간결하게 작성
- 동사 원형 사용 (명령문 형태)
- 제목 끝에 마침표 사용 금지
- 영문 기준 50자 이내 권장
- 무엇을 변경했는지 한 눈에 알 수 있도록 작성
  - ex) 회원가입 기능 구현

<br/>

**2. Body (본문, 선택)**
- 변경 이유나 부연 설명이 필요한 경우 작성
- 한 줄당 72자 이내로 작성
- “어떻게 변경했는지”보다 **“무엇을 / 왜 변경했는지”** 중심으로 작성



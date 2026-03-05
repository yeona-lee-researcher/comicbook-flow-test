# 만화책 대여 관리 시스템 📚

Java와 MySQL을 사용한 콘솔 기반 만화책 대여 관리 시스템입니다.

## 🎯 프로젝트 개요

이 프로젝트는 다음과 같은 기능을 제공합니다:
- 만화책 등록, 조회, 수정, 삭제 (CRUD)
- 회원 등록 및 관리
- 만화책 대여/반납 시스템
- 대여 기록 조회

## 🛠️ 기술 스택

- **언어**: Java 17+
- **데이터베이스**: MySQL 8.0
- **JDBC**: MySQL Connector/J
- **빌드 도구**: 수동 컴파일 (또는 IDE 사용)

## 📁 프로젝트 구조

```
aibe5_Team2/
├── src/
│   ├── Main.java              # 메인 진입점
│   ├── App.java               # 애플리케이션 로직
│   ├── Rq.java                # 명령어 파싱 유틸
│   ├── DBUtil.java            # DB 연결 유틸
│   ├── Comic.java             # 만화책 데이터 클래스
│   ├── Member.java            # 회원 데이터 클래스
│   ├── Rental.java            # 대여 기록 데이터 클래스
│   ├── ComicRepository.java   # 만화책 DB 처리
│   ├── MemberRepository.java  # 회원 DB 처리
│   └── RentalRepository.java  # 대여 기록 DB 처리
├── schema.sql                 # DB 스키마
├── db.properties             # DB 설정 파일
└── README.md                 # 프로젝트 설명서
```

## 🚀 실행 방법

### 1. 사전 준비사항

- Java 17 이상 설치
- MySQL 8.0 이상 설치 및 실행
- MySQL JDBC 드라이버 다운로드 (mysql-connector-java-8.x.x.jar)

### 2. 데이터베이스 설정

1. MySQL에 접속하여 다음 명령어로 데이터베이스와 테이블을 생성:
   ```sql
   source schema.sql
   ```

2. `db.properties` 파일에서 DB 연결 정보를 본인 환경에 맞게 수정:
   ```properties
   db.url=jdbc:mysql://localhost:3306/comic_rental?serverTimezone=UTC&characterEncoding=UTF-8
   db.username=root
   db.password=your_password_here
   ```

### 3. 컴파일 및 실행

#### 명령줄에서 컴파일 (MySQL JDBC 드라이버 필요)
```bash
# 컴파일
javac -cp ".:mysql-connector-java-8.x.x.jar" src/*.java

# 실행
java -cp ".:mysql-connector-java-8.x.x.jar:src" Main
```

#### IDE 사용 시
1. 프로젝트를 IDE에서 열기
2. MySQL JDBC 드라이버를 클래스패스에 추가
3. Main.java 파일 실행

## 📝 사용 가능한 명령어

| 명령어 | 설명 | 예시 |
|--------|------|------|
| `comic-add` | 만화책 등록 | `comic-add` |
| `comic-list` | 만화책 목록 조회 | `comic-list` |
| `comic-detail [id]` | 만화책 상세 조회 | `comic-detail 1` |
| `comic-update [id]` | 만화책 정보 수정 | `comic-update 1` |
| `comic-delete [id]` | 만화책 삭제 | `comic-delete 1` |
| `member-add` | 회원 등록 | `member-add` |
| `member-list` | 회원 목록 조회 | `member-list` |
| `rent [comicId] [memberId]` | 대여 처리 | `rent 1 1` |
| `return [rentalId]` | 반납 처리 | `return 1` |
| `rental-list` | 전체 대여 목록 | `rental-list` |
| `rental-list open` | 미반납 대여 목록 | `rental-list open` |
| `help` | 도움말 | `help` |
| `exit` | 프로그램 종료 | `exit` |

## 💡 실행 예시

```
=== 만화책 대여 관리 시스템 ===
프로그램이 시작되었습니다.
데이터베이스 연결 성공!
'help'를 입력하면 명령어 목록을 확인할 수 있습니다.

명령어: member-add
이름: 홍길동
전화번호 (선택사항): 010-1234-5678
=> 회원이 등록되었습니다. (id=1)

명령어: comic-add
제목: 슬램덩크
권수: 1
작가: 이노우에 다케히코
=> 만화책이 등록되었습니다. (id=1)

명령어: comic-list
번호 | 제목            | 권수 | 작가                 | 상태       | 등록일
--------------------------------------------------------------------
1    | 슬램덩크        | 1    | 이노우에 다케히코     | 대여가능   | 2024-03-05

총 1권의 만화책이 등록되어 있습니다.

명령어: rent 1 1
대여할 만화책: 슬램덩크 1권
대여자: 홍길동
=> 대여 완료: [대여id=1] 만화(1) → 회원(1)

명령어: rental-list
대여id | 만화책            | 회원       | 대여일     | 반납일
----------------------------------------------------------
1     | 슬램덩크          | 홍길동     | 2024-03-05 | -

총 1건의 대여 기록이 있습니다.

명령어: return 1
=> 반납 완료: 대여id=1

명령어: exit
프로그램을 종료합니다.
```

## 🗄️ 데이터베이스 구조

### comic 테이블 (만화책)
- `id`: 만화책 ID (Primary Key, Auto Increment)
- `title`: 제목
- `volume`: 권수
- `author`: 작가
- `is_rented`: 대여 중 여부 (Boolean)
- `reg_date`: 등록일

### member 테이블 (회원)
- `id`: 회원 ID (Primary Key, Auto Increment)
- `name`: 이름
- `phone`: 전화번호 (선택사항)
- `reg_date`: 등록일

### rental 테이블 (대여 기록)
- `id`: 대여 ID (Primary Key, Auto Increment)
- `comic_id`: 만화책 ID (Foreign Key)
- `member_id`: 회원 ID (Foreign Key)
- `rental_date`: 대여일
- `return_date`: 반납일 (NULL이면 미반납)

## 🔧 주요 기능 특징

### 트랜잭션 처리
- 대여/반납 시 데이터 일관성을 위해 트랜잭션 적용
- 실패 시 자동 롤백으로 데이터 무결성 보장

### 입력 검증
- 필수 입력값 검증
- 숫자 타입 변환 검증
- 존재하지 않는 ID 처리

### 자원 관리
- try-with-resources 또는 finally 블록을 통한 DB 자원 해제
- Connection Pool 대신 매번 연결 생성/해제 (학습용)

## 🎯 학습 포인트

이 프로젝트를 통해 다음을 학습할 수 있습니다:

1. **Java 기본기**: OOP, 예외 처리, 컬렉션 활용
2. **JDBC**: DB 연결, PreparedStatement, ResultSet 활용
3. **SQL**: CRUD 操작, JOIN, Transaction
4. **설계**: Repository 패턴, 관심사 분리
5. **협업**: Git, 코드 리뷰, Pull Request 과정

## 🚧 향후 개선 사항

- [ ] Connection Pool 적용으로 성능 향상
- [ ] 로깅 시스템 추가 (SLF4J + Logback)
- [ ] 단위 테스트 작성 (JUnit)
- [ ] Maven/Gradle 빌드 시스템 적용
- [ ] 설정 관리 개선 (Properties → YAML)
- [ ] 연체 기능 추가 (대여일 + 7일)
- [ ] GUI 버전 개발 (JavaFX 또는 Swing)

## 🤝 기여하기

1. 이 저장소를 Fork합니다
2. 새로운 기능 브랜치를 만듭니다 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 Push합니다 (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성합니다

## 📄 라이선스

이 프로젝트는 교육용으로 제작되었습니다.

---

## ⚠️ 문제 해결

### MySQL 연결 실패 시
1. MySQL 서버가 실행 중인지 확인
2. `db.properties` 파일의 연결 정보 확인
3. MySQL JDBC 드라이버가 클래스패스에 있는지 확인
4. 방화벽 설정 확인

### 한글 깨짐 현상 시
1. MySQL 데이터베이스와 테이블이 UTF-8로 설정되었는지 확인
2. JDBC URL에 `characterEncoding=UTF-8` 파라미터 확인
3. Java 실행 시 `-Dfile.encoding=UTF-8` 옵션 추가

---

**개발 팀**: aibe5_Team2  
**개발 기간**: 2024.03  
**문의**: [이메일 주소 또는 이슈 트래커]
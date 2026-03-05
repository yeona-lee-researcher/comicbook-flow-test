# 빠른 시작 가이드 🚀

## 1단계: MySQL 설정

### MySQL에서 데이터베이스 생성
```sql
-- MySQL에 접속 후 실행
source schema.sql
```

### 또는 수동으로 실행
```sql
CREATE DATABASE IF NOT EXISTS comic_rental CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE comic_rental;

-- schema.sql 파일의 나머지 내용 복사/붙여넣기
```

## 2단계: DB 설정 파일 수정

`db.properties` 파일에서 다음을 수정:
```properties
db.password=your_actual_password
```

## 3단계: MySQL JDBC 드라이버 다운로드

MySQL 공식 사이트에서 MySQL Connector/J 다운로드:
- https://dev.mysql.com/downloads/connector/j/
- `mysql-connector-java-8.0.33.jar` 파일을 프로젝트 루트에 저장

## 4단계: 컴파일 및 실행

### Windows
```batch
compile.bat    # 컴파일
run.bat       # 실행
```

### Linux/Mac
```bash
# 컴파일
javac -cp ".:mysql-connector-java-8.0.33.jar" src/*.java

# 실행  
java -cp ".:mysql-connector-java-8.0.33.jar:src" Main
```

### Maven 사용시 (권장)
```bash
mvn compile exec:java
```

## 문제 해결

### "Access denied" 오류
- MySQL 사용자명/비밀번호 확인
- MySQL 서버가 실행 중인지 확인

### "ClassNotFoundException" 오류
- MySQL JDBC 드라이버 파일이 있는지 확인
- 클래스패스에 jar 파일이 포함되었는지 확인

### 한글 깨짐
- 터미널에서 `chcp 65001` 실행 (Windows)
- Java 실행시 `-Dfile.encoding=UTF-8` 옵션 사용
-- 만화책 대여 관리 시스템 DB 스키마
-- MySQL 8.0 기준

-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS comic_rental CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE comic_rental;

-- 만화책 테이블
CREATE TABLE comic (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL COMMENT '제목',
    volume INT NOT NULL DEFAULT 1 COMMENT '권수',
    author VARCHAR(255) NOT NULL COMMENT '작가',
    is_rented BOOLEAN DEFAULT FALSE COMMENT '대여 중 여부',
    reg_date DATE DEFAULT (CURRENT_DATE) COMMENT '등록일'
);

-- 회원 테이블
CREATE TABLE member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '이름',
    phone VARCHAR(20) COMMENT '전화번호',
    reg_date DATE DEFAULT (CURRENT_DATE) COMMENT '등록일'
);

-- 대여 기록 테이블
CREATE TABLE rental (
    id INT AUTO_INCREMENT PRIMARY KEY,
    comic_id INT NOT NULL COMMENT '만화책 ID',
    member_id INT NOT NULL COMMENT '회원 ID',
    rental_date DATE DEFAULT (CURRENT_DATE) COMMENT '대여일',
    return_date DATE DEFAULT NULL COMMENT '반납일',
    FOREIGN KEY (comic_id) REFERENCES comic(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
);

-- 테스트 데이터 삽입
INSERT INTO comic (title, volume, author) VALUES 
('슬램덩크', 1, '이노우에 다케히코'),
('원피스', 1, '오다 에이치로'),
('나루토', 1, '키시모토 마사시'),
('드래곤볼', 1, '토리야마 아키라');

INSERT INTO member (name, phone) VALUES 
('홍길동', '010-1234-5678'),
('김영희', '010-8765-4321'),
('박민수', '010-1111-2222');
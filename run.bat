@echo off
echo === 만화책 대여 관리 시스템 실행 ===
echo.

REM MySQL JDBC 드라이버 경로
set MYSQL_JAR=mysql-connector-java-8.0.33.jar

REM 클래스패스 설정 
set CLASSPATH=.;%MYSQL_JAR%;src

REM 프로젝트 루트로 이동
cd /d "%~dp0"

REM DB 설정 파일 존재 여부 확인
if not exist db.properties (
    echo db.properties 파일이 없습니다.
    echo DB 연결 정보를 설정해주세요.
    pause
    exit /b 1
)

echo 프로그램을 시작합니다...
echo.

REM Java 프로그램 실행
java -Dfile.encoding=UTF-8 -cp "%CLASSPATH%" Main

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 프로그램 실행 중 오류가 발생했습니다.
    echo - MySQL 서버가 실행 중인지 확인하세요
    echo - db.properties 파일의 설정을 확인하세요
    echo - MySQL JDBC 드라이버가 있는지 확인하세요
)

pause
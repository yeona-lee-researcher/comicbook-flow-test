@echo off
echo === Java 컴파일 스크립트 ===
echo.

REM MySQL JDBC 드라이버 경로 (사용자가 다운로드한 경로에 맞게 수정)
set MYSQL_JAR=mysql-connector-java-8.0.33.jar

REM 클래스패스 설정
set CLASSPATH=.;%MYSQL_JAR%

REM src 디렉토리로 이동
cd /d "%~dp0"

echo 컴파일 중...
javac -cp "%CLASSPATH%" src\*.java

if %ERRORLEVEL% EQU 0 (
    echo 컴파일 성공!
    echo.
    echo 실행하려면 run.bat를 실행하세요.
) else (
    echo 컴파일 실패! MySQL JDBC 드라이버 경로를 확인해주세요.
    echo MySQL Connector/J를 다운로드하고 MYSQL_JAR 경로를 수정하세요.
)

pause
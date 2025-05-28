@echo off
echo Compiling Java project...

:: Set classpath (bin, mysql connector, jfreechart)
set CLASSPATH=bin;src\lib\mysql-connector-j-9.3.0.jar;src\lib\jfreechart.jar

:: Create bin directory if it doesn't exist
if not exist bin mkdir bin

:: Compile all .java files from src to bin
for /R src %%f in (*.java) do (
    echo Compiling %%f
    javac -cp "%CLASSPATH%" -d bin "%%f"
)

if %ERRORLEVEL% equ 0 (
    echo Compilation successful!
) else (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Running program...
java -cp "%CLASSPATH%" Main

pause

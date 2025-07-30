@echo off
echo 🚀 Starting Railway deployment preparation...

REM Check if Java 17 is available
java -version 2>&1 | findstr "version \"17" >nul
if errorlevel 1 (
    echo ❌ Java 17 is required but not found
    exit /b 1
)

echo ✅ Java 17 found

REM Clean and build the project
echo 🔨 Building the application...
call mvnw.cmd clean package -DskipTests

if errorlevel 1 (
    echo ❌ Build failed
    exit /b 1
)

echo ✅ Build successful
echo 📦 JAR file created: target/dump-truck-services-0.0.1-SNAPSHOT.jar

REM Check if the JAR file exists
if exist "target\dump-truck-services-0.0.1-SNAPSHOT.jar" (
    echo ✅ JAR file verified
    for %%A in ("target\dump-truck-services-0.0.1-SNAPSHOT.jar") do echo 📊 JAR file size: %%~zA bytes
) else (
    echo ❌ JAR file not found
    exit /b 1
)

echo 🎉 Railway deployment preparation completed!
echo 📋 Next steps:
echo    1. Push your code to GitHub
echo    2. Connect your repository to Railway
echo    3. Add MySQL database service
echo    4. Configure environment variables
echo    5. Deploy! 
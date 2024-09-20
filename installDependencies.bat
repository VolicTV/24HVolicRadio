@echo off
:: BatchGotAdmin
:-------------------------------------
REM  --> Check for permissions
    IF "%PROCESSOR_ARCHITECTURE%" EQU "amd64" (
>nul 2>&1 "%SYSTEMROOT%\SysWOW64\cacls.exe" "%SYSTEMROOT%\SysWOW64\config\system"
) ELSE (
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
)

REM --> If error flag set, we do not have admin.
if '%errorlevel%' NEQ '0' (
    echo Requesting administrative privileges...
    goto UACPrompt
) else ( goto gotAdmin )

:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
    set params= %*
    echo UAC.ShellExecute "cmd.exe", "/c ""%~s0"" %params:"=""%", "", "runas", 1 >> "%temp%\getadmin.vbs"

    "%temp%\getadmin.vbs"
    del "%temp%\getadmin.vbs"
    exit /B

:gotAdmin
    pushd "%CD%"
    CD /D "%~dp0"
:--------------------------------------

echo Installing dependencies for 24 Hour Volic Radio project...

REM Check if Chocolatey is installed, if not, install it
where choco >nul 2>nul
if %errorlevel% neq 0 (
    echo Installing Chocolatey...
    @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "[System.Net.ServicePointManager]::SecurityProtocol = 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
)

REM Install Node.js
where node >nul 2>nul
if %errorlevel% neq 0 (
    echo Installing Node.js...
    choco install nodejs -y
) else (
    echo Node.js is already installed.
)

REM Install Java 17
java -version 2>&1 | findstr /i "version 17" >nul
if %errorlevel% neq 0 (
    echo Installing Java 17...
    choco install openjdk17 -y
) else (
    echo Java 17 is already installed.
)

REM Install Azure CLI
where az >nul 2>nul
if %errorlevel% neq 0 (
    echo Installing Azure CLI...
    choco install azure-cli -y
) else (
    echo Azure CLI is already installed.
)

REM Install Azure Functions Core Tools
where func >nul 2>nul
if %errorlevel% neq 0 (
    echo Installing Azure Functions Core Tools...
    npm install -g azure-functions-core-tools@4
) else (
    echo Azure Functions Core Tools is already installed.
)

REM Install Azurite
where azurite >nul 2>nul
if %errorlevel% neq 0 (
    echo Installing Azurite...
    npm install -g azurite
) else (
    echo Azurite is already installed.
)

echo All dependencies have been installed or were already present.
echo Please restart your command prompt to ensure all changes take effect.
pause
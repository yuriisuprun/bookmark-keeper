# Library Backend Startup Script
# This script starts the Spring Boot backend application in development mode

Write-Host "Starting Library Backend..." -ForegroundColor Green

# Use the new development startup script
& "$PSScriptRoot\start-backend-dev.ps1" -Profile "dev"

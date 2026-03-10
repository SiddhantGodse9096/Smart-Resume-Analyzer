# Smart Resume Analyzer

A Spring Boot application that analyzes resumes and provides intelligent feedback.

## Features
- Upload resumes in PDF, DOCX, or TXT format
- Extract skills, contact information, education, and experience
- Calculate resume score (0-100)
- Provide improvement suggestions
- Modern, responsive UI

## Technologies
- Spring Boot 3.2.0
- Apache PDFBox (PDF parsing)
- Apache POI (DOCX parsing)
- Thymeleaf (UI templates)
- Lombok

## How to Run

### Option 1: Using Maven Wrapper (Recommended)
1. Ensure Java 17+ is installed
2. Open terminal/command prompt
3. Navigate to project directory:
   ```
   cd "c:\Users\LOQ\OneDrive\Documents\VS CODE\New Project\smart-resume-analyzer"
   ```
4. Run:
   ```
   mvnw.cmd spring-boot:run
   ```
5. Wait for "Started SmartResumeAnalyzerApplication" message
6. Open browser: http://localhost:8080

### Option 2: Using IDE (IntelliJ/Eclipse/VS Code)
1. Open project in your IDE
2. Right-click on `SmartResumeAnalyzerApplication.java`
3. Select "Run" or "Run As > Java Application"
4. Open browser: http://localhost:8080

### Option 3: Using Maven Command
1. Ensure Maven is installed
2. Navigate to project directory
3. Run:
   ```
   mvn clean install
   mvn spring-boot:run
   ```
4. Open browser: http://localhost:8080

## Usage
1. Click upload area and select your resume
2. Click "Analyze Resume"
3. View detailed analysis with score and suggestions

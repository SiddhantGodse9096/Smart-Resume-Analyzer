package com.resumeanalyzer.service;

import com.resumeanalyzer.model.ResumeAnalysis;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResumeAnalyzerService {

    private static final List<String> COMMON_SKILLS = Arrays.asList(
        "Java", "Python", "JavaScript", "C\\+\\+", "C#", "React", "Angular", "Vue",
        "Spring", "Node.js", "Django", "Flask", "SQL", "MongoDB", "PostgreSQL",
        "AWS", "Azure", "Docker", "Kubernetes", "Git", "Machine Learning", "AI",
        "HTML", "CSS", "REST API", "Microservices", "Agile", "Scrum"
    );

    public ResumeAnalysis analyzeResume(String text, String fileName) {
        ResumeAnalysis analysis = new ResumeAnalysis();
        analysis.setFileName(fileName);
        analysis.setExtractedText(text);
        analysis.setSkills(extractSkills(text));
        analysis.setEmails(extractEmails(text));
        analysis.setPhoneNumbers(extractPhoneNumbers(text));
        analysis.setExperienceYears(estimateExperience(text));
        analysis.setEducation(extractEducation(text));
        
        int score = calculateScore(analysis);
        analysis.setScore(score);
        analysis.setSuggestions(generateSuggestions(analysis));
        
        return analysis;
    }

    private List<String> extractSkills(String text) {
        List<String> foundSkills = new ArrayList<>();
        for (String skill : COMMON_SKILLS) {
            Pattern pattern = Pattern.compile("\\b" + skill + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(text).find()) {
                foundSkills.add(skill.replace("\\+\\+", "++").replace("\\\\", ""));
            }
        }
        return foundSkills;
    }

    private List<String> extractEmails(String text) {
        List<String> emails = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            emails.add(matcher.group());
        }
        return emails;
    }

    private List<String> extractPhoneNumbers(String text) {
        List<String> phones = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            phones.add(matcher.group());
        }
        return phones;
    }

    private int estimateExperience(String text) {
        Pattern pattern = Pattern.compile("(\\d+)\\+?\\s*(years?|yrs?)\\s*(of)?\\s*experience", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private List<String> extractEducation(String text) {
        List<String> education = new ArrayList<>();
        String[] degrees = {"Bachelor", "Master", "PhD", "B.Tech", "M.Tech", "MBA", "B.S", "M.S", "Diploma"};
        for (String degree : degrees) {
            if (text.toLowerCase().contains(degree.toLowerCase())) {
                education.add(degree);
            }
        }
        return education;
    }

    private int calculateScore(ResumeAnalysis analysis) {
        int score = 0;
        score += Math.min(analysis.getSkills().size() * 5, 40);
        score += analysis.getExperienceYears() * 5;
        score += analysis.getEducation().size() * 10;
        score += analysis.getEmails().isEmpty() ? 0 : 10;
        score += analysis.getPhoneNumbers().isEmpty() ? 0 : 10;
        return Math.min(score, 100);
    }

    private List<String> generateSuggestions(ResumeAnalysis analysis) {
        List<String> suggestions = new ArrayList<>();
        if (analysis.getSkills().size() < 5) {
            suggestions.add("Add more technical skills to strengthen your resume");
        }
        if (analysis.getEmails().isEmpty()) {
            suggestions.add("Include a professional email address");
        }
        if (analysis.getPhoneNumbers().isEmpty()) {
            suggestions.add("Add a contact phone number");
        }
        if (analysis.getEducation().isEmpty()) {
            suggestions.add("Include your educational qualifications");
        }
        if (analysis.getExperienceYears() == 0) {
            suggestions.add("Mention your years of experience clearly");
        }
        if (suggestions.isEmpty()) {
            suggestions.add("Great resume! Keep it updated with recent achievements");
        }
        return suggestions;
    }
}

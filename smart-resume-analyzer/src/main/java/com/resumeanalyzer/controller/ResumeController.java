package com.resumeanalyzer.controller;

import com.resumeanalyzer.model.ResumeAnalysis;
import com.resumeanalyzer.service.ResumeAnalyzerService;
import com.resumeanalyzer.service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResumeController {

    @Autowired
    private ResumeParserService parserService;

    @Autowired
    private ResumeAnalyzerService analyzerService;

    @GetMapping("/")
    public String home() {
        return "uploadResume";
    }

    @PostMapping("/analyze")
    public String analyzeResume(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String text = parserService.parseResume(file);
            ResumeAnalysis analysis = analyzerService.analyzeResume(text, file.getOriginalFilename());
            model.addAttribute("analysis", analysis);
            return "result";
        } catch (Exception e) {
            model.addAttribute("error", "Error analyzing resume: " + e.getMessage());
            return "uploadResume";
        }
    }
}

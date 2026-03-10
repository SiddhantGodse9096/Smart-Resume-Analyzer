package com.resumeanalyzer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeParserService {

    public String parseResume(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) return "";

        if (fileName.endsWith(".pdf")) {
            return parsePDF(file);
        } else if (fileName.endsWith(".docx")) {
            return parseDOCX(file);
        } else if (fileName.endsWith(".txt")) {
            return new String(file.getBytes());
        }
        throw new IllegalArgumentException("Unsupported file format");
    }

    private String parsePDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String parseDOCX(MultipartFile file) throws IOException {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            StringBuilder text = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText()).append("\n");
            }
            return text.toString();
        }
    }
}

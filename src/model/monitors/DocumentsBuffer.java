package model.monitors;

import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;

public class DocumentsBuffer {

	private final Queue<PDDocument> documents = new ArrayDeque<>();

    public DocumentsBuffer(List<PDDocument> documents) {
        this.documents.addAll(documents);
    }

    public synchronized Optional<PDDocument> pickDocument() {
        return Optional.ofNullable(this.documents.poll());
    }
    
}

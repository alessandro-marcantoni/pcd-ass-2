package controller.tasks;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class StripTask extends RecursiveTask<Void> {

    private final File file;
    private PDFTextStripper stripper;
    private String[] text;

    public StripTask(File file) {
        this.file = file;
    }

    @Override
    protected Void compute() {
        try (final PDDocument document = PDDocument.load(this.file)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                try {
                    throw new IOException("You do not have permission to extract text");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.setStripper(document);
            this.text = this.stripper.getText(document)
                    .toLowerCase()
                    .replaceAll("\\p{Punct}|\\d", "")
                    .split("\\s+");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CountTask task = new CountTask(text);
        task.fork();
        task.join();
        return null;
    }

    private void setStripper(PDDocument document) throws IOException {
        this.stripper = new PDFTextStripper();
        this.stripper.setSortByPosition(true);
        this.stripper.setStartPage(1);
        this.stripper.setEndPage(document.getNumberOfPages());
    }
}

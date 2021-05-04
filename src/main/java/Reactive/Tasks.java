package Reactive;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tasks {

    private static Parameter parameter;

    public static void setParameter(final Parameter parameter) throws IOException {
        Tasks.parameter = parameter;
        parameter.setIgnoredWords();
    }

    public static List<String> strip(final File f) throws IOException {
        System.out.println("[" + Thread.currentThread().getName() + "] strip");
        final PDDocument document = PDDocument.load(f);
        final AccessPermission ap = document.getCurrentAccessPermission();
        if (!ap.canExtractContent()) {
            throw new IOException("You do not have permission to extract text");
        }
        final PDFTextStripper stripper = new PDFTextStripper();
        stripper.setSortByPosition(true);
        stripper.setStartPage(1);
        stripper.setEndPage(document.getNumberOfPages());
        final List<String> text = Arrays.asList(stripper.getText(document)
                .toLowerCase()
                .replaceAll("\\p{Punct}|\\d", "")
                .split("\\s+"));
        document.close();
        return text;
    }

    public static Map<String, Integer> count(List<String> words) {
        System.out.println("[" + Thread.currentThread().getName() + "] count");
        final Map<String, Integer> occurrences = new HashMap<>();
        words.stream()
                .filter(w -> !parameter.getIgnoredWords().contains(w))
                .forEach(w -> {
                    if (occurrences.containsKey(w)) {
                        occurrences.replace(w, occurrences.get(w) + 1);
                    } else {
                        occurrences.put(w, 1);
                    }
                });
        return occurrences;
    }

    public static Map<String, Integer> getTop(Map<String, Integer> map) {
        //System.out.println("[" + Thread.currentThread().getName() + "] getTop");
        return map.keySet().stream()
                .sorted((a, b) -> map.get(b) - map.get(a))
                .limit(parameter.getNWords())
                .collect(Collectors.toMap(k -> k, map::get));
    }

}

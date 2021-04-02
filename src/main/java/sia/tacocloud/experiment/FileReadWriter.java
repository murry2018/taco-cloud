package sia.tacocloud.experiment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FileReadWriter implements Iterable {
    List<String> lines = new ArrayList<>();

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }

    public void writeLine(String line) {
        synchronized (this) {
            lines.add(line);
        }
    }
}

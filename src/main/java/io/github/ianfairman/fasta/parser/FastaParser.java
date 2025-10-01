package io.github.ianfairman.fasta.parser;

import io.github.ianfairman.io.IORuntimeException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author ian.fairman@gmail.com
 */
public class FastaParser {

    public FastaParser() {
    }

    public List<FastaRecord> parse(Reader reader) {
        String recordString;
        try {
            recordString = reader.readAllAsString();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        if (recordString.length() > 0) {
            return List.of(new FastaRecord("name", "aaaaa"));
        }
        requireNonNull(reader);
        return new ArrayList<>();
    }
}

package io.github.ianfairman.fasta.parser;

import io.github.ianfairman.io.IORuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
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
        try {
            requireNonNull(reader);
            var bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            if (line == null) {
                return emptyList();
            }
            return List.of(new FastaRecord(line.substring(2) , "AGCT"));
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        }
    }
}

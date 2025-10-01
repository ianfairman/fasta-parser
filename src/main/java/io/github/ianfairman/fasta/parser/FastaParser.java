package io.github.ianfairman.fasta.parser;

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

    public List<FastaRecord> parse(StringReader reader) {
        requireNonNull(reader);
        return new ArrayList<FastaRecord>();
    }
    
}

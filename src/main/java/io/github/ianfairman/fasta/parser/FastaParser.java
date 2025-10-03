/*
 * Copyright 2025 Ian Fairman.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ianfairman.fasta.parser;

import io.github.ianfairman.io.IORuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
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
        requireNonNull(reader, "Reader cannot be null");

        var records = new ArrayList<FastaRecord>();
        String description = null;
        StringBuilder sequenceBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isDescription(line)) {
                    // When a new description line is found, add the previous record
                    if (description != null) {
                        records.add(new FastaRecord(description, sequenceBuilder.toString()));
                    }
                    // Start the new record
                    description = extractDescription(line);
                    sequenceBuilder.setLength(0); // Clear the builder for the new sequence
                } else {
                    if (containsText(line)) {
                        sequenceBuilder.append(line.trim());
                    }
                }
            }
            
            if (description != null) {
                records.add(new FastaRecord(description, sequenceBuilder.toString()));
            }
            
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        }
        
        return records;
    }

    private static String extractDescription(String line) {
        return line.substring(1).trim();
    }

    private static boolean isDescription(String line) {
        return line.startsWith(">");
    }

    private static boolean containsText(String line) {
        return !line.trim().isEmpty();
    }
}
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

    private class FastaRecordBuilder {

        private final String description;
        private final StringBuilder sequenceBuilder;
        
        public FastaRecordBuilder(String description) {
            this.description = description.substring(1).trim();
            this.sequenceBuilder = new StringBuilder();
        }
        
        public void addSubSequence(String subSequence) {
            sequenceBuilder.append(subSequence);
        }
        
        public FastaRecord build() {
            return new FastaRecord(description, sequenceBuilder.toString());
        }
    }
    
    public List<FastaRecord> parse(Reader reader) {
        requireNonNull(reader);
        var result = new ArrayList<FastaRecord>();
        try (var bufferedReader = new BufferedReader(reader)) {
            FastaRecordBuilder builder = null;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (builder != null) {
                        result.add(builder.build());
                    }
                    builder = new FastaRecordBuilder(line);
                } else {
                    builder.addSubSequence(line);
                }
            }
            if (builder != null) {
                result.add(builder.build());
            }
            return result;
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        }
    }
}

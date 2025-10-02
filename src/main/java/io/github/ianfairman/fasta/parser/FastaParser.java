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
            var line = bufferedReader.readLine();
            if (line == null) {
                return emptyList();
            }
            var line2 = bufferedReader.readLine();
            var line3 = bufferedReader.readLine();
            if (line3 != null) {
                line2 += line3;
            }
            return List.of(new FastaRecord(line.substring(2) , line2));
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        }
    }
}

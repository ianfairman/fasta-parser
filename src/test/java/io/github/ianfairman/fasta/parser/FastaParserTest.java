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

import java.io.StringReader;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author Ian Fairman <ian.fairman@gmail.com>
 */
public class FastaParserTest {

    public FastaParserTest() {
    }
    
    @Test
    void emptyReaderParsedIntoZeroRecords() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals(Collections.emptyList(), result);     
    }
    
    @Test
    void nullReaderCausesThrowOfNullException() {
        // Given
        var parser = new FastaParser();
        StringReader reader = null;
        // When
        try {
            parser.parse(reader);
        } catch (NullPointerException _) {
            return;
        }
        fail();
    }
    
    @Test
    void readInOneRecord() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > name
                                      AGCT""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals(1, result.size());     
    }
    
    @Test
    void readInOneRecordWithName() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > name
                                      AGCT""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("name", result.getFirst().description());     
    }
    
    @Test
    void readInOneRecordWithName2() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > nom
                                      AGCT""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("nom", result.getFirst().description());     
    }
    
    @Test
    void readInOneRecordWithSequence() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > nom
                                      AGCT""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("AGCT", result.getFirst().sequence());     
    }
    
    @Test
    void readInOneRecordWithSequence2() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > nom
                                      AGTC""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("AGTC", result.getFirst().sequence());     
    }
    
    @Test
    void readInOneRecordWithTwoLineSequence() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > nom
                                      AGTC
                                      GGGG""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("AGTCGGGG", result.getFirst().sequence());     
    }
    
    @Test
    void readInOneRecordWithFourLineSequence() {
                // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      > nom
                                      AGTC
                                      GGGG
                                      CCCC
                                      TTTT""");
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("AGTCGGGGCCCCTTTT", result.getFirst().sequence());     
    }
    
    @Test
    void readInOneRecordWithNoSpaceAfterGreaterThan() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      >nom
                                      AGTC
                                      """);
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("nom", result.getFirst().description());     
    }
    
    @Test
        void readInThreeRecords() {
        // Given
        var parser = new FastaParser();
        var reader = new StringReader("""
                                      >nom1
                                      AGTC
                                      >nom2
                                      AAAA
                                      CCCC
                                      GGGG
                                      >nom3
                                      TTTT
                                      """);
        // When
        var result = parser.parse(reader);
        // Then
        assertEquals("nom3", result.get(2).description());     
    }

}

package com.helger.smpmate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Diese Klasse ist eine Abwandlung von {@link StringTokenizer}, die mehr den Erwartungen entspricht, wenn die
 * Trennzeichen keine Whitespaces sind. Die Klasse {@link StringTokenizer} bündelt benachbarte Trennzeichen und macht
 * sie dadurch für CSV-Belange unbrauchbar.
 * Diese Klasse hingegen behandelt jedes Trennzeichen als LF (linefeed) und erfasst jedes Token quasi als Zeile
 * einer Testdatei.
 */
public class Tokenizer extends StringTokenizer implements Iterable<String> {
    private final List<String> tokens;
    private int pos;

    public Tokenizer(String str) {
        this(str, ",");
    }

    public Tokenizer(String str, String delim) {
        super(str);

        int n = str.length();

        StringBuilder builder = new StringBuilder(str);

        for (int i = 0; i < n; i++) {
            if (delim.indexOf(builder.charAt(i)) >= 0) {
                builder.setCharAt(i, '\n');
            }
        }

        if (n > 0 && delim.indexOf(str.charAt(n - 1)) >= 0) {
            builder.append('\n');
        }

        tokens = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new StringReader(builder.toString()));
        try {
            for (String line; (line = reader.readLine()) != null;) {
                tokens.add(line);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new Error(e);
        }

        pos = 0;
    }

    public String[] tokens() {
        return tokens.toArray(new String[0]);
    }

    @Override
    public boolean hasMoreTokens() {
        return pos < tokens.size();
    }

    @Override
    public String nextToken() {
        if (!hasMoreTokens()) {
            throw new NoSuchElementException();
        }

        return tokens.get(pos++);
    }

    @Override
    public String nextToken(String delim) {
        return nextToken();
    }

    @Override
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    @Override
    public Object nextElement() {
        return nextToken();
    }

    @Override
    public int countTokens() {
        return tokens.size();
    }

    @Override
    public Iterator<String> iterator() {
        return new StringIterator();
    }

    private class StringIterator implements Iterator<String> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < tokens.size();
        }

        @Override
        public String next() {
            if (!hasMoreTokens()) {
                throw new NoSuchElementException();
            }

            return tokens.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

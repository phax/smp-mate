package com.helger.smpmate.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.*;

/**
 * Diese Klasse ist eine Abwandlung von {@link StringTokenizer}, die mehr den
 * Erwartungen entspricht, wenn die Trennzeichen keine Whitespaces sind. Die
 * Klasse {@link StringTokenizer} bündelt benachbarte Trennzeichen und macht sie
 * dadurch für CSV-Belange unbrauchbar. Diese Klasse hingegen behandelt jedes
 * Trennzeichen als LF (linefeed) und erfasst jedes Token quasi als Zeile einer
 * Testdatei.
 */
public class Tokenizer implements Iterable <String>
{
  private static final char LF = '\n';

  private final List <String> tokens = new ArrayList <> ();

  public Tokenizer (String str)
  {
    this (str, ",");
  }

  public Tokenizer (String str, String delim)
  {
    int n = str.length ();
    StringBuilder builder = new StringBuilder (str);

    for (int i = 0; i < n; i++)
    {
      if (delim.indexOf (builder.charAt (i)) >= 0)
      {
        builder.setCharAt (i, LF);
      }
    }

    if (n > 0 && delim.indexOf (str.charAt (n - 1)) >= 0)
    {
      builder.append (LF);
    }

    try (BufferedReader reader = new BufferedReader (new StringReader (builder.toString ())))
    {
      for (String line; (line = reader.readLine ()) != null;)
      {
        tokens.add (line);
      }
    }
    catch (IOException e)
    {
      throw new UncheckedIOException (e);
    }
  }

  public String [] tokens ()
  {
    return tokens.toArray (new String [0]);
  }

  @Override
  public Iterator <String> iterator ()
  {
    return tokens.iterator ();
  }
}

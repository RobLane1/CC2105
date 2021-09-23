package de.hybris.platform.mockcarrieraddon.utils;

import de.hybris.platform.servicelayer.exceptions.SystemException;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Handle input buffer data.
 *
 */
public class BufferReaderUtils {

	private static final int MAX_LENGTH = 10 * 1000;

	private BufferReaderUtils()
	{
	}

	public static StringBuilder getStringBuiderByBufferedReader(BufferedReader reader) throws IOException
	{
		final StringBuilder result = new StringBuilder("");
		int ch;
		while ((ch = reader.read()) != -1)
		{
			if (result.length() >= MAX_LENGTH)
			{
				throw new SystemException("Input too long");
			}
			result.append((char) ch);
		}
		return result;
	}

}

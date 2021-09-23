package de.hybris.platform.mockcarrieraddon.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;


public class ConsignmentX509ExtendedTrustManager extends X509ExtendedTrustManager
{
	@Override
	public void checkClientTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString)
			throws CertificateException
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public void checkServerTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString)
			throws CertificateException
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public X509Certificate[] getAcceptedIssuers()
	{
		return new X509Certificate[0];
	}

	@Override
	public void checkClientTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString,
			final Socket paramSocket) throws CertificateException
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public void checkServerTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString,
			final Socket paramSocket) throws CertificateException
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public void checkClientTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString,
			final SSLEngine paramSSLEngine) throws CertificateException
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public void checkServerTrusted(final X509Certificate[] paramArrayOfX509Certificate, final String paramString,
			final SSLEngine paramSSLEngine) throws CertificateException
	{
		// YTODO Auto-generated method stub
	}
}
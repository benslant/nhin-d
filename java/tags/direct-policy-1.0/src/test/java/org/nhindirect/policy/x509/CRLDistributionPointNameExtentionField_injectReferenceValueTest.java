package org.nhindirect.policy.x509;

import java.security.cert.X509Certificate;
import java.util.Collection;

import org.nhindirect.policy.util.TestUtils;
import org.nhindirect.policy.PolicyRequiredException;
import org.nhindirect.policy.x509.CRLDistributionPointNameExtentionField;

import junit.framework.TestCase;

public class CRLDistributionPointNameExtentionField_injectReferenceValueTest extends TestCase
{
	public void testInjectRefereneValue_crlPointDoesNotExist_notRequired_assertValueEmpty() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		assertTrue(field.getPolicyValue().getPolicyValue().isEmpty());
	}
	
	public void testInjectRefereneValue_crlPointDoesNotExist_required_assertException() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("altNameOnly.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(true);
		
		boolean exceptionOccured = false;
		
		try
		{
			field.injectReferenceValue(cert);
		}
		catch (PolicyRequiredException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}	
	
	public void testInjectRefereneValue_crlPointExists_assertValue() throws Exception
	{
		final X509Certificate cert = TestUtils.loadCertificate("CernerDirectProviderCA.der");
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(false);
		
		field.injectReferenceValue(cert);
		
		Collection<String> usages = field.getPolicyValue().getPolicyValue();
		assertFalse(field.getPolicyValue().getPolicyValue().isEmpty());
		
		assertTrue(usages.contains("http://ca.cerner.com/CRL/CERNER-ROOTCA00.crl"));
		
	}	
	
	public void testInjectRefereneValue_noInjection_getPolicyValue_assertException() throws Exception
	{
		
		final CRLDistributionPointNameExtentionField field = new CRLDistributionPointNameExtentionField(true);
		
		boolean exceptionOccured = false;
		
		try
		{
			field.getPolicyValue();
		}
		catch (IllegalStateException e)
		{
			exceptionOccured = true;
		}
		assertTrue(exceptionOccured);
	}
}

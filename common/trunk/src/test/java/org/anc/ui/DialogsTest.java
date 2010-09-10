package org.anc.ui;

import org.junit.Ignore;

@Ignore
public class DialogsTest {

	@Ignore
	public void testErrorBoxComponentException() {
		Exception e = new Exception("This is a test exception");
		Dialogs.errorBox(null, e);
	}

	public static void main(String[] args)
	{
		new DialogsTest().testErrorBoxComponentException();
	}
}

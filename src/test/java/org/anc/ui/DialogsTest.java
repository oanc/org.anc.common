package org.anc.ui;

import static org.junit.Assert.*;

import org.junit.Test;

public class DialogsTest {

	@Test
	public void testErrorBoxComponentException() {
		Exception e = new Exception("This is a test exception");
		Dialogs.errorBox(null, e);
	}

}

package org.anair.flyway;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;


public class FlywayScriptNameEnforceTest {

	@Test
	public void test() {
		Iterator<File> sqlFiles = VersionFlywayScript.getUnversionedSqlFiles();
		assertNotNull(sqlFiles);
		int count = 0;
		for(Iterator<File> iter = sqlFiles;sqlFiles.hasNext();){
			System.out.println("'" + iter.next().getPath() +  "' does not follow naming standards.");
			count++;
		}
		assertEquals("Found " + count + " invalid sql files.", 0, count);
	}

}

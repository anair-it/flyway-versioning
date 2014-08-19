package org.anair.flyway;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Script to version flyway sql scripts.
 * <p>
 * This program finds all files in src/main/resources/ that are not versioned.
 * It then prepends the file name with current date in yyyyMMddHHmmssSSS format.
 * <p>
 * Example: "update_table1.sql" will be renamed to
 * "V20140731123200312__update_table1.sql"
 * 
 * @author <a href="mailto:anoopnair.it@gmail.com">Anoop Nair</a>
 *
 */
public class VersionFlywayScript {
	// Ignore file names starting with "V" and having "__" in the file name.
	private static String SQL_FILENAME_PATTERN = "^(V)(\\d)+__";
	private static String SQL_FILE_SUFFIX = ".sql";
	private static String PARENT_SQL_FOLDER = "src/main/resources";
	private static String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";
	private static DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);

	public static void main(String[] args) throws InterruptedException {
		versionFlywaySqlScripts();
	}

	public static void versionFlywaySqlScripts() throws InterruptedException {
		Iterator<File> unversionedSqlFiles = getUnversionedSqlFiles();

		renameSqlFile(unversionedSqlFiles);
	}

	public static Iterator<File> getUnversionedSqlFiles() {
		return FileUtils.iterateFiles(new File(PARENT_SQL_FOLDER),
				FileFilterUtils.and(new NotFileFilter(new RegexFileFilter(
						SQL_FILENAME_PATTERN)), new SuffixFileFilter(
						SQL_FILE_SUFFIX)), TrueFileFilter.INSTANCE);
	}

	private static void renameSqlFile(Iterator<File> sqlFiles)
			throws InterruptedException {
		for (Iterator<File> iter = sqlFiles; sqlFiles.hasNext();) {
			File noPrefixFile = iter.next();
			String newFileName = MessageFormat.format(
					"{0}V{1}__{2}",
					new Object[] {
							FilenameUtils.getFullPath(noPrefixFile
									.getAbsolutePath()),
							df.format(new Date()),
							FilenameUtils.getName(noPrefixFile
									.getAbsolutePath()) });

			File newPrefixedFile = new File(newFileName);
			if (!noPrefixFile.renameTo(newPrefixedFile)) {
				System.err.println(noPrefixFile.getAbsolutePath()
						+ " not renamed to " + newFileName);
			}
			Thread.sleep(1);
		}
	}
}

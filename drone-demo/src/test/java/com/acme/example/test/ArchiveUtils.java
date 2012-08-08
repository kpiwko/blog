package com.acme.example.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * An utility to add resources to the archive using file system independent abstraction
 *
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class ArchiveUtils {

    /**
     * Recursively adds files present in {@code webAppDirectory} to the {@code archive}.
     *
     * @param archive The archive to be enriched
     * @param webAppDirectory Directory to be added to the archive
     * @return Modified archive
     */
    public static WebArchive addWebResourcesRecursively(WebArchive archive, File webAppDirectory) {
        for (File file : FileUtils.listFiles(webAppDirectory)) {
            if (!file.isDirectory()) {
                archive.addAsWebResource(file, getArchivePath(webAppDirectory, file));
            }
        }
        return archive;
    }

    private static String getArchivePath(File parent, File file) {
        if (file == null) {
            throw new IllegalArgumentException("File to be archived must not be null.");
        }

        try {
            String parentCanonicalPath = parent.getCanonicalPath();
            String fileCanonicalPath = file.getCanonicalPath();
            return fileCanonicalPath.replace(parentCanonicalPath, "").replace(File.separatorChar, '/');
        } catch (IOException e) {
            return file.getPath().replace(File.separatorChar, '/');
        }
    }

    /**
     * Simple directory listing utility
     *
     * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
     *
     */
    private static final class FileUtils {
        public static Collection<File> listFiles(File root) {

            List<File> allFiles = new ArrayList<File>();
            Queue<File> dirs = new LinkedList<File>();
            dirs.add(root);
            while (!dirs.isEmpty()) {
                for (File f : dirs.poll().listFiles()) {
                    if (f.isDirectory()) {
                        allFiles.add(f);
                        dirs.add(f);
                    } else if (f.isFile()) {
                        allFiles.add(f);
                    }
                }
            }
            return allFiles;
        }
    }
}

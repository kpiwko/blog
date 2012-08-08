package com.acme.example.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.acme.example.model.Member;
import com.acme.example.rest.MemberService;
import com.acme.example.util.Resources;

/**
 *
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 *
 */
public class Deployments {

    private static final String WEBAPP_SRC = "src/main/webapp";
    private static final String QUNIT_SRC = "src/test/qunit";

    public static WebArchive createDeployment() {
        /*
         * final EffectivePomMavenDependencyResolver resolver =
         * DependencyResolvers.use(MavenDependencyResolver.class).goOffline() .loadEffectivePom("pom.xml");
         */

        WebArchive war = addWebResourcesTo(ShrinkWrap.create(WebArchive.class, "demo.war"), WEBAPP_SRC)
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql", "import.sql");

        // add test stuff
        war = addWebResourcesTo(war, QUNIT_SRC);

        war.as(ZipExporter.class).exportTo(new File("target/demo.war"), true);

        return war;

    }

    private static WebArchive addWebResourcesTo(WebArchive archive, String path) {
        final File webAppDirectory = new File(path);
        for (File file : FileUtils.listFiles(webAppDirectory)) {
            if (!file.isDirectory()) {
                archive.addAsWebResource(file, FileUtils.getArchivePath(path, file));
            }
        }
        return archive;
    }

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

        public static String getArchivePath(String prefix, File file) {
            if (file == null) {
                throw new IllegalArgumentException("File to be archived must not be null.");
            }
            // we have to have file system independent packages
            int prefixLength = prefix != null ? prefix.length() : 0;
            return file.getPath().substring(prefixLength).replace(File.separatorChar, '/');
        }
    }
}
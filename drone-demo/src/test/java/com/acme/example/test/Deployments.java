package com.acme.example.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.acme.example.model.Member;
import com.acme.example.rest.MemberService;
import com.acme.example.util.Resources;

public class Deployments {

    private static final String WEBAPP_SRC = "src/main/webapp";

    public static WebArchive createDeployment() {
        /*
         * final EffectivePomMavenDependencyResolver resolver =
         * DependencyResolvers.use(MavenDependencyResolver.class).goOffline() .loadEffectivePom("pom.xml");
         */

        return addWebResourcesTo(ShrinkWrap.create(WebArchive.class, "demo.war"))
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/persistence.xml", "import.sql");

    }

    private static WebArchive addWebResourcesTo(WebArchive archive) {
        final File webAppDirectory = new File(WEBAPP_SRC);
        for (File file : FileUtils.listFiles(webAppDirectory)) {
            if (!file.isDirectory()) {
                archive.addAsWebResource(file, file.getPath().substring(WEBAPP_SRC.length()));
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
    }
}
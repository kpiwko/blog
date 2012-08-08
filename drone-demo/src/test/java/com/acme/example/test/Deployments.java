package com.acme.example.test;

import java.io.File;

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

    public static WebArchive createDeployment() {

        /*
         * final EffectivePomMavenDependencyResolver resolver =
         * DependencyResolvers.use(MavenDependencyResolver.class).goOffline() .loadEffectivePom("pom.xml");
         */

        WebArchive war = ArchiveUtils.addWebResourcesRecursively(ShrinkWrap.create(WebArchive.class), new File(WEBAPP_SRC))
                .addPackages(true, Member.class.getPackage(), MemberService.class.getPackage(), Resources.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql", "import.sql");

        war.as(ZipExporter.class).exportTo(new File("target/demo.war"), true);

        return war;

    }

}
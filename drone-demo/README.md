drone-demo
========================

What is it?
-----------

This is your project! It's a deployable Maven 3 project to help you
get your foot in the door developing HTML5 based desktop/mobile web applications with Java EE 6
on JBoss. This project is setup to allow you to create a basic Java EE 6 application
using HTML5, jQuery Mobile, JAX-RS, CDI 1.0, EJB 3.1, JPA 2.0 and Bean Validation 1.0. It includes
a persistence unit and some sample persistence and transaction code to help 
you get your feet wet with database access in enterprise Java.

This application is built using a technique called Plain Old HTML5 (POH5).  This uses a pure HTML
client that interacts with with the application server via restful end-points (JAX-RS).  This
application also uses some of the latest HTML5 features and advanced JAX-RS. And since testing
is just as important with POH5 as it is server side core this application also uses QUnit to show
you how to unit test your JavaScript.

What is a modern web application without mobile web support? This application also integrates
jQuery mobile and basic client side device detection to give you both a desktop and mobile 
version of the interface. Both support the same features, including form validation, member
registration, etc. However the mobile version adds in mobile layout, touch, and performance 
improvements needed to get you started with mobile web development on JBoss.  

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better.

The application this project produces is designed to be run on JBoss AS 7+ or EAP 6+.

An HTML5 compatible browser such as Chrome 14+, Safari 5+, Firefox 5+, or IE 9+ are
required. and note that some behaviors will vary slightly (ex. validations) based on browser support,
especially IE 9.

Mobile web support is limited to Android and iOS devices.  It should run on HP,
and Black Berry devices as well.  Windows Phone, and others will be supported as 
jQuery Mobile announces support.
 
With the prerequisites out of the way, you're ready to build and deploy.

Deploying the application
-------------------------
 
First you need to start the JBoss container. To do this, run
  
    $JBOSS_HOME/bin/standalone.sh
  
or if you are using windows
 
    $JBOSS_HOME/bin/standalone.bat
    
Note: Adding "-b 0.0.0.0" to the above commands will allow external clients (phones, tablets, 
desktops, etc...) connect through your local network.
      
For example

    $JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 

To deploy the application, you first need to produce the archive to deploy using
the following Maven goal:

    mvn package

You can now deploy the artifact by executing the following command:

    mvn jboss-as:deploy

This will deploy `target/drone-demo`.
 
The application will be running at the following URL <http://localhost:8080/drone-demo/>.

To undeploy run this command:

    mvn jboss-as:undeploy

You can also start the JBoss container and deploy the project using JBoss Tools. See the
Getting Started Guide for Developers for more information.

Testing with Arquillian
=======================

(check out https://gist.github.com/gists/1848342 to see how this application was created)

1. Remove Java EE 6 Spec BOM file with a Java EE 6 + Tools BOM file in `<dependencyManagement>` section.

            <!-- JBoss distributes a complete set of Java EE 6 APIs including a Bill of Materials (BOM). A BOM specifies the versions 
                of a "stack" (or a collection) of artifacts. We use this here so that we always get the correct versions of artifacts. Here we use 
                the jboss-javaee-6.0-with tools stack (you can read this as the JBoss stack of the Java EE 6 APIs, with some extras tools for your 
                project, such as Arquillian for testing) -->
            <dependency>
                <groupId>org.jboss.bom</groupId>
                <artifactId>jboss-javaee-6.0-with-tools</artifactId>
                <version>1.0.0.M1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

2. Remove Arquillian versions as they are managed by new BOM file

3. (Optional) Remove `<repositories>` sections, they are no longer necessary

4. Update surefire plugin version to 2.10

5. Set **arq-jbossas-remote** as active profile

6. Start JBoss AS 7.1 container from within IDE

Adding Arquillian Drone to the example
--------------------------------------

Add following artifact to enable Arquillian Ajocado, which is one of the supported UI browsers. See more of supported browser an
[Arquillian Drone Documentation](https://docs.jboss.org/author/display/ARQ/Drone).

    <dependency>
        <artifactId>arquillian-ajocado-junit</artifactId>
        <groupId>org.jboss.arquillian.ajocado</groupId>
        <type>pom</type>
        <scope>test</scope>
    </dependency>

Now, you are able to test you example.

Creating a complete application archive 
---------------------------------------

This part is based on experimental Maven plugin support for ShrinkWrap. You can get it following these instructions:

1. Get source code and branch to a next branch using following commands

        git clone git://github.com/shrinkwrap/resolver.git
        cd resolver
        git checkout origin/next -b next

2. Change version and install source into your local repository

        mvn -batch-mode release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=2.0.0-alpha-1-next
        mvn clean install

3. Add ShrinkWrap Maven resolution depchain and ShrinkWrap Maven plugin to your project

        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.jboss.shrinkwrap.resolver</groupId>
                    <artifactId>shrinkwrap-resolver-bom</artifactId>
                    <version>2.0.0-alpha-1-next-SNAPSHOT</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>

        <dependency>
            <groupId>org.jboss.shrinkwrap.resolver</groupId>
            <artifactId>shrinkwrap-resolver-depchain</artifactId>
            <version>2.0.0-alpha-1-next-SNAPSHOT</version>
            <type>pom</type>
            <scope>test</scope>
        </dependency>

    *Note that _shrinkwrap-resolver-bom_ __must__ be placed before Tools Bom file*

4. For current version in order to *MavenImporter* to work, you need to run `mvn clean package` before executing any test from the IDE. This is a     limitation we're currently working on.

5. In your tests, following snippet gives you complete archive to be used for integration testing with Arquillian Drone.

    @Deployment(testable=false)
    public static Archive<?> getApplicationDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                         .loadEffectivePom("pom.xml")
                         .importBuildOutput()
                         .as(WebArchive.class);
    }


Adding support for Arquillian Drone
-----------------------------------

First, it is reasonable to update to latest Selenium and WebDriver bits available. It does not make much sense to release Arquillian Drone as often as Selenium developers develop Selenium and WebDriver. Therefore, you can override `<dependencyManagement>` section as described in [Arquillian FAQ](https://community.jboss.org/wiki/SpecifyingSeleniumVersionInArquillianDrone)

    <properties>
        <version.selenium>2.19.0</version.selenium>
        <version.selenium.server>2.19.0</version.selenium.server>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- Selenium Server -->
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-server</artifactId>
                <version>${version.selenium.server}</version>
            </dependency> 

            <!-- Selenium dependecies -->
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-api</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-java</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-support</artifactId>
                <version>${version.selenium}</version>
            </dependency>

            <!-- Drivers -->
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-android-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-chrome-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-firefox-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-htmlunit-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-ie-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-iphone-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-remote-driver</artifactId>
                <version>${version.selenium}</version>
            </dependency>        
           
        </dependencies>
    </dependencyManagement>

Next, you should add following dependency to enable Arquillian Ajocado. This dependency contains everything you need, including selenium artifacts, selenium-server and appropriate Arquillian Drone bindings. You can consider it being a Depchain for Arquillian Ajocado.

    <dependency>
        <groupId>org.jboss.arquillian.ajocado</groupId>
        <artifactId>arquillian-ajocado-junit</artifactId>
        <type>pom</type>
        <scope>test</scope>
    </dependency>

To include support for basic Selenium 1.0 *DefaultSelenium*, you should add following (expecting you won't remove Ajocado depchain)

    <dependency>
        <groupId>org.jboss.arquillian.extension</groupId>
        <artifactId>arquillian-drone-selenium</artifactId>
        <scope>test</scope>
    </dependency>

The same holds for Selenium 2.0 *WebDriver* support

    <dependency>
        <groupId>org.jboss.arquillian.extension</groupId>
        <artifactId>arquillian-drone-webdriver</artifactId>
        <scope>test</scope>
    </dependency>

Congratulations, now you're able to use any of the browser which are supported by Arquillian Drone out of the stock!

Executing multiple Drones in a single test
------------------------------------------

Add following to your test class, which already contains the deployment method:

    @ArquillianResource
    URL contextPath;

    @Test
    public void simpleAjocadoTest(@Drone AjaxSelenium ajocado) {
        ajocado.open(contextPath);
        ajocado.waitForPageToLoad();
        Assert.assertTrue(true);
    }

    @Test
    public void simpleAjocadoFirefox9Test(@Drone @Firefox9 AjaxSelenium ajocado) {
        ajocado.open(contextPath);
        ajocado.waitForPageToLoad();
        Assert.assertTrue(true);
    }

    @Test
    public void simpleWebdriverTest(@Drone FirefoxDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }

    @Test
    public void simpleWebdriverChromeTest(@Drone ChromeDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }
    
    @Test
    public void simpleDefaultSeleniumTest(@Drone DefaultSelenium selenium) {
        selenium.open(contextPath.toString());
        selenium.waitForPageToLoad("5000");
        Assert.assertTrue(true);
    }

The code shows you following things:

* Drone is able to instantiate _DefaultSelenium_, _AjaxSelenium_ or _WebDriver_ stuff
* You have the choice to make browser life cycle bound to a method or to a class (if `@Drone` is used to annotate a field)
* You are able to distinguish between same types of browser using type-safe qualifers

Are you wondering how a qualifier looks like? Here you go:

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @Target({ ElementType.FIELD, ElementType.PARAMETER })
    public @interface Firefox9 {
    }

Now the most interesting point. Where is the browser configuration? Sure, it is hold in *arquillian.xml* file
    
    <extension qualifier="ajocado">
        <property name="browser">*firefox</property>
    </extension>

    <!-- this configuration will be used by @Firefox9 annotated browsers -->
    <extension qualifier="ajocado-firefox9">
        <property name="browser">*firefox /opt/firefox-9.0.1/firefox-bin</property>
    </extension>

    <extension qualifier="webdriver">
        <property name="chromeBinary">/opt/google/chrome/chrome</property>
        <property name="chromeSwitches">--user-data-dir=/tmp --disable-web-security</property>
    </extension>

*Note that I need to set up Chrome because I use non standard setup at my desktop, this step might not be needed at your place!* 

Executing a single test multiple times with different Drones
------------------------------------------------------------

This step is more interesting if you have to repeat execution. You have multiple ways how this can be done, I personally prefer running
multiple executions of Maven Surefire plugin. You can set a distinct profile to do that:

    <profile>
        <id>repeated-drone-execution</id>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <includes>
                            <include>**/RepeatedDroneTest*</include>
                        </includes>
                    </configuration>
                    <executions>
                        <execution>
                            <id>default-test</id>
                            <goals>
                                <goal>test</goal>
                            </goals>
                            <phase>test</phase>
                            <configuration>
                                <skip>true</skip>
                            </configuration>
                        </execution>
                        <execution>
                            <id>firefox8</id>
                            <goals>
                                <goal>test</goal>
                            </goals>
                            <phase>test</phase>
                            <configuration>
                                <systemPropertyVariables>
                                    <arquillian.xml>arquillian-ff9.xml</arquillian.xml>
                                </systemPropertyVariables>
                                <reportNameSuffix>ff9</reportNameSuffix>
                            </configuration>
                        </execution>
                        <execution>
                            <id>chrome17</id>
                            <goals>
                                <goal>test</goal>
                            </goals>
                            <phase>test</phase>
                            <configuration>
                                <systemPropertyVariables>
                                    <arquillian.xml>arquillian-ch17.xml</arquillian.xml>                               
                                </systemPropertyVariables>
                            <reportNameSuffix>ch17</reportNameSuffix>
                            </configuration>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    </profile>

I've overriden *default-test* execution as well. In combination with *<reportNameSuffix>* it will give me a nicer test report. So how does Arquillian execution work here? The trick is to have multiple *arquillian.xml* files, which are passed to configure Arquillian. Then every execution has its own settings and thus completely different browser.

See the test:

    @ArquillianResource
    URL contextPath;

    @Test
    public void simpleWebdriverTest(@Drone WebDriver webdriver) {
        webdriver.get(contextPath.toString());

        webdriver.findElement(By.id("name")).sendKeys("Samuel");
        webdriver.findElement(By.id("email")).sendKeys("samuel@vimes.dw");
        webdriver.findElement(By.id("phoneNumber")).sendKeys("1234567890");
        webdriver.findElement(By.id("register")).submit();

        // FIXME with Ajocado, you can wait for a request
        Assert.assertTrue(true);
    }

Running in OpenShift
--------------------

If you want to test you application in the cloud, there is nothing easier than to simply switch the container configuration 
from AS7 running on your instance to OpenShift instance. Arquillian Drone won't care, it will find your application, so no
modification of the tests is required.

Let's create a distinct Maven profile:

    <profile>            
        <id>openshift</id>
        <build>
            <plugins>                 
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <systemPropertyVariables>
                            <arquillian.launch>openshift</arquillian.launch>
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        
        <dependencies>
            <dependency>
                <groupId>org.jboss.arquillian.container</groupId>
                <artifactId>arquillian-openshift-express</artifactId>
                <version>1.0.0.Alpha1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </profile>

*Note `arquillian.launch` property. It allows you to select a non-default configuration from your `arquillian.xml` file*
Next, you want to provide a configuration for OpenShift in your `arquillian.xml` file:

    <container qualifier="openshift">
        <configuration>
            <property name="namespace">arqtest</property>
            <property name="application">drone</property>
            <property name="sshUserName"><!-- UUID --></property>
            <!-- Passphrase can be specified by defining the environment variable SSH_PASSPHRASE -->
            <!-- <property name="passphrase"></property> -->
            <property name="login">kpiwko@redhat.com</property>
        </configuration>
    </container>

You'll get required information when the application is created via command line or tools. You can also
check OpenShift management interface. 

That's it. You can test your application using OpenShift container.

Happy Drone testing!


 
Running the Arquillian tests
============================

By default, tests are configured to be skipped. The reason is that the sample
test is an Arquillian test, which requires the use of a container. You can
activate this test by selecting one of the container configuration provided 
for JBoss.

To run the test in JBoss, first start the container instance. Then, run the
test goal with the following profile activated:

    mvn clean test -Parq-jbossas-remote

Running the QUnit tests
============================

QUnit is a JavaScript unit testing framework used and built by jQuery.This 
application include a set of QUnit tests in order to verify JavaScript that
is core to this HTML5 application.  Executing QUnit test cases is quite easy.
Simply load the following HTML is a browser.

    <app-root>/src/test/qunit/index.html

For more information on QUnit tests see http://docs.jquery.com/QUnit

Importing the project into an IDE
=================================

If you created the project using the Maven archetype wizard in your IDE
(Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
already have an IDE project.

Detailed instructions for using Eclipse / JBoss Tools with are provided in the 
Getting Started Guide for Developers.

If you created the project from the commandline using archetype:generate, then
you need to import the project into your IDE. If you are using NetBeans 6.8 or
IntelliJ IDEA 9, then all you have to do is open the project as an existing
project. Both of these IDEs recognize Maven projects natively.

Downloading the sources and Javadocs
====================================

If you want to be able to debug into the source code or look at the Javadocs
of any library in the project, you can run either of the following two
commands to pull them into your local repository. The IDE should then detect
them.

    mvn dependency:sources
    mvn dependency:resolve -Dclassifier=javadoc

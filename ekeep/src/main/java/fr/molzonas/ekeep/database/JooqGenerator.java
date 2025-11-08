package fr.molzonas.ekeep.database;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;
import org.jooq.meta.jaxb.Database;

import java.io.File;
import java.util.logging.Logger;

public class JooqGenerator {
    private static final String JAVA_SRC_FOLDER_PATH = System.getProperty("user.dir") + "/core/src/main/java";
    private static final String SQL_PATH = "../ekeep-core/src/main/resources/db/migration";
    private static final Logger log = Logger.getLogger(JooqGenerator.class.getName());
    public record StepResult(boolean isOk, Exception exception) {}

    public static void main(String[] args) throws Exception {
        log.info(JAVA_SRC_FOLDER_PATH);
        long t1 = System.currentTimeMillis();
        JooqGenerator jooqGenerator = new JooqGenerator();
        StepResult rs = jooqGenerator.generateJooq();
        long t2 = System.currentTimeMillis();
        double timeInSeconds = (t2 - t1) / 1000.0;
        if (rs.isOk()) {
            log.info("Generation finished in " + timeInSeconds + "s");
        } else {
            log.warning("Generation failed");
            log.warning(rs.exception().getMessage());
            throw rs.exception();
        }
    }

    private StepResult generateJooq() {
        try {
            if ("true".equalsIgnoreCase(System.getenv("database.distant"))) {
                log.info("Distant database used");
                GenerationTool.generate(getDistantConfiguration());
            } else {
                log.info("Local database used");
                GenerationTool.generate(getConfiguration());
            }
        } catch (Exception e) {
            return new StepResult(false, e);
        }
        return new StepResult(true, null);
    }

    private Configuration getConfiguration() {
        return new Configuration()
                .withLogging(Logging.DEBUG)
                .withJdbc(new Jdbc()
                        .withDriver("org.jooq.meta.extensions.ddl.DDLDatabase"))
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.extensions.ddl.DDLDatabase")
                                .withInputSchema("public")
                                .withProperties(
                                        new Property().withKey("scripts").withValue(SQL_PATH + File.separator + "*.sql"),
                                        new Property().withKey("sort").withValue("semantic"),
                                        new Property().withKey("defaultNameCase").withValue("as_is")
                                )
                        )
                        .withGenerate(new Generate()
                                .withPojos(true)
                                .withFluentSetters(true)
                                .withJavaTimeTypes(true)
                        )
                        .withTarget(new Target()
                                .withPackageName("fr.molzonas.ekeep.database.generated")
                                .withDirectory(JAVA_SRC_FOLDER_PATH)
                        )
                );
    }

    private Configuration getDistantConfiguration() {
        return new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver(System.getenv("database.driver"))
                        .withUrl(System.getenv("database.url"))
                        .withUsername(System.getenv("database.user"))
                        .withPassword(System.getenv("database.password"))
                ).withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName(System.getenv("database.dialect"))
                                .withIncludes(".*")
                                .withInputSchema(System.getenv("database.schema")))
                        .withTarget(new Target()
                                .withPackageName("fr.molzonas.ekeep.database.generated")
                                .withDirectory(JAVA_SRC_FOLDER_PATH)));
    }
}

package fr.molzonas.mcfr.ekeep.core.utils;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class JooqGenerator {
    private static final String LOCAL_PROPERTIES_FILE_PATH = System.getProperty("user.dir") + "/local.properties";
    private static final String JAVA_SRC_FOLDER_PATH = System.getProperty("user.dir") + "/core/src/main/java";
    private Properties properties = null;
    public record StepResult(boolean isOk, Exception exception) {}

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        JooqGenerator jooqGenerator = new JooqGenerator();
        StepResult rs = jooqGenerator.generateJooq();
        long t2 = System.currentTimeMillis();
        double timeInSeconds = (t2 - t1) / 1000.0;
        if (rs.isOk()) {
            System.out.println("Generation finished in " + timeInSeconds + "s");
        } else {
            System.out.println("Generation failed");
            System.err.println(rs.exception().getMessage());
            throw rs.exception();
        }
    }

    private StepResult generateJooq() {
        StepResult sr = loadLocalPropertiesFile();
        if (!sr.isOk()) return sr;
        try {
            GenerationTool.generate(getConfiguration());
        } catch (Exception e) {
            return new StepResult(false, e);
        }
        return new StepResult(true, null);
    }

    private Configuration getConfiguration() {
        return new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver(properties.getProperty("local.database.driver"))
                        .withUrl(properties.getProperty("local.database.url"))
                        .withUsername(properties.getProperty("local.database.user"))
                        .withPassword(properties.getProperty("local.database.password"))
                ).withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName(properties.getProperty("local.database.dialect"))
                                .withIncludes(".*")
                                .withInputSchema(properties.getProperty("local.database.schema")))
                        .withTarget(new Target()
                                .withPackageName("fr.molzonas.mcfr.ekeep.core.database.generated")
                                .withDirectory(JAVA_SRC_FOLDER_PATH)));
    }

    private StepResult loadLocalPropertiesFile() {
        try (FileInputStream fis = new FileInputStream(new File(LOCAL_PROPERTIES_FILE_PATH))) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            return new StepResult(false, e);
        }
        return new StepResult(true, null);
    }
}

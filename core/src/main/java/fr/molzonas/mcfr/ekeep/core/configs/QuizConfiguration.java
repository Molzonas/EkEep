package fr.molzonas.mcfr.ekeep.core.configs;

import fr.molzonas.mcfr.ekeep.api.entities.QuizAnswer;
import fr.molzonas.mcfr.ekeep.api.entities.QuizQuestion;
import fr.molzonas.mcfr.ekeep.api.entities.QuizTotal;
import fr.molzonas.mcfr.ekeep.core.utils.Reloadable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class QuizConfiguration implements Reloadable {
    private final FileConfiguration config;
    private Map<String, UUID> cacheAliases = new HashMap<>();
    private QuizTotal cacheQuizTotal;
    private boolean tried = false;

    public QuizConfiguration(FileConfiguration config) {
        this.config = config;
    }

    public Optional<QuizTotal> getQuizTotal() {
        if (cacheQuizTotal == null && !tried) {
            cacheQuizTotal = createQuizTotal(config).orElse(null);
            tried = true;
        }
        return Optional.ofNullable(cacheQuizTotal);
    }

    public Optional<QuizTotal> createQuizTotal(FileConfiguration config) {
        if (cacheAliases == null || cacheAliases.isEmpty()) cacheAliases = getAlias();
        List<QuizQuestion> questions = new ArrayList<>();
        ConfigurationSection csQuestions = config.getConfigurationSection("questions");
        if (csQuestions == null) return Optional.empty();
        for (String key : csQuestions.getKeys(false)) {
            createQuestion(key, csQuestions.getConfigurationSection(key)).ifPresent(questions::add);
        }
        return Optional.of(new QuizTotal(questions.toArray(new QuizQuestion[0])));
    }

    private Optional<QuizQuestion> createQuestion(String key, ConfigurationSection csQuestion) {
        if (csQuestion == null) return Optional.empty();
        String title = csQuestion.getString("title");
        ConfigurationSection csAnswers = csQuestion.getConfigurationSection("answers");
        if (csAnswers == null) return Optional.empty();
        List<QuizAnswer> answers = new ArrayList<>();
        for (String csAnswer : csAnswers.getKeys(false)) {
            createAnswer(csAnswers.getConfigurationSection(csAnswer)).ifPresent(answers::add);
        }
        return Optional.of(new QuizQuestion(key, title, answers));
    }

    private Optional<QuizAnswer> createAnswer(ConfigurationSection csAnswer) {
        if (csAnswer == null) return Optional.empty();
        String label = csAnswer.getString("label");
        ConfigurationSection csAnswerPoints = csAnswer.getConfigurationSection("points");
        if (csAnswerPoints == null) return Optional.empty();
        Map<UUID, Integer> points = new HashMap<>();
        for (String csPoints : csAnswerPoints.getKeys(false)) {
            int csAnswerPoint = csAnswerPoints.getInt(csPoints);
            UUID uuid = cacheAliases.containsKey(csPoints) ? cacheAliases.get(csPoints) : UUID.fromString(csPoints);
            points.put(uuid, csAnswerPoint);
        }
        return Optional.of(new QuizAnswer(label, points));
    }

    public Map<String, UUID> getAlias() {
        Map<String, UUID> rs = new HashMap<>();
        ConfigurationSection csAliases = config.getConfigurationSection("teams-alias");
        if (csAliases == null) return rs;
        for (String key : csAliases.getKeys(false)) {
            String value =  csAliases.getString(key);
            if (value == null) continue;
            rs.put(key, UUID.fromString(value));
        }
        return rs;
    }

    @Override
    public void reload() {
        this.tried = false;
        this.cacheQuizTotal = null;
        this.cacheAliases.clear();
    }
}

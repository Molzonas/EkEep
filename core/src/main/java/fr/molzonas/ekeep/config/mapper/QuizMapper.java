package fr.molzonas.ekeep.config.mapper;

import fr.molzonas.ekeep.api.domain.quiz.QAnswer;
import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import fr.molzonas.ekeep.api.enums.ReloadableType;
import fr.molzonas.ekeep.api.lifecycle.Reloadable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class QuizMapper implements Reloadable {
    private final YamlConfiguration config;
    private QQuiz cache;
    private Map<String, UUID> cacheAliases = new HashMap<>();
    private boolean tried = false;

    public QuizMapper(YamlConfiguration config) {
        this.config = config;
    }

    public QQuiz getQuiz() {
        if (cache != null) return QQuiz.copy(cache);
        return null;
    }

    public Optional<QQuiz> createQuizTotal(FileConfiguration config) {
        if (cacheAliases == null || cacheAliases.isEmpty()) cacheAliases = getAlias();
        List<QQuestion> questions = new ArrayList<>();
        ConfigurationSection csQuestions = config.getConfigurationSection("questions");
        if (csQuestions == null) return Optional.empty();
        for (String key : csQuestions.getKeys(false)) {
            createQuestion(key, csQuestions.getConfigurationSection(key)).ifPresent(questions::add);
        }
        return Optional.of(new QQuiz(questions.toArray(new QQuestion[0])));
    }

    private Optional<QQuestion> createQuestion(String key, ConfigurationSection csQuestion) {
        if (csQuestion == null) return Optional.empty();
        String title = csQuestion.getString("title");
        ConfigurationSection csAnswers = csQuestion.getConfigurationSection("answers");
        if (csAnswers == null) return Optional.empty();
        List<QAnswer> answers = new ArrayList<>();
        for (String csAnswer : csAnswers.getKeys(false)) {
            createAnswer(csAnswers.getConfigurationSection(csAnswer)).ifPresent(answers::add);
        }
        return Optional.of(new QQuestion(key, title, answers));
    }

    private Optional<QAnswer> createAnswer(ConfigurationSection csAnswer) {
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
        return Optional.of(new QAnswer(label, points));
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
        this.cache = null;
        this.cacheAliases.clear();
    }

    @Override
    public ReloadableType reloadableType() {
        return ReloadableType.MAPPER;
    }
}

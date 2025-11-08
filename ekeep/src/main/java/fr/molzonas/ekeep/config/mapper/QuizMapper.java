package fr.molzonas.ekeep.config.mapper;

import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import org.bukkit.configuration.file.YamlConfiguration;

public class QuizMapper {
    private QQuiz cache;
    private YamlConfiguration config;

    public QuizMapper(YamlConfiguration config) {
        this.config = config;
    }

    public QQuiz getQuiz() {
        if (cache != null) return QQuiz.copy(cache);
        return null;
    }
}

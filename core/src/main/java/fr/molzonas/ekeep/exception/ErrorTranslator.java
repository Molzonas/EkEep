package fr.molzonas.ekeep.exception;

import fr.molzonas.ekeep.api.exceptions.DataIntegrityException;
import fr.molzonas.ekeep.api.exceptions.DatabaseOperationException;
import fr.molzonas.ekeep.api.exceptions.TransientDbException;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;

import java.sql.SQLException;

public class ErrorTranslator implements ExecuteListener {
    @Override
    public void exception(ExecuteContext ctx) {
        SQLException e = ctx.sqlException();
        if (e == null) return;
        String state = e.getSQLState();
        if (state == null) return;

        if (state.startsWith("23")) { // constraint violation
            ctx.exception(new DataIntegrityException(e));
        } else if (state.startsWith("08")) { // connection exception
            ctx.exception(new TransientDbException(e));
        } else {
            ctx.exception(new DatabaseOperationException(e));
        }
    }
}

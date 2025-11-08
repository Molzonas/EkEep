package fr.molzonas.ekeep.api.lifecycle;

import fr.molzonas.ekeep.api.enums.ReloadableType;

public interface Reloadable {
    void reload();
    ReloadableType reloadableType();
}

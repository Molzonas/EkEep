package fr.molzonas.mcfr.ekeep.core.configs;

import fr.molzonas.mcfr.ekeep.core.configs.base.*;

public class DatabaseKeys extends BaseKeys {
    private static final KeySpace DB = KeySpace.of("database");
    public static final Key<String> IP = key(DB, "ip", new TypeRef<String>() {}, "localhost");
    public static final Key<Integer> PORT = key(DB, "port", new TypeRef<Integer>() {}, 3306);
    public static final Key<String> USERNAME = key(DB, "user", new TypeRef<String>() {}, "root");
    public static final Key<String> PASSWORD = key(DB, "password", new TypeRef<String>() {}, "");

}

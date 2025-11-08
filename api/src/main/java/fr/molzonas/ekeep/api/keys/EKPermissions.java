package fr.molzonas.ekeep.api.keys;

public enum EKPermissions {
    ADMIN("ekeep.admin", EKPermissionsDefaultType.OP),
    ;


    private final String permission;
    private final EKPermissionsDefaultType defaultType;

    EKPermissions(String permission, EKPermissionsDefaultType defaultType) {
        this.permission = permission;
        this.defaultType = defaultType;
    }

    public String getPermission() {
        return permission;
    }

    public EKPermissionsDefaultType getDefaultType() {
        return defaultType;
    }
}

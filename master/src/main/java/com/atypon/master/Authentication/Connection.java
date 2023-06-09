package com.atypon.master.Authentication;

public final class Connection {

    private static boolean isAdmin = false;
    private static boolean connected = false;

    public static boolean isConnected() {
        return connected;
    }
    public static void setConnected() {
        Connection.connected = true;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        Connection.isAdmin = isAdmin;
    }

}

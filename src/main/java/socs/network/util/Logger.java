package socs.network.util;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Logger {
    private static Logger singleton = null;
    public static Logger getSingleton() {
        return singleton;
    }
    public static void setLogger(OutputStream outputStream) {
        singleton = new Logger(outputStream);
    }

    private final PrintWriter writer;
    private String prefix = "";
    private boolean silent = false;

    public Logger(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    public synchronized Logger write(CharSequence charSequence) {
        if (silent) return this;
        writer.print(prefix);
        writer.println(charSequence);
        writer.flush();
        return this;
    }

    public synchronized Logger setPrefix(CharSequence prefix) {
        this.prefix = prefix.toString();
        return this;
    }

    public synchronized Logger setIsSilent(boolean silent) {
        this.silent = silent;
        return this;
    }
}

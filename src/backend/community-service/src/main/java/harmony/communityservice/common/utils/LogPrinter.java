package harmony.communityservice.common.utils;

public abstract class LogPrinter {
    public abstract void doLogging(String message);

    public void logging(String message) {
        this.doLogging(message);
    }
}

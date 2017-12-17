package applicationManager.module;

import com.google.inject.Injector;

public class DI {
    private Injector injector;
    private static DI di;

    private DI() {

    }

    public static DI di() {
        if (di == null) {
            di = new DI();
        }
        return di;

    }

    public void init(Injector injector) {
        this.injector = injector;
    }

    public <T> T getInstance(Class<T> genericClass) {
        return injector.getInstance(genericClass);
    }
}

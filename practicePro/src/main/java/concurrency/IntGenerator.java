package concurrency;

/**
 * Created by I311352 on 6/24/2016.
 */
public abstract class IntGenerator {
    private volatile boolean caceled = false;
    public abstract  int next();
    public void cacel() {caceled = true;}
    public boolean isCaceled() {return caceled;}
}

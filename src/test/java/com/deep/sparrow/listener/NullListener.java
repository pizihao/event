package java.com.deep.sparrow.listener;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/7 15:27
 */
public class NullListener implements Listener{

    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        return null;
    }
}
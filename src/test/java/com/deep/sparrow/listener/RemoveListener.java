package java.com.deep.sparrow.listener;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/3 14:25
 */
public class RemoveListener implements Listener{
    @Override
    public Object execEvent(Object t) {
        System.out.println(t);
        return this;
    }
}
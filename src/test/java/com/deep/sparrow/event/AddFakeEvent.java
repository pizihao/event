package java.com.deep.sparrow.event;

/**
 * <h2>添加伪事件</h2>
 *
 * @author Create by liuwenhao on 2022/6/29 17:41
 */
public class AddFakeEvent {

    String name;

    public AddFakeEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddFakeEvent{" +
            "name='" + name + '\'' +
            '}';
    }
}
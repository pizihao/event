package java.com.deep.sparrow.event;

/**
 * <h2></h2>
 *
 * @author Create by liuwenhao on 2022/7/3 13:48
 */
public class RemoveEvent extends Event {

    String name ;

    public RemoveEvent(Object source, String name) {
        super(source);
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
        return "RemoveEvent{" +
            "name='" + name + '\'' +
            '}';
    }
}
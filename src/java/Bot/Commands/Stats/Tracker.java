package Bot.Commands.Stats;

public class Tracker {

    private String name;
    private long value;

    public Tracker(){
        this.name = "N/A";
        this.value = 0;
    }

    public Tracker(String name, long value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String toString() {
        return "Tracker{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

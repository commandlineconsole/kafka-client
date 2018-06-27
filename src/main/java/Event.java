public class Event {

    private String name;
    private int numCats;

    public Event(String name, int numCats) {
        this.name = name;
        this.numCats = numCats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCats() {
        return numCats;
    }

    public void setNumCats(int numCats) {
        this.numCats = numCats;
    }
}

public class Event {

    private String name;
    private int numCats;
    private boolean isCrazyCatLady;

    public Event(String name, int numCats, boolean isCrazyCatLady) {
        this.name = name;
        this.numCats = numCats;
        this.isCrazyCatLady = isCrazyCatLady;
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
    public boolean isCrazyCatLady() {
        return isCrazyCatLady;
    }

    public void setCrazyCatLady(boolean crazyCatLady) {
        isCrazyCatLady = crazyCatLady;
    }
}

package Logic;

public class Entry {
    private Entry next;
    private String username;
    private int position;

    public Entry(String username, int position) {
        this.username = username;
        this.next = null;
        this.position = position;
    }

    public Entry getNext() {
        return next;
    }

    public String getUsername() {
        return username;
    }

    public int getPosition() {
        return position;
    }

    public void setNext(Entry next) {
        this.next = next;
    }
}

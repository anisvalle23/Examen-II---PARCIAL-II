package Logic;

public enum Trophy {
    PLATINO(5, "Platino"),
    ORO(3, "Oro"),
    PLATA(2, "Plata"),
    BRONCE(1, "Bronce");

    private final int points;
    private final String type;

    Trophy(int points, String type) {
        this.points = points;
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " (Puntos: " + points + ")";
    }
}

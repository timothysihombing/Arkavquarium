public class Piranha extends Fish {
    public Piranha() {
        super();
        setSpeed(2);
    }

    public void makeCoins(LinkedList<Coins> listCoins, Guppy guppy) {
        Coins C = new Coins(getX(), getY(), (guppy.getPhase() + 1) * 500);
    }

    public void draw() {

    }

    public double distanceTo(Guppy other) {
        return Math.pow((Math.pow(other.getX() - getX(), 2) + (Math.pow(other.getY() - getY(), 2))), 0.5);
    }

    public void moveToFood(Guppy other) {
        setSpeed(getSpeed() + 1);
        double a = Math.atan2(other.getY() - getY(), other.getX() - getX());
        if ((getSpeed() * Math.cos(a)) >= 0) {
            setDirection(Direction.RIGHT);
        } else {
            setDirection(Direction.LEFT);
        }
        setX(getX() + getSpeed() * Math.cos(a));
        setY(getY() + getSpeed() * Math.sin(a));
    }

    public void lifeCycle(LinkedList<Guppy> listGuppy, LinkedList<Coins> listCoins, LinkedList<Piranha> listPiranha) {
        int idx = 0;
        if (!listGuppy.isEmpty() && !getIsFull()) {
            Guppy closeGuppy = listGuppy.get(idx);
            idx++;
            while (idx < listGuppy.getIdx()) {
                if (distanceTo(closeGuppy) > distanceTo(listGuppy.get(idx))) {
                    closeGuppy = listGuppy.get(idx);
                }
                idx++;
            }
            moveToFood(closeGuppy);
            if (Math.abs(getX() - closeGuppy.getX()) < getRadius() && Math.abs(getY() - closeGuppy.getY()) < getRadius()) {
                setIsFull(true);
                setTime(0);
                makeCoins(listCoins, closeGuppy);
                listGuppy.remove(closeGuppy);
                setSpeed(getSpeed() - 1);
            }
        } else {
            move();
        }
        if (time >= timeT) {
            setIsFull(false);
        }
        if (time >= timetoDeath) {
            listPiranha.remove(this);
        }
        time++;
    }
}

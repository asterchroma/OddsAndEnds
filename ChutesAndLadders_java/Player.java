class Player {
    private int Id;
    private int position;

    public Player(int id) {
        this.Id = id;
        this.position = 0;
    }

    public void moveTo(int to) {
        this.position = to;
    }

    public int getPosition() {
        return this.position;
    }

    public int getId() {
        return this.Id;
    }
}
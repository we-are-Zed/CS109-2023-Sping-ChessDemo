package model;


import java.util.Objects;

public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if(this.getOwner()==target.getOwner()) return false;
        if(this.rank>=target.rank&& !Objects.equals(this.name, "rat") && !Objects.equals(target.name, "elephant"))
        {
            return true;
        }
        if(Objects.equals(this.name, "rat") &&target.name=="elephant") return true;
        if(Objects.equals(this.name, "elephant") &&target.name=="rat") return false;

        return false;
    }
    public void setRank(int rank){
        this.rank=rank;
    }
    public int getRank()
    {
        return rank;
    }


    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}

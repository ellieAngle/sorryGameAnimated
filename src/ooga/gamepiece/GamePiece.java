package ooga.gamepiece;

public class GamePiece implements Piece{
    int pieceLocation;
    boolean home;
    boolean base;


    public GamePiece(int pieceLocation, int base){
        this.base = (base!=0);
        if(this.base){
            this.pieceLocation = pieceLocation;
        } else {
            this.pieceLocation = -1;
        }
        home = false;
    }

    public GamePiece(Piece piece){
        this.pieceLocation = piece.getLocation();
        this.home = piece.inHome();
        this.base = piece.leftBase();
    }

    @Override
    public int getLocation(){return pieceLocation;}

    @Override
    public void setLocation(int newLocation){ pieceLocation = newLocation;}

    @Override
    public boolean inHome(){return home;}

    @Override
    public boolean leftBase(){return base;}

    @Override
    public void setInHome(boolean val) { home = val; }

    @Override
    public void setLeftBase(boolean val){ base = val; }
}

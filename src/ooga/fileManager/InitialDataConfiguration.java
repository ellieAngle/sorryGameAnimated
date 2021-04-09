package ooga.fileManager;
public class InitialDataConfiguration {
    public int calculateHomeAndBase(int playerCount, int sideLength, boolean inHome) {
        int home, base;
        if(playerCount >= 1 && playerCount <= 4){
            home = (playerCount - 1) * (sideLength - 1) + 2;
        } else{
            playerCount = playerCount - 4;
            home = (sideLength - 1) * (playerCount) - 4;
        }
        base = home + 2;
        if(inHome){return home;}
        else{return base;}
    }
    public String getDefaultPieceLocation(int pieceCount){
        String initialPieceLocation = "";
        for(int i = 1; i <= pieceCount; i++){
            initialPieceLocation = initialPieceLocation + "-1,";
        }
        return initialPieceLocation.substring(0,initialPieceLocation.length() - 1);
    }
    public String setDefaultBool(int pieceCount){
        String bool = "";
        for(int i = 1; i <= pieceCount; i++){
            bool = bool + "0,";
        }
        return bool.substring(0, bool.length() - 1);
    }
}

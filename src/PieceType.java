

public enum PieceType {
    RED(1), White(-1);
    final int moveDir;

    PieceType(int moveDir){
        this.moveDir = moveDir;
    }
}
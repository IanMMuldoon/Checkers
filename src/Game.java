import java.util.ArrayList; // import the ArrayList class

public class Game
{
    private GamePiece[] _pieces;   //array of GamePiece class
    private int _gameID;
    private int _player1UserID;
    private int _player2UserID;
    private int _totalMoves;
    private int _winnerUserID;
    
    //List of Moves
    //element 0 & even numbers are old position, element 1 & odd numbers are new position
    List<Int> _moveHistoryP1 = new ArrayList<Int>();
    List<Int> _moveHistoryP2 = new ArrayList<Int>();

    //Move Board
    private final int MAX_MOVES_SHOWN = 10;
    private int[][] _movesBoardP1 = new int[2][MAX_MOVES_SHOWN]; //OLD Position + new Positon
    private int[][] _movesBoardP2 = new int[2][MAX_MOVES_SHOWN];

    public Game(int player1userid, int player2userid)
    {
        this._player1UserID = player1userid;
        this._player2UserID = player2userid;

        this._reset();
    }

    public int _getGameID() { return this._gameID; }
    public int _getPlayer1UserID() { return this._player1UserID; }
    public int _getPlayer2UserID() { return this._player2UserID; }
    public int _getTotalMoves() { return this._totalMoves; }
    public int _getWinnerUserID() { return this._winnerUserID; }
    public void _reset()
    {
        this._totalMoves = 0;
        this._winnerUserID = 0;

        //Set up the pieces (maybe this can be two arrays? player 1 pieces/player 2 pieces?
        this._pieces = new GamePiece[24];

        //We will define ROWS as 1-8
        //And POSITIONS on those rows as 1-4

        //We will define PLAYER 1 start position as the BOTTOM (rows 1-3)
        for (int i = 1; i <= 12; i++)
        {
            this._pieces[i]._playerID = this._player1UserID;

            //integer division for the row
            this._pieces[i]._row = (i + 3) / 4;
            this._pieces[i]._position = (i - (this._pieces[i]._row-1)*4);
        }

        //We will define PLAYER 2 start position as the TOP (rows 6-8)
        for (int i = 13; i <= 24; i++)
        {
            this._pieces[i]._playerID = this._player2UserID;

            //integer division for the row
            this._pieces[i]._row = ((i + 3) / 4) + 2; //skipping two rows
            this._pieces[i]._position = (i-12) - (this._pieces[i]._row-6)*4;

//How the position formula works
//            @i = 1: 1 - (1-1)*4 = 1
//            @i = 11: 11 - (3-1)*4 = 11-8 = 3
//            @i = 13: (13-12) - (((13 + 3) / 4) + 2 -6)*4 -> 1 - (4+2-6)*4 -> 1
//            @i = 20: (20-12) - (((20 + 3) / 4) + 2 -6)*4 -> 8 - (5+2-6)*4 -> 8 - 4 = 4
//            @i = 17: (17-12) - (((17 + 3) / 4) + 2 -6)*4 -> 5 - (5+2-6)*4 -> 5 - 4 = 1
//            @i = 23: (23-12) - (((23 + 3) / 4) + 2 -6)*4 -> 11 - (6+2-6)*4 -> 11 - 8 = 3
        }
    }

    public boolean _canMovePiece(GamePiece piece, int to_row, int to_position)
    {
        //Determine if this is a valid position
        boolean can_move = false;

        //First check if there is a piece there
        if (_squareHasPiece(to_row, to_position))
            return false;

        if (piece._isKing)
        {

        }
        else
        {
            //regular pieces
            if (this._pieceIsPlayer1(piece))
            {
                //player 1 regular pieces move UP

                //check if moving or jumping
                if (to_row == (piece._row + 1))
                {
                    //Moving
                    if (piece._row %2 == 0 //Even rows move LEFT 0 or RIGHT 1
                            && (to_position == piece._position || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
                else if (to_row == (piece._row + 2))
                {
                    //Jumping
                    if (piece._row %2 == 0 //Even rows move LEFT 1 or RIGHT 1
                            && (to_position == (piece._position - 1) || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
//                else
//                    can_move = false;
            }
            else
            {
                //player 2 regular pieces move DOWN

                //check if moving or jumping
                if (to_row == (piece._row - 1))
                {
                    //Moving
                    if (piece._row %2 == 0 //Even rows move LEFT 0 or RIGHT 1
                            && (to_position == piece._position || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
                else if (to_row == (piece._row - 2))
                {
                    //Jumping
                    if (piece._row %2 == 0 //Even rows move LEFT 1 or RIGHT 1
                            && (to_position == (piece._position - 1) || to_position == (piece._position + 1 )))
                    {
                        can_move = true;
                    }
//                    else
//                        can_move = false;
                }
//                else
//                    can_move = false;
            }
        }

        return can_move;
    }

    private boolean _squareHasPiece(int row, int position)
    {
        boolean has_piece = false;

        for (GamePiece piece: this._pieces)
        {
            if (piece._row == row && piece._position == position)
            {
                has_piece = true;
                break;
            }
        }

        return has_piece;
    }

    public boolean _movePiece(GamePiece piece, int to_row, int to_position)
    {
        if (this._canMovePiece(piece, to_row, to_position))
        {
            _updateMovesList(piece,to_row, to_position);
            piece._row = to_row;
            piece._position = to_position;
            _totalMoves++;
        }

        //TODO: logic for if a piece was captured

        //TODO: return true if must jump again? (or maybe return an enum?)

        return true;
    }

    public void _kingPiece(GamePiece piece)
    {
        if (piece._isKing)
            return;

        //Determine if this piece is actually in appropriate row
        if ((this._pieceIsPlayer1(piece) && piece._row == 8)
                || (piece._row == 1))
            piece._isKing = true;
    }

    private boolean _pieceIsPlayer1(GamePiece piece)
    {
        if (piece._playerID == this._player1UserID)
            return true;
        else
            return false;
    }

    private void _updateMovesList(Piece piece, int to_row, int to_position){
        //called before pieces are moved & after move is deemed valid (INSIDE  
        // ALSO, the format is old position, new position, old position, new positon etc.
        
        if(this._pieceIsPlayer1(piece)){
            this._moveHistoryP1.add(_convertPosition(piece._row, piece._position));
            this._moveHistoryP1.add(_convertPosition(to_row, to_position));
            
        }
        else{
            this._moveHistoryP2.add(_convertPosition(piece._row, piece._position));
            this._moveHistoryP2.add(_convertPosition(to_row, to_position));
        } 
    }
    private int _convertPosition(int row, int position){ 
        //to make it look like wireframe outline
        //Example:It will say 11-15 instead of Row8Column2 to Row4Column4
        
        if(row%2==0)
            return (row*8) + (2*position) - 8;
        else
            return (row*8) + (2*position) - 9;
    }
    private void _updateMovesBoard{
        //called after peices are moved
        if(this._totalMoves<= this.MAX_MOVES_SHOWN*2){
            if(this._totalMoves%2 == 1){ //player 1 just finished
                    this._moveBoardP1[0][((this._totalMoves+1)/2)-1] = 
                        this._moveHistoryP1.get(this._moveHistoryP1.size()-2);
                    this._moveBoardP1[1][((this._totalMoves+1)/2)-1] = 
                        this._moveHistoryP1.get(this._moveHistoryP1.size()-1);
             }
             else{
                    this._moveBoardP2[0][(this._totalMoves/2)-1] = 
                        this._moveHistoryP2.get(this._moveHistoryP2.size()-2);
                    this._moveBoardP2[1][(this._totalMoves/2)-1] = 
                        this._moveHistoryP2.get(this._moveHistoryP2.size()-1);
                }
        }
        else{ // if one or both players are on their 11th turn, meaning we need to refresh MovesList
            j =  this._moveHistoryP1.size() - this.MAX_MOVES_SHOWN;
            k =  this._moveHistoryP2.size() - this.MAX_MOVES_SHOWN;

            for(int i=0; i < this.MAX_MOVES_SHOWN; i++ ){
                this._moveBoardP1[0][i] = this._moveHistoryP1.get(j+i);
                this._moveBoardP1[1][i] = this._moveHistoryP1.get(j+i);

                this._moveBoardP2[0][i] = this._moveHistoryP2.get(k+i);
                this._moveBoardP2[1][i] = this._moveHistoryP2.get(k+i);
            }
        }

    }
}

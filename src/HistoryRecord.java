public class HistoryRecord
{
    public int ID;
    public String Name;
    public int Wins;
    public int Losses;

    public HistoryRecord(int ID, String name, int wins, int losses)
    {
        this.ID = ID;
        this.Name = name;
        this.Wins = wins;
        this.Losses = losses;
    }

    public HistoryRecord(String[] values)
    {
        this.ID = Integer.parseInt(values[0]);
        this.Name = values[1];
        this.Wins = Integer.parseInt(values[2]);
        this.Losses = Integer.parseInt(values[3]);
    }

    @Override
    public String toString() {
        return Name +
                ", Wins=" + Wins +
                ", Losses=" + Losses;
    }
}

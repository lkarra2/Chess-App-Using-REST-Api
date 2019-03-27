package chessgame;

public class Move {

    private String begin;
    private String end;

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public  Move() {
    }

    public Move(String begin, String end) {
        this.begin = begin;
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public String getBegin() {
        return begin;
    }

    @Override
    public String toString() {
        return "chessgame.Move{" +
                "begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}

package chessengine;

import java.util.Objects;
import java.util.UUID;

public class Session {

    // It helps to keep track of the state of the game by storing unique ID

    String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                '}';
    }

    public Session() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session1 = (Session) o;
        return id.equals(session1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

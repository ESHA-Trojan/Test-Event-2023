import java.util.Objects;

public class Artifact {

    private final String name;
    private final String power;

    public Artifact(String name, String power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return this.hashCode() == artifact.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, power);
    }

}

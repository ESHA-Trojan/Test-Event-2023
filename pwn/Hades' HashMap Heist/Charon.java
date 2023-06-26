import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Charon {

    public static HashMap<Artifact, Integer> ARTIFACT_PRICES = new HashMap<>();

    static {
        // Thanks Hades!
        ARTIFACT_PRICES.put(new Artifact("Zeus' Thunderbolt", "Harnesses the power of lightning"), 1000);
        ARTIFACT_PRICES.put(new Artifact("Poseidon's Trident", "Controls the might of the seas"), 800);
        ARTIFACT_PRICES.put(new Artifact("Athena's Shield", "Unbreakable defense and tactical prowess"), 250);
        ARTIFACT_PRICES.put(new Artifact("Apollo's Lyre", "Enchants with divine melodies"), 50);
        ARTIFACT_PRICES.put(new Artifact("Hermes' Winged Sandals", "Bestows incredible speed and agility"), 4000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String name = "Artemis' Silver Bow";

        System.out.println("< This looks like " + name + ", but I don't know much about it.");
        System.out.println("< Can you tell me more about its power? I'll see if my HashMap recognizes it.");

        String power = scanner.nextLine();
        Artifact artifact = new Artifact(name, power);

        if (!ARTIFACT_PRICES.containsKey(artifact)) {
            System.out.println("< I cannot value this artifact, please bring come coins instead.");
            return;
        }

        int price = ARTIFACT_PRICES.get(artifact);

        System.out.println("< Ah, the " + name + "! A remarkable artifact indeed, with the power of " + power + ".");
        System.out.println("< I can offer you passage across these rivers for a mere 10 coins.");

        // price > 10, as Hades doesn't store cheap artifacts

        System.out.println("< But since the value of this artifact is " + price + " coins, I will gladly accept it.");
        System.out.println("< I will consider the difference as my tip.");

        System.out.println("< Prepare yourself, for the journey ahead will not be an easy one...");

        System.out.println("< Congratulations on your successful journey!");
        System.out.println("< As a token of your achievement, here is the flag: " + getFlag());
    }

    private static String getFlag() {
        try {
            return Files.lines(Paths.get("flag.txt"))
                    .findFirst()
                    .orElseThrow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package Utils;

import java.util.HashSet;
import java.util.Random;

/**
 * Класс-генератор ID.
 */
public class IDGenerator {
    private static IDGenerator idGenerator;

    public static IDGenerator getIdGenerator() {
        if (idGenerator == null) { idGenerator = new IDGenerator(); }
        return idGenerator;
    }

    private HashSet<Integer> hashSetId = new HashSet<>();

    public Integer generateID() {
        Integer id = new Random().nextInt(Integer.MAX_VALUE);

        if (hashSetId.contains(id)) {
            while (hashSetId.contains(id)) {
                id = new Random().nextInt(Integer.MAX_VALUE);
            }
        }

        hashSetId.add(id);
        return id;
    }

    Integer generateID(Integer ID) {
        Integer id = ID;

        if (hashSetId.contains(id)) {
            while (hashSetId.contains(id)) {
                id = new Random().nextInt(Integer.MAX_VALUE);
            }
        }

        hashSetId.add(id);
        return id;
    }
}

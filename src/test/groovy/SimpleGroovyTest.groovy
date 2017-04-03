import org.junit.Test

/**
 * Created by bdevaux on 03/04/17.
 */
class SimpleGroovyTest {
    @Test
    void test() {
        def tags = [
                "DDYT-BASE-1.0.0",
                "DDYT-BASE-1.0.1",
                "DDYT-BASE-1.0.10",
                "DDYT-BASE-1.0.11",
                "DDYT-BASE-1.0.12",
                "DDYT-BASE-1.0.13",
                "DDYT-BASE-1.0.14",
                "DDYT-BASE-1.0.2",
                "DDYT-BASE-1.0.3",
                "DDYT-BASE-1.0.4",
                "DDYT-BASE-1.0.5",
                "DDYT-BASE-1.0.6",
                "DDYT-BASE-1.0.7",
                "DDYT-BASE-1.0.8",
                "DDYT-BASE-1.0.9",
                "DDYT-BASE-2.3.45",
                "DDYT-BASE-4.1.32",
                "afzfa",
                "qsdfzef",
        ]

        // Keep only relevant tags and extract version
        tags = tags
                .findAll { it.contains "DDYT-BASE-" }
                .collect { it.replaceAll("DDYT-BASE-", "") }

        // Define intermediate structure
        def mem = [
                0: [version: 0, tag: 0], // IGNORED
                1: [version: 0, tag: 0], // major
                2: [version: 0, tag: 0], // minor
                3: [version: 0, tag: 0] // bugfix
        ]

        // Define result
        def candidates = [
                1: "",
                2: "",
                3: ""
        ]

        // Store last version for major, minor and bugfix in the intermediate structure
        tags.eachWithIndex { it, index ->
            def items = it.split("\\.")
            items = ["0", *items]
            for (int i = 0; i <= 3; i++) {
                if (items[i].toInteger() > mem.get(i).version) {
                    mem.put(i, [version: items[i].toInteger(), tag: it])
                }
            }
        }

        // For each retrieved "last version", compute the next version to suggest to the user
        mem.each { i, res ->
            if (i == 0) return
            def items = res.tag.split("\\.")
            items = ["0", *items]

            switch (i) {
                case 1:
                    candidates.put(i, "${items[1].toInteger() + 1}." + items[2] + "." + items[3])
                    break
                case 2:
                    candidates.put(i, items[1] + "." + "${items[2].toInteger() + 1}." + items[3])
                    break
                case 3:
                    candidates.put(i, items[1] + "." + items[2] + ".${items[3].toInteger() + 1}")
                    break
            }
        }

        println(candidates)
    }
}

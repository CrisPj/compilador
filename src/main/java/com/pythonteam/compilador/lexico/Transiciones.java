package com.pythonteam.compilador.lexico;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class Transiciones {
        private final Map<Integer, Map<Character, Integer>> function = new HashMap<>();

        public void addTransition(Integer startState, Integer goalState, char character) {
            function.computeIfAbsent(startState, k -> new HashMap<>()).put(character, goalState);
        }

        private Optional<Integer> process(Integer startState, char character) {
            return Optional.ofNullable(function.getOrDefault(startState, Collections.emptyMap()).get(character));
        }

        public Optional<Integer> processAll(Integer startState, char[] characters) {
            return IntStream.range(0, characters.length)
                    .boxed()
                    .reduce(
                            Optional.of(startState),
                            (o, i) -> o.flatMap(s -> process(s, characters[i])),
                            (o1, o2) -> o1.map(Optional::of).orElse(o2)
                    );
        }
}

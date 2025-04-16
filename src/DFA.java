import java.util.*;

class DFA {
    Set<Integer> states;
    Set<Character> alphabet;
    Map<Integer, Map<Character, Integer>> transitionFunction;
    int startState;
    Set<Integer> acceptingStates;

    public DFA(Set<Integer> states, Set<Character> alphabet, Map<Integer, Map<Character, Integer>> transitionFunction,
               int startState, Set<Integer> acceptingStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionFunction = transitionFunction;
        this.startState = startState;
        this.acceptingStates = acceptingStates;
    }

    public DFA minimize() {
        // 1. Régi állapotok eltüntetése
        Set<Integer> nonAcceptingStates = new HashSet<>(states);
        nonAcceptingStates.removeAll(acceptingStates);
        Set<Set<Integer>> partitions = new HashSet<>(Arrays.asList(acceptingStates, nonAcceptingStates));
        boolean changed;

        do {
            changed = false;
            Set<Set<Integer>> newPartitions = new HashSet<>();
            for (Set<Integer> group : partitions) {
                Map<String, Set<Integer>> splitGroups = new HashMap<>();
                for (int state : group) {
                    StringBuilder signature = new StringBuilder();
                    for (char c : alphabet) {
                        int nextState = transitionFunction.get(state).getOrDefault(c, -1);
                        for (Set<Integer> partition : partitions) {
                            if (partition.contains(nextState)) {
                                signature.append(partition.hashCode()).append("|");
                                break;
                            }
                        }
                    }
                    splitGroups.computeIfAbsent(signature.toString(), k -> new HashSet<>()).add(state);
                }
                newPartitions.addAll(splitGroups.values());
            }
            if (!newPartitions.equals(partitions)) {
                changed = true;
                partitions = newPartitions;
            }
        } while (changed);

        // 2. Új, minimalizált állapotok létrehozása
        Map<Integer, Integer> stateMapping = new HashMap<>();
        int newStateId = 0;
        for (Set<Integer> partition : partitions) {
            for (int state : partition) {
                stateMapping.put(state, newStateId);
            }
            newStateId++;
        }

        // 3. Átmenet fv (minimalizált) konstruálása
        Map<Integer, Map<Character, Integer>> newTransitions = new HashMap<>();
        for (int state : states) {
            int newState = stateMapping.get(state);
            newTransitions.putIfAbsent(newState, new HashMap<>());
            for (char c : alphabet) {
                int nextState = transitionFunction.get(state).getOrDefault(c, -1);
                if (nextState != -1) {
                    newTransitions.get(newState).put(c, stateMapping.get(nextState));
                }
            }
        }

        // 4. Új végállapotok beállítása
        Set<Integer> newAcceptingStates = new HashSet<>();
        for (int state : acceptingStates) {
            newAcceptingStates.add(stateMapping.get(state));
        }

        // 5. Új kezdőállapot
        int newStartState = stateMapping.get(startState);

        return new DFA(new HashSet<>(stateMapping.values()), alphabet, newTransitions, newStartState, newAcceptingStates);
    }
}
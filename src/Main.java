import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Eredeti DFA definiálása
        Set<Integer> states = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1'));
        Map<Integer, Map<Character, Integer>> transitionFunction = new HashMap<>() {{
            put(0, Map.of('0', 1, '1', 2));
            put(1, Map.of('0', 1, '1', 2));
            put(2, Map.of('0', 3, '1', 4));
            put(3, Map.of('0', 3, '1', 4));
            put(4, Map.of('1', 6, '0', 5));
            put(5, Map.of('0', 5, '1', 6));
            put(6, Map.of('1', 2, '0', 1));
        }};
        int startState = 0;
        Set<Integer> acceptingStates = new HashSet<>(Arrays.asList(0, 1, 6));


        DFA dfa = new DFA(states, alphabet, transitionFunction, startState, acceptingStates);

        // Eredeti DFA kiíratása
        System.out.println("Original DFA States: " + dfa.states);
        System.out.println("Original DFA Transitions: " + dfa.transitionFunction);
        System.out.println("Original DFA Start State: " + dfa.startState);
        System.out.println("Original DFA Accepting States: " + dfa.acceptingStates);

        DFA minimizedDFA = dfa.minimize();

        System.out.println("\nMinimized DFA States: " + minimizedDFA.states);
        System.out.println("Minimized DFA Transitions: " + minimizedDFA.transitionFunction);
        System.out.println("Minimized DFA Start State: " + minimizedDFA.startState);
        System.out.println("Minimized DFA Accepting States: " + minimizedDFA.acceptingStates);
    }
}
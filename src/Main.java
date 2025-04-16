import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Eredeti DFA definiálása
        Set<Integer> states = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Map<Integer, Map<Character, Integer>> transitionFunction = new HashMap<>() {{
            put(0, Map.of('a', 1, 'b', 2));
            put(1, Map.of('a', 0, 'b', 3));
            put(2, Map.of('a', 4, 'b', 5));
            put(3, Map.of('a', 4, 'b', 5));
            put(4, Map.of('a', 4, 'b', 5));
            put(5, Map.of('a', 5, 'b', 5));
        }};
        int startState = 0;
        Set<Integer> acceptingStates = new HashSet<>(Arrays.asList(3, 5));


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
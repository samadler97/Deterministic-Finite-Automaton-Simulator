public class DFAGenerator {
    //creates a state in the dfa
    public static class State {
        public String name = "";
        public boolean startState = false;
        public boolean acceptState = false;

        public State (String name, boolean startState, boolean acceptState) {
            this.name = name;
            this.startState = startState;
            this.acceptState = acceptState;
        }
    }

    public static class Transition {
        String start;
        String input;
        String end;

        public Transition (String start, String input, String end) {
            this.start = start;
            this.input = input;
            this.end = end;
        }
    }
}

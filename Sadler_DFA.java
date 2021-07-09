import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import java.io.*;

public class Sadler_DFA {
    public static void main(String[] args) {
        File inputFile = null;
        if (args.length == 2) {
            inputFile = new File(args[0]);
        }
        else {
            System.out.println("Incorrect numbers of arguments.");
            System.exit(0);
        }

        //General values needed for this program
        List<String> states = new ArrayList<String>();
        List<DFAGenerator.State> stateObjects = new ArrayList<DFAGenerator.State>();
        List<String> alphabet = new ArrayList<String>();
        String stringPassed = args[1];
        String[] stringToCheck = null;
        String startState = null;
        List<String> acceptStates = new ArrayList<String>();
        List<String> transitions = new ArrayList<String>();
        List<DFAGenerator.Transition> transitionObjects = new ArrayList<DFAGenerator.Transition>();

        //Reading in the file and setting the values for a properly formatted file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String tempStates = reader.readLine();
            states = Arrays.asList(tempStates.split(","));
            String tempAlphabet = reader.readLine();
            alphabet = Arrays.asList(tempAlphabet.split(","));
            startState = reader.readLine();
            String tempAccStates = reader.readLine();
            acceptStates = Arrays.asList(tempAccStates.split(","));

            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                String tempTransition = tempLine;
                transitions.add(tempTransition);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException x) {
            x.printStackTrace();
        }

        //Setting up the string passed to check if it is accepted by the DFA
        //or not, or if it malformed.
        //Checks if the string is malformed by checking is all characters in
        //the string are withing the alphabet
        stringToCheck = stringPassed.split("");
        for (int i = 0; i < stringToCheck.length; i++) {
            boolean pass = false;
            for (int j = 0; j < alphabet.size(); j++) {
                if (stringToCheck[i].equals(alphabet.get(j))) {
                    pass = true;
                }
            }

            if (pass == false) {
                System.out.println("Malformed.");
                System.exit(0);
            }
        }

        //Creating each state and putting them into the list containing all the states
        for (int i = 0; i < states.size(); i++) {
            boolean tempBoolStart = false;
            boolean tempBoolAccept = false;

            if (states.get(i).equals(startState)) {
                tempBoolStart = true;
            }
            for (int j = 0; j < acceptStates.size(); j++) {
                if (acceptStates.get(j).equals(states.get(i))) {
                    tempBoolAccept = true;
                }
            }

            DFAGenerator.State tempState = new DFAGenerator.State(states.get(i), tempBoolStart, tempBoolAccept);
            stateObjects.add(tempState);
        }

        //Creating each transition and inserting into list
        for (int i = 0; i < transitions.size(); i++) {
            String start;
            String input;
            String end;

            String[] tempTran = transitions.get(i).split("->");
            tempTran[0] = tempTran[0].replace("(","");
            tempTran[0] = tempTran[0].replace(")","");
            String firstHalf = tempTran[0];

            end = tempTran[1];
            String[] startInput = firstHalf.split(",");
            start = startInput[0];
            input = startInput[1];

            DFAGenerator.Transition tempTransition = new DFAGenerator.Transition(start, input, end);
            transitionObjects.add(tempTransition);
        }

        System.out.println(DFAChecker(stateObjects, transitionObjects, stringToCheck));
    }

    //Runs a check to see whether the user inputted string is accepted by the DFA or not
    public static String DFAChecker (List<DFAGenerator.State> states, List<DFAGenerator.Transition> transitions, String[] stringToCheck) {
        String currentState;
        String startState = null;
        boolean acceptOrReject = false;
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).startState == true) {
                startState = states.get(i).name;
                break;
            }
        }

        currentState = startState;
        for (int i = 0; i < stringToCheck.length; i++) {
            for (int j = 0; j < transitions.size(); j++) {
                if ((currentState.equals(transitions.get(j).start)) && (stringToCheck[i].equals(transitions.get(j).input))) {
                    currentState = transitions.get(j).end;
                    break;
                }

            }

            if (i == stringToCheck.length - 1) {
                for (int j = 0; j < states.size(); j++) {
                    if (currentState.equals(states.get(j).name)) {
                        if (states.get(j).acceptState == true) {
                            acceptOrReject = true;
                        }
                    }
                }
            }
        }
        //System.out.println(acceptOrReject);

        if (acceptOrReject == true) {
            return "Accepted!";
        }
        else {
            return "Rejected.";
        }
    }
}

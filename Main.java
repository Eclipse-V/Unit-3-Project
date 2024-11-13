import java.util.Scanner;
/**
 * A program to carry on conversations with a human user.
 * Uses advanced search for keywords 
 * Will transform statements as well as react to keywords
 *
 * @author 
 * @version 
 *
 */
public class Main
{
    /**
     * main method - DO NOT CHANGE!
     */ 
    public static void main(String[] args)
    {
        System.out.println ("Hello, let's talk!");
        @SuppressWarnings("resource")
        Scanner in = new Scanner (System.in);
        String statement = in.nextLine();
        //Uses a while statement - we will learn this next unit!
        //Enter Bye to end the program
        while (!statement.equals("Bye"))
        {
            System.out.println (getResponse(statement));
            statement = in.nextLine();
        }
    }
    
    /**
     * Gives a response to a user statement
     * 
     * @param statement
     *            the user statement
     * @return a response based on the rules given
     */
    public static String getResponse(String statement)
    {  
        String response = "";
        if (statement.trim().length() == 0)
        {
            response = "Say something, please.";
        }

        //Complete Part 3 below
        
        //If a statement contains the word "dog" or "cat", it asks for more info
        else if (findKeyword(statement, "cat", 0) >= 0
                || findKeyword(statement, "dog", 0) >= 0)
        {    
            response = "Tell me more about your pets.";
        }

        //If a statement contains the name of a loved teacher, it responds favorably
        else if (findKeyword(statement, "Mr.", 0) >= 0)
        {    
            response = "He sounds like a good teacher.";
        }
        else if (findKeyword(statement, "Miss.", 0) >= 0
                || findKeyword(statement, "Ms.", 0) >= 0
                || findKeyword(statement, "Mrs.", 0) >= 0)
        {    
            response = "She sounds like a good teacher.";
        }
       
        //If a statement contains the word "fun", it asks for more info
        else if (findKeyword(statement, "fun", 0) >= 0)
        {    
            response = "What did you do?";
        }
        //If a statement contains the word "yes", it gives a statement
        else if (findKeyword(statement, "yes", 0) >= 0)
        {    
            response = "You're a pushover.";
        }
        //If a statement contains the word "study", it asks why the user told them
        else if (findKeyword(statement, "study", 0) >= 0)
        {    
            response = "Why are you telling me?";
        }

        //Do not change anything else in this method below here until part 5 and 6
       
        else if (findKeyword(statement, "I want", 0) >= 0)
        {
            response = transformIWantToStatement(statement);
        }

        else if (findKeyword(statement, "mother", 0) >= 0
                || findKeyword(statement, "father", 0) >= 0
                || findKeyword(statement, "sister", 0) >= 0
                    || findKeyword(statement, "brother", 0) >= 0)
        {
            response = "Tell me more about your family.";
        }

        else if (findKeyword(statement, "no", 0) >= 0)
        {
            response = "Why so negative?";
        }
        
        else
        {
            // Look for a two word (I <something> you)
            // pattern
            int psn = findKeyword(statement, "I", 0);

            if (psn >= 0 && findKeyword(statement, "you", psn) >= 0)
            {
                response = transformYouMeStatement(statement);
            }
            else 
            { 
                response = getRandomResponse();
            }
        } 
        
        return response;
    }
    
    /**
     * Pick a default response to use if nothing else fits.
     * @return a non-committal string
     */
    private static String getRandomResponse()
    {
        //Complete part 4 below here
        final int NUMBER_OF_RESPONSES = 6;
        double r = Math.random();
        int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
        String response = "";
        
        if (whichResponse == 0)
        {
            response = "Interesting, tell me more.";
        }
        else if (whichResponse == 1)
        {
            response = "Hmmm.";
        }
        else if (whichResponse == 2)
        {
            response = "Do you really think so?";
        }
        else if (whichResponse == 3)
        {
            response = "You don't say.";
        } 

        else if (whichResponse == 4)
        {
            response = "Yeah.";
        } 
        else if (whichResponse == 5)
        {
            response = "I don't like that response.";
        }

        return response;
    }
    
    /**
     * Take a statement with "I want <something>." and transform it into 
     * Would you really be happy if you had <something>?”
     * @param statement the user statement, assumed to contain "I want"
     * @return the transformed statement
     */
    private static String transformIWantToStatement(String statement)
    {
        //Complete part 5 in here
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        int psn = findKeyword (statement, "I want", 0);
        String restOfStatement = statement.substring(psn + 7).trim();
        return "Would you really be happy if you had " + restOfStatement + "?";
    }

    /**
     * Take a statement with "I <something> you" and transform it into 
     * “Why do you <something> me?”
     * @param statement the user statement, assumed to contain "I" followed by "you"
     * @return the transformed statement
     */
    private static String transformYouMeStatement(String statement)
    {
        //Complete part 6 in here
        //  Remove the final period, if there is one
        statement = statement.trim();
        String lastChar = statement.substring(statement.length() - 1);
        if (lastChar.equals("."))
        {
            statement = statement.substring(0, statement.length() - 1);
        }
        
        int psnOfYou = findKeyword (statement, "I", 0);
        int psnOfMe = findKeyword (statement, "you", psnOfYou + 3);
        
        String restOfStatement = statement.substring(psnOfYou + 2, psnOfMe).trim();
        return "Why do you " + restOfStatement + " me?";
    }
    
    
    //Do Not Change Anything Below (But you should look through and see how it works!)
    /**
     * Search for one word in phrase. The search is not case
     * sensitive. This method will check that the given goal
     * is not a substring of a longer string (so, for
     * example, "I know" does not contain "no").
     *
     * @param statement
     *            the string to search
     * @param goal
     *            the string to search for
     * @param startPos
     *            the character of the string to begin the
     *            search at
     * @return the index of the first occurrence of goal in
     *         statement or -1 if it's not found
     */
    private static int findKeyword(String statement, String goal,
            int startPos)
    {
        String phrase = statement.trim().toLowerCase();
        goal = goal.toLowerCase();

        // The only change to incorporate the startPos is in
        // the line below
        int psn = phrase.indexOf(goal, startPos);
        
        // Refinement--make sure the goal isn't part of a
        // word
        while (psn >= 0)
        {
            // Find the string of length 1 before and after
            // the word
            String before = " ", after = " ";
            if (psn > 0)
            {
                before = phrase.substring(psn - 1, psn);
            }
            if (psn + goal.length() < phrase.length())
            {
                after = phrase.substring(
                        psn + goal.length(),
                        psn + goal.length() + 1);
            }

            // If before and after aren't letters, we've
            // found the word
            if (((before.compareTo("a") < 0) || (before
                    .compareTo("z") > 0)) // before is not a
                                            // letter
                    && ((after.compareTo("a") < 0) || (after
                            .compareTo("z") > 0)))
            {
                return psn;
            }

            // The last position didn't work, so let's find
            // the next, if there is one.
            psn = phrase.indexOf(goal, psn + 1);

        }

        return -1;
    }
}

import java.util.Random;
import java.util.Scanner;

public class ParticipationWK3 {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        Random r = new Random();

        int winningNum = r.nextInt(90)+10;
        System.out.print("Please enter your guess: ");
        int guess = inp.nextInt();

        if (guess == winningNum) {
            System.out.println("You win $1000!");
        }
        else if (guess == (((winningNum%10)*10)+(winningNum/10))) {
            System.out.println("You win $500!");
        }
        else if (guess%10 == winningNum%10 ||
                guess/10 == winningNum%10 ||
                guess%10 == winningNum/10 ||
                guess/10 == winningNum/10) {
            System.out.println("You win $100!");
        }
        else {
            System.out.println("Better luck next time!");
        }
        System.out.println(winningNum);
    }
}

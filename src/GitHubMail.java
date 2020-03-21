import java.io.BufferedInputStream;
import java.net.URL;
import java.util.*;

public class GitHubMail {

    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);

        while (true) {

            System.out.print("GitHub username:");
            String input=scanner.nextLine();

            String[] mailAddresses=getMail(input);

            if ( mailAddresses==null ) {
                System.out.println("Something went wrong...");
            } else {
                for (String act:mailAddresses) System.out.println( act );
            }

            System.out.println();
        }
    }

    public static String[] getMail(String user){

        List<String> words=new ArrayList<>();
        Set<String> mailAddresses=new HashSet<>();

        try {
            BufferedInputStream data = new BufferedInputStream(new URL("https://api.github.com/users/" + user + "/events/public").openStream());
            int act;
            char actC;
            String cache="";

            while ( (act=data.read())!=-1 ) {

                actC=(char)act;

                if ( actC=='\"' ) {
                    words.add(cache);
                    cache="";
                } else {
                    cache+=actC;
                }
            }

            data.close();
        } catch (Exception e) {
            return null;
        }

        for (int n=0; n<words.size(); n++) {
            if (words.get(n).equals("email"))
                mailAddresses.add(words.get(n + 2));
        }

        String[] out=new String[mailAddresses.size()];
        int count=0;

        for (String mailAddress:mailAddresses)
            out[count++]=mailAddress;

        return out;
    }

}

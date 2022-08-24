import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
class Node
{
    Character ch;

    Integer freq;

    Node left = null;
    Node right = null;

    Node(Character ch, Integer freq)
    {
        this.ch = ch;
        this.freq = freq;
    }

    public Node(Character ch, Integer freq, Node left, Node right)
    {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

public class DEMO_CLASS
{
    public static void createHuffmanTree(String text)
    {
        if (text == null || text.length() == 0)
        {
            return;
        }
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray())
        {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        for (var entry: freq.entrySet())
        {

            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();

            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }

        Node root = pq.peek();

        Map<Character, String> huffmanCode = new HashMap<>();
        encodeData(root, "", huffmanCode);
        int[] f = new int[text.length()];
        int i, j;

        //Converts given string into character array
        char[] string = text.toCharArray();

        for(i = 0; i <text.length(); i++)
        {
            f[i] = 1;
            for(j = i+1; j <text.length(); j++)
            {
                if(string[i] == string[j])
                {
                    f[i]++;

                    //Set string[j] to 0 to avoid printing visited character
                    string[j] = '0';
                }
            }
        }

        //Displays the each character and their corresponding frequency
        System.out.println("Characters and their corresponding frequencies: ");
        for(i = 0; i <f.length; i++)
        {
            if(string[i] != ' ' && string[i] != '0')
                System.out.println(string[i] + "-" + f[i]);
        }
        float[] prob =new float[text.length()];
        System.out.println("Characters and their corresponding probabilities: ");
        for( i=0;i<f.length;++i)
        {
            prob[i]=f[i]/(float)text.length();
            if(string[i] != ' ' && string[i] != '0')
                System.out.println(string[i] + "-" + prob[i]);
        }
        float entropy=0;
        for( i=0;i<f.length;++i)
        {
            entropy += -1*((Math.log(prob[i])/Math.log(2))*prob[i]);
        }
        System.out.println("entropy of the string:" + entropy);

        System.out.println("Huffman Codes of the characters are: " + huffmanCode);

        System.out.println("Code lengths of each character code : ");
        for(Map.Entry m:huffmanCode.entrySet())
        {
            System.out.println(m.getValue()+" - "+ String.valueOf(m.getValue()).length());
            ++i;
        }

        System.out.println("The initial string is: " + text);

        StringBuilder sb = new StringBuilder();


        for (char c: text.toCharArray())
        {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("The encoded string is: " + sb);
        System.out.println("size of encoded msg is : " + sb.length());
        System.out.print("The decoded string is: ");
        if (isLeaf(root))
        {

            while (root.freq-- > 0)
            {
                System.out.print(root.ch);
            }
        }
        else
        {

            int index = -1;
            while (index < sb.length() - 1)
            {
                index = decodeData(root, index, sb);
            }
        }
        System.out.println();
        System.out.println("size of string is : " + 8*text.length() );
        int d;
        int enc=sb.length();
        int slen=8*text.length();
        d= __gcd(enc,slen);
        enc=enc/d;
        slen=slen/d;
        System.out.println("compression ratio : " + enc + ":" + slen);
    }
    static int __gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return __gcd(b, a % b);

    }
    public static void encodeData(Node root, String str, Map<Character, String> huffmanCode)
    {
        if (root == null)
        {
            return;
        }

        if (isLeaf(root))
        {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        encodeData(root.left, str + '0', huffmanCode);
        encodeData(root.right, str + '1', huffmanCode);
    }

    public static int decodeData(Node root, int index, StringBuilder sb)
    {

        if (root == null)
        {
            return index;
        }

        if (isLeaf(root))
        {
            System.out.print(root.ch);
            return index;
        }
        index++;
        root = (sb.charAt(index) == '0') ? root.left : root.right;
        index = decodeData(root, index, sb);
        return index;
    }

    public static boolean isLeaf(Node root)
    {

        return root.left == null && root.right == null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {

            File f0 = new File("Strings.txt");
            Scanner sc = new Scanner(f0);
            String fileData = null;
            while (sc.hasNextLine())
            {
                fileData = sc.nextLine();

                createHuffmanTree(fileData);
                System.out.println();
                System.out.println();
            }

        }
        catch (FileNotFoundException exception) {
            System.out.println("Unexpected error occurred!");
            exception.printStackTrace();
        }

    }
}


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
//This class reads a file name and then generates an output file ie output_file.txt after running this main class
public class hashtagcounter {
	
	public static void main(String args[])  {
		String file = args[0];
		File InputFile = new File(file);
		Scanner scanner;
		try {
			scanner = new Scanner(InputFile);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;}
		File OutputFile = new File("output_file.txt"); //Writes as an output file in terms of output_file.txt 
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OutputFile)));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		String input = null;
		HashTag hashtag = null;
		String[] cnt = null;
		Hashtable<String, HashTag> hashtagtitle = new Hashtable<String, HashTag>();
		FibonacciHeap<HashTag> heapOfHashTagscnt = new FibonacciHeap<HashTag>();
		String hashTagTitle;
		while (scanner.hasNextLine()) 
		{
			input = scanner.nextLine();
			if (input.trim().equalsIgnoreCase("Stop")) 
			{ //If stop appears in the file then exit the program
				break;
			}	
			if (!(input.charAt(0) == '#')) {
				try {
					int popularhash = Integer.parseInt(input);
					if (popularhash > hashtagtitle.size()) {
						System.out.println("Invalid answer" + popularhash+ ", because the size of the total input is lesser:" + hashtagtitle.size());
						return;
					}
					heapOfHashTagscnt = outputHashTags(heapOfHashTagscnt, popularhash, bw); bw.newLine();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					return;
				}
				
			} 
			else 
			{
				cnt = input.split(" ");
				hashTagTitle = cnt[0].substring(1, cnt[0].length());
				hashtag = hashtagtitle.get(hashTagTitle);
				if (hashtag == null) {
					hashtag = new HashTag();
					hashtag.setHashTagTitle(hashTagTitle);
					hashtag.setData(Integer.parseInt(cnt[1]));
					hashtagtitle.put(hashTagTitle, hashtag);
					heapOfHashTagscnt = heapOfHashTagscnt.insert(heapOfHashTagscnt, hashtag);
				} 
				else 
				{
				
					heapOfHashTagscnt = heapOfHashTagscnt.increaseKey(heapOfHashTagscnt, hashtag,
							Integer.parseInt(cnt[1])); //If the same input exists, just increase the count
				}
			}

		}
		try {
			bw.flush(); bw.close();
		} catch (IOException e) {
			System.out.println();
			return;
		}
		scanner.close();
	}

	public static FibonacciHeap<HashTag> outputHashTags(FibonacciHeap<HashTag> H, int TopHashtags, BufferedWriter bw) throws IOException {
		StringBuffer outputTags = new StringBuffer();
		HashTag topHashTags = null;
		List<HashTag> removenodes = new ArrayList<>();
		for (int i = 0; i < TopHashtags; i++) {
			topHashTags = H.getMax();
			removenodes.add(topHashTags);
			if (i > 0) {
				outputTags.append(",");
			}
			outputTags.append(topHashTags.getHashTagTitle());
			H = H.removeMax(H);
		}
		bw.write(outputTags.toString()); //Reinsert it in the heap
		for (Iterator<HashTag> iterator = removenodes.iterator(); iterator.hasNext();) 
		{
			HashTag hashTag = (HashTag) iterator.next();
			H = H.insert(H, hashTag);
		}
		return H;
	}

}

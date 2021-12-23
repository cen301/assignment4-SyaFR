import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;



public class assingment4 {

	static class twovalue {
	    public int path;
	    public String pagefault;
	    public int count;

	    public twovalue(int path, String pagefault,int count) {
	        this.path = path;
	        this.pagefault = pagefault;
	        this.count = count;
	    }

	    public int getpath() {
	        return path;
	    }

	    public int getcount() {
	        return count;
	    }
	    public String getpagefault() {
	        return pagefault;
	    }
	}
	
	public static void main(String[] args) {
		
		try {

			String filepath = args[2];//key.nextLine(); 
			String all = "";
			String method = args[1];
			Scanner key = new Scanner(System.in);
			String memory =args[0];
			File file = new File(filepath);
	        FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
	        
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
			while(bufferedReader.readLine() != null) {
				String temp = bufferedReader.readLine();
				if(!temp.contains("#")) {
					all +=temp;
				}
			}
			String[] numberstr = all.split(" ");
			int[] numbers = Arrays.stream(numberstr).mapToInt(Integer::parseInt).toArray();
			//System.out.println(Arrays.toString(numbers));
			String[] memoryarray = new String[Integer.valueOf(memory)];
			if(method.equals("FIFO")) {
				fifo(numberstr,memoryarray, Integer.valueOf(memory),0);
			}if(method.equals("LRU")) {
				lru(numberstr,memoryarray, Integer.valueOf(memory),0);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                     

	}
	
	
	public static void lru(String[] number,String[] memoryarray,int memory,int count)
	{	
		String[] page = new String[memory];
		
		for (int i = 0; i < number.length-1; i++) {
			if (page[i % memory] == null && memoryarray[i % memory] == null) {
				page[i % memory] = number[i+1]; 
				memoryarray[i % memory] = String.valueOf(i % memory); 
				System.out.println(number[i+1]+" --> " + Arrays.toString(page) );
				}else {
					
					//if(diff(page,Integer.valueOf(findold(memoryarray)),Integer.valueOf(number[i+1]))) {
					//System.out.println("diff");}
					//System.out.println(findold(memoryarray) + " olds");
					int old = findold(memoryarray,page,Integer.valueOf(number[i+1]),count).path;
					String pagefault = findold(memoryarray,page,Integer.valueOf(number[i+1]),count).pagefault;
					count = findold(memoryarray,page,Integer.valueOf(number[i+1]),count).count;
					//System.out.println(count);
					page[old] = number[i+1]; 
					
					memoryarray[old] = String.valueOf(2); 
					//System.out.println(old + " old");
					//System.out.println(Arrays.toString(memoryarray) + " memoryarray");
					for (int j = 0; j < memory; j++) {
						
						if (old != j && Integer.valueOf(memoryarray[j]) != 0 ) {
							//System.out.println(j + " dd");
							memoryarray[j] = String.valueOf(Integer.valueOf(memoryarray[j]) - 1);
						}
					}
					System.out.println(number[i+1]+" --> " + Arrays.toString(page) +" "+ pagefault);
					
					
					
					
				}
			
		
			
		}
		System.out.println();
		System.out.println("Total number of page faults is " + count+".");
	}
	public static void fifo(String[] number,String[] memoryarray,int memory,int count)
	{	
		String[] page = new String[memory];
		
		for (int i = 0; i < number.length-1; i++) {
			if (page[i % memory] == null && memoryarray[i % memory] == null) {
				page[i % memory] = number[i+1]; 
				memoryarray[i % memory] = String.valueOf(i % memory); 
				System.out.println(number[i+1]+" --> " + Arrays.toString(page) );
				}else {
					
					//if(diff(page,Integer.valueOf(findold(memoryarray)),Integer.valueOf(number[i+1]))) {
					//System.out.println("diff");}
					//System.out.println(findold(memoryarray) + " olds");
					int old = findoldfifo(memoryarray,page,Integer.valueOf(number[i+1]),count).path;
					String pagefault = findoldfifo(memoryarray,page,Integer.valueOf(number[i+1]),count).pagefault;
					count = findoldfifo(memoryarray,page,Integer.valueOf(number[i+1]),count).count;
					//System.out.println(count);
					page[old] = number[i+1]; 
					
					memoryarray[old] = String.valueOf(2); 
					//System.out.println(old + " old");
					//System.out.println(Arrays.toString(memoryarray) + " memoryarray");
					for (int j = 0; j < memory; j++) {
						
						if (old != j && Integer.valueOf(memoryarray[j]) != 0 ) {
							//System.out.println(j + " dd");
							memoryarray[j] = String.valueOf(Integer.valueOf(memoryarray[j]) - 1);
						}
					}
					System.out.println(number[i+1]+" --> " + Arrays.toString(page) +" "+ pagefault);
					
					
					
					
				}
			
		
			
		}
		System.out.println();
		System.out.println("Total number of page faults is " + count+".");
	}
	public static String diff(String[] page,int t) 
	{		
		String path = "";
		for (int i = 0; i < page.length; i++) {
			if (Integer.valueOf(page[i]) == t) {
				path = String.valueOf(i);
			}
		}
		
		return path;
		
	}
	
	
	public static twovalue findold(String[] memoryarray,String[] page, int t,int count) 
	{	String path=diff(page,t);
		String pagefault="";
		
		if(path.equals("")) {
			for (int i = 0; i < memoryarray.length; i++) {
				if(Integer.valueOf(memoryarray[i]) == 0) {
					path = String.valueOf(i);
				}
			}
			pagefault = "page fault";
			count+=1;
		}
		
		
		twovalue twovalue = new twovalue(Integer.valueOf(path), pagefault,count);
		return twovalue;
	}
	
	public static twovalue findoldfifo(String[] memoryarray,String[] page, int t,int count) 
	{	String path="";
		String pagefault="";
		
		if(path.equals("")) {
			for (int i = 0; i < memoryarray.length; i++) {
				if(Integer.valueOf(memoryarray[i]) == 0) {
					path = String.valueOf(i);
				}
			}
			pagefault = "page fault";
			count+=1;
		}
		
		
		twovalue twovalue = new twovalue(Integer.valueOf(path), pagefault,count);
		return twovalue;
	}
	
	
	

}

/* You have k servers numbered from 0 to k-1 that are being used to handle multiple requests simultaneously. Each 
server has infinite computational capacity but cannot handle more than one request at a time. The requests are 
assigned to servers according to a specific algorithm:
1)- The ith (0-indexed) request arrives.
2)- If all servers are busy, the request is dropped (not handled at all).
3)- If the (i % k)th server is available, assign the request to that server.
4)- Otherwise, assign the request to the next available server (wrapping around the list of servers and starting 
from 0 if necessary). For example, if the ith server is busy, try to assign the request to the (i+1)th server, then 
the (i+2)th server, and so on.
You are given a strictly increasing array arrival of positive integers, where arrival[i] represents the arrival time 
of the ith request, and another array load, where load[i] represents the load of the ith request (the time it takes 
to complete). Your goal is to find the busiest server(s). A server is considered busiest if it handled the most 
number of requests successfully among all the servers. Return a list containing the IDs (0-indexed) of the busiest 
server(s). You may return the IDs in any order.
* Eg 1 :   k = 3     arrival = [1,2,3,4,5]         load = [5,2,3,3,3]      Server = [1]     
* Eg 2 :   k = 3     arrival = [1,2,3,4]           load = [1,2,1,2]        Server = [0]     
* Eg 3 :   k = 3     arrival = [1,2,3]             load = [10,12,11]       Server = [0,1,2] 
*/
import java.util.*;
public class Busiest
{
      public List<Integer> BusiestServer(int arrival[], int load[], int k)
      {
            int server[] = new int[k];      //*  Array Declaration -> O(k)
            int count[] = new int[k];       //*  Array Declaration -> O(k)
            Arrays.fill(server, 0);     // Initialising the arrays...
            Arrays.fill(count, 0);
            int max = 0;
            for(int i = 0; i < arrival.length; i++)    //! Iteration -> O(N)
                  max = Math.max(max, arrival[i]);    // Extracting the last arrival time...
            int time = 0, request = 0, ServerIndex = 0;
            while(time != max+1)      //! Iterating to the last arrival time -> O(N)
            {
                  if(time == arrival[request])    // If request is recieved...
                  {
                        if((server[request % k] == 0) || (server[request % k] <= arrival[request]))
                        {     // If the i % k server is available...
                              count[request % k]++;
                              server[request % k] = server[request % k] + load[request];
                        }
                        else     // If i % k server is not available...
                        {
                              ServerIndex = IdleServerIndex(server, request % k, time);   //! Function -> O(k)
                              // Getting the index of the available server...
                              if(ServerIndex != -1)    // If a server is free...
                              {
                                    count[ServerIndex]++;
                                    server[request % k] = server[request % k] + load[request];
                              }
                        }
                        request++;     // Move to the next request...
                  }
                  time++;    // Increment the time...
            }
            List<Integer> answer = new ArrayList<Integer>();     //*  List for storingt the Server indeces -> O(k)
            int index = 0; max = 0;
            for(int i = 0; i < count.length; i++)    //! Getting the Maximum Count Server -> O(k)
            {
                  if(max < count[i])
                  {
                        index = i;     // Extracting the index...
                        max = count[i];
                  }
            }
            answer.add(index);
            for(int i = 0; i < count.length; i++)     //! Getting the similar count Server -> O(k)
            {
                  if((max == count[i]) && (answer.get(0) != i))
                        answer.add(i);    // If the server is not added in the list...
            }
            return answer;
      }
      public int IdleServerIndex(int server[], int index, int time)   // To check the available server...
      {     //! Function Runtime -> O(k)
            int i = index, count = 0;      //* Variable declaration -> O(1)
            while(count != server.length)    // Checking every server...
            {
                  if(i == server.length)    // If last server is reached...
                  {
                        i = 0;
                        continue;
                  }
                  else if(server[i] <= time)    // If the server is free...
                        return i;
                  i++;
                  count++;
            }
            return -1;    // If no server is available, we drop the request...
      }
      public static void main(String args[])
      {
            Scanner sc = new Scanner(System.in);
            int x;
            System.out.print("Enter number of Requests : ");
            x = sc.nextInt();
            int arrival[] = new int[x];
            int load[] = new int[x];
            for(int i = 0; i < arrival.length; i++)
            {
                  System.out.print("Enter arrival time : ");
                  arrival[i] = sc.nextInt();
                  System.out.print("Enter leaving time : ");
                  load[i] = sc.nextInt();
            }
            System.out.print("Enter number of Servers : ");
            x = sc.nextInt();
            Busiest busiest = new Busiest();     // Object creation...
            List<Integer> lst = new ArrayList<Integer>();
            lst = busiest.BusiestServer(arrival, load, x);     // Function calling...
            System.out.print("The Index of the Busiest Servers are : ");
            for(int i = 0; i < lst.size(); i++)
                  System.out.print(lst.get(i)+", ");
            sc.close();
      }
}



//! Time Complexity -> O(N x k)
//* Space Complexity -> O(k)
import java.io.*;
import java.util.*;

public class SnakesLadder 
// extends AbstractSnakeLadders 
{
	
	int N, M;
	int snakes[];
	int ladders[];
	ArrayList<Integer> movesListForward = new ArrayList<Integer>(); 
	ArrayList<Integer> movesListBackward = new ArrayList<Integer>(); 
	ArrayList<ArrayList<Integer>> laddersOnly = new ArrayList<ArrayList<Integer>>();
	boolean[] visitedForward; 
	
	public SnakesLadder(String name)throws Exception{
		

		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());

	    snakes = new int[N];
		ladders = new int[N];
	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
		}

		for(int i=0;i<M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

			if(source<destination){
				ladders[source] = destination;
			}
			else{
				snakes[source] = destination;
			}
        }




			
				int[] tempS = new int[N],tempL = new int[N];
				
				visitedForward= new boolean[N+1];
				rev(tempS,tempL,N);	
			
				this.movesListForward = new ArrayList<Integer>();
				for(int i=0;i<N+1;i++){
					this.movesListForward.add(Integer.MAX_VALUE);
				}
				bfsMovesForward(tempS,tempL,this.movesListForward,visitedForward);
				this.movesListBackward = this.movesListForward;
				// backward traversal
				// System.out.println(this.movesListBackward);

				// forward traversal
				
				for (int i = 0; i < N; i++){
					tempS[i] = this.snakes[i];
					tempL[i] = this.ladders[i];
				}
				for (int i = 0; i < N; i++){
					tempS[i] = this.snakes[i];
					tempL[i] = this.ladders[i];
				}


				visitedForward= new boolean[N+1];
				this.movesListForward = new ArrayList<Integer>();
				for(int i=0;i<N+1;i++){
					this.movesListForward.add(Integer.MAX_VALUE);
				}
				bfsMovesForward(tempS,tempL,this.movesListForward,visitedForward);
				// System.out.println(this.movesListForward);
		
				// for(int i=0;i<N+1;i++){
				// 	System.out.print("("+i+" : "+this.movesListForward.get(i)+")");
				// }
				// // System.out.println();
				
				// for(int i=0;i<N+1;i++){
				// 	System.out.print("("+i+" : "+this.movesListBackward.get(i)+")");
				// }

				// initializing the laddersonly array
				for(int i=0;i<N;i++){
					if(this.ladders[i]!=-1){
						ArrayList<Integer>temp = new ArrayList<Integer>();
						temp.add(i);
						temp.add(this.ladders[i]);
						laddersOnly.add(temp);
					}
				}
	}

	public int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		// idea is to use BFS to find the minimum number of moves
		

		return this.movesListForward.get(N);
	}

	public int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			// System.out.println("x: " + x+" y: "+y);
			int a = this.movesListForward.get(x);
			int b = this.movesListBackward.get(N-y);
			int c = this.movesListForward.get(N);
			// System.out.println(this.movesListBackward.get(50)+" "+" "+	this.movesListBackward.get(10));
			// System.out.println( "a: "+a+" b: "+b+" c: "+c);
			if(a+b<c)return 1;
			else return -1;

	}

	public int[] FindBestNewSnake()
	{
		int result[] = {-1, -1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */

	
			// System.out.println(laddersOnly);
			int minm=this.movesListForward.get(N), val;
			for(int i=0;i<laddersOnly.size();i++ ){
				if(this.movesListForward.get(laddersOnly.get(i).get(0))>minm)continue;
				for(int j=i+1;j<laddersOnly.size();j++){
					if(laddersOnly.get(j).get(0)>laddersOnly.get(i).get(1))break;
					if(laddersOnly.get(j).get(1)>laddersOnly.get(i).get(1)){
						val = this.movesListForward.get(laddersOnly.get(i).get(0)) + this.movesListBackward.get(N-laddersOnly.get(j).get(1));
						if(val<this.movesListForward.get(N)){
							if(minm>val){
								// System.out.println(val);
								minm = val;
								result[0]=laddersOnly.get(i).get(1);
								result[1]=laddersOnly.get(j).get(0);
								// System.out.println(result[0]+" "+result[1]);
							}
						}
					}
				}
			}

		return result;
	}



	private void rev(int[] arrS,int[]arrL,int N ){
		for (int i = 0; i < N; i++){
			arrS[i] = -1;
			arrL[i] = -1;
		}

		for(int i=0;i<N;i++){
			// int temp = arr[i];
			// arr[i] = i;
			// arr[temp]=arr[i];
			if(this.snakes[i]!=-1 &&arrS[i]==-1){
				arrS[N-this.snakes[i]]=N-i;
			}
			if(this.ladders[i]!=-1 &&arrL[i]==-1){
				arrL[N-this.ladders[i]]=N-i;
			}
		}
		// System.out.println();
		return;
	}

	private void bfsMovesForward(int[]s,int[]l,ArrayList<Integer> movesListForward,boolean[] visitedForward){
		// this.visitedForward and movesListForward are defined in constructor when its called
		Queue<ArrayList<Integer>> q = new LinkedList<ArrayList<Integer>>();
		visitedForward[0]=true;
		// first index of qElem represents vertex and second represents moves required to reach it from 0
		ArrayList<Integer> qElem = new ArrayList<Integer>();
		qElem.add(0);
		qElem.add(0);
		q.add(qElem);
		movesListForward.set(0,0);


		while(!q.isEmpty()){
			ArrayList<Integer> point = q.poll();
			for(int i=point.get(0)+1;i<=point.get(0)+6&& i<=N;i++){
				if(!visitedForward[i]){
					visitedForward[i]=true;
					if(point.get(1)+1<movesListForward.get(i)){
					movesListForward.set(i,point.get(1)+1);
					ArrayList<Integer> temp2 = new ArrayList<Integer>();
						temp2.add(i);
						temp2.add(point.get(1)+1);
						q.add(temp2);
					}

					// handling ladders and snakes
					int k =i;
					if( k!=N){
						while( (l[k]!=-1||s[k]!=-1)){
								if(s[k]!=-1){
									k = s[k];
								}
								if(l[k]!=-1){
									k = l[k];
								}
								// System.out.print(k+" ");

								visitedForward[k]=true;
								if(point.get(1)+1<movesListForward.get(k)){
									movesListForward.set(k,point.get(1)+1);
									ArrayList<Integer> temp2 = new ArrayList<Integer>();
										temp2.add(k);
										temp2.add(point.get(1)+1);
										q.add(temp2);
									}								
							}
					}
					
				}
			}
		}
		// System.out.println();
		

	}

	public static void main(String args[])throws Exception
	{
		SnakesLadder game = new SnakesLadder("input.txt");
		System.out.println(game.OptimalMoves());
		// System.out.println(game.Query(1, 10));
		// int result[] = game.FindBestNewSnake();
		// System.out.println(result[0] + " " + result[1]);
		// System.out.println(game.Query(54, 50));
		game.FindBestNewSnake();
	}
   
   
}

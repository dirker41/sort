
import java.io.* ;
import java.util.*;
import java.util.concurrent.CountDownLatch;

abstract class G {
        
  static String OUTPUTSTR = "" ;
  
        
  static void Print( String str ) {
    System.out.print( str );
    // OutputCollect( str ) ;
  } // Print()
  
  static void Println( String str ) {
    System.out.println( str );
    // OutputCollect( ( str + "\r\n" ) ) ;
  } // Println()
  
  static void Println() {
    System.out.println();
    // OutputCollect( "\r\n" ) ;
  } // Println()
  
  
  
  static void OutputCollect( String str ) {
        OUTPUTSTR += str ;
  } // OutputCollect()
  
  static void WriteToFile() throws Exception {
        System.out.println( "Enter a file name to write:" ) ;
        InputStreamReader isr = new InputStreamReader( System.in );
        BufferedReader bf = new BufferedReader( isr );
    
        String fileName = bf.readLine();
        isr.close();
        bf.close();
        
        File f = new File( fileName ) ;
        if ( !f.exists() ) f.createNewFile() ;
        FileWriter fp = new FileWriter( f ) ;

        fp.write( OUTPUTSTR ) ;
        fp.flush();
        fp.close();
        
        
        // OUTPUTSTR += str ;
  } // OutputCollect()
  
  
} // Class G

class IntObj {
  public int mNum ;
  
  public IntObj( int num ) {
    mNum = num ;
  } // IntObj()
}


class BubbleSortThread implements Runnable {
   private final CountDownLatch doneSignal;
   private Vector < IntObj > sortList ;
   private int a;
   private int b;
   
   BubbleSortThread( CountDownLatch doneSignal, 
                     int a,
                     int b,
                     Vector < IntObj > sortList ) {
	        
	        
	        
      this.doneSignal = doneSignal;
      this.a = a;
      this.b = b;
      this.sortList = sortList;
   } // BubbleSortThread()
   
   
   
   public void run() {
      try {
        // G.Println( "thread start at :" + a + "\n" + 
        //           "thread end at :" + b ) ;
        
        
        bubbleSort( a,b ) ;
        
        
        
        
        
        doneSignal.countDown();
      } catch ( Exception e ) {} // return;
   }
   
   
  void swap ( int a,int b ) {
        
    
      int temp = sortList.get(a).mNum ;
      sortList.get(a).mNum = sortList.get(b).mNum;
      sortList.get(b).mNum = temp ;
    
  } // swap()
  
  void bubbleSort( int head,int tail ) {
        
        boolean flag = true;
        for ( int j = head ; j < tail ; j ++  ) {
          for( int i = head ; i < head + tail - j ; i ++ ) {
	
	if ( sortList.get(i).mNum > sortList.get( i+1 ).mNum ) {
	        swap( i , i+1 ) ;
	        flag = false;
	} // if
	
          } // for
          
          if (flag == true)
            break;
                        
        } // for
        
        
        
  } // bubbleSort()

   
} //Class BubbleSortThread

class MergeSortThread implements Runnable {
   private final CountDownLatch doneSignal;
   private Vector < IntObj > sortList ;
   private int a;
   private int b;
   
   MergeSortThread( CountDownLatch doneSignal, 
                     int a,
                     int b,
                     Vector < IntObj > sortList ) {
	        
	        
	        
      this.doneSignal = doneSignal;
      this.a = a;
      this.b = b;
      this.sortList = sortList;
   } // BubbleSortThread()
   
   
   
   public void run() {
      try {
        // G.Println( "thread start at :" + a + "\n" + 
           //         "thread end at :" + b ) ;
        
        
         Merge_Sort( a,b ) ;
        
        
        
        
        
        doneSignal.countDown();
      } catch ( Exception e ) {} // return;
   } // run()
   
   
  
  
  void Merge_Sort( int p,int r ) throws Exception {
        if( p < r )  {
	
          int q = (p+r) /2 ;
          
          
          CountDownLatch doneSignal = new CountDownLatch( 2 );
        

          MergeSortThread task1 = new MergeSortThread( doneSignal, p, q , sortList ) ;
          MergeSortThread task2 = new MergeSortThread( doneSignal, q+1, r , sortList ) ;
          new Thread( task1 ).start() ;
          new Thread( task2 ).start() ;
        

        
          doneSignal.await();
          
          
          
          
          Merge( p,q,r) ;
          
        } // if
  } // Merge_Sort()
  
  
  void Merge( int p,int q, int r ) {
        
       int n1 = q-p +1 ;
       int n2 = r-(q+1) +1;
       
       
       // G.Println( "p:" + p + "\nq:" + q + "\nr:" +r ) ;
       
       Vector < IntObj > L = new Vector < IntObj > ();
       Vector < IntObj > R = new Vector < IntObj > ();
       
       
       for( int i = 0 ; i < n1 ; i ++ ) 
         L.add( new IntObj( sortList.get( p+i ).mNum ) ) ;
         
       for( int i = 0 ; i < n2 ; i ++ ) 
         R.add( new IntObj( sortList.get( q+1+i ).mNum ) ) ;
         
         
       L.add( new IntObj( Integer.MAX_VALUE ) ) ;
       R.add( new IntObj( Integer.MAX_VALUE ) ) ;
         
         
       
       /*
       G.Println( "n1:" + n1 + "\nn2:" + n2 ) ;
       
       G.Print( "L:" ) ;
       for( int i = 0 ; i < n1 ; i ++ ) 
         G.Print( L.get(i).mNum + " " ) ;
         
       G.Print( "\nR:" ) ;
       for( int i = 0 ; i < n2 ; i ++ ) 
         G.Print( R.get(i).mNum + " " ) ;
         
       */
       
       int i = 0 ;
       int j = 0 ;
        
       
       for( int k = p ; k <= r ; k ++ ) {
        if(  L.get(i).mNum <= R.get(j).mNum ) {
          sortList.get( k ).mNum = L.get(i).mNum ;
          i ++ ;
        } // if
        
        else {
          
         
          sortList.get( k ).mNum = R.get(j).mNum ;
          j ++ ;
        } // else
        
       } // for 
       
        
  } // Merge()

   
} //Class MergeSortThread




class Main {  // Please do not put 'public' before 'class'

  static String mtext = "" ;
  static Vector < IntObj > sortList = new Vector < IntObj > ();
  static int case1 ;
  
  static void swap ( int a,int b ) {
        
    
      int temp = sortList.get(a).mNum ;
      sortList.get(a).mNum = sortList.get(b).mNum;
      sortList.get(b).mNum = temp ;
    
  } // swap()
  
  static void bubbleSort( int head,int tail ) {
        
        boolean flag = true;
        for ( int j = head ; j < tail ; j ++  ) {
          for( int i = head ; i < head + tail - j ; i ++ ) {
	
	if ( sortList.get(i).mNum > sortList.get( i+1 ).mNum ) {
	        swap( i , i+1 ) ;
	        flag = false;
	} // if
	
          } // for
          
          if (flag == true)
            break;
                        
        } // for
        
        
        
  } // bubbleSort()
  
  
  static void Merge_Sort( int p,int r ) {
        if( p < r )  {
	
          int q = (p+r) /2 ;
          Merge_Sort( p,q ) ;
          Merge_Sort( q+1,r );
          Merge( p,q,r) ;
        } // if
  } // Merge_Sort()
  
  static void Merge_Sort_Thread( int p,int r ) throws Throwable {
        if( p < r )  {
	
          int q = (p+r) /2 ;
          
          CountDownLatch doneSignal = new CountDownLatch( 2 );
        

          MergeSortThread task1 = new MergeSortThread( doneSignal, p, q , sortList ) ;
          MergeSortThread task2 = new MergeSortThread( doneSignal, q+1, r , sortList ) ;
          new Thread( task1 ).start() ;
          new Thread( task2 ).start() ;
        

        
          doneSignal.await();
          Merge( p,q,r) ;
        } // if
  } // Merge_Sort_Thread()
  
  
  static void Merge( int p,int q, int r ) {
        
       int n1 = q-p +1 ;
       int n2 = r-(q+1) +1;
       
       
       // G.Println( "p:" + p + "\nq:" + q + "\nr:" +r ) ;
       
       Vector < IntObj > L = new Vector < IntObj > ();
       Vector < IntObj > R = new Vector < IntObj > ();
       
       
       for( int i = 0 ; i < n1 ; i ++ ) 
         L.add( new IntObj( sortList.get( p+i ).mNum ) ) ;
         
       for( int i = 0 ; i < n2 ; i ++ ) 
         R.add( new IntObj( sortList.get( q+1+i ).mNum ) ) ;
         
         
       L.add( new IntObj( Integer.MAX_VALUE ) ) ;
       R.add( new IntObj( Integer.MAX_VALUE ) ) ;
         
         
       
       /*
       G.Println( "n1:" + n1 + "\nn2:" + n2 ) ;
       
       G.Print( "L:" ) ;
       for( int i = 0 ; i < n1 ; i ++ ) 
         G.Print( L.get(i).mNum + " " ) ;
         
       G.Print( "\nR:" ) ;
       for( int i = 0 ; i < n2 ; i ++ ) 
         G.Print( R.get(i).mNum + " " ) ;
         
       */
       
       int i = 0 ;
       int j = 0 ;
        
       
       for( int k = p ; k <= r ; k ++ ) {
        if(  L.get(i).mNum <= R.get(j).mNum ) {
          sortList.get( k ).mNum = L.get(i).mNum ;
          i ++ ;
        } // if
        
        else {
          
         
          sortList.get( k ).mNum = R.get(j).mNum ;
          j ++ ;
        } // else
        
       } // for 
       
        
  } // Merge()

  public static boolean GetInput() throws Exception {
    InputStreamReader isr = new InputStreamReader( System.in );
    BufferedReader bf = new BufferedReader( isr );
    String fileName = "" ;
    
    System.out.println( "Enter a file name to read:" ) ;
    fileName = bf.readLine();
    // fileName = "input_1.txt";
    
    
    File file = new File( fileName ) ;
    
    if ( !file.exists() ) {
        G.Println( fileName + "not found!" ) ;
        return false ;
    } // if
    
    FileInputStream FIS = new FileInputStream( file ) ;
    
    DataInputStream DIS = new DataInputStream ( FIS );
    
    try {
        String line = "" ;
        line = DIS.readLine();
        
        try {
	case1 = Integer.parseInt( line ) ;

              } catch (Exception e) { //try catch
                
                G.Println( "there is an error :\"" + line + "\"" ) ;
                System.exit( 0 ) ;
              } // catch
        
        
        
        
        
        
        while( line != null ) {
          line = DIS.readLine();
          
          if ( line != null ) {
            // G.Println( line + "" ) ;
            
            // get int
            while( line.trim().length() > 0 ) {
              line = line.trim() ; 
              
              String intstr = "" ;
              if ( line.indexOf( " " ) != -1 ) {
                intstr = line.substring( 0 , line.indexOf(" ") ) ;
                
                line = line.substring( line.indexOf(" ") , line.length() ) ;
              } // if
              else {
	intstr = line.trim() ;
	line = "";
              } // else
              
              try {
	int num = Integer.parseInt( intstr ) ;
	sortList.add( new IntObj( num ) ) ;
	
	
	// G.Println( num + "" );
              } catch (Exception e) { //try catch
                
                G.Println( "there is an error :\"" + intstr + "\"" ) ;
                System.exit( 0 ) ;
              } // catch
              
              
              
              
            } // while
          }
        } // while
        
    } catch ( Exception e ) {
    } // catch()
    
    DIS.close();
    FIS.close();
    
    return true ;
    
  } // GetInput()

  
  public static void main( String[] args ) throws Throwable {
    
    
    
    InputStreamReader isr = new InputStreamReader( System.in );
    BufferedReader bf = new BufferedReader( isr );
    
    System.out.println( "Enter k to decide how many part:" ) ;
    String kstr = "" ;
    kstr = bf.readLine();
    // kstr = "" + 100 ;
    
    int k = Integer.parseInt( kstr ) ; // how many part 
    
    // G.Println( k + "" ) ;
    
    GetInput(); 
    
    int N = sortList.size() ; // data size
    
    int extra = N%k ; // N/K maybe have Remainder ¡A 
    // so we need an "extra" to allocate extra data to N%k process 
    // it means N%k process should deal n/k + 1 datas ;
    
    
    System.out.println( "Enter a file name to write:" ) ;
    InputStreamReader isr1 = new InputStreamReader( System.in );
    BufferedReader bf1 = new BufferedReader( isr1 );
    
    String fileName = bf1.readLine();
    isr1.close();
    bf1.close();
        
    File f = new File( fileName ) ;
    if ( !f.exists() ) f.createNewFile() ;
    FileWriter fp = new FileWriter( f ) ;
    
    
    long startTime = System.currentTimeMillis(); 
    
    
    
    if( case1 == 1 )
      bubbleSort( 0 , sortList.size()-1 ) ;
    else if ( case1 == 2 ) {
        CountDownLatch doneSignal = new CountDownLatch( k );
        
        for (int i = 0; i < k; i++ ) { // create and start threads
          int begin = 0 ;
          int end = 0 ;
          
          
          if ( i < extra ) begin = ((N/k)+1)*i ; 
          else begin = ((N/k)+1)*extra + ( i - extra )*(N/k) ;
          if ( i < extra ) end = begin+ (N/k)  ;
          else end = begin + (N/k) -1;
          
          
          BubbleSortThread task = new BubbleSortThread( doneSignal, begin, end , sortList ) ;
          new Thread( task ).start() ;
          
        } // for
        
        
        long processTime = System.currentTimeMillis() - startTime; 
        G.Println( "spend " + ( ( float ) processTime / 1000 ) + " second" ) ;
        doneSignal.await();
        processTime = System.currentTimeMillis() - startTime; 
        G.Println( "spend " + ( ( float ) processTime / 1000 ) + " second" ) ;
        
        
        // System.exit(0);
        
        for( int i = 1 ; i < k ; i ++ ) {
          int begin = 0 ;
          int end = 0 ;
          
          
          if ( i < extra ) begin = ((N/k)+1)*i ; 
          else begin = ((N/k)+1)*extra + ( i - extra )*(N/k) ;
          if ( i < extra ) end = begin+ (N/k)  ;
          else end = begin + (N/k) -1;
          
          
          // G.Println( "end:" + end ) ;
          
          Merge( 0,begin-1,end ) ;
        } // for
        
    } 
    else if ( case1 == 3 ) {
        
        
        Merge_Sort( 0 , sortList.size()-1 ) ;
        
    } 
    else if ( case1 == 4 ) { 
        for (int i = 0; i < k; i++ ) { // create and start threads
          int begin = 0 ;
          int end = 0 ;
          
          
          if ( i < extra ) begin = ((N/k)+1)*i ; 
          else begin = ((N/k)+1)*extra + ( i - extra )*(N/k) ;
          if ( i < extra ) end = begin+ (N/k)  ;
          else end = begin + (N/k) -1;
          
          
          bubbleSort( begin,end ) ;
          
        } // for
        
        for( int i = 1 ; i < k ; i ++ ) {
          int begin = 0 ;
          int end = 0 ;
          
          
          if ( i < extra ) begin = ((N/k)+1)*i ; 
          else begin = ((N/k)+1)*extra + ( i - extra )*(N/k) ;
          if ( i < extra ) end = begin+ (N/k)  ;
          else end = begin + (N/k) -1;
          
          
          // G.Println( "end:" + end ) ;
          
          Merge( 0,begin-1,end ) ;
        } // for
        
    } 
    else {
        G.Println( "case error:\"" + case1 + "\"" ) ;
    } // else
    
    
    
    

    
    
        
    
    
    long processTime = System.currentTimeMillis() - startTime; 
    
    for( int i = 0 ; i < sortList.size() ; i++ ) {
        G.Print( sortList.get(i).mNum + " " ) ;
        fp.write( sortList.get(i).mNum + " " ) ;
        // G.OutputCollect( sortList.get(i).mNum + " " ) ;
        
        if ( i != 0 && i%15 == 0 ) {
	G.Println(""); 
	fp.write( "\r\n" ) ;
        } // if
    } // for
    
    G.Println(""); 
    
    G.Println( "list size = "+ sortList.size() ) ;
    
    
    
    
    G.Println( "spend " + ( ( float ) processTime / 1000 ) + " second" ) ;
    fp.write( "\r\nspend " + ( ( float ) processTime / 1000 ) + " second" ) ;
    
    
    fp.flush();
    fp.close();
    
    
    
    // t1.join() ;
    // t2.join() ;
    
    // G.WriteToFile();
    
    
    
    
  } // main()
  
  
  
} // class Main
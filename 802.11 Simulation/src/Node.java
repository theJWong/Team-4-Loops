public class Node
{
   public static final String IDLE = "idle";
   public static final String BUSY = "busy";
   
   private String ID;
   private String status;
   
   public Node(String id)
   {
      ID = id;
      status = IDLE;
   }
   
   public boolean send(String ID, Message m)
   {
      System.out.print("[" + this.ID + "]: ");
      if (m.getMessage().equals(Message.RTS))
      {
         System.out.println("Sending RTS to Node " + ID);
      }
      else if (m.getMessage().equals(Message.CTS))
      {
         System.out.println("Sending CTS to Node " + ID);
      }
      else if (m.getMessage().equals(Message.DATA))
      {
         System.out.println("Sending DATA to Node " + ID);
      }
      else
      {
         System.out.println(m.getMessage());
      }
      
      return true;
   }
   
   public boolean receive(String ID, Message m)
   {
      if (this.ID.equals(ID) && status.equals(IDLE))
      {
         System.out.print("[" + this.ID + "]: ");
         if (m.getMessage().equals(Message.RTS))
         {
            System.out.println("Receiving RTS from Node " + ID);
         }
         else if (m.getMessage().equals(Message.CTS))
         {
            System.out.println("Receiving CTS from Node " + ID);
         }
         else if (m.getMessage().equals(Message.DATA))
         {
            System.out.println("Receiving DATA from Node " + ID);
         }
         else
         {
            System.out.println(m.getMessage());
         }
      }
      else if(status.equals(BUSY))
      {
         System.out.println("[" + this.ID + "]: I am busy!");
      }
      else
      {
         System.out.println("[" + this.ID + "]: I am not " + ID);
      }
      
      return true;
   }
   
   /* expected backoff takes in number of collisions.
    * returns a time represented as a double
    */
   public double backoff(int collision)
   {
       
       return (Math.pow(2, collision) - 1) / 2; //http://en.wikipedia.org/wiki/Exponential_backoff
 
   }
   
   public String getStatus()
   {
      return status;
   }
   
   public String getID()
   {
      return ID;
   }
}
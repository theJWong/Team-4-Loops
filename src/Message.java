public class Message
{
   protected static final String RTS = "rts";
   protected static final String CTS = "cts";
   protected static final String DATA = "data";
   
   private String message;
   
   public Message(String m)
   {
      message = m;
   }
   
   public String getMessage()
   {
      return message;
   }
}
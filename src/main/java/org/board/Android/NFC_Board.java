package org.board.Android;
import java.nio.ByteBuffer;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

public class NFC_Board {
    private static final String UNKNOWN_CMD_SW = "0000";
    private static final String SELECT_OK_SW = "9000";
    
    public String run() {
        CardTerminal terminal;
        String UID;

            try {
                terminal = InitializeTerminal();
                
                if(IsCardPresent(terminal)) {                                   // ������ ���� ī��(�ڵ���)�� ���� ���
                    UID = GetUID(terminal);

                    if(UID.length()==12)
                    return UID;
                }
                
                Thread.sleep(2000);
                
            } catch (CardException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "0";
        }
    
    public CardTerminal InitializeTerminal() throws CardException { 
        // Get terminal 
        System.out.println("Searching for terminals..."); 
        CardTerminal terminal = null; 
        TerminalFactory factory = TerminalFactory.getDefault(); 
        List<CardTerminal> terminals = factory.terminals().list(); 
      
        //Print list of terminals 
        for(CardTerminal ter:terminals) { 
            System.out.println("Found: "  +ter.getName().toString()); 
            terminal = terminals.get(0);// We assume just one is connected 
        } 
     
        return terminal; 
    }
    
    public boolean IsCardPresent(CardTerminal terminal) throws CardException {
        System.out.println("Waiting for card...");
        
        boolean isCard = false;
        
       
            isCard = terminal.waitForCardPresent(3000);
            if(isCard)
                System.out.println("Card was found! :-)");
        
        return isCard;
    }
    
    public String GetUID(CardTerminal terminal) throws CardException {
        Card card = terminal.connect("*");
        CardChannel channel = card.getBasicChannel();
        
        byte[] baReadUID = new byte[5];
        baReadUID = new byte[]{(byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        
        // tag�� uid (unique ID)�� ���� �� ���
        System.out.println("UID : " + SendCommand(baReadUID, channel));
        
        return SendCommand(baReadUID, channel);
    }
    
    
    public static String SendCommand(byte[] cmd, CardChannel channel) { 
        String response = "";
        byte[] baResp = new byte[258];
         
        ByteBuffer bufCmd = ByteBuffer.wrap(cmd);
        ByteBuffer bufResp = ByteBuffer.wrap(baResp);
         
        int output = 0; 
         
        try { 
            output = channel.transmit(bufCmd, bufResp); 
        } catch(CardException ex){ 
            ex.printStackTrace(); 
        } 
        System.out.println(output);

        for (int i = 0; i < output; i++) {
            response += String.format("%02X", baResp[i]); 
        }
          
        return response;  
    }
}

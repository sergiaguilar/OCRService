package com.everis.ticket;

import com.everis.finereadercontrol.interfaces.ITicketControl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
//@RequestMapping("/secure/ocr/")
public class TicketController {

    @Autowired
    private ITicketControl iTicketControl;

    private static String DOWNLOAD_FOLDER = "C:\\Temp\\Salida\\";

    @RequestMapping(value = "/ticketInfo/{ticketId}", method = RequestMethod.GET)
    public String getTicketInfo(@PathVariable("ticketId") String ticket) {
        String state = iTicketControl.getTicketInfo(ticket);
        if (state.equals("Process finished")){
            String error = iTicketControl.getTicketError(ticket);
            return state + ". " + error + ".";
        }
        else return state + ".";
    }

    @RequestMapping(value = "/getDownload/{ticketId}", method = RequestMethod.GET)
    public String getDownload(@PathVariable("ticketId") String ticket, HttpServletResponse response) throws IOException {
        String result = iTicketControl.getTicketInfo(ticket);
        if (result.equals("Ticket doesn't exists!")) return result;
        else if(result.equals("Downloaded")) return "The file has been downloaded previously.";
        else if (result.equals("Process finished")) {
            String error = iTicketControl.getTicketError(ticket);
            System.out.println(error);

                if (!error.startsWith("Error")) {
                    File initial = new File(DOWNLOAD_FOLDER + ticket + ".txt");
                    InputStream myStream = new FileInputStream(initial);

                    response.addHeader("Content-disposition", "attachment;filename="+ ticket +".txt");
                    response.setContentType("txt/plain");

                    IOUtils.copy(myStream, response.getOutputStream());
                    response.flushBuffer();

                    myStream.close();
                    removeFile(ticket);
                    iTicketControl.setDownloaded(ticket);
                    return "Download done.";
                }
                else return "Can't download: " + error;
        }
        else return "The process has not finished!.";

    }

    public void removeFile(String ticket) {
        File fichero = new File(DOWNLOAD_FOLDER + ticket + ".txt");
        fichero.delete();
    }
}

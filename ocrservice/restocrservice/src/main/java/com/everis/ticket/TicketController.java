package com.everis.ticket;

import com.everis.finereadercontrol.interfaces.IFineReaderControl;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class TicketController {

    @Autowired
    private IFineReaderControl iFineReaderControl;

    private static String DOWNLOAD_FOLDER = "C:\\Temp\\Salida";

    @PostMapping(value = "/ticketInfo")
    public String getTicketInfo(@RequestParam("ticket") String ticket) {
        String state = iFineReaderControl.getTicketInfo(ticket);
        if (state.equals("Process finished")){
            String error = iFineReaderControl.getTicketError(ticket);
            return state + ". " + error + ".";
        }
        else return state + ".";
    }

    @RequestMapping(value = "/getDownload", method = RequestMethod.POST)
    public String getDownload(@RequestParam("ticket") String ticket, HttpServletResponse response) throws IOException {
        String result = iFineReaderControl.getTicketInfo(ticket);
        String error = iFineReaderControl.getTicketError(ticket);
        System.out.println(error);
        if(result.equals("Process finished")) {
            if(!error.startsWith("Error")) {
                File initial = new File(DOWNLOAD_FOLDER + ticket + ".txt");
                InputStream myStream = new FileInputStream(initial);

                response.addHeader("Content-disposition", "attachment;filename=descarga.txt");
                response.setContentType("txt/plain");

                IOUtils.copy(myStream,response.getOutputStream());
                response.flushBuffer();
                removeFile(ticket);
                return "Download done.";
            }
            else return "Can't download: " + error;

        }
        else if (result.equals("Ticket doesn't exists!")) return result;
        else return "The process has not finished!.";

    }

    public void removeFile(String ticket) {
        File fichero = new File(DOWNLOAD_FOLDER + ticket + ".txt");

        fichero.delete();
    }
}

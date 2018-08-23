package com.everis.ocrservice;

import com.everis.autorizationauthentication.model.Users;
import com.everis.billing.model.License;
import com.everis.businesslogic.interfaces.IProductControl;
import com.everis.facturationcontrol.interfaces.IFacturationControl;
import com.everis.finereadercontrol.interfaces.ITicketControl;
import com.everis.tokenuser.TokenUser;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
@ComponentScan({"com.everis.*"})
@RequestMapping("/secure/ocr/")
public class OCRController {

    private static String UPLOADED_FOLDER = "Z:\\ocr\\entrada\\";

    private final Logger logger = LoggerFactory.getLogger(OCRController.class);


    @Autowired
    private QueueClient queueClientForSending;

    @Autowired
    private IProductControl iProductControl;

    private TokenUser tokenUser = new TokenUser();

    @Autowired
    private ITicketControl iTicketControl;

    @Autowired
    private IFacturationControl iFacturationControl;

    @PostMapping(value = "/fineReader")
    public ResponseEntity<?> uploadFileAbby(@RequestParam("file")MultipartFile uploadFile, @RequestParam("precision") Integer precision, HttpServletRequest request)  {
        String idProduct = "ABBYYFR";
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        Users users = tokenUser.getUser(token);

        logger.debug("Single file upload!");
        if(uploadFile.isEmpty()) {
            return new ResponseEntity("Please select a file!!!!", HttpStatus.BAD_REQUEST);
        }

        if (precision < 1 || precision > 3) return new ResponseEntity("Precision must be a number between 1 and 3!", HttpStatus.BAD_REQUEST);

        if(iProductControl.userCanUseProduct(idProduct, users)) {
            License license = iFacturationControl.getLicense(idProduct);

            if (license != null) {
                try {
                    String ticket = generateTicket();
                    iTicketControl.newTicket(ticket);

                    saveUploadedeFiles(uploadFile, ticket);

                    String extension = uploadFile.getOriginalFilename();
                    Integer intaux = extension.indexOf('.');
                    extension = extension.substring(intaux, extension.length());
                    run(precision,token, ticket, extension, "ABBYFR");
                    return new ResponseEntity("Enviado correctamente! Este es su número de ticket: " + ticket,new HttpHeaders(),HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return new ResponseEntity("This product doesn't have any license associated!!!", HttpStatus.OK);
            }
        }
        return new ResponseEntity("User can't use this product!!", HttpStatus.OK);


    }

    @PostMapping(value = "/tesseract")
    public ResponseEntity<?> uploadFileTesseract(@RequestParam("file")MultipartFile uploadFile, @RequestParam("precision") Integer precision, HttpServletRequest request)  {
        String idProduct = "tesseract";
        String token = request.getHeader("Authorization");
        Users users = tokenUser.getUser(token);

        logger.debug("Single file upload!");
        if(uploadFile.isEmpty()) {
            return new ResponseEntity("Please select a file!!!!", HttpStatus.BAD_REQUEST);
        }

        if (precision < 1 || precision > 3) return new ResponseEntity("Precision must be a number between 1 and 3!", HttpStatus.BAD_REQUEST);

        if(iProductControl.userCanUseProduct(idProduct, users)) {
            License license = iFacturationControl.getLicense(idProduct);

            if (license != null) {
                try {
                    String ticket = generateTicket();
                    iTicketControl.newTicket(ticket);

                    saveUploadedeFiles(uploadFile, ticket);

                    String extension = uploadFile.getOriginalFilename();
                    Integer intaux = extension.indexOf('.');
                    extension = extension.substring(intaux, extension.length());
                    run(precision,token, ticket, extension, "tesseract");
                    return new ResponseEntity("Enviado correctamente! Este es su número de ticket: " + ticket,new HttpHeaders(),HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return new ResponseEntity("This product doesn't have any license associated!!!", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("User can't use this product!!", HttpStatus.BAD_REQUEST);


    }

    public String generateTicket() {
        char[] caracteres;
        caracteres = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Integer repet = 8;
        String pass = "";
        for (int i = 0; i < repet; i++) {
            pass += caracteres[new Random().nextInt(62)];
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
        String dateAsString = simpleDateFormat.format(new Date());
        return pass + dateAsString;
    }

    private void saveUploadedeFiles(MultipartFile file, String ticket) throws IOException {
        if(!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String aux = file.getOriginalFilename();
            Integer intaux = aux.indexOf('.');
            aux = aux.substring(intaux, aux.length());
            Path path = Paths.get(UPLOADED_FOLDER + ticket + aux);
            Files.write(path, bytes);

        }

    }



    public void run(Integer precision, String token, String ticket, String extension, String ocr) throws Exception {
        System.out.println("Sending message...");
        StringBuilder message = new StringBuilder(precision.toString());
        message.append("|").append(token);
        message.append("|").append(ticket);
        message.append("|").append(extension);
        message.append("|").append(ocr);

        try {
            sendQueueMessage(message.toString());
        } catch (ServiceBusException e) {
            System.out.println("Error processing messages: "+ e);
        } catch (InterruptedException e) {
            System.out.println("Error processing messages: " + e);
        }
    }

    private void sendQueueMessage(String ms) throws ServiceBusException, InterruptedException {
        final Message message = new Message(
                ms.getBytes(StandardCharsets.UTF_8));
        queueClientForSending.send(message);
    }

}

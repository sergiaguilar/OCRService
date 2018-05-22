package com.everis.ocrservice;

import com.everis.autorizationauthentication.model.Users;
import com.everis.billing.model.License;
import com.everis.businesslogic.interfaces.IProductControl;
import com.everis.facturationcontrol.interfaces.IFacturationControl;
import com.everis.finereadercontrol.interfaces.IFineReaderControl;
import com.everis.rabbitmq.Runner;
import com.everis.tokenuser.TokenUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/secure/ocr/Finereader")
public class FineReaderController {

    private static String UPLOADED_FOLDER = "C:\\Temp\\Entrada\\";

    private final Logger logger = LoggerFactory.getLogger(FineReaderController.class);

    String idProduct = "ABBYYFR";

    @Autowired
    private IProductControl iProductControl;

    private TokenUser tokenUser = new TokenUser();

    @Autowired
    private IFineReaderControl iFineReaderControl;

    @Autowired
    private IFacturationControl iFacturationControl;

    @Autowired
    private Runner runner;

    public FineReaderController() {

    }

    @PostMapping(value = "/")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile uploadFile, @RequestParam("precision") Integer precision, HttpServletRequest request)  {

        String token = request.getHeader("Authorization");
        token = token.substring(7);
        Users users = tokenUser.getUser(token);

        logger.debug("Single file upload!");
        if(uploadFile.isEmpty()) {
            return new ResponseEntity("Please select a file!!!!", HttpStatus.OK);
        }

        if (precision < 1 || precision > 3) return new ResponseEntity("Precision must be a number between 1 and 3!", HttpStatus.OK);

        if(iProductControl.userCanUseProduct(idProduct, users)) {
            License license = iFacturationControl.getLicense(idProduct);

            if (license != null) {
                try {
                    String ticket = generateTicket();
                    iFineReaderControl.newTicket(ticket);
                    try {
                        saveUploadedeFiles(uploadFile, ticket);
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    String extension = uploadFile.getOriginalFilename();
                    Integer intaux = extension.indexOf('.');
                    extension = extension.substring(intaux, extension.length());
                    runner.run(precision,token, ticket, extension);
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

}

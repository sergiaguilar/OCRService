package com.everis.userservice;

import com.everis.businesslogic.interfaces.IUserControl;
import com.everis.facturationcontrol.interfaces.IFacturationControl;
import com.everis.tokenuser.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/secure/user")
public class UserController {

    @Autowired
    private IUserControl iUserControl;

    @Autowired
    private IFacturationControl iFacturationControl;

    private TokenUser tokenUser = new TokenUser();

}

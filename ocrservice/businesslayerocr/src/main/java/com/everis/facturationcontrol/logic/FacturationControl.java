package com.everis.facturationcontrol.logic;

import com.everis.autorizationauthentication.model.Products;
import com.everis.autorizationauthentication.model.Users;
import com.everis.autorizationauthentication.repository.ProductsRepository;
import com.everis.autorizationauthentication.repository.UsersRepository;
import com.everis.billing.model.Facturation;
import com.everis.billing.model.FacturationComposed;
import com.everis.billing.model.License;
import com.everis.billing.repository.FacturationRepository;
import com.everis.billing.repository.LicenseRepository;
import com.everis.facturationcontrol.interfaces.IFacturationControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@EntityScan({"com.everis.*"})
public class FacturationControl implements IFacturationControl{

    @Autowired
    private FacturationRepository facturationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    public String newFacturation(Timestamp timestamp, Users users, License license, Integer numPags) {
        String idCompany = users.getIdCompany();
        String idLicense = license.getIdLicense();
        FacturationComposed facturationComposed = new FacturationComposed(timestamp, idCompany, idLicense);
        Facturation facturation = new Facturation(facturationComposed,numPags, license, users);

        facturationRepository.save(facturation);

        return "Páginas añadidas a la factura.";
    }

    public License getLicense(String idProduct) {
        List<License> licenses = licenseRepository.findAll();
        for(int i = 0; i < licenses.size(); ++i) {
            if( licenses.get(i).getIdProduct().getIdProduct().equals(idProduct)) {
                return licenses.get(i);
            }
        }
        return null;
    }

    public void addPages(License license, Integer numPags) {
        Integer aux = license.getActualVolume();
        license.setActualVolume(aux + numPags);

    }

    public Integer getActualVolume(String idLicense) {
        return licenseRepository.findById(idLicense).get().getActualVolume();
    }

    public Integer getVolume(String idLicense) {
        return licenseRepository.findById(idLicense).get().getVolume();
    }


}

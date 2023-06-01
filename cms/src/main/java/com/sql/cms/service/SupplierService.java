package com.sql.cms.service;

import java.util.Map;

public interface SupplierService {

    Map<String, String> supplierLogin(String id, String password);

    Map<String, String> supplierRegister(String id, String password, String confirmedPassword);

}

package com.ar.unnoba.congresos.Controller;
import com.ar.unnoba.congresos.Service.IOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class OrganizadorController {
    @Autowired
    private IOrganizadorService organizadorService;

}

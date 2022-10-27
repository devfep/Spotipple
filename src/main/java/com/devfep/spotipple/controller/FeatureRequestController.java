package com.devfep.spotipple.controller;

import com.devfep.spotipple.ui.model.request.FeatureRequestModel;
import com.devfep.spotipple.service.Impl.FeatureRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FeatureRequestController {

    //    @Autowired wont work at field level
//    because it is final so use constructor based DI
    private final FeatureRequestServiceImpl featureRequestServiceImpl;

    @Autowired
    public FeatureRequestController(FeatureRequestServiceImpl featureRequestServiceImpl) {
        this.featureRequestServiceImpl = featureRequestServiceImpl;
    }

    @RequestMapping("/feature-request")
    public String displayFeatureRequestPage() {
        return "feature-request";
    }

//    ModelAndView allows you to send data and view to the UI
//with FeatureRequestServiceImpl built out, it can be autowired via the controller
//here in order to transfer pojo object to it


    @PostMapping("/saveRequest")
    public ModelAndView saveRequest(FeatureRequestModel featureRequestModel) {
        featureRequestServiceImpl.SaveFeatureRequest(featureRequestModel);
        return new ModelAndView("redirect:/thank-you");
    }
}

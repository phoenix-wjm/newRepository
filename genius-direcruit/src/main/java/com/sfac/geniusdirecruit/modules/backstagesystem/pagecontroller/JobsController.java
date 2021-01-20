package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/jobs")
public class JobsController {
    @Autowired
    private JobService jobService;

    @RequestMapping("/jobsPage")
    public String jobsPage(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/job/jobs");
        return "common/managerIndex";
    }
}

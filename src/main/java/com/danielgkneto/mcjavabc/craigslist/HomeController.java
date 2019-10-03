package com.danielgkneto.mcjavabc.craigslist;

import com.danielgkneto.mcjavabc.craigslist.Job;
import com.danielgkneto.mcjavabc.craigslist.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/")
    public String jobList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }
    @GetMapping("/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/processjob")
    public String saveJob(@ModelAttribute Job job /*, @RequestParam(name = "postedDate")
            String postedDate */){
/*
        String pattern = "yyyy-MM-dd";
        System.out.println("before - " + postedDate);
        try {
//            String formattedDate = date.substring(1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date realDate = simpleDateFormat.parse(postedDate);
            System.out.println("after - " + realDate);
            job.setPostedDate(realDate);
        }
        catch (java.text.ParseException e){
            e.printStackTrace();
        }
*/

        jobRepository.save(job);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model){
        model.addAttribute("job", jobRepository.findById(id).get());
        return "jobform";
    }

    @RequestMapping("/delete/{id}")
    public String delJob(@PathVariable("id") long id){
        jobRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processsearch")
    public String searchResult(Model model,@RequestParam(name="search") String search) {
        model.addAttribute("jobs", jobRepository.findByTitleContainingIgnoreCase(search));
        return "index";
    }
}

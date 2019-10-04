package com.danielgkneto.mcjavabc.craigslist;

import com.danielgkneto.mcjavabc.craigslist.Job;
import com.danielgkneto.mcjavabc.craigslist.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    JobRepository jobRepository;

    @RequestMapping("/f")
    public String fillTable() {
        jobRepository.save(new Job("Java Developer", "A Java developer is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "Daniel", "(111) 111-1111"));
        jobRepository.save(new Job("Network Technician", "A Network Technician is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "Daniel", "(111) 111-1111"));
        jobRepository.save(new Job("C# Developer", "A C# developer is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "Someone Else", "(333) 333-3333"));
        jobRepository.save(new Job("Project Manager", "A Project Manager is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "Someone Else", "(333) 333-3333"));
        jobRepository.save(new Job("Babysitter", "A Babysitter is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "Super Nanny", "(888) 888-8888"));
        jobRepository.save(new Job("Cooker", "A Cooker is responsible for many duties throughout the development lifecycle of applications, from concept and design right through to testing.", "James Oliver", "(999) 999-9999"));

        return "redirect:/";
    }

    @RequestMapping("/")
    public String jobList(Model model){
        model.addAttribute("jobs", jobRepository.findAll());
        return "index";
    }

    @RequestMapping("/docs")
    public String docs(Model model){
        return "docs";
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
        String[] keywords = search.split(" ");
        ArrayList<Job> jobs = new ArrayList<Job>();

        for (int i = 0; i < keywords.length; i++) {
            jobs.addAll(jobRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keywords[i], keywords[i]));
        }
        model.addAttribute("jobs", jobs);
        return "index";
    }
}

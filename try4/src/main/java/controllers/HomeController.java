package controllers;

import models.data.IngredientRepository;
import models.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

        @Autowired
        private RecipeRepository reciperepository;
        @Autowired
        private IngredientRepository ingredientRepository;

        @RequestMapping("")
        public String index(Model model) {

            model.addAttribute("title", "My Jobs");
            model.addAttribute("jobs", jobRepository.findAll());


            return "index";
        }

        @GetMapping("add")
        public String displayAddJobForm(Model model) {
            model.addAttribute("title", "Add Job");
            model.addAttribute(new Job());
            model.addAttribute("employer", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());

            return "add";

        }
        @PostMapping("add")
        public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                        Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

            if (errors.hasErrors()) {
                model.addAttribute("title", "Add Job");
                return "add";
            }

            Employer newEmployer = employerRepository.findById(employerId).orElse(new Employer());
            newJob.setEmployer(newEmployer);
            List<Skill> skillsObjs = (List<Skill>) skillRepository.findAllById(skills);
            newJob.setSkills(skillsObjs);
            jobRepository.save(newJob);
            model.addAttribute("job", jobRepository.findAll());


            return "redirect:";
        }

        @GetMapping("view/{jobId}")
        public String displayViewJob(Model model, @PathVariable int jobId) {

            return "view";
        }


    }



package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.net.URI;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    // using as -- http://localhost:8900/rest/meals/between?startDate=2015-05-30&endDate=2015-05-30&startTime=11:00&endTime=21:00
    public List<MealWithExceed>  getByMail(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") String startDate,
            @RequestParam(value = "endDate",   required = false) @DateTimeFormat(pattern="yyyy-MM-dd") String endDate,
            @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern="HH:mm:ss")   String startTime,
            @RequestParam(value = "endTime",   required = false) @DateTimeFormat(pattern="HH:mm:ss")   String endTime) {

        if (startDate == null)
            startDate = DateTimeUtil.toString(MIN_DATE);
        if (endDate == null)
            endDate = DateTimeUtil.toString(MAX_DATE);
        if (startTime == null)
            startTime = DateTimeUtil.toString(MIN_TIME);
        if (endTime == null)
            endTime = DateTimeUtil.toString(MAX_TIME);

        return super.getBetween(parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate),parseLocalTime(endTime));
    }

}
package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.goal.GoalService;
import com.ourjupiter.springboot.web.dto.GoalRequestDto;
import com.ourjupiter.springboot.web.dto.RoutineCreateRequestDto;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class GoalController {
    private final GoalService goalService;

    @CrossOrigin("*")
    @PostMapping("/routine")
    public String createRoutine(@RequestHeader("x-access-token") String token,
                                @RequestBody RoutineCreateRequestDto routineCreateRequestDto) {

        return goalService.createRoutine(token, routineCreateRequestDto);
    }

    @CrossOrigin("*")
    @GetMapping("/routine/{groupId}")
    public LocalDate hasRoutine(@PathVariable Long groupId) {

        return goalService.hasRoutine(groupId);
    }

    @CrossOrigin("*")
    @GetMapping("/goal/{groupId}")
    public Map<String, String> getGoalPenalty(@RequestHeader("x-access-token") String token,
                                              @PathVariable Long groupId) {

        return goalService.getGoalPenalty(token, groupId);
    }

    @CrossOrigin("*")
    @GetMapping("/goalList/{groupId}")
    public List<Pair<String, String>> getGoalList(@RequestHeader("x-access-token") String token,
                                                @PathVariable Long groupId) {

        return goalService.getGoalList(token, groupId);
    }

    @CrossOrigin("*")
    @PostMapping("/goal/{groupId}")
    public String setGoalPenalty(@RequestHeader("x-access-token") String token,
                                 @RequestBody GoalRequestDto goalRequestDto,
                                 @PathVariable Long groupId) {

        return goalService.setGoalPenalty(token, goalRequestDto, groupId);
    }
}

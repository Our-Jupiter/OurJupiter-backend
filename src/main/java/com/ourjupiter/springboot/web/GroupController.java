package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.goal.GoalService;
import com.ourjupiter.springboot.service.group.GroupService;
import com.ourjupiter.springboot.web.dto.*;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GroupController {
    private final GroupService groupService;
    private final GoalService goalService;

    @CrossOrigin("*")
    @GetMapping("/group")
    public List<Pair<Long, String>> getGroup(@RequestHeader("x-access-token") String token) {

        return groupService.getGroup(token);
    }

    @CrossOrigin("*")
    @GetMapping("/group/{id}")
    public String getOwnerEmail(@PathVariable Long id) {

        return groupService.getOwnerEmail(id);
    }

    @CrossOrigin("*")
    @PostMapping("/group")
    public String createGroup(@RequestHeader("x-access-token") String token,
                              @RequestBody GroupCreateRequestDto requestDto) {

        return groupService.createGroup(token, requestDto);
    }

    @CrossOrigin("*")
    @PutMapping("/group/{id}")
    public String update(@PathVariable Long id,
                         @RequestHeader("x-access-token") String token,
                         @RequestBody GroupUpdateRequestDto requestDto) {

        return groupService.updateGroup(id, token, requestDto);
    }

    @CrossOrigin("*")
    @DeleteMapping("/group/{id}")
    public String delete(@PathVariable Long id,
                         @RequestHeader("x-access-token") String token) {

        goalService.deleteGoal(id, token);
        return groupService.deleteGroup(id, token);
    }
}

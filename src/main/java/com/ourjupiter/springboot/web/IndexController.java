package com.ourjupiter.springboot.web;

import com.ourjupiter.springboot.service.posts.FileService;
import com.ourjupiter.springboot.service.posts.PostsService;
import com.ourjupiter.springboot.web.dto.FileDto;
import com.ourjupiter.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final FileService fileService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/detail/{id}")
    public String postsDetail(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        FileDto fileDto = fileService.getFile(dto.getFileId());

        model.addAttribute("post", dto);
        model.addAttribute("file",fileDto);

        System.out.println(fileDto);

        return "posts-detail";
    }
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);

        model.addAttribute("post", dto);

        return "posts-update";

    }
}
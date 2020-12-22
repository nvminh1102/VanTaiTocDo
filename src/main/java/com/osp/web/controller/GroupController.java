package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.User;
import com.osp.model.view.AuthorityView;
import com.osp.model.view.GroupView;
import com.osp.web.dao.GroupAuthorityDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
@Controller
@RequestMapping("/system/group")
public class GroupController {

    private Logger logger = LogManager.getLogger(GroupController.class);
    @Autowired
    GroupAuthorityDAO groupService;

    @GetMapping("/list")
    @Secured(ConstantAuthor.Group.view)
    public String list(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "filterName", required = false, defaultValue = "") String filterName) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page = groupService.page(Utils.trim(filterName), page).orElse(new PagingResult());
        model.addAttribute("page", page);
        model.addAttribute("filterName", filterName);
        return "group.list";
    }

    @GetMapping("/add")
    @Secured(ConstantAuthor.Group.add)
    public String groupAdd(Model model) {
        List<Authority> items = groupService.loadAllAuthority().orElse(new ArrayList<>());
        if (items == null && items.size() == 0) {
            return "404";
        }
        loadAuthorityToModel(model, items);
        GroupView item = new GroupView();
        model.addAttribute("item", item);
        return "group.add";
    }

    public void loadAuthorityToModel(Model model, List<Authority> items) {
        List<AuthorityView> list = new ArrayList<>();
        List<Authority> childrens = new ArrayList<>();
        for (Authority item : items) {
            if (item.getFid() == 0) {
                AuthorityView au = new AuthorityView();
                au.setParent(item);
                list.add(au);
            }
        }

        for (AuthorityView item : list) {
            childrens = new ArrayList<>();
            for (Authority authority : items) {
                if (authority.getFid() == item.getParent().getId()) {
                    childrens.add(authority);
                }
            }
            item.setChildrens(childrens);
        }
        model.addAttribute("groups", list);
    }

    @PostMapping("/add")
    @Secured(ConstantAuthor.Group.add)
    public String groupAddSave(Model model, @Valid GroupView item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        if (item.getGroupName() == null || item.getGroupName().isEmpty()) {
            model.addAttribute("messageError", "");
            model.addAttribute("item", item);
            List<Authority> items = groupService.loadAllAuthority().orElse(new ArrayList<>());
            if (items == null && items.size() == 0) {
                return "404";
            }
            loadAuthorityToModel(model, items);
            return "group.add";
        }
        try {
            if (!result.hasErrors() && groupService.saveGroupView(item, Utils.getIpClient(request)).orElse(false)) {
                attributes.addFlashAttribute("success", "message.group.add.success");
                return "redirect:/system/group/list";
            }
        } catch (Exception e) {
            logger.error("Have error GroupController.groupAddSave: " + e.getMessage());
        }
        /*Reload when error*/
        List<Authority> items = groupService.loadAllAuthority().orElse(new ArrayList<>());
        if (items == null && items.size() == 0) {
            return "404";
        }
        loadAuthorityToModel(model, items);
        model.addAttribute("messageError", "message.group.message.add.fail!");
        model.addAttribute("item", item);
        return "group.add";

    }

    @GetMapping("/edit/{id}")
    @Secured(ConstantAuthor.Group.edit)
    public String groupEdit(Model model, @PathVariable("id") Long id) {
        if (id == null || id.intValue() == 0) {
            return "404";
        }
        GroupView item = groupService.getGroupView(id).orElse(null);
        if (item == null) {
            return "404";
        }
        List<Authority> items = groupService.loadAllAuthority().orElse(new ArrayList<>());
        if (items == null && items.size() == 0) {
            return "404";
        }

        loadAuthorityToModel(model, items);
        model.addAttribute("item", item);
        return "group.edit";
    }

    @Secured(ConstantAuthor.Group.edit)
    @PostMapping("/edit")
    public String groupEditSave(Model model, @Valid GroupView item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        if (item.getId() == null || item.getId().intValue() == 0) {
            return "404";
        }
        if (item.getGroupName() == null || item.getGroupName().isEmpty()) {
            model.addAttribute("messageError", "message.groupname.not.isempty");
            model.addAttribute("item", item);
            return "group.edit";
        }
        try {
            if (!result.hasErrors() && groupService.editGroupView(item, Utils.getIpClient(request)).orElse(false)) {
                attributes.addFlashAttribute("success", "message.group.edit.success");
                return "redirect:/system/group/list";
            }
        } catch (Exception e) {
            logger.error("Have error GroupController.groupEditSave: " + e.getMessage());
        }
        List<Authority> items = groupService.loadAllAuthority().orElse(new ArrayList<>());
        if (items == null && items.size() == 0) {
            return "404";
        }
        loadAuthorityToModel(model, items);
        model.addAttribute("messageError", "message.group.edit.failed!");
        model.addAttribute("item", item);
        return "group.edit";
    }

    @PostMapping("/delete")
    @Secured(ConstantAuthor.Group.delete)
    public String GroupDelete(Long id, RedirectAttributes attributes, HttpServletRequest request) {
        long check = 0L;
        try {
            String ipClient = Utils.getIpClient(request);
            check = groupService.deleteGroup(id, ipClient).orElse(0L);
        } catch (Exception e) {
            logger.error("have an error UserDelete:" + e.getMessage());
        }
        switch (Integer.parseInt(check +"")) {
            case 0:
                attributes.addFlashAttribute("messageError", "message.have.error");
                return "redirect:/system/group/list";
            case 2:
                attributes.addFlashAttribute("messageError", "message.failure.group");
                return "redirect:/system/group/list";
            case 1:
                attributes.addFlashAttribute("success", "message.group.delete.success");
                return "redirect:/system/group/list";
            default:
                attributes.addFlashAttribute("messageError", "message.have.error");
                return "redirect:/system/group/list";
        }

    }

    @GetMapping("/search-user-by-group-{groupId}")
    @Secured(ConstantAuthor.Group.view)
    public String ViewUserByGroup(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @PathVariable long groupId) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(10);
        if (groupId == 0) {
            return "404";
        }

        page = groupService.pageUserOfGroup(groupId, page).orElse(new PagingResult());

        model.addAttribute("pageViewUser", page);
        Group group = groupService.get(groupId).orElse(null);
        model.addAttribute("groupView", group);
        return "system/group/resultUserByGroup";
    }

    @GetMapping("/search-authority-by-group-{groupId}")
    @Secured(ConstantAuthor.Group.view)
    public String ViewAuthorityGroup(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @PathVariable long groupId) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(10);
        if (groupId == 0) {
            return "404";
        }
        page = groupService.pageAuthorityOfGroup(groupId, page).orElse(new PagingResult());

        model.addAttribute("pageViewAuthority", page);
        Group group = groupService.get(groupId).orElse(null);
        model.addAttribute("groupView", group);
        return "system/group/resultAuthorityByGroup";
    }

}

package com.osp.web.controller;

import com.osp.common.ConstantAuthor;
import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.Utils;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.GroupUser;
import com.osp.model.User;
import com.osp.model.VtArea;
import com.osp.model.view.AuthorityView;
import com.osp.model.view.GroupView;
import com.osp.web.dao.AreaDAO;
import com.osp.web.dao.LogAccessDAO;
import com.osp.web.dao.UserDAO;
import com.osp.web.dao.GroupAuthorityDAO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Admin on 12/26/2017.
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

    private Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    UserDAO useService;
    @Autowired
    GroupAuthorityDAO groupService;
    @Autowired
    LogAccessDAO logAccessService;
    @Autowired
    AreaDAO areaDAO;

    @GetMapping("/list")
    @Secured(ConstantAuthor.User.view)
    public String UserList(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "filterUsername", required = false, defaultValue = "") String filterUsername) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(10);
        page = useService.pageUser(Utils.trim(filterUsername), page).orElse(new PagingResult());
        model.addAttribute("page", page);
        model.addAttribute("filterUsername", filterUsername);
        return "user.list";
    }

    @GetMapping("/search-group-by-user-{userId}")
    @Secured(ConstantAuthor.User.view)
    public String ViewGroupList(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int pageNumber,
            @PathVariable long userId) {
        PagingResult page = new PagingResult();
        page.setPageNumber(pageNumber);
        page.setNumberPerPage(10);
        if (userId == 0) {
            return "404";
        }

//        List<Group> listGroups = groupService.loadAllGroupOfUser(userId).orElse(new ArrayList<>());
//
//        if(listGroups != null && !listGroups.isEmpty()){
//            page.setItems(listGroups);
//            page.setRowCount(listGroups.size());
//        }
        page = groupService.pageGroupByUser(userId, page).orElse(new PagingResult());

        model.addAttribute("pageViewGroup", page);
        User user = useService.get(userId).orElse(null);
        model.addAttribute("userView", user);
        return "system/user/groupByUser";
    }

    @GetMapping("/add")
    @Secured(ConstantAuthor.User.add)
    public String UserAddView(Model model) {
        List<VtArea> list = areaDAO.getAllArea();
        model.addAttribute("listArea", list);
        return "user.add";
    }

    @PostMapping("/add")
    @Secured(ConstantAuthor.User.add)
    public String UserAdd(Model model, @Valid User user, BindingResult result, String confirmPassword, RedirectAttributes attributes, HttpServletRequest request) {
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            model.addAttribute("messageError", "label.user.account.isemty");
            model.addAttribute("user", user);
            return "user.add";
        }
        if (user.getPassword() == null || user.getPassword().length() == 0) {
            model.addAttribute("messageError", "message.user.password.isempty");
            model.addAttribute("user", user);
            return "user.add";
        }
        if (confirmPassword == null || confirmPassword.length() == 0) {
            model.addAttribute("messagePassword", "message.user.password.isempty");
            model.addAttribute("user", user);
            return "user.add";
        }

        if (!confirmPassword.equals(user.getPassword())) {
            model.addAttribute("messagePassword", "message.user.re-passowrd.notcorrect");
            model.addAttribute("user", user);
            return "user.add";
        }

        if (user.getFullName() == null || user.getFullName().length() == 0) {
            model.addAttribute("messageError", "message.user.fullname.isempty");
            model.addAttribute("user", user);
            return "user.add";
        }
        if (!Utils.checkRegex(user.getUsername(), Constants.REGEX_TEXT_USERNAME)) {
            model.addAttribute("messageError", "message.user.not.space.special.char");
            model.addAttribute("user", user);
            return "user.add";
        }

        User userCheck = useService.getByUsername(user.getUsername().trim()).orElse(null);
        if (userCheck != null) {
            model.addAttribute("messageError", "message.account.exist.already");
            model.addAttribute("user", user);
            return "user.add";
        }
        user.setFullName(user.getFullName().trim());
        try {
            String ipClient = Utils.getIpClient(request);
            boolean checkAdd = useService.addUser(user, ipClient).orElse(false);
            if (!checkAdd) {
                model.addAttribute("messageError", "message.have.error");
                model.addAttribute("user", user);
                return "user.add";
            }
        } catch (Exception e) {
            logger.error("Have a error UserController.UserAdd:" + e.getMessage());
            model.addAttribute("messageError", "message.have.error");
            model.addAttribute("user", user);
            return "user.add";
        }

        attributes.addFlashAttribute("success", "message.user.add.completed");
        return "redirect:/system/user/list";
    }

    @GetMapping("/edit/{id}")
    @Secured(ConstantAuthor.User.edit)
    public String UserEditView(Model model, @PathVariable("id") Long id) {
        if (id == null || id == 0) {
            return "404";
        }
        List<VtArea> list = areaDAO.getAllArea();
        model.addAttribute("listArea", list);
        User user = useService.get(id).orElse(new User());
        model.addAttribute("item", user);
        return "user.edit";
    }

    @PostMapping("/edit")
    @Secured(ConstantAuthor.User.edit)
    public String UserEdit(Model model, @Valid User item, BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
        boolean check = false;
        if (item.getFullName() == null || item.getFullName().length() == 0) {
            model.addAttribute("messageError", "message.user.fullname.isempty");
            model.addAttribute("item", item);
            return "user.edit";
        }
        item.setFullName(item.getFullName().trim());

        try {
            String ipClient = Utils.getIpClient(request);
            check = useService.editUserFromView(item, ipClient).orElse(false);
        } catch (Exception e) {
            logger.error("Have an error UserController.UserEdit:" + e.getMessage());

        }
        if (!check) {
            model.addAttribute("messageError", "message.have.error");
            model.addAttribute("item", item);
            return "user.edit";
        }
        attributes.addFlashAttribute("success", "message.user.edit.completed");
        return "redirect:/system/user/list";
    }

//    @PostMapping("/delete")
//    public String UserDelete(Long id, RedirectAttributes attributes,HttpServletRequest request) {
//        boolean check=false;
//        try{
//            String ipClient= Utils.getIpClient(request);
//            check=userService.deleteUser(id,ipClient).orElse(false);
//        }catch (Exception e){
//            logger.error("have an error UserDelete:"+e.getMessage());
//        }
//        if(!check){
//            attributes.addFlashAttribute("messageError","Có lỗi xảy ra. Hãy thử lại sau!");
//            return "redirect:/system/user/list";
//        }
//        attributes.addFlashAttribute("success", "Xóa người dùng thành công!");
//
//        return "redirect:/system/user/list";
//    }
    @GetMapping("/user-group/{id}")
    @Secured(ConstantAuthor.User.author)
    public String userGroup(Model model, @PathVariable("id") Long id) {
        if(id==null) return "404";
        User user=useService.get(id).orElse(null);
        if(user==null) return "404";
        //load all groups
        List<Group> allGroups=groupService.loadAllGroup().orElse(new ArrayList<>());
        //load group of user
        List<Group> listGroups=groupService.loadAllGroupOfUser(id).orElse(new ArrayList<>());
        String groups="";
        if(listGroups.size()>0){
            for(Group item:listGroups){
                groups+=item.getId()+",";
            }
        }
        model.addAttribute("user",user);
        model.addAttribute("groups",groups);
        model.addAttribute("allGroups",allGroups);
        return "user.group";
    }

    @GetMapping("/user-group-view-{id}")
    @Secured(ConstantAuthor.User.author)
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

        loadGroupAuthorityToModel(model, item, items);
        model.addAttribute("item", item);
        return "system/user/userGroupView";
    }

    @PostMapping("user-group")
    @Secured(ConstantAuthor.User.author)
    public String addUserGroup(Model model, Long id, String listGroup, RedirectAttributes attributes) {
        if (id == null) {
            return "404";
        }
        User user = useService.get(id).orElse(null);
        if (user == null) {
            return "404";
        }
        listGroup = Utils.trim(listGroup);
        try {
            if (listGroup.length() > 0) {
                String[] array = listGroup.split(",");
                List<String> stringList = Arrays.stream(array).collect(Collectors.toList());
                if (stringList.size() > 0) {
                    List<GroupUser> items = new ArrayList<>();
                    User userCurrent = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    for (String item : stringList) {
                        items.add(new GroupUser(Long.valueOf(item), id, userCurrent.getUsername(), new Date()));
                    }
                    if (items.size() > 0) {
                        groupService.addListGroupUser(items, id);
                    }
                }
            } else {
                groupService.deleteListGroupOfUser(id);
            }
            attributes.addFlashAttribute("success", "message.user.set.role.success");
            return "redirect:/system/user/list";
        } catch (Exception e) {
            logger.error("Have an error UserController.addUserGroup:" + e.getMessage());
            model.addAttribute("errorMessage", "message.have.error");
            //load all groups
            List<Group> allGroups = groupService.loadAllGroup().orElse(new ArrayList<>());
            model.addAttribute("user", user);
            model.addAttribute("groups", listGroup);
            model.addAttribute("allGroups", allGroups);
        }
        model.addAttribute("errorMessage", "message.have.error");
        return "user.group";
    }

    @GetMapping("/change-my-pass")
    public String changeMyPassView() {
        return "user.change.pass";
    }

    @PostMapping("/change-my-pass")
    public ResponseEntity<Integer> changeMyPass(@RequestParam String passwordCurrent, @RequestParam String passwordNew, HttpServletRequest request,Authentication authentication) {
        //0-dieu kien ko phu hop, 1-oke thanh cong,2-mat khau cu khong dung,3-co loi server khi change
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        passwordCurrent = Utils.trim(passwordCurrent);
        passwordNew = Utils.trim(passwordNew);
        if (StringUtils.isBlank(passwordCurrent) || StringUtils.isBlank(passwordNew)) {
            return new ResponseEntity<Integer>(0, HttpStatus.OK);
        }
        Integer result = 0;
        try {
            String ipClient = Utils.getIpClient(request);
            result = useService.changeMyPass(passwordCurrent, passwordNew, user, ipClient).orElse(3);
            WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
            String remoteIpAddress = webAuthenticationDetails.getRemoteAddress();



        } catch (Exception e) {
            logger.error("Have an error changMyPass:" + e.getMessage());
            return new ResponseEntity<Integer>(3, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    @PostMapping("/khoi-phuc-mat-khau")
    @Secured(ConstantAuthor.User.edit)
    @ResponseBody
    public String restorePassword(HttpServletRequest request) {
        String result = "message.restore.failed";
        try {
            if (request.getParameter("userId") != null) {
                String userId = request.getParameter("userId");
                Long _id = new Long(userId);
                User userRs = useService.get(_id).orElse(null);
                if (userRs != null) {

                    if (!Objects.equals(userRs.getStatus(), 1)) {
                        return "message.restore.pass.failed.by.account!";
                    }
                    String ipClient = Utils.getIpClient(request);
                    if (useService.restorePassword(userRs, ipClient)) {
                        return "ok";
                    }

                } else {
                    return "message.restore.pass.failed.not.found.account";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/active.html", method = RequestMethod.POST)
    @Secured(ConstantAuthor.User.edit)
    public String activeShipper(RedirectAttributes model, Long userId, String active, HttpServletRequest request) {
        String actionName = "Kich hoạt";
        User userEdit = null;
        try {
            userEdit = useService.get(userId).orElse(null);
            if (userEdit == null) {
                return "404";
            }
            if (!"1".equals(active)) {
                actionName = "Khóa";
            }
            if (active == null || (!active.equals("0") && !active.equals("1"))) {
                return "404";
            }

            userEdit.setStatus(new Short(active));
            useService.editUser(userEdit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            logAccessService.addLog(actionName + " người dùng ", Constants.Log.system, Utils.getIpClient(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addFlashAttribute("success", actionName + " thành công !");
        return "redirect:/system/user/list";

    }

    public void loadGroupAuthorityToModel(Model model, GroupView groupView, List<Authority> items) {
        List<String> listAutho = new ArrayList<String>(Arrays.asList(groupView.getListAuthority().split(",")));
        List<AuthorityView> list = new ArrayList<>();
        List<AuthorityView> resultList = new ArrayList<>();
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
                    if (listAutho.contains(authority.getId() + "")) {
                        childrens.add(authority);
                    }
                }
            }
            item.setChildrens(childrens);
        }

        for (AuthorityView item : list) {
            if (!item.getChildrens().isEmpty() || listAutho.contains(item.getParent().getId()+"")) {
                resultList.add(item);
            }
        }

        model.addAttribute("groups", resultList);
    }

}

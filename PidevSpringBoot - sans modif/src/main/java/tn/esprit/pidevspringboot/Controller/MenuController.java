package tn.esprit.pidevspringboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidevspringboot.Entities.CommentRequest;
import tn.esprit.pidevspringboot.Entities.Menu.Comment;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.MenuLike;
import tn.esprit.pidevspringboot.Entities.PlatAssociationRequest;
import tn.esprit.pidevspringboot.Service.IMenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menus")
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PostMapping("/add")
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        return menuService.updateMenu(id, menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }
    @PostMapping("/{menuId}/associatePlats")
    public ResponseEntity<String> associatePlatsToMenu(@PathVariable Long menuId, @RequestBody PlatAssociationRequest request) {
        try {

            menuService.associatePlatsWithMenu(menuId, request.getPlatIds());
            return ResponseEntity.ok("Plats successfully associated with the menu.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error associating plats: " + e.getMessage());
        }
    }
    @GetMapping("/{menuId}/like-status")
    public ResponseEntity<Map<String, Object>> getUserLikeStatus(
            @PathVariable Long menuId,
            @RequestParam Long userId) {

        // Call the service to get the like/dislike status for the user
        Map<String, Object> status = menuService.getUserLikeStatus(menuId, userId);

        return ResponseEntity.ok(status);
    }
    @GetMapping("/menus/confirmed")
    public List<Menu> getConfirmedMenus() {
        return menuService.getConfirmedMenus();
    }
    @PutMapping("/{menuId}/like/{userId}")
    public ResponseEntity<?> toggleLikeDislike(
            @PathVariable Long menuId,
            @PathVariable Long userId,
            @RequestParam String status) {

        // Handle like/dislike toggle
        try {
            menuService.toggleLikeDislike(menuId, userId, status);
            return ResponseEntity.ok("Like/Dislike status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating Like/Dislike status.");
        }
    }

    @PostMapping("/{menuId}/comment/{userId}")
    public Comment commentOnMenu(@PathVariable Long menuId, @PathVariable Long userId, @RequestBody CommentRequest comment) {
        return menuService.addComment(menuId, userId,comment);
    }

    @GetMapping("/{menuId}/with-comments")
    public List<Comment> getCommentByMenuId(@PathVariable Long menuId) {
        // Call service to get Menu with limited comments
        return menuService.getCommentByMenuId(menuId);
    }
    @PutMapping("/{menuId}/comment/{commentId}")
    public Comment updateComment(@PathVariable Long menuId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return menuService.updateComment(menuId, commentId, commentRequest);
    }

    // Endpoint for deleting the comment
    @DeleteMapping("/{menuId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long menuId, @PathVariable Long commentId) {
        menuService.deleteComment(menuId, commentId);
    }
    @GetMapping("/search")
    public List<Menu> searchMenus(@RequestParam(required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            return menuService.getConfirmedMenus(); // Show all confirmed menus
        }
        List<Menu> result = menuService.searchMenus(query);
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }




}
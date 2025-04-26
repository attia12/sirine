package tn.esprit.pidevspringboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidevspringboot.Entities.CommentRequest;
import tn.esprit.pidevspringboot.Entities.Menu.Comment;
import tn.esprit.pidevspringboot.Entities.Menu.MenuLike;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.Plat;
import tn.esprit.pidevspringboot.Entities.User.User;
import tn.esprit.pidevspringboot.Repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRepository menuLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Menu updateMenu(Long id, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(id).orElse(null);
        if (existingMenu != null) {
            existingMenu.setNomMenu(updatedMenu.getNomMenu());
            existingMenu.setDateDebut(updatedMenu.getDateDebut());
            existingMenu.setDateFin(updatedMenu.getDateFin());
            existingMenu.setStatut(updatedMenu.getStatut());
            existingMenu.setConfirme(updatedMenu.getConfirme());
            existingMenu.setNutritionniste(updatedMenu.getNutritionniste());
            existingMenu.setPlats(updatedMenu.getPlats());

            return menuRepository.save(existingMenu);
        }
        return null;
    }

    @Override
    public void associatePlatsWithMenu(Long menuId, List<Long> platIds) {
        System.out.println("this is the menu from thefront"+menuId);
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        // Fetch the plats by their IDs
        List<Plat> plats = platRepository.findAllById(platIds);
        menu.setPlats(plats);

        // Save the menu with the associated plats
        menuRepository.save(menu);
    }

    @Override
    public List<Menu> getConfirmedMenus() {
        return menuRepository.findByConfirme(true);


    }



    @Override
    public Comment addComment(Long menuId, Long userId, CommentRequest comment) {
        // Retrieve the Menu entity by ID
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        // Retrieve the User entity by ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Comment object
        Comment comment1 = new Comment();
        comment1.setText(comment.getText());
        comment1.setMenu(menu);
        comment1.setUser(user);

        // Save the comment to the database
        Comment commentSaved=commentRepository.save(comment1);

        // Optionally, add the comment to the menu's list of comments (if you want to keep it in memory)
        menu.getComments().add(comment1);
        System.out.println("debbuger"+menu.getComments());

        // Optionally, you can update the menu's comments count or other attributes if needed
        menuRepository.save(menu); // Save the menu to persist the chang

        return commentSaved;


    }

    @Override
    public void toggleLikeDislike(Long menuId, Long userId, String status) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Determine like/dislike status
        MenuLike.LikeStatus likeStatus = status.equalsIgnoreCase("LIKE") ? MenuLike.LikeStatus.LIKE : MenuLike.LikeStatus.DISLIKE;

        // Check if the user has already liked or disliked the menu
        MenuLike existingMenuLike = menuLikeRepository.findByMenuAndUser(menu, user);

        if (existingMenuLike != null) {
            // If already liked or disliked, toggle the status
            if (existingMenuLike.getStatus() == likeStatus) {
                // If the same status, remove the like/dislike
                menuLikeRepository.delete(existingMenuLike);
            } else {
                // If the status is different, update the status
                existingMenuLike.setStatus(likeStatus);
                menuLikeRepository.save(existingMenuLike);
            }
        } else {
            // If no like/dislike exists, create a new MenuLike
            MenuLike menuLike = new MenuLike(menu, user, likeStatus);
            menuLikeRepository.save(menuLike);
        }

        // Update the likes count in the Menu entity
        menu.updateLikesCount();
        menuRepository.save(menu); //
    }

    @Override
    public Map<String, Object> getUserLikeStatus(Long menuId, Long userId) {
        Map<String, Object> status = new HashMap<>();

        // Check if the user has liked or disliked the menu
        MenuLike likeDislike = menuLikeRepository.findByMenu_IdMenuAndUser_IdUser(menuId, userId);

        if (likeDislike != null) {
            // User has interacted with the menu (like or dislike)
            status.put("liked", likeDislike.isLiked());
            status.put("disliked", likeDislike.isDisliked());
        } else {
            // Default state if no like/dislike action is recorded
            status.put("liked", false);
            status.put("disliked", false);
        }

        // Count the number of likes and dislikes for the menu
        status.put("likesCount", menuLikeRepository.countByMenu_IdMenuAndStatus(menuId, MenuLike.LikeStatus.LIKE));
        status.put("dislikesCount", menuLikeRepository.countByMenu_IdMenuAndStatus(menuId, MenuLike.LikeStatus.DISLIKE));

        return status;
    }

    @Override
    public List<Comment> getCommentByMenuId(Long menuId) {
        return commentRepository.findByMenu_IdMenu(menuId);
    }

    @Override
    public Comment updateComment(Long menuId, Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setText(commentRequest.getText()); // Update the comment text
        return commentRepository.save(comment); // Save the updated comment
    }

    @Override
    public void deleteComment(Long menuId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.delete(comment); // Del
    }



    @Override
    public List<Menu> searchMenus(String query) {
        return menuRepository.findByNomMenuContainingIgnoreCaseAndConfirmeTrue(query);
    }


}

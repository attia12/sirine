package tn.esprit.pidevspringboot.Service;

import tn.esprit.pidevspringboot.Entities.CommentRequest;
import tn.esprit.pidevspringboot.Entities.Menu.Comment;
import tn.esprit.pidevspringboot.Entities.Menu.Menu;
import tn.esprit.pidevspringboot.Entities.Menu.MenuLike;

import java.util.List;
import java.util.Map;

public interface IMenuService {
    List<Menu> getAllMenus();
    Menu getMenuById(Long id);
    Menu createMenu(Menu menu);
    void deleteMenu(Long id);
    Menu updateMenu(Long id, Menu menu);

    void associatePlatsWithMenu(Long menuId, List<Long> platIds);

    List<Menu> getConfirmedMenus();



    Comment addComment(Long menuId, Long userId, CommentRequest commentText);




    void toggleLikeDislike(Long menuId, Long userId, String status);

    Map<String, Object> getUserLikeStatus(Long menuId, Long userId);

    List<Comment> getCommentByMenuId(Long menuId);

    Comment updateComment(Long menuId, Long commentId, CommentRequest commentRequest);

    void deleteComment(Long menuId, Long commentId);



    List<Menu> searchMenus(String query);
}

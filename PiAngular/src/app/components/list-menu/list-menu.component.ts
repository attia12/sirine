import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MenuService} from "../../backoff/pages/menu/menu.service";
import {AnimationOptions} from "ngx-lottie";

@Component({
  selector: 'app-list-menu',
  templateUrl: './list-menu.component.html',
  styleUrls: ['./list-menu.component.css']
})
export class ListMenuComponent implements OnInit{
  menus: any[] = [];
  displayedMenus: any[] = [];
  menuCount = 3; // Number of menus to display initially
  visiblePlatCount: { [key: number]: number } = {}; // To track visible plats for each menu
  currentUserId=1;
  favoriteMenuId: number | null = null;

  constructor(private menuService: MenuService, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {

    // this.menuService.getConfirmedMenus().subscribe(data => {
    //   this.menus = data;
    //   this.menus.forEach(menu => {
    //     // Fetch comments for each menu using the new API
    //     this.menuService.getCommentsByMenuId(menu.idMenu).subscribe(comments => {
    //       menu.comments = comments; // Store the fetched comments in the menu object
    //       // Initialize the visibleComments with the first 3 comments
    //       menu.visibleComments = comments
    //     });
    //
    //     this.menuService.getUserLikeStatus(menu.idMenu, 1).subscribe((status: any) => {
    //       menu.liked = status.liked;
    //       menu.disliked = status.disliked;
    //       menu.likesCount = status.likesCount;
    //       menu.dislikesCount = status.dislikesCount;
    //     });
    //
    //     menu.showCommentBox = false; // Comment input box is initially hidden
    //   });
    //
    //   this.loadMoreMenus(); // Load the first set of menus
    // });
    this.menuService.getConfirmedMenus().subscribe(data => {
      this.menus = data;
      this.initializeMenus(this.menus);
      this.loadMoreMenus(); // Load the first set of menus
    });
  }

  // Function to load more menus (3 at a time)
  loadMoreMenus(): void {
    const nextMenus = this.menus.slice(this.displayedMenus.length, this.displayedMenus.length + this.menuCount);
    this.displayedMenus.push(...nextMenus);

    // Initialize the visible plat count for each menu
    nextMenus.forEach(menu => {
      this.visiblePlatCount[menu.idMenu] = 1; // Initially show 1 plat per menu
    });
  }

  // Function to load more plats for a given menu
  loadMorePlats(menuId: number): void {
    if (this.visiblePlatCount[menuId] < this.menus.find(menu => menu.idMenu === menuId)?.plats.length) {
      this.visiblePlatCount[menuId] += 1; // Increment plat count for the menu
    }
  }

  // Function to get visible plats for each menu
  getVisiblePlats(menu: any): any[] {
    return menu.plats.slice(0, this.visiblePlatCount[menu.idMenu]);
  }



  toggleCommentBox(menuId: number): void {
    const menu = this.menus.find(m => m.idMenu === menuId);
    if (menu) {
      menu.showCommentBox = !menu.showCommentBox;
    }
  }


  editingComment: any = null; // Store the comment being edited
  currentCommentId: number = 0; // Track the comment id for editing
  toggleLikeDislike(menuId: number, userId: number): void {
    const menu = this.menus.find(m => m.idMenu === menuId);

    // Log the current state for debugging
    console.log('Before toggle:', menu);

    if (menu) {
      if (menu.liked) {
        // If already liked, remove the like and set disliked
        console.log('Changing from liked to disliked');
        menu.likesCount--;
        menu.liked = false;
        menu.dislikesCount++;
        menu.disliked = true;
      } else if (menu.disliked) {
        // If already disliked, change to like
        console.log('Changing from disliked to liked');
        menu.dislikesCount--;
        menu.disliked = false;
        menu.likesCount++;
        menu.liked = true;
      } else {
        // If neither liked nor disliked, like it
        console.log('Setting to liked');
        menu.likesCount++;
        menu.liked = true;
      }

      // Log updated state
      console.log('After toggle:', menu);

      // Call the service to update the status in the backend
      this.menuService.toggleLikeDislike(menuId, userId, menu.liked ? 'LIKE' : 'DISLIKE').subscribe(() => {
        // After success, update UI based on new status
      });
    }
  }

  toggleComments(menuId: number): void {
    const menu = this.menus.find(m => m.idMenu === menuId);
    if (menu) {
      menu.showComments = !menu.showComments;
    }
  }






  addComment(menuId: number, userId: number, commentText: string): void {
    console.log("commentText", commentText);
    const menu = this.menus.find(m => m.idMenu === menuId);
    if (menu && commentText) {
      this.menuService.addComment(menuId, userId, commentText).subscribe(comment => {
        console.log("Comment added:", comment);

        // Push the new comment to the menu's comments array
        menu.comments.push(comment);

        // Update visibleComments if necessary (e.g., show the first 3 comments)
        menu.visibleComments = menu.comments;

        // Clear the input field
        menu.newComment = '';

        // Trigger change detection to ensure the UI is updated
        this.cdr.detectChanges();

        // Optionally, show the comments if it's not already visible
        menu.showComments = true;
      });
    }
  }



  toggleCommentMenu(comment: any) {
    comment.showMenu = !comment.showMenu;

  }

  editComment(comment: any): void {
    this.editingComment = { ...comment }; // Copy comment to avoid direct mutation
    this.currentCommentId = comment.id;
  }

  saveEditedComment(): void {
    if (this.editingComment) {
      this.menuService.updateComment(this.editingComment).subscribe((updatedComment) => {
        const menu = this.menus.find((m) => m.idMenu === this.editingComment.menu.idMenu);
        if (menu) {
          const index = menu.comments.findIndex((c:any) => c.id === this.currentCommentId);
          if (index !== -1) {
            menu.comments[index] = updatedComment; // Update the comment in the array
            menu.visibleComments = menu.comments.slice(0, 3); // Update visible comments
            this.editingComment = null; // Reset the edit mode
            this.cdr.detectChanges(); // Trigger UI update
          }
        }
      });
    }
  }

  // Delete a comment
  deleteComment(menuId: number, commentId: number): void {
    this.menuService.deleteComment(menuId, commentId).subscribe(() => {
      console.log('Comment deleted successfully');
      // After successful deletion, remove the comment from the local menu list
      const menu = this.menus.find((m) => m.idMenu === menuId);
      if (menu) {

        menu.visibleComments= menu.comments = menu.comments.filter((c:any) => c.id !== commentId);

      }
    }, (error) => {
      console.error('Error deleting comment:', error);
    });
  }
  searchQuery: string = '';
  searchTimeout: any = null; // For debounce
  lottieOptions: AnimationOptions = {
    path: '/assets/no_data.json',
    loop: true,
    autoplay: true
  };

  onSearchChange(): void {
    clearTimeout(this.searchTimeout);
    this.searchTimeout = setTimeout(() => {
      this.menuService.searchMenus(this.searchQuery).subscribe(data => {
        this.menus = data;
        console.log("search result", data);
        this.displayedMenus = [];

        if (this.menus.length > 0) {
          this.initializeMenus(this.menus); // Only initialize if not empty
          this.loadMoreMenus(); // Load menus normally
        } else {
          // If no menus found, clear everything
          this.visiblePlatCount = {};
        }

        this.cdr.detectChanges(); // force UI update
      });
    }, 300);
  }
  initializeMenus(menus: any[]): void {
    menus.forEach(menu => {
      // Fetch comments
      this.menuService.getCommentsByMenuId(menu.idMenu).subscribe(comments => {
        menu.comments = comments;
        menu.visibleComments = comments; // or slice(0, 3) if you want limited comments
      });

      // Fetch likes/dislikes
      this.menuService.getUserLikeStatus(menu.idMenu, this.currentUserId).subscribe((status: any) => {
        menu.liked = status.liked;
        menu.disliked = status.disliked;
        menu.likesCount = status.likesCount;
        menu.dislikesCount = status.dislikesCount;
      });

      menu.showCommentBox = false; // default
    });
  }

  toggleFavorite(menuId: number): void {
    if (this.favoriteMenuId === menuId) {
      this.favoriteMenuId = null;
      localStorage.removeItem('favoriteMenuId');
    } else {
      this.favoriteMenuId = menuId;
      localStorage.setItem('favoriteMenuId', String(menuId));
    }
  }
}

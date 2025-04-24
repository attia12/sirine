import {Component, OnInit, ViewChild} from '@angular/core';
import {MenuService} from "./menu.service";
import Swal from 'sweetalert2';
import {PlatService} from "../plat/service/plat.service";
declare var bootstrap: any;

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit{
  @ViewChild('newMenuModal', { static: false }) newMenuModal: any;  // Access the modal using ViewChild

  menus: any[] = [];
  newMenu: any = {
    nomMenu: '',
    dateDebut: '',
    dateFin: '',
    statut: 'VALIDE',
    confirme: true
  };

  isEditMode: boolean = false;
  currentMenuId: number = 0;

  constructor(private menuService: MenuService,private platService: PlatService) {}

  ngOnInit(): void {
    this.loadMenus();
    this.loadPlats();
  }

  // Load all menus from the backend
  loadMenus(): void {
    this.menuService.getAllMenus().subscribe(
      data => this.menus = data,
      error => console.error(error)
    );
  }

  // Open modal in create mode using Bootstrap JavaScript API
  openCreateMode(): void {
    console.log('openCreateMode triggered');
    this.isEditMode = false;
    this.resetForm();
    const modalElement = this.newMenuModal.nativeElement;  // Get the modal DOM element
    const modal = new bootstrap.Modal(modalElement);  // Create a Bootstrap modal instance
    modal.show();  // Open the modal
  }


  // Open modal in edit mode using Bootstrap JavaScript API
  openEditMode(menu: any): void {
    console.log('openEditMode triggered for menu', menu);  // Debugging line
    this.isEditMode = true;
    this.currentMenuId = menu.idMenu;
    this.newMenu = { ...menu };  // Pre-fill the form with menu data
    const modalElement = this.newMenuModal.nativeElement;  // Get the modal DOM element
    const modal = new bootstrap.Modal(modalElement);  // Create a Bootstrap modal instance
    modal.show();  // Open the modal
  }

  // Add a new menu
  addNewMenu(): void {
    this.menuService.createMenu(this.newMenu).subscribe(
      response => {
        this.menus.push(response);
        this.resetForm();
        this.closeModal();  // Close the modal after successful save
      },
      error => console.error(error)
    );
  }

  // Update an existing menu
  updateMenu(): void {
    this.menuService.updateMenu(this.currentMenuId, this.newMenu).subscribe(
      response => {
        const index = this.menus.findIndex(menu => menu.idMenu === this.currentMenuId);
        if (index !== -1) {
          this.menus[index] = response;  // Update the menu in the list
        }
        this.resetForm();
        this.closeModal();  // Close the modal after successful update
      },
      error => console.error(error)
    );
  }

  // Delete a menu
  deleteMenu(menuId: number): void {
    // Display SweetAlert2 confirmation dialog
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this menu!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        // Proceed with the deletion if confirmed
        this.menuService.deleteMenu(menuId).subscribe(
          () => {
            // Successfully deleted, remove menu from the list
            this.menus = this.menus.filter(menu => menu.idMenu !== menuId);
            Swal.fire('Deleted!', 'Your menu has been deleted.', 'success');  // Success alert
          },
          error => {
            // Handle error
            console.error(error);
            Swal.fire('Error!', 'There was an issue deleting the menu.', 'error');  // Error alert
          }
        );
      }
    });
  }

  // Reset the form fields
  selectedPlats: any[]=[];
  allPlats: any[]=[];

  selectedPlatId: any;
  resetForm(): void {
    this.newMenu = {
      nomMenu: '',
      dateDebut: '',
      dateFin: '',
      statut: 'VALIDE',
      confirme: true
    };
  }


  closeModal(): void {
    const modalElement = this.newMenuModal.nativeElement;
    const modalInstance = bootstrap.Modal.getInstance(modalElement);  // Get the modal instance
    modalInstance.hide();  // Close the modal

    // Manually remove the backdrop if it's still there
    const backdropElement = document.querySelector('.modal-backdrop');
    if (backdropElement) {
      backdropElement.remove();  // Remove the backdrop manually
    }
  }

  associatePlat(menuId: number): void {

  }

  removeSelectedPlat(index: number) {
    this.selectedPlats.splice(index, 1);

  }

  private loadPlats() {
    this.platService.getAllPlats().subscribe(
      data => this.allPlats = data,
      error => console.error(error)
    );
  }
  addPlatToSelection(plat: any): void {
    if (!this.selectedPlats.some(selectedPlat => selectedPlat.idPlat === plat.idPlat)) {
      this.selectedPlats.push(plat);
    }
  }
  isPlatSelected(plat:any) {
    return this.selectedPlats.some(p => p.idPlat === plat.idPlat);
  }

  associatePlatsToMenu(menuid:number) {
    console.log("associatePlatsToMenu called with menuid:", menuid);

    if (menuid === 0 || !this.selectedPlats.length) {
      console.error("Invalid menuId or no plats selected.");
      return;
    }

    const platIds = this.selectedPlats.map(plat => plat.idPlat);  // Extract plat IDs


    this.menuService.associatePlatsToMenu(menuid, platIds).subscribe(
      response => {
        console.log('Plats successfully associated with the menu!', response);

      },
      error => {
        console.error('Error associating plats:', error);
      }
    );
  }
}
